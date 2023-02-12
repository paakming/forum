package com.wbm.forum.controller;

import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.core.lang.UUID;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.wbm.forum.common.Result;
import com.wbm.forum.common.ResultCode;
import com.wbm.forum.utils.RedisUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author：Ming
 * @Date: 2022/11/10 16:52
 */
@RestController
public class VerifyCodeController {

    @Autowired
    private DefaultKaptcha defaultKaptcha;
    @Autowired
    private RedisUtils redisUtils;

    @GetMapping(value = "/verifyCode")
    public Result verifyCode() throws IOException {
        String uuid = UUID.randomUUID().toString();
        String text = defaultKaptcha.createText();
        //存入redis
        redisUtils.set(uuid,text,60*5L);
        BufferedImage bi = defaultKaptcha.createImage(text);
        FastByteArrayOutputStream stream = new FastByteArrayOutputStream();
        ImageIO.write(bi, "jpg", stream);
        Map<String, String> map = new HashMap<>();
        String code = Base64.encodeBase64String(stream.toByteArray());
        map.put("uuid",uuid);
        map.put("verifyCode",code);
        return Result.success(ResultCode.CODE_200,"verifyCode succeed",map);
    }
    @PostMapping(value = "/checkVerify/{verifyCode}/{uuid}")
    public Result check(@PathVariable("verifyCode") String verifyCode,@PathVariable("uuid") String uuid){
        if (uuid == null || verifyCode == null){
            return Result.error(ResultCode.CODE_400,"验证码错误",null);
        }
        String text = redisUtils.get(uuid);
        if (text.equals(verifyCode)){
            redisUtils.delete(uuid);
            return Result.success(ResultCode.CODE_200,null,null);
        }
        return Result.error(ResultCode.CODE_400,"验证码错误",null);
    }
}
