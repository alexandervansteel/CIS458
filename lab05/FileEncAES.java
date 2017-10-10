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

public class FileEncAES {
  public static void encrypt(String key, String file, String encrypted) {
    FileInputStream fis;
    FileOutputStream fos;
    CipherOutputStream cos;
    try {
      SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
      KeySpec spec = new PBEKeySpec(key.toCharArray());
      SecretKey tmp = factory.generateSecret(spec);
      SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

      Cipher cipher = Cipher.getInstance("AES128_CBC");
      fis = new FileInputStream(file);
      fos = new FileOutputStream(encrypted);

      cipher.init(Cipher.ENCRYPT_MODE, secret);
      cos = new CipherOutputStream(fos, cipher);

      byte[] block = new byte[8];
      int i;
      while ((i = fis.read(block)) != -1) {
        cos.write(block, 0 , i);
      }

      fis.close();
      fos.close();
      cos.close();

    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static void main(String[] args) {
    FileEncAES encryptor = new FileEncAES();
    String key = args[0];
    String file = args[1];
    String encrypted = args[2];

    encryptor.encrypt(key, file, encrypted);
  }

}
