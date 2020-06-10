package com.example.mallsystem.Public.Token;


import com.example.mallsystem.Manager.Account.DAO.User;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class JwtToken {
    private static Logger logger = LoggerFactory.getLogger(JwtToken.class);

    /** 过期时间(秒) */
//    @Value("${jwt.expire}")
    private final long expire = 300000000;
    @Getter
    private final String userKey = "user_info";

    private static final String SECRET = "cuAihCz53DZRjZwbsGcZJ2Ai6At+T142uphtJMsk7iQ=";

    private Map<String, Object> headerParam() {
        Map<String, Object> map = new HashMap<>();
        map.put("typ", "JWT");
        map.put("alg", "HS256");
        return map;
    }

    /**
     * 生成jwt token
     */
    public String generateToken(User user) {
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);
        Map<String, Object> claims = new HashMap<>();
        Gson gson = new Gson();
        claims.put(userKey, gson.toJson(user));
        return Jwts.builder()
                .setHeaderParams(headerParam())
                .setClaims(claims)
                .setSubject("UserInfo")
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(getSecretKey())
                .compact();
    }

    public Claims getClaimByToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
//        String[] header = token.split(" ");
//        token = header[1];
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            logger.debug("validate is token error ", e);
            return null;
        }
    }

    /**
     * token是否过期
     * @return  true：过期
     */
    public static boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }

    /**
     * SecretKey 根据 SECRET 的编码方式解码后得到：
     * Base64 编码：SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));
     * Base64URL 编码：SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretString));
     * 未编码：SecretKey key = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
     * */
    private SecretKey getSecretKey() {
        byte[] encodeKey = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(encodeKey);
    }

    // Getter && Setter
}