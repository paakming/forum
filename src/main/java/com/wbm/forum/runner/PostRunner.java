package com.wbm.forum.runner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wbm.forum.entity.Like;
import com.wbm.forum.entity.Post;
import com.wbm.forum.mapper.LikeMapper;
import com.wbm.forum.mapper.PostMapper;
import com.wbm.forum.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author：Ming
 * @Date: 2023/3/14 19:41
 */
@Component
public class PostRunner implements CommandLineRunner {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private LikeMapper likeMapper;
    @Override
    public void run(String... args) throws Exception {
        List<Post> postList = postMapper.selectList(null);
        Map<String, Integer> postViews = postList.stream()
                .collect(Collectors.toMap(post -> post.getPid().toString(),post -> post.getViews().intValue()));
        redisUtils.hmsetInt("postViews",postViews);
        postList.forEach(post -> {
            LambdaQueryWrapper<Like> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Like::getPid,post.getPid());
            List<Like> likes = likeMapper.selectList(wrapper);
            for (Like like : likes) {
                redisUtils.sSet("postLikes"+post.getPid().toString(),like.getUid());
            }
        });
    }
}
