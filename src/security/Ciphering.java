package security;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/**
 * Methods to encrypt the password for sending it to the server
 *
 * @author Martin Gros and Kerman Rodríguez
 */
public class Ciphering {
    
    /**
     * Reads the public key from the application to cipher the password
     * using it
     * @return a byte array with the public key
     * @throws IOException if there is an error reading the public key
     */
    public byte[] getPublicFileKey() throws IOException {

        InputStream keyfis = Ciphering.class.getClassLoader()
                .getResourceAsStream("security/Public.key");
       
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        // read bytes from the input stream and store them in buffer
        while ((len = keyfis.read(buffer)) != -1) {
            // write bytes from the buffer into output stream
            os.write(buffer, 0, len);
        }
        keyfis.close();
        return os.toByteArray();
    }
    

    /**
     * This  method reads the public key and uses it to cipher the string
     * @param mensaje the message that will be ciphered
     * @return the ciphered string in hexadecimal
     */
    public String cifrarTexto(String mensaje) {
        byte[] encodedMessage = null;
        String encodedHexMessage="";
        try {
            // Public Key loaded from a relative path (keys are placed inside the project)
            byte fileKey[] = getPublicFileKey();
            //fileReader("security.PublicKey.properties"); //getClass().getResource("c:\\\\claves\\\\Public.key"); //fileReader(getClass().getResource("Public.key").getFile())
            System.out.println("SIZE -> " + fileKey.length + " bytes");

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(fileKey);
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            
            //Creates a cipher for the password
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            encodedMessage = cipher.doFinal(mensaje.getBytes());
            //encodedMessage = base.encode(encodedMessage);
            encodedHexMessage = toHexadecimal(encodedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodedHexMessage;
    }

    /**
     * This method gets a byte array and returns a hexadecimal string
     * @param resumen the byte array to change into string
     * @return the hexed password
     */
    private static String toHexadecimal(byte[] resumen) {
        String HEX = "";
        for (int i = 0; i < resumen.length; i++) {
            String h = Integer.toHexString(resumen[i] & 0xFF);
            if (h.length() == 1) {
                HEX += "0";
            }
            HEX += h;
        }
        return HEX.toUpperCase();
    }

}
