package com.redpack.Utils;

import javax.crypto.*;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * AES加密工具类
 */
public class AESUtil {


    public static final String ALGORITHM = "AES";

    public static final String KEY = "garfield";

    /**
     * 生成密钥
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static SecretKey generateKey() throws NoSuchAlgorithmException {
        KeyGenerator secretGenerator = KeyGenerator.getInstance(ALGORITHM);
        SecureRandom secureRandom = new SecureRandom();
        secretGenerator.init(secureRandom);
        //设置加密用的种子，密钥,每次生成出来的密钥都是一样的。
        secureRandom.setSeed(KEY.getBytes());
        SecretKey secretKey = secretGenerator.generateKey();
        return secretKey;
    }

    static Charset charset = Charset.forName("UTF-8");

    /**
     * 加密
     *
     * @param content
     * @param secretKey
     * @return
     */
    public static byte[] encrypt(String content, SecretKey secretKey) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException { // 加密
        return aes(content.getBytes(charset), Cipher.ENCRYPT_MODE, secretKey);
    }

    /**
     * 解密
     *
     * @param contentArray
     * @param secretKey
     * @return
     */
    public static String decrypt(byte[] contentArray, SecretKey secretKey) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException { // 解密
        byte[] result = aes(contentArray, Cipher.DECRYPT_MODE, secretKey);
        return new String(result, charset);
    }

    private static byte[] aes(byte[] contentArray, int mode, SecretKey secretKey)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(mode, secretKey);
        byte[] result = cipher.doFinal(contentArray);
        return result;
    }

//    public static void main(String[] args) {
//        String content = "青年人需要认识到自己所负的责任！";
//        SecretKey secretKey;
//        try {
//            long timeStartEncry = System.currentTimeMillis();
//            // 生成密钥
//            secretKey = generateKey();
//
//            byte[] encryptResult = encrypt(content, secretKey);
//            long timeEndEncry = System.currentTimeMillis();
//            System.out.println("加密后的结果为==" + new String(encryptResult, charset));
//
//            String decryptResult = decrypt(encryptResult, secretKey);
//            System.out.println("解密后的结果==" + decryptResult);
//
//            System.out.println("加密用时：" + (timeEndEncry - timeStartEncry));
//        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
//            e.printStackTrace();
//        }
//    }
}
