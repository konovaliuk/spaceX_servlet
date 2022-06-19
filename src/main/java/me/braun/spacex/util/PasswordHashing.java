package me.braun.spacex.util;

import me.braun.spacex.util.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class PasswordHashing {
    private static final String BUNDLE = "protection";
    private static final ResourceBundle resource = ResourceBundle.getBundle(BUNDLE);
    private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

    private static final String ITER_COUNT_KEY = "ITER_COUNT";
    private static final String SALT_BYTES_KEY = "SALT_BYTES";
    private static final String HASH_BYTES_KEY = "HASH_BYTES";

    public static final int ITERATION_INDEX = 0;
    public static final int SALT_INDEX = 1;
    public static final int HASH_INDEX = 2;

    private static final Logger log = LoggerFactory.getLogger(PasswordHashing.class);
    private static final String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";


    public static String createHash(String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        return createHash(password.toCharArray());
    }

    public static String createHash(char[] password)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[Integer.parseInt(resource.getString(SALT_BYTES_KEY))];
        random.nextBytes(salt);

        // Hash the password
        byte[] hash = pbkdf2(password, salt,
                Integer.parseInt(resource.getString(ITER_COUNT_KEY)),
                Integer.parseInt(resource.getString(HASH_BYTES_KEY)));
        // format iterations:salt:hash

        return resource.getString(ITER_COUNT_KEY) + ":" + toHex(salt) + ":" +  toHex(hash);
    }

    public static Boolean validatePassword(String password, String goodHash)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        return validatePassword(password.toCharArray(), goodHash);
    }

    private static Boolean validatePassword(char[] password, String goodHash)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        // Decode the hash into its parameters
        String[] params = goodHash.split(":");
        int iterations = Integer.parseInt(params[ITERATION_INDEX]);
        byte[] salt = fromHex(params[SALT_INDEX]);
        byte[] hash = fromHex(params[HASH_INDEX]);
        // Compute the hash of the provided password, using the same salt,
        // iteration count, and hash length
        byte[] testHash = pbkdf2(password, salt, iterations, hash.length);
        // Compare the hashes in constant time. The password is correct if
        // both hashes match.
        return slowEquals(hash, testHash);
    }

    private static Boolean slowEquals(byte[] a, byte[] b)
    {
        int diff = a.length ^ b.length;
        for(int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
        return skf.generateSecret(spec).getEncoded();
    }

    private static byte[] fromHex(String hex)
    {
        byte[] binary = new byte[hex.length() / 2];
        for(int i = 0; i < binary.length; i++)
        {
            binary[i] = (byte)Integer.parseInt(hex.substring(2*i, 2*i+2), 16);
        }
        return binary;
    }

    private static String toHex(byte[] array)
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
            return String.format("%0" + paddingLength + "d", 0) + hex;
        else
            return hex;
    }

    public static void checkPasswordValid(String password) throws ServiceException {
        if(!Pattern.compile(passwordRegex).matcher(password).matches()){
            throw new ServiceException("    Password isn't valid. " +
                    "Password must consist of upper case, " +
                    "lover case letters, numbers and symbols.");
        }
    }
}
