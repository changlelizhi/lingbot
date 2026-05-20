package space.changle.lingbot.user;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

/**
 * @author 长乐
 * @date 2026/5/19
 * @time 17:06
 * @description
 */
public class JwtSecretGenerator {

    public static void main(String[] args) throws Exception {
        // 指定 HMAC-SHA256 算法，自动生成 256 位密钥
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
        SecretKey secretKey = keyGen.generateKey();

        // 转换为 Base64 字符串（方便存入配置文件或环境变量）
        String base64Secret = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println("Generated access-secret (Base64): " + base64Secret);
    }
}
