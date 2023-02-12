package com.wbm.forum.controller;


import com.wbm.forum.common.Result;
import com.wbm.forum.common.ResultCode;
import com.wbm.forum.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.websocket.server.PathParam;
import java.util.Random;

/**
 * @Author：Ming
 * @Date: 2022/11/10 16:47
 */
@RestController
public class EmailController {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String from;

    @GetMapping(value = "/email")
    public Result getEmail(@PathParam("emailReceiver") String emailReceiver){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(emailReceiver);
        mailMessage.setSubject("luntan|验证码");
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0;i<6;i++){
            int r = random.nextInt(10);
            code.append(r);
        }
        String text = "【Test】您的验证码为："+code+"，请勿泄露给他人，有效期为10分钟。";
        mailMessage.setText(text);
        redisUtils.set(emailReceiver,code.toString(),60*30L);
        javaMailSender.send(mailMessage);
        return Result.success(ResultCode.CODE_200,null,null);
    }

}
