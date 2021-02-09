package com.plan.frame.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletException;
import java.util.Date;

/**
 * @Author Huangry
 * @Description: JWT token解析
 * @Date 2019-06-05
 */
public class JwtUtil {
    /**
     * 私钥
     */
    final static String base64EncodedSecretKey = "base64EncodedSecretKey";
    /**
     * 过期时间1天, 1000 * 60测试使用60秒
     */
//    final static long TOKEN_EXP = 1000 * 60 * 60 *24;
            //失效时间1分钟

    public static String getToken(String userName,Long tokenExp) {
        return Jwts.builder()
                //签发人：荷载部分的标准字段之一，代表这个 JWT 的所有者。通常是 username、userid 这样具有用户代表性的内容
                .setSubject(userName)
                .claim("username", userName)
                //签发时间：荷载部分的标准字段之一，代表这个 JWT 的生成时间
                .setIssuedAt(new Date())
                //token过期时间
                .setExpiration(new Date(tokenExp))
                //设置生成签名的算法和秘钥
                .signWith(SignatureAlgorithm.HS256, base64EncodedSecretKey)
                .compact();
    }

    public String decodeToken(String jwtToken) {
        final Claims claims = Jwts.parser().setSigningKey(base64EncodedSecretKey).parseClaimsJws(jwtToken).getBody();
        String username = (String) claims.get("username");
        return username;
    }

    /**
     * 解析token
     */
    public static void checkToken(String token) throws AuthenticationException {
        try {
            final Claims claims = Jwts.parser().setSigningKey(base64EncodedSecretKey).parseClaimsJws(token).getBody();

            System.out.println("从token中解析到的username==" + claims);
            String username = (String) claims.get("username");
            System.out.println("username==" + username);

        } catch (ExpiredJwtException e1) {
            throw new DisabledAccountException("token过期");
        } catch (Exception e) {
            throw new DisabledAccountException("无效的token");
        }
    }
}
