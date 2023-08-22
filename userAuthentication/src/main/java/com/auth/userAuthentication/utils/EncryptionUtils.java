package com.auth.userAuthentication.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.util.Base64;

@Component
public class EncryptionUtils {

    @Value("${AES_ALGORITHM}")

    private String AES_ALGORITHM;
    @Value("${ENCRYPTION_KEY}")
    private String ENCRYPTION_KEY;

    public EncryptionUtils() {
        Security.addProvider(new BouncyCastleProvider ());
    }

    public String encrypt(String plainText) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(ENCRYPTION_KEY.getBytes(StandardCharsets.UTF_8), AES_ALGORITHM);
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);

            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            byte[] encodedBytes = Base64.getEncoder().encode(encryptedBytes);

            return new String(encodedBytes, StandardCharsets.UTF_8);
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }
    public String decrypt(String encryptedText) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(ENCRYPTION_KEY.getBytes(StandardCharsets.UTF_8), AES_ALGORITHM);
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);

            byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);

            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }
}
