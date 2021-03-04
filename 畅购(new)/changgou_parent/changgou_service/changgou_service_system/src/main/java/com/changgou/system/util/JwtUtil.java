package com.changgou.system.util;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

public class JwtUtil {
    // 有效期
    public static final Long JWT_TTL = 360000L;  // 60*60*1000 一小时
    //设置秘钥明文
    public static final String JWT_KEY = "itcast";

    /*
        创建token
     */
    public static String createJMT(String id, String subject, Long ttMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (ttMillis == null) {
            ttMillis = JwtUtil.JWT_TTL;
        }
        long expMillis = nowMillis + ttMillis;
        Date expDate = new Date(expMillis);
        SecretKey secretKey = generalKey();

        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setSubject(subject)
                .setIssuer("admin")
                .setIssuedAt(now)
                .signWith(signatureAlgorithm, secretKey)
                .setExpiration(expDate);
        return builder.compact();
    }

    // 生成加密后的秘钥secretKey
    public static SecretKey generalKey() {
        byte[] encodeedKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        SecretKey key = new SecretKeySpec(encodeedKey, 0, encodeedKey.length, "AES");
        return key;
    }
}
