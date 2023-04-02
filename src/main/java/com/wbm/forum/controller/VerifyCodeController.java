package com.wbm.forum.controller;

import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.wbm.forum.common.Code;
import com.wbm.forum.common.Result;
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
        redisUtils.set(uuid,text,30L);
        BufferedImage bi = defaultKaptcha.createImage(text);
        FastByteArrayOutputStream stream = new FastByteArrayOutputStream();
        ImageIO.write(bi, "jpg", stream);
        String code = Base64.encodeBase64String(stream.toByteArray());
        Map<String, String> map = new HashMap<>();
        map.put("uuid",uuid);
        map.put("verifyCode",code);
        return Result.success(Code.VERIFY_CODE_SUCCESS.getCode(), Code.VERIFY_CODE_SUCCESS.getMsg(),map);
    }
    @PostMapping(value = "/checkVerify/{verifyCode}/{uuid}")
    public Result check(@PathVariable("verifyCode") String verifyCode,@PathVariable("uuid") String uuid){
        if (uuid == null || verifyCode == null){
            return Result.error(Code.VERIFICATION_FAIL.getCode(), Code.VERIFICATION_FAIL.getMsg());
        }
        String text = redisUtils.strGet(uuid);
        if (StrUtil.isBlank(text)){
            return Result.error(Code.VERIFY_CODE_EXPIRED.getCode(), Code.VERIFY_CODE_EXPIRED.getMsg());
        }
        if (text.equals(verifyCode)){
            redisUtils.delete(uuid);
            return Result.success(Code.VERIFICATION_SUCCESS.getCode(), Code.VERIFICATION_SUCCESS.getMsg());
        }
        return Result.error(Code.VERIFICATION_FAIL.getCode(), Code.VERIFICATION_FAIL.getMsg());
    }
}
