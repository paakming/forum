package com.wbm.forum.service.impl;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wbm.forum.dto.PostDTO;
import com.wbm.forum.entity.Comment;
import com.wbm.forum.entity.Post;
import com.wbm.forum.entity.SecurityUser;
import com.wbm.forum.entity.User;
import com.wbm.forum.mapper.CommentMapper;
import com.wbm.forum.mapper.PostMapper;
import com.wbm.forum.mapper.UserMapper;
import com.wbm.forum.service.PostService;
import com.wbm.forum.utils.BeanCopyUtils;
import com.wbm.forum.utils.RedisUtils;
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
    public PostDTO getPost(Integer pid) {
        Post post = postMapper.selectById(pid);
        PostDTO postDTO = new PostDTO();
        BeanUtils.copyProperties(post,postDTO);
        User user = userMapper.selectById(post.getUid());
        postDTO.setAvatar(user.getAvatar());
        postDTO.setNickname(user.getNickname());
        return postDTO;
    }

    @Override
    public Map<String, Object> getAllPost(Integer pageNum, Integer pageSize) {
        String s = redisUtils.get("post_" + pageNum + "_" + pageSize);
        if (StrUtil.isNotBlank(s)){
            Map<String, Object> map = JSONUtil.toBean(s, new TypeReference<Map<String, Object>>() {
            }, true);
            return map;
        }
        LambdaQueryWrapper<Post> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(Post::getIsTop,"0")
                .orderBy(true,false,Post::getUpdateTime);
        Page<Post> posts = postMapper.selectPage(new Page<>(pageNum,pageSize),queryWrapper);
        List<PostDTO> postDTOS = BeanCopyUtils.copyBeanList(posts.getRecords(), PostDTO.class);
        postDTOS.forEach(postDTO -> setPostDTO(postDTO));
        Map<String,Object> map = new HashMap<>();
        map.put("post",postDTOS);
        map.put("total",posts.getTotal());
        map.put("top",getTopPost());
        redisUtils.set("post_"+pageNum+"_"+pageSize,JSONUtil.toJsonStr(map));
        return map;
    }

    @Override
    public List<PostDTO> getTopPost() {
        String p = redisUtils.get("topPost");
        if (StrUtil.isBlank(p)){
            LambdaQueryWrapper<Post> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper
                    .eq(Post::getIsTop,"1")
                    .orderBy(true,false,Post::getUpdateTime);
            List<Post> posts = postMapper.selectList(queryWrapper);
            List<PostDTO> topPost = BeanCopyUtils.copyBeanList(posts, PostDTO.class);
            topPost.forEach(postDTO -> setPostDTO(postDTO));
            redisUtils.set("topPost",JSONUtil.toJsonStr(topPost));
        }
        List<PostDTO> topPost = JSONUtil.toBean(p, new TypeReference<List<PostDTO>>() {
        }, true);
        return topPost;
    }

    @Override
    public Integer addPost(Post post) {
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setUid(securityUser.getUser().getUid());
        return postMapper.insert(post);
    }

    public PostDTO setPostDTO(PostDTO postDTO){
        String s = redisUtils.get("user" + postDTO.getUid());
        User user = JSONUtil.toBean(s, new TypeReference<User>() {}, true);
        //User user = userMapper.selectById(postDTO.getUid());
        postDTO.setNickname(user.getNickname()).setAvatar(user.getAvatar());
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Comment::getPid,postDTO.getPid());
        int size = commentMapper.selectList(lambdaQueryWrapper).size();
        postDTO.setCommentNum(size);
        return postDTO;
    }
}




