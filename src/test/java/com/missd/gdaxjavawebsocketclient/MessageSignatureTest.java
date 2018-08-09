package com.missd.gdaxjavawebsocketclient;

import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class MessageSignatureTest extends GdaxClientTest {

    @Test
    public void shouldGenerateSignature() throws MessageSignatureGenerationException {
        //given
        long timestamp = Instant.now(Clock.fixed(Instant.EPOCH, ZoneId.of("UTC"))).getEpochSecond();
        String expectedResult = "2SDqozh+oOpnQXkufsS6sMxIzuHjJXNnTL3Wx96ch0I=";

        //when
        String result = MessageSignature.generate("", timestamp, TEST_SECRET);

        //then
        assertThat(result).isEqualTo(expectedResult);
    }
}