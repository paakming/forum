package com.wbm.forum.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wbm.forum.entity.Comment;
import com.wbm.forum.entity.SecurityUser;
import com.wbm.forum.entity.SubComment;
import com.wbm.forum.entity.User;
import com.wbm.forum.entity.vo.CommentVO;
import com.wbm.forum.mapper.CommentMapper;
import com.wbm.forum.mapper.SubCommentMapper;
import com.wbm.forum.mapper.UserMapper;
import com.wbm.forum.service.CommentService;
import com.wbm.forum.utils.BeanCopyUtils;
import com.wbm.forum.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
* @author Ming
* @description 针对表【comment】的数据库操作Service实现
* @createDate 2022-11-12 13:10:05
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SubCommentMapper subCommentMapper;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public Map<String,Object> getComment(Integer pid, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getPid,pid);
        Page<Comment> commentPage = page(new Page<>(pageNum, pageSize),queryWrapper);
        int total = (int) commentPage.getTotal();
        List<CommentVO> commentVOS = BeanCopyUtils.copyBeanList(commentPage.getRecords(), CommentVO.class);
        for (CommentVO commentVO : commentVOS) {
            User user = getUserInfo(commentVO.getReplyId());
            commentVO.setAvatar(user.getAvatar()).setReplyName(user.getNickname());
            LambdaQueryWrapper<SubComment> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SubComment::getRootId,commentVO.getCid());
            List<SubComment> subComments = subCommentMapper.selectList(wrapper);
            commentVO.setSubCommentNum(subComments.size()).setLikes(getLikes(commentVO.getCid()));
            Boolean like = isLike(commentVO.getCid());
            if (like){
                commentVO.setIsLike("1");
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("comment",commentVOS);
        return map;
    }

    @Override
    public Boolean doComment(Comment comment) {
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper
                .select(Comment::getFloor)
                .orderByDesc(Comment::getFloor).eq(Comment::getPid,comment.getPid());
        List<Comment> comments = list(lambdaQueryWrapper);
        if (comments.size()==0){
            comment.setFloor(2);
        }else {
            Integer lastFloor = comments.get(0).getFloor();
            comment.setFloor(lastFloor+1);
        }
        return  save(comment);
    }

    public User getUserInfo(Integer uid){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUid,uid);
        User user = userMapper.selectOne(wrapper);
        return user;
    }

    public Integer getLikes(Integer cid){
        Set<Object> objects = redisUtils.setMembers("commentLikes" + cid);
        return objects.size();
    }

    public Boolean isLike(Integer cid){
        SecurityUser principal = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<Object> objects = redisUtils.setMembers("commentLikes" + cid);
        return objects.contains(principal.getUser().getUid());
    }
}




