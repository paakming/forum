package com.wbm.forum;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wbm.forum.entity.*;
import com.wbm.forum.mapper.CommentMapper;
import com.wbm.forum.mapper.PostMapper;
import com.wbm.forum.mapper.UserMapper;
import com.wbm.forum.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
class ForumApplicationTests {
    private final RedisUtils redisUtils;
    @Autowired
    ForumApplicationTests(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private CommentMapper commentMapper;

    @Test
    void contextLoads() {
    }

    @Test
    public void floor(){
        List<User> users = userMapper.selectList(null);
        users.forEach(user -> redisUtils.set("user"+user.getUid(),JSON.toJSONString(user)));
    }
    @Test
    public void test(){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        userMapper.selectList(queryWrapper).forEach(System.out::println);
    }

    @Test
    public void test2(){
        String pwd = "$2a$10$GG3Xro1Jrs71FsLFsHxI7eGFRIqUEiE7uFLcF5rrD3YFIWB6gN/Ua";
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean encode = encoder.matches("222",pwd);
        System.out.println(encode);
    }
    @Test
    public void test3(){
        String key = "h";
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Post::getTitle,key)
                .or()
                .like(Post::getContent,key);
        List<Post> postList = postMapper.selectList(wrapper);
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Comment::getContent,key);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        System.out.println(comments);
        System.out.println(postList);
    }
}
