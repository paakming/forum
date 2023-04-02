package com.wbm.forum.job;

import com.wbm.forum.entity.Post;
import com.wbm.forum.service.PostService;
import com.wbm.forum.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author：Ming
 * @Date: 2023/3/14 20:22
 */
@Component
public class UpdatePostViews {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private PostService postService;

    @Scheduled(cron = "0/5 * * * * ?")
    public void updateViews(){
        Map<Object, Object> postViews = redisUtils.hGetAll("postViews");
        List<Post> collect = postViews.entrySet().stream().map(entry -> {
            Post post = new Post();
            post.setPid(Integer.valueOf(entry.getKey().toString()))
                    .setViews(Long.valueOf(entry.getValue().toString()));
            return post;
        }).collect(Collectors.toList());
        postService.updateBatchById(collect);
    }
}
