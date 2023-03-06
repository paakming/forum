package com.wbm.forum.controller;


import cn.hutool.core.collection.ListUtil;
import com.wbm.forum.common.Code;
import com.wbm.forum.common.Result;
import com.wbm.forum.common.ResultCode;
import com.wbm.forum.entity.vo.PostVO;
import com.wbm.forum.entity.Post;
import com.wbm.forum.service.PostService;
import com.wbm.forum.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('system:post:get')")
    public Result getAllPost(@PathParam("pageNum") Integer pageNum,@PathParam("pageSize") Integer pageSize){
        Map<String, Object> allPost = postService.getAllPost(pageNum, pageSize);
        return Result.success(Code.SUCCESS.getCode(), Code.SUCCESS.getMsg(),allPost);
    }

    @GetMapping(value = "/pid/{pid}")
    @PreAuthorize("hasAuthority('system:post:get')")
    public Result getPost(@PathVariable("pid") Integer pid){
        PostVO postVO = postService.getPost(pid);
        return Result.success(Code.SUCCESS.getCode(), Code.SUCCESS.getMsg(), postVO);
    }

    @GetMapping(value = "/search/{key}")
    @PreAuthorize("hasAuthority('system:post:get')")
    public Result getPostByTitle(@PathVariable("key")  String key){
        List<PostVO> postByTitle = postService.getPostByTitle(key);
        if(postByTitle.isEmpty()){
            return Result.success(Code.ERROR_QUERY.getCode(), Code.ERROR_QUERY.getMsg());
        }
        return Result.success(Code.SUCCESS.getCode(), Code.SUCCESS.getMsg(),postByTitle);
    }

    @GetMapping(value = "/uid/{uid}")
    @PreAuthorize("hasAuthority('system:post:get')")
    public Result getPostByUid(@PathVariable("uid") Integer uid,@PathParam("pageNum") Integer pageNum,@PathParam("pageSize") Integer pageSize){
        Map<String, Object> postByUid = postService.getPostByUid(uid, pageNum, pageSize);
        return Result.success(Code.SUCCESS.getCode(), Code.SUCCESS.getMsg(),postByUid);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('system:post:post')")
    public Result post(@RequestBody Post post){
        Integer result = postService.addPost(post);
        return Result.success(Code.SUCCESS.getCode(), Code.SUCCESS.getMsg(),result);
    }
    @DeleteMapping(value = "/{pid}")
    @PreAuthorize("hasAuthority('system:post:delete')")
    public Result deletePost(@PathVariable Integer pid){
        postService.deletePost(pid);
        return Result.success(Code.SUCCESS.getCode(), "删除成功");
    }

    @PostMapping(value = "/del/{ids}")
    @PreAuthorize("hasAuthority('system:post:delete')")
    public Result deletePosts(@PathVariable List<Integer> ids){
        postService.removeByIds(ids);
        return Result.success(Code.SUCCESS.getCode(),"删除成功");
    }
    @PutMapping("/top")
    public Result changeIsTop(@RequestBody Post post){
        System.out.println(post);
        if (post.getIsTop().equals("0")){
            post.setIsTop("1");
        }else if (post.getIsTop().equals("1")){
            post.setIsTop("0");
        }
        postService.updateById(post);
        return Result.success(Code.SUCCESS.getCode(),"");
    }

}
