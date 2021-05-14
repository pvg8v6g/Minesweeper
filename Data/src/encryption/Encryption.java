package encryption;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * This file has been created by:
 * pvaughn on
 * 5/12/2021 at
 * 16:13
 */
public class Encryption {

    // region Encrypt

    public static void encryptData(Serializable serializable, OutputStream outputStream) {
        try {
            encrypt(serializable, outputStream);
        } catch (InvalidKeyException | IOException | NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }

    private static void encrypt(Serializable object, OutputStream ostream) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException {
        var sks = new SecretKeySpec(key, "AES");

        var cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.ENCRYPT_MODE, sks);
        var sealedObject = new SealedObject(object, cipher);

        var cos = new CipherOutputStream(ostream, cipher);
        var outputStream = new ObjectOutputStream(cos);
        outputStream.writeObject(sealedObject);
        outputStream.close();
    }

    // endregion

    // region Decrypt

    public static Serializable decryptData(InputStream inputStream) {
        try {
            return (Serializable) decrypt(inputStream);
        } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Object decrypt(InputStream istream) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, ClassNotFoundException {
        var sks = new SecretKeySpec(key, "AES");
        var cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.DECRYPT_MODE, sks);

        var cipherInputStream = new CipherInputStream(istream, cipher);
        var inputStream = new ObjectInputStream(cipherInputStream);
        var sealedObject = (SealedObject) inputStream.readObject();
        inputStream.close();
        return sealedObject.getObject(cipher);
    }

    // endregion

    // region Fields

    private static final byte[] key = "rqI$5;9%C@_U*+LE".getBytes();
    private static final String transformation = "AES/ECB/PKCS5Padding";

    // endregion

}
