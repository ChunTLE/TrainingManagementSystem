package cn.youthol.trainingmanagementsystem.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.Map;

public class JwtUtil {

    private static final String KEY = "pcs";
    private static final ObjectMapper objectMapper = new ObjectMapper();


    //接收业务数据,生成token并返回
    public static String genToken(Map<String, Object> claims) throws JsonProcessingException {
        String claimsJson = objectMapper.writeValueAsString(claims);
        return JWT.create()
                .withClaim("claims", claimsJson)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .sign(Algorithm.HMAC256(KEY));
    }

    //接收token,验证token,并返回业务数据
    public static Map<String, Object> parseToken(String token) throws JsonProcessingException {
        String claimsJson = JWT.require(Algorithm.HMAC256(KEY))
                .build()
                .verify(token)
                .getClaim("claims")
                .asString();
        return objectMapper.readValue(claimsJson, Map.class);
    }

}