package com.wbm.forum.utils;


import java.util.Date;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @Author：Ming
 * @Date: 2022/11/9 22:01
 */
public class JwtUtils {
    //有效期
    public static final long EXPIRE = 1000*60*60*24; //24小时
    private static String signature = "ming";

    public static String getJwtToken(String id){
        String JwtToken =Jwts.builder()
                //JWT头信息
                //.setId()//唯一ID
                //.setIssuer()//签发者
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS2256")

                //设置分类；设置过期时间 一个当前时间，一个加上设置的过期时间常量
                .setSubject("security")//主题
                .setIssuedAt(new Date())//签发时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))

                //设置token主体信息，存储用户信息
                .claim("uid", id)
                .signWith(SignatureAlgorithm.HS256, signature)
                .compact();
        return JwtToken;
    }

    public static Claims parseJwtToken(String token){
        return Jwts.parser().setSigningKey(signature)
                .parseClaimsJws(token).getBody();
    }
}
