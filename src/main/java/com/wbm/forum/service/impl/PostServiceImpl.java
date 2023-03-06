package com.wbm.forum.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.istack.internal.NotNull;
import com.wbm.forum.entity.Comment;
import com.wbm.forum.entity.Post;
import com.wbm.forum.entity.SecurityUser;
import com.wbm.forum.entity.User;
import com.wbm.forum.entity.vo.PostVO;
import com.wbm.forum.mapper.CommentMapper;
import com.wbm.forum.mapper.PostMapper;
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
    private RedisUtils redisUtils;

    @Override
    public PostVO getPost(Integer pid) {
        Post post = postMapper.selectById(pid);
        PostVO postVO = new PostVO();
        BeanUtils.copyProperties(post, postVO);
        User user = userMapper.selectById(post.getUid());
        postVO.setAvatar(user.getAvatar());
        postVO.setNickname(user.getNickname());
        return postVO;
    }

    @Override
    public Map<String, Object> getPostByUid(Integer uid,Integer pageNum,Integer pageSize) {
        LambdaQueryWrapper<Post> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Post::getUid,uid);
        Page<Post> postPage = postMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper);
        List<PostVO> postVOS = BeanCopyUtils.copyBeanList(postPage.getRecords(), PostVO.class);
        postVOS.forEach(postVO -> setUserInfo(postVO));
        int total = (int) postPage.getTotal();
        Map<String,Object> map = new HashMap<>();
        map.put("post", postVOS);
        map.put("total",total);
        return map;
    }

    @Override
    public Map<String, Object> getAllPost(Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Post> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .orderBy(true,false,Post::getIsTop)
                .orderBy(true,false,Post::getUpdateTime);
        Page<Post> posts = postMapper.selectPage(new Page<>(pageNum,pageSize),queryWrapper);
        List<PostVO> postVOS = BeanCopyUtils.copyBeanList(posts.getRecords(), PostVO.class);
        postVOS.forEach(postVO -> setUserInfo(postVO));
        int total = (int) posts.getTotal();
        Map<String,Object> map = new HashMap<>();
        map.put("post", postVOS);
        map.put("total",total);
     //   map.put("top",getTopPost());
        return map;
    }

    @Override
    public List<PostVO> getTopPost() {
            LambdaQueryWrapper<Post> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper
                    .eq(Post::getIsTop,"1")
                    .orderBy(true,false,Post::getUpdateTime);
            List<Post> posts = postMapper.selectList(queryWrapper);
            List<PostVO> topPost = BeanCopyUtils.copyBeanList(posts, PostVO.class);
            topPost.forEach(postVO -> setUserInfo(postVO));
        return topPost;
    }

    @Override
    public Integer addPost(Post post) {
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setUid(securityUser.getUser().getUid());
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
        List<PostVO> postVOS = BeanCopyUtils.copyBeanList(postList, PostVO.class);
        return postVOS;
    }

    public PostVO setUserInfo(@NotNull PostVO postVO){
        Object userRedis = redisUtils.hget("userMap", postVO.getUid().toString());
        User user = Convert.convert(User.class, userRedis);
        if (ObjectUtil.isNull(userRedis)){
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getUid, postVO.getUid());
            user = userMapper.selectOne(queryWrapper);
        }
        postVO.setNickname(user.getNickname()).setAvatar(user.getAvatar());
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Comment::getPid, postVO.getPid());
        int size = commentMapper.selectList(lambdaQueryWrapper).size();
        postVO.setCommentNum(size);
        return postVO;
    }
}




