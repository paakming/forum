package com.wbm.forum.job;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wbm.forum.entity.Like;
import com.wbm.forum.entity.Post;
import com.wbm.forum.service.LikeService;
import com.wbm.forum.service.PostService;
import com.wbm.forum.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Set;

/**
 * @Author：Ming
 * @Date: 2023/3/15 0:05
 */
@Component
public class UpdatePostLikes {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private PostService postService;
    @Autowired
    private LikeService likeService;

    @Scheduled(cron = "0/5 * * * * ?")
    public void updateLikes(){
        List<Post> list = postService.list();
        list.forEach(post -> {
            Set<Object> objects = redisUtils.sGet("postLikes" + post.getPid());
            if (objects.size()>0){
                for (Object o : objects) {
                    Like like = new Like();
                    like.setPid(post.getPid()).setUid((Integer) o);
                    LambdaQueryWrapper<Like> wrapper = new LambdaQueryWrapper<>();
                    wrapper.eq(Like::getPid,like.getPid()).eq(Like::getUid,like.getUid());
                    Like one = likeService.getOne(wrapper);
                    if (ObjectUtil.isNull(one)){
                        likeService.save(like);
                    }
                }
            }
        });
    }
}
