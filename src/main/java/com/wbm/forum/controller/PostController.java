package com.wbm.forum.controller;


import com.wbm.forum.common.Result;
import com.wbm.forum.common.ResultCode;
import com.wbm.forum.dto.PostDTO;
import com.wbm.forum.entity.Post;
import com.wbm.forum.service.PostService;
import com.wbm.forum.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

/**
 * @Author：Ming
 * @Date: 2022/11/10 23:52
 */
@RestController
@RequestMapping(value = "/post")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private RedisUtils redisUtils;

    @GetMapping
    public Result getAllPost(@PathParam("pageNum") Integer pageNum,@PathParam("pageSize") Integer pageSize){
        Map<String, Object> allPost = postService.getAllPost(pageNum, pageSize);
        return Result.success(ResultCode.CODE_200,"success",allPost);
    }

    @GetMapping(value = "/{pid}")
    public Result getPost(@PathVariable Integer pid){
        PostDTO postDTO = postService.getPost(pid);
        return Result.success(ResultCode.CODE_200,"success",postDTO);
    }
    @PostMapping
    public Result post(@RequestBody Post post){
        Integer result = postService.addPost(post);
        redisUtils.deleteByKeys("post_");
        return Result.success(ResultCode.CODE_200,"success",result);
    }
    @DeleteMapping(value = "/{pid}")
    public Result deletePost(@PathVariable Integer pid){
        return Result.success(ResultCode.CODE_200,"success",null);
    }

    @DeleteMapping(value = "/del/{ids}")
    public Result deletePosts(@PathVariable List<Integer> ids){
        return Result.success(ResultCode.CODE_200,"success",null);
    }

}
