package com.wbm.forum.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wbm.forum.dto.CommentDTO;
import com.wbm.forum.entity.Comment;
import com.wbm.forum.entity.SecurityUser;
import com.wbm.forum.entity.User;
import com.wbm.forum.mapper.CommentMapper;
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
    private RedisUtils redisUtils;
    @Override
    public List<CommentDTO> getComment(Integer pid) {
        String com = redisUtils.get("comment_" + pid);
        if (StrUtil.isNotBlank(com)){
            List<CommentDTO> commentDTOS = JSONUtil.toBean(com, new TypeReference<List<CommentDTO>>() {
            }, true);
            return commentDTOS;
        }
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(Comment::getPid,pid);
        List<Comment> commentList = commentMapper.selectList(lambdaQueryWrapper);
        List<CommentDTO> commentDTOS = commentList.stream().map(config -> Convert.convert(CommentDTO.class, config)).collect(Collectors.toList());
        for (Comment comment : commentList) {
            for (CommentDTO commentDTO : commentDTOS) {
                if (!comment.getCid().equals(commentDTO.getCid())){
                    continue;
                }
                if (comment.getTargetId() != null ){
                    String s = redisUtils.get("user" + comment.getTargetId());
                    User user = JSONUtil.toBean(s, new TypeReference<User>() {}, true);
                    commentDTO.setTargetName(user.getNickname());
                }
                String s = redisUtils.get("user" + comment.getReplyId());
                User user = JSONUtil.toBean(s, new TypeReference<User>() {}, true);
                commentDTO.setReplyName(user.getNickname()).setAvatar(user.getAvatar());
            }
        }
        List<CommentDTO> rootCommentDTOS = commentDTOS.stream().filter(commentDTO -> commentDTO.getTargetName() == null).collect(Collectors.toList());
        for (CommentDTO rootCommentDTO : rootCommentDTOS) {
            rootCommentDTO.setSubComment(commentDTOS.stream()
                    .filter(commentDTO -> rootCommentDTO.getCid().equals(commentDTO.getSubId()))
                    .collect(Collectors.toList()));
        }
        redisUtils.set("comment_"+pid,JSONUtil.toJsonStr(rootCommentDTOS));
        return rootCommentDTOS;
    }

    @Override
    public Integer doComment(Comment comment) {
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        comment.setReplyId(securityUser.getUser().getUid());
        if (comment.getSubId()==null && comment.getTargetId()==null &&comment.getCid()==null){
            LambdaQueryWrapper<Comment> lambdaQueryWrapper = Wrappers.lambdaQuery();
            lambdaQueryWrapper.select(Comment::getFloor).isNotNull(Comment::getFloor).orderByDesc(Comment::getFloor).eq(Comment::getPid,2);
            Integer lastFloor = commentMapper.selectList(lambdaQueryWrapper).get(0).getFloor();
            comment.setFloor(lastFloor+1);
        }
        if (comment.getCid()!=null){
            comment.setReplyId(securityUser.getUser().getUid());
            LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Comment::getCid,comment.getCid());
            Integer TargetId = commentMapper.selectOne(queryWrapper).getReplyId();
            comment.setTargetId(TargetId).setSubId(comment.getCid()).setCid(null);
        }
        return commentMapper.insert(comment);
    }

    @Override
    public Integer deleteComment(Integer cid) {
        return commentMapper.deleteById(cid);
    }

    @Override
    public Integer deleteComments(List<Integer> ids) {
        return commentMapper.deleteBatchIds(ids);
    }

}




