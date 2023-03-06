package com.wbm.forum.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wbm.forum.entity.vo.CommentVO;
import com.wbm.forum.entity.Comment;
import com.wbm.forum.entity.SecurityUser;
import com.wbm.forum.entity.User;
import com.wbm.forum.mapper.CommentMapper;
import com.wbm.forum.mapper.UserMapper;
import com.wbm.forum.service.CommentService;
import com.wbm.forum.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author Ming
* @description 针对表【comment】的数据库操作Service实现
* @createDate 2022-11-12 13:10:05
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public List<CommentVO> getComment(Integer pid) {
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(Comment::getPid,pid);
        List<Comment> commentList = commentMapper.selectList(lambdaQueryWrapper);
        List<CommentVO> commentVOS = commentList.stream().map(config -> Convert.convert(CommentVO.class, config)).collect(Collectors.toList());
        for (Comment comment : commentList) {
            for (CommentVO commentVO : commentVOS) {
                if (!comment.getCid().equals(commentVO.getCid())){
                    continue;
                }
                if (comment.getTargetId() != null ){
                    User user = getUser(comment.getReplyId());
                    commentVO.setTargetName(user.getNickname());
                }
                User user = getUser(comment.getReplyId());
                commentVO.setReplyName(user.getNickname()).setAvatar(user.getAvatar());
            }
        }
        List<CommentVO> rootCommentVOS = commentVOS.stream().filter(commentVO -> commentVO.getTargetName() == null).collect(Collectors.toList());
        for (CommentVO rootCommentVO : rootCommentVOS) {
            rootCommentVO.setSubComment(commentVOS.stream()
                    .filter(commentVO -> rootCommentVO.getCid().equals(commentVO.getSubId()))
                    .collect(Collectors.toList()));
        }
        return rootCommentVOS;
    }

    public User getUser(Integer uid){
        Object userRedis = redisUtils.hget("userMap",uid.toString());
        User user = Convert.convert(User.class, userRedis);
        if (ObjectUtil.isNull(userRedis)){
            user= userMapper.selectById(uid);
        }
        return user;
    }



    @Override
    public Integer doComment(Comment comment) {
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        comment.setReplyId(securityUser.getUser().getUid());
        if (comment.getSubId()==null && comment.getTargetId()==null &&comment.getCid()==null){
            LambdaQueryWrapper<Comment> lambdaQueryWrapper = Wrappers.lambdaQuery();
            lambdaQueryWrapper
                    .select(Comment::getFloor).isNotNull(Comment::getFloor)
                    .orderByDesc(Comment::getFloor).eq(Comment::getPid,comment.getPid());
            List<Comment> comments = commentMapper.selectList(lambdaQueryWrapper);
            if (comments.size()==0){
                comment.setFloor(2);
            }else {
                Integer lastFloor = comments.get(0).getFloor();
                comment.setFloor(lastFloor+1);
            }
        }
        if (comment.getCid()!=null){
            comment.setReplyId(securityUser.getUser().getUid());
            LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Comment::getCid,comment.getCid());
            Integer TargetId = commentMapper.selectOne(queryWrapper).getReplyId();
            comment.setTargetId(TargetId).setSubId(comment.getCid()).setCid(null);
        }
        int insert = commentMapper.insert(comment);
        return insert;
    }

    @Override
    public Integer deleteComment(Integer cid) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getCid,cid);
        Integer pid = commentMapper.selectOne(queryWrapper).getPid();
        return commentMapper.deleteById(cid);
    }

    @Override
    public Integer deleteComments(List<Integer> ids) {
        return commentMapper.deleteBatchIds(ids);
    }

}




