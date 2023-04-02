package com.wbm.forum.runner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wbm.forum.entity.Comment;
import com.wbm.forum.entity.Like;
import com.wbm.forum.entity.SubComment;
import com.wbm.forum.mapper.CommentMapper;
import com.wbm.forum.mapper.LikeMapper;
import com.wbm.forum.mapper.SubCommentMapper;
import com.wbm.forum.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * @Author：Ming
 * @Date: 2023/3/15 19:40
 */
@Component
public class CommentRunner implements CommandLineRunner {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private SubCommentMapper subCommentMapper;
    @Autowired
    private LikeMapper likeMapper;


    @Override
    public void run(String... args) throws Exception {
        List<Comment> comments = commentMapper.selectList(null);
        comments.forEach(comment -> {
            LambdaQueryWrapper<Like> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Like::getCid,comment.getCid());
            List<Like> likes = likeMapper.selectList(wrapper);
            for (Like like : likes) {
                redisUtils.sSet("commentLikes"+comment.getCid().toString(),like.getUid());
            }
        });

        List<SubComment> subComments = subCommentMapper.selectList(null);
        subComments.forEach(subComment -> {
            LambdaQueryWrapper<Like> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Like::getSubId,subComment.getSubId());
            List<Like> likes = likeMapper.selectList(wrapper);
            for (Like like : likes) {
                redisUtils.sSet("sucCommentLikes"+subComment.getSubId().toString(),like.getUid());
            }
        });
    }
}
