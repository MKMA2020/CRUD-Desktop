/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.codec.binary.Base32;

/**
 * Methods to encrypt and decrypt
 *
 * @author Martin Gros
 */
public class Ciphering {
    
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
    

    public String cifrarTexto(String mensaje) {
        byte[] encodedMessage = null;
        Base32 base = new Base32();
        String encodedHexMessage="";
        try {
            // Public Key loaded from a relative path (keys are placed inside the project)
            byte fileKey[] = getPublicFileKey();
            //fileReader("security.PublicKey.properties"); //getClass().getResource("c:\\\\claves\\\\Public.key"); //fileReader(getClass().getResource("Public.key").getFile())
            System.out.println("SIZE -> " + fileKey.length + " bytes");

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(fileKey);
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

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
     * Descifra un texto con RSA, modo ECB y padding PKCS1Padding (asim�trica) y
     * lo retorna
     *
     * @param mensaje El mensaje a descifrar
     * @return El mensaje descifrado
     */
    public byte[] descifrarTexto(byte[] mensaje) {
        byte[] decodedMessage = null;
        Base32 base = new Base32();
        try {
            // Private Key
            byte fileKey[] = fileReader(getClass().getResource("Private.key").getFile());
            //getClass().getResource("c:\\\\claves\\\\Public.key");
            System.out.println("SIZE:--> " + fileKey.length + " bytes");

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec pKCS8EncodedKeySpec = new PKCS8EncodedKeySpec(fileKey);
            PrivateKey privateKey = keyFactory.generatePrivate(pKCS8EncodedKeySpec);

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            decodedMessage = cipher.doFinal(mensaje);
            decodedMessage = base.decode(decodedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decodedMessage;
    }

    /**
     * Retorna el contenido de un fichero
     *
     * @param path Path del fichero
     * @return El texto del fichero
     */
    private byte[] fileReader(String path) {
        byte ret[] = null;
        File file = new File(path);
        try {
            ret = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }
    // Convierte Array de Bytes en hexadecimal
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