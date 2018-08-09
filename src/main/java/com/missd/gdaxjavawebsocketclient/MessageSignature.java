package com.missd.gdaxjavawebsocketclient;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

class MessageSignature {

    private static final String GET = "GET";
    private static final String REQUEST_PATH = "/users/self/verify";
    private static final String HMAC_SHA256_ALGO = "HmacSHA256";

    static String generate(String bodyAsString, long timestamp, String secret) throws MessageSignatureGenerationException{
        try {
            String prehash = String.valueOf(timestamp) + GET + REQUEST_PATH + bodyAsString;
            byte[] secretBytes = Base64.getDecoder().decode(secret);
            Mac hmac = Mac.getInstance(HMAC_SHA256_ALGO);
            SecretKeySpec keySpec = new SecretKeySpec(secretBytes, HMAC_SHA256_ALGO);
            hmac.init(keySpec);
            hmac.update(prehash.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hmac.doFinal());

        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            throw new MessageSignatureGenerationException("Generating signature for message failed", e);
        }
    }
}
