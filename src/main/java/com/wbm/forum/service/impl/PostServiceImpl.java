package com.wbm.forum.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.istack.internal.NotNull;
import com.wbm.forum.common.Code;
import com.wbm.forum.entity.*;
import com.wbm.forum.entity.vo.PostVO;
import com.wbm.forum.exception.MyServiceException;
import com.wbm.forum.mapper.CommentMapper;
import com.wbm.forum.mapper.PostMapper;
import com.wbm.forum.mapper.SubCommentMapper;
import com.wbm.forum.mapper.UserMapper;
import com.wbm.forum.service.PostService;
import com.wbm.forum.utils.BeanCopyUtils;
import com.wbm.forum.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author Ming
* @description 针对表【post】的数据库操作Service实现
* @createDate 2022-11-10 21:47:48
*/
@Service
@Slf4j
public class PostServiceImpl extends ServiceImpl<PostMapper, Post>
    implements PostService {

    @Autowired
    private PostMapper postMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private SubCommentMapper subCommentMapper;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public PostVO getPost(Integer pid) {
        Post post = postMapper.selectById(pid);
        PostVO postVO = new PostVO();
        BeanUtils.copyProperties(post, postVO);
        User user = userMapper.selectById(post.getUid());
        postVO.setAvatar(user.getAvatar()).setNickname(user.getNickname()).setLikes(getLikes(pid));
        Boolean like = isLike(pid);
        if (like){
            postVO.setIsLike("1");
        }
        return postVO;
    }

    @Override
    public Map<String, Object> getPostByUid(Integer uid,Integer pageNum,Integer pageSize) {
        LambdaQueryWrapper<Post> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Post::getUid,uid);
        Page<Post> postPage = postMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper);
        List<PostVO> postVOS = BeanCopyUtils.copyBeanList(postPage.getRecords(), PostVO.class);
        postVOS.forEach(this::setUserInfo);
        int total = (int) postPage.getTotal();
        Map<String,Object> map = new HashMap<>();
        map.put("post", postVOS);
        map.put("total",total);
        return map;
    }

    @Override
    public Map<String, Object> getAllPost(Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Post> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderBy(true,false,Post::getIsTop)
                .orderBy(true,false,Post::getUpdateTime);
        Page<Post> posts = postMapper.selectPage(new Page<>(pageNum,pageSize),queryWrapper);
        List<PostVO> postVOS = BeanCopyUtils.copyBeanList(posts.getRecords(), PostVO.class);
        postVOS.forEach(this::setUserInfo);
        postVOS.forEach(this::setCommentNum);
        int total = (int) posts.getTotal();
        Map<String,Object> map = new HashMap<>();
        map.put("post", postVOS);
        map.put("total",total);
        return map;
    }


    @Override
    public Integer addPost(Post post) {
        if (StrUtil.isBlank(post.getTitle())|| StrUtil.isBlank(post.getType())||StrUtil.isBlank(post.getContent())){
            throw new MyServiceException(Code.ERROR.getCode(),"发帖失败");
        }
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setUid(securityUser.getUser().getUid()).setViews(1L).setLikes(0L);
        return postMapper.insert(post);
    }

    @Override
    public Integer deletePost(Integer pid) {
        return postMapper.deleteById(pid);
    }

    @Override
    public List<PostVO> getPostByTitle(String key) {
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Post::getTitle,key);
        List<Post> postList = postMapper.selectList(wrapper);
        return BeanCopyUtils.copyBeanList(postList, PostVO.class);
    }
    public Integer getLikes(Integer pid){
        Set<Object> objects = redisUtils.setMembers("postLikes" + pid);
        return objects.size();
    }
    public Boolean isLike(Integer pid){
        SecurityUser principal = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<Object> objects = redisUtils.setMembers("postLikes" + pid);
        return objects.contains(principal.getUser().getUid());
    }

    public void setUserInfo(PostVO postVO){
        Object userRedis = redisUtils.hget("userMap", postVO.getUid().toString());
        User user = Convert.convert(User.class, userRedis);
        if (ObjectUtil.isNull(userRedis)){
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getUid, postVO.getUid());
            user = userMapper.selectOne(queryWrapper);
        }
        postVO.setNickname(user.getNickname()).setAvatar(user.getAvatar());
    }

    public void setCommentNum(PostVO postVO) {
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Comment::getPid, postVO.getPid());
        List<Comment> comments = commentMapper.selectList(lambdaQueryWrapper);
        int sub = 0;
        for (Comment comment : comments) {
            LambdaQueryWrapper<SubComment> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SubComment::getRootId,comment.getCid());
            int size = subCommentMapper.selectList(wrapper).size();
            sub +=size;
        }
        int size = comments.size();
        postVO.setCommentNum(size+sub);
    }
}




