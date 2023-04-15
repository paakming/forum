package com.wbm.forum.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wbm.forum.common.Code;
import com.wbm.forum.common.Result;
import com.wbm.forum.entity.Post;
import com.wbm.forum.entity.vo.PostVO;
import com.wbm.forum.exception.MyServiceException;
import com.wbm.forum.service.PostService;
import com.wbm.forum.utils.BeanCopyUtils;
import com.wbm.forum.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.HashMap;
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
    public Result getPostByUid(@PathVariable("uid") Integer uid,@PathParam("pageNum") Integer pageNum,
                               @PathParam("pageSize") Integer pageSize){
        Map<String, Object> postByUid = postService.getPostByUid(uid, pageNum, pageSize);
        return Result.success(Code.SUCCESS.getCode(), Code.SUCCESS.getMsg(),postByUid);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('system:post:post')")
    public Result post(@RequestBody Post post){
        Integer result = postService.addPost(post);
        if (result == 1){
            return Result.success(Code.SUCCESS.getCode(), Code.SUCCESS.getMsg());
        }
        return Result.error(Code.ERROR_ADD.getCode(), "发帖失败");
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
        if (post.getIsTop().equals("0")){
            post.setIsTop("1");
        }else if (post.getIsTop().equals("1")){
            post.setIsTop("0");
        }
        postService.updateById(post);
        return Result.success(Code.SUCCESS.getCode(),"");
    }
    @PostMapping("/searchByTime")
    public Result searchByTime(@RequestBody JSONObject jsonObject,@PathParam("pageNum") Integer pageNum,
                               @PathParam("pageSize") Integer pageSize){
        Date from = jsonObject.getDate("from");
        Date to = jsonObject.getDate("to");
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
        wrapper.between(Post::getCreateTime, from, to);
        Page<Post> page = postService.page(new Page<>(pageNum, pageSize), wrapper);
        List<Post> list = page.getRecords();
        List<PostVO> postVOS = BeanCopyUtils.copyBeanList(list, PostVO.class);
        int total = (int) page.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("post",postVOS);
        return Result.success(Code.SUCCESS.getCode(),"",map);
    }
    @PostMapping("/searchByPid")
    public Result searchByPid(@RequestBody Post post) {
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Post::getPid,post.getPid());
        List<Post> list = postService.list(wrapper);
        List<PostVO> postVOS = BeanCopyUtils.copyBeanList(list, PostVO.class);
        return Result.success(Code.SUCCESS.getCode(),"",postVOS);
    }
    @PostMapping("/selectByType")
    public Result selectByType(@RequestBody Post post,@PathParam("pageNum") Integer pageNum,@PathParam("pageSize") Integer pageSize){
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Post::getType,post.getType())
                .orderBy(true,false,Post::getIsTop)
                .orderBy(true,false,Post::getUpdateTime);
        Page<Post> page = postService.page(new Page<>(pageNum, pageSize), wrapper);
        List<Post> list = page.getRecords();
        List<PostVO> postVOS = BeanCopyUtils.copyBeanList(list, PostVO.class);
        int total = (int) page.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("post",postVOS);
        return Result.success(Code.SUCCESS.getCode(),"",map);
    }
    @PutMapping("/views/{pid}")
    public Result getViews(@PathVariable Integer pid){
        redisUtils.hIncrBy("postViews",pid.toString(),1);
        Integer views = (Integer) redisUtils.hget("postViews", pid.toString());
        return Result.success(Code.SUCCESS.getCode(),"",views);
    }
    @PutMapping("/doLike/{pid}/{uid}")
    public Result doLike(@PathVariable("pid") Integer pid,@PathVariable("uid")Integer uid){
        boolean b = redisUtils.sHasKey("postLikes" + pid, uid);
        if (b){
            throw new MyServiceException(Code.ERROR.getCode(),"已点赞");
        }else {
            redisUtils.sSet("postLikes" + pid,uid);
            return Result.success(Code.SUCCESS.getCode(),"点赞成功");
        }
    }
    @PutMapping("/unDoLike/{pid}/{uid}")
    public Result unDoLike(@PathVariable("pid") Integer pid,@PathVariable("uid")Integer uid){
        boolean b = redisUtils.sHasKey("postLikes" + pid, uid);
        if (b) {
            redisUtils.setRemove("postLikes" + pid, uid);
            return Result.success(Code.SUCCESS.getCode(),"取消成功");
        }else {
            throw new MyServiceException(Code.ERROR.getCode(),"没有点赞");
        }
    }



}
