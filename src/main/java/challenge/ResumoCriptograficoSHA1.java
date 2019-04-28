package challenge;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ResumoCriptograficoSHA1 {

    public static String execute(String frase) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA1");
        return new BigInteger(1, digest.digest(frase.getBytes())).toString(16);
    }

}
