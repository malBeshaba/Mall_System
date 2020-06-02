package com.example.mallsystem.Public.Token;

import com.example.mallsystem.Manager.Account.DAO.User;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.DeserializationException;
import io.jsonwebtoken.io.Deserializer;
import io.jsonwebtoken.lang.Maps;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/token")
public class TokenController {
    /**
     * SECRET 是签名密钥，只生成一次即可，生成方法：
     * Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
     * String secretString = Encoders.BASE64.encode(key.getEncoded()); # 本文使用 BASE64 编码
     * */
    private static final String SECRET = "cuAihCz53DZRjZwbsGcZJ2Ai6At+T142uphtJMsk7iQ=";

    private static final String USER_INFO_KEY = "user_info";

    private static final long TOKEN_EXPIRED_SECOND = 60;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtToken jwtToken;

    @RequestMapping("/get")
    public String  get() {
        User user = new User();
        return jwtToken.generateToken(user);
    }

    @GetMapping("/getUserInfo")
    public String getUserInfo(@RequestHeader("Authorization") String authHeader) throws AuthenticationException {
        // 黑名单token
        List<String> blacklistToken = Arrays.asList("禁止访问的token");
        Claims claims = jwtToken.getClaimByToken(authHeader);
        if (claims == null || JwtToken.isTokenExpired(claims.getExpiration()) || blacklistToken.contains(authHeader)) {
            throw new AuthenticationException("token 不可用");
        }

        String userId = claims.getSubject();
        // 根据用户id获取接口数据返回接口
        return userId;
    }

//    @RequestMapping("/verify")
//    public Object verify() {
//        String token = request.getHeader("Authorization");
//
//        SecretKey secretKey = getSecretKey();
//        Jws<Claims> jws = Jwts.parserBuilder()
//                // 解析 JWT 的服务器与创建 JWT 的服务器的时钟不一定完全同步，此设置允许两台服务器最多有 3 分钟的时差
//                .setAllowedClockSkewSeconds(180L)
//                .setSigningKey(secretKey)
//                // 默认情况下 JJWT 只能解析 String, Date, Long, Integer, Short and Byte 类型，如果需要解析其他类型则需要配置 JacksonDeserializer
//                .deserializeJsonWith(new JacksonDeserializer(Maps.of(USER_INFO_KEY, UserInfo.class).build()))
//                .deserializeJsonWith(new Deserializer<Map<String, ?>>() {
//                    @Override
//                    public Map<String, ?> deserialize(byte[] bytes) throws DeserializationException {
//                        return null;
//                    }
//                })
//                .build().parseClaimsJws(token);
//
//        Claims claims = jws.getBody();
//
//        UserInfo userInfo = claims.get(USER_INFO_KEY, UserInfo.class);
//
//        return userInfo;
//    }

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

    public static void main(String[] args) {
        TokenController controller = new TokenController();
        System.out.println(controller.get());
    }
}