package com.wbm.forum.runner;

import com.alibaba.fastjson.JSON;
import com.wbm.forum.entity.User;
import com.wbm.forum.mapper.UserMapper;
import com.wbm.forum.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author：Ming
 * @Date: 2023/3/3 20:24
 */
@Component
public class UserRunner implements CommandLineRunner {

    @Autowired
    private  UserMapper userMapper;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public void run(String... args) throws Exception {
        List<User> users = userMapper.selectList(null);
        Map<String, Object> userMap = users.stream().collect(Collectors.toMap(user -> user.getUid().toString(), user -> user));
        redisUtils.hmset("userMap",userMap);
    }
}
