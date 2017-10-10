import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Security;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class FileDecAES {
  public static void decrypt(String key, String file, String decrypted) {
    FileInputStream fis;
    FileOutputStream fos;
    CipherInputStream cis;
    try {
      SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
      KeySpec spec = new PBEKeySpec(key.toCharArray());
      SecretKey tmp = factory.generateSecret(spec);
      SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

      Cipher cipher = Cipher.getInstance("AES128_CBC");
      fis = new FileInputStream(file);
      fos = new FileOutputStream(decrypted);

      cipher.init(Cipher.DECRYPT_MODE, secret);
      cis = new CipherInputStream(fis, cipher);

      byte[] block = new byte[8];
      int i;
      while ((i = cis.read(block)) != -1) {
        fos.write(block, 0 , i);
      }

      fis.close();
      fos.close();

      cis.close();

    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static void main(String[] args) {
    FileDecAES decryptor = new FileDecAES();
    String key = args[0];
    String file = args[1];
    String decrypted = args[2];

    decryptor.decrypt(key, file, decrypted);
  }
}
