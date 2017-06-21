
package az.com.cybernet.zebra.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SAMIR-PC
 */
public class SignUtils {


    public static String signData(String productInfo, String privateKeyStr) {
        try {
            PrivateKey privateKey = getPrivateKey(privateKeyStr);
            byte[] data = productInfo.getBytes("UTF8");
            Signature sig = Signature.getInstance("SHA1WithRSA");
            sig.initSign(privateKey);
            sig.update(data);
            byte[] signatureBytes = sig.sign();
            String signture = Base64.getEncoder().encodeToString(signatureBytes);
            String qrData = productInfo + "," + signture;
//            System.out.println("SIGNED DATA  :"+signture);
            return qrData;
//            sig.initVerify(keyPair.getPublic());
//            sig.update(data);
//
//            System.out.println(" Verify :  " + sig.verify(signatureBytes));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | InvalidKeyException | SignatureException ex) {
            Logger.getLogger(SignUtils.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (Exception ex) {
            Logger.getLogger(SignUtils.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

//    public static boolean verifySignature(Signature signature,String publicKey){
//        byte[] signatureBytes = signature.sign();
//            signature.initVerify(keyPair.getPublic());
//            signature.update(data);
//
//            System.out.println(" Verify :  " + sig.verify(signatureBytes));
//    }
    public static PrivateKey getPrivateKey(String privateKey) throws Exception {
//        StringBuilder pkcs8Lines = new StringBuilder();
//        BufferedReader rdr = new BufferedReader(new StringReader(privateKey));
//        String line;
//        while ((line = rdr.readLine()) != null) {
//            System.out.println(line);
//            pkcs8Lines.append(line);
//        }
//
//        // Remove the "BEGIN" and "END" lines, as well as any whitespace
//        String pkcs8Pem = pkcs8Lines.toString();
        privateKey = privateKey.replace("-----BEGIN PRIVATE KEY-----", "");
        privateKey = privateKey.replace("-----END PRIVATE KEY-----", "");
        privateKey = privateKey.replaceAll("\\s+", "");

//        System.out.println(privateKey);
        // Base64 decode the result
        byte[] pkcs8EncodedBytes = Base64.getDecoder().decode(privateKey);
        
        // extract the private key
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8EncodedBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privKey = kf.generatePrivate(keySpec);
        return privKey;
    }

//    private static KeyPair createKeyPair(byte[] encodedPrivateKey,byte[] encodedPublicKey) {
//        try {
//            EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
//            KeyFactory generator = KeyFactory.getInstance("RSA");
//            PrivateKey privateKey = generator.generatePrivate(privateKeySpec);
//
//            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
//            PublicKey publicKey = generator.generatePublic(publicKeySpec);
//            return new KeyPair(publicKey,privateKey);
//        } catch (Exception e) {
//            throw new IllegalArgumentException("Failed to create KeyPair from provided encoded keys", e);
//        }
//    }
//
//    private static KeyPair getKeyPair() throws NoSuchAlgorithmException {
//        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
//        kpg.initialize(1024);
//        return kpg.genKeyPair();
//    }
}
