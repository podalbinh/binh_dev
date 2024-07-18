package com.example.bookmanagement.utils;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Công cụ hỗ trợ mã hóa và giải mã AES.
 */
@Component
public class AESUtil {
    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 128;
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private static SecretKey secretKey;

    static {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            keyGenerator.init(KEY_SIZE);
            secretKey = keyGenerator.generateKey();
        } 
        catch(NoSuchAlgorithmException e){
            throw new RuntimeException("Error initializing AES key: ", e);
        }
        catch (Exception e) {
            throw new RuntimeException("Error: ", e);
        }
    }

    /**
     * Mã hóa văn bản bằng khóa AES.
     *
     * @param plainText Văn bản cần mã hóa.
     * @param aesKey    Khóa AES dùng để mã hóa.
     * @return Văn bản đã được mã hóa dưới dạng chuỗi Base64.
     * @throws NoSuchPaddingException    Ngoại lệ khi không tìm thấy lề mã hóa.
     * @throws NoSuchAlgorithmException  Ngoại lệ khi không tìm thấy thuật toán mã
     *                                   hóa.
     * @throws InvalidKeyException       Ngoại lệ khi khóa không hợp lệ.
     * @throws BadPaddingException       Ngoại lệ khi lề mã hóa sai.
     * @throws IllegalBlockSizeException Ngoại lệ khi kích thước khối không hợp lệ.
     * @throws IllegalArgumentException  Ngoại lệ khi khóa bí mật là null.
     */
   
    public static String encrypt(String data)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException{
        if(data==null){
            throw new IllegalArgumentException("Data must not be null");
        }
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * Giải mã văn bản đã được mã hóa bằng khóa AES.
     *
     * @param encryptedText Văn bản đã được mã hóa dưới dạng chuỗi Base64.
     * @param aesKey        Khóa AES dùng để giải mã.
     * @return Văn bản đã được giải mã.
     * @throws NoSuchPaddingException    Ngoại lệ khi không tìm thấy lề giải mã.
     * @throws NoSuchAlgorithmException  Ngoại lệ khi không tìm thấy thuật toán giải
     *                                   mã.
     * @throws InvalidKeyException       Ngoại lệ khi khóa không hợp lệ.
     * @throws BadPaddingException       Ngoại lệ khi lề giải mã sai.
     *  @throws IllegalArgumentException  Ngoại lệ khi khóa bí mật là null.
     * @throws IllegalBlockSizeException Ngoại lệ khi kích thước khối không hợp lệ.
     */
    public static  String decrypt(String encryptedData)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException,IllegalArgumentException {
        if(encryptedData==null){
                    throw new IllegalArgumentException("Encrypted Data must not be null");
        }
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }
}
