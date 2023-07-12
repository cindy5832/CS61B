import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class PlatformApi {
    public static void main(String[] args) {
        String random = UUID.randomUUID().toString();
        String token = Encrypt.MD5("DG023002K9" + "ac2c4ed824ba47aeb7bcc92ae3b68202" + random);
        System.out.println("random =" +random);
        System.out.println("token="+ token);
    }



}
    class Encrypt{
        public static String MD5(String sourceStr) {
            String result = "";
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(sourceStr.getBytes("UTF-8"));
                byte b[] = md.digest();
                int i;
                StringBuffer buf = new StringBuffer("");
                for (int offset = 0; offset < b.length; offset++) {
                    i = b[offset];
                    if (i < 0)
                        i += 256;
                    if (i < 16)
                        buf.append("0");
                    buf.append(Integer.toHexString(i));
                }
                result = buf.toString();

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return result;
        }
    }