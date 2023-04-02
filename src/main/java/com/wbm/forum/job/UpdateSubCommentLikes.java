package com.wbm.forum.job;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wbm.forum.entity.Like;
import com.wbm.forum.entity.SubComment;
import com.wbm.forum.service.LikeService;
import com.wbm.forum.service.SubCommentService;
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
public class UpdateSubCommentLikes {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private SubCommentService subCommentService;
    @Autowired
    private LikeService likeService;

    @Scheduled(cron = "0/5 * * * * ?")
    public void updateLikes(){
        List<SubComment> list = subCommentService.list();
        list.forEach(subComment -> {
            Set<Object> objects = redisUtils.sGet("sucCommentLikes" + subComment.getSubId());
            if (objects.size()>0){
                for (Object o : objects) {
                    Like like = new Like();
                    like.setSubId(subComment.getSubId()).setUid((Integer) o);
                    LambdaQueryWrapper<Like> wrapper = new LambdaQueryWrapper<>();
                    wrapper.eq(Like::getSubId,like.getSubId()).eq(Like::getUid,like.getUid());
                    Like one = likeService.getOne(wrapper);
                    if (ObjectUtil.isNull(one)){
                        likeService.save(like);
                    }
                }
            }
        });
    }
}
