package com.missd.gdaxjavawebsocketclient;

class AuthAttributes {
    private String key;
    private String secret;
    private String passphrase;

    public String getKey() {
        return key;
    }

    public AuthAttributes setKey(String key) {
        this.key = key;
        return this;
    }

    public String getSecret() {
        return secret;
    }

    public AuthAttributes setSecret(String secret) {
        this.secret = secret;
        return this;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public AuthAttributes setPassphrase(String passphrase) {
        this.passphrase = passphrase;
        return this;
    }
}
