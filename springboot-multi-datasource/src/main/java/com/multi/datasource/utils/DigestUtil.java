package com.multi.datasource.utils;

import com.ulisesbocchio.jasyptspringboot.encryptor.DefaultLazyEncryptor;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PBEStringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.StandardEnvironment;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * @description: 加解密工具类
 * @author: QL Zhang
 * @time: 2020/08/11 11:46
 **/
public class DigestUtil {
    private static final Logger log = LoggerFactory.getLogger(DigestUtil.class);
    private static final String DEFAULT_ENCODE = "UTF-8";
    private static final String DEFAULT_KEY = "ctg-tsdb1234#!@";

    public static String encryptAes(String sSrc) {
        return encrypt(sSrc, "AES");
    }

    public static String decryptAes(String sSrc) {
        return decrypt(sSrc, "AES");
    }

    public static String encryptDes(String sSrc) {
        return encrypt(sSrc, "DES");
    }

    public static String decryptDes(String sSrc) {
        return decrypt(sSrc, "DES");
    }

    public static String encrypt(String sSrc, String algorithm) {
        return encrypt(sSrc, algorithm, DEFAULT_KEY);
    }

    public static String decrypt(String sSrc, String algorithm) {
        return decrypt(sSrc, algorithm, DEFAULT_KEY);
    }

    public static String encrypt(String sSrc, String algorithm, String key) {
        try {
            Cipher cipher = buildCipher(Cipher.ENCRYPT_MODE, algorithm, key);
            byte[] result = cipher.doFinal(sSrc.getBytes(DEFAULT_ENCODE));
            return Base64.getEncoder().encodeToString(result);
        } catch (Exception e) {
            log.error(algorithm + "加密异常", e);
        }
        return sSrc;
    }

    public static String decrypt(String sSrc, String algorithm, String key) {
        try {
            Cipher cipher = buildCipher(Cipher.DECRYPT_MODE, algorithm, key);
            byte[] result = cipher.doFinal(Base64.getDecoder().decode(sSrc));
            return new String(result, DEFAULT_ENCODE);
        } catch (Exception e) {
            log.error(algorithm + "解密异常", e);
        }
        return sSrc;
    }

    private static Cipher buildCipher(int aesMode, String algorithm, String key) throws InvalidKeyException,
            NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException, InvalidKeySpecException {
        Cipher cipher = null;
        switch (algorithm) {
            case "AES":
                cipher = buildAesCipher(aesMode, key);
                break;
            case "DES":
                cipher = buildDesCipher(aesMode, key);
                break;
            default:
                break;
        }
        return cipher;
    }

    private static Cipher buildAesCipher(int aesMode, String key) throws NoSuchAlgorithmException,
            UnsupportedEncodingException, NoSuchPaddingException, InvalidKeyException {
        //1.生成key
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(key.getBytes(DEFAULT_ENCODE));
        kgen.init(128, random);
        SecretKey secretKey = kgen.generateKey();
        byte[] keyBytes = secretKey.getEncoded();
        //2.key的转换
        Key secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        //3.加密
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(aesMode, secretKeySpec);
        return cipher;
    }

    private static Cipher buildDesCipher(int aesMode, String key) throws InvalidKeyException,
            UnsupportedEncodingException, NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchPaddingException {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key.getBytes(DEFAULT_ENCODE));
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密钥初始化Cipher对象
        cipher.init(aesMode, securekey, sr);
        return cipher;
    }

    /**
     * JASYPT DES算法
     */
    private static final String JASYPT_DEFAULT_KEY = ";_P<70lD4m>*8j)y";
    private static final int JASYPT_POOL_SIZE = 10;

    public static String encryptDesWithPBE(String sSrc) {
        return encryptDesWithPBE(sSrc, JASYPT_DEFAULT_KEY);
    }

    public static String decryptDesWithPBE(String sSrc) {
        return decryptDesWithPBE(sSrc, JASYPT_DEFAULT_KEY);
    }

    public static String encryptDesWithPBE(String sSrc, String key) {
        try {
            StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
            EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
            config.setPassword(key);
            config.setAlgorithm("PBEWithMD5AndDES");
            config.setPoolSize(JASYPT_POOL_SIZE);
            encryptor.setConfig(config);
            encryptor.initialize();
            return encryptor.encrypt(sSrc);
        } catch (Exception e) {
            log.error("JASYPT DES加密异常", e);
        }
        return sSrc;
    }

    public static PBEStringEncryptor buildEncryptor(String key) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(key);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setPoolSize(JASYPT_POOL_SIZE);
        config.setKeyObtentionIterations(1000);
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.salt.NoOpIVGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        encryptor.initialize();
        return encryptor;
    }


    public static String decryptDesWithPBE(String sSrc, String key) {
        try {
            StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
            EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
            config.setPassword(key);
            config.setAlgorithm("PBEWithMD5AndDES");
            encryptor.setConfig(config);
            encryptor.initialize();
            return encryptor.decrypt(sSrc);
        } catch (Exception e) {
            log.error("JASYPT DES解密异常", e);
        }
        return sSrc;
    }

    // 人工加密
    public static void main(String[] args) {
        System.setProperty("jasypt.encryptor.algorithm", "PBEWithMD5AndDES");
        System.setProperty("jasypt.encryptor.pool-size", "10");
        System.setProperty("jasypt.encryptor.password", ";_P<70lD4m>*8j)y");
        StringEncryptor stringEncryptor = new DefaultLazyEncryptor(new StandardEnvironment());
        System.out.println(stringEncryptor.encrypt("*****"));
        System.out.println(stringEncryptor.encrypt("*****"));
    }
}
