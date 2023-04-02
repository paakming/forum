package com.wbm.forum.controller;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.wbm.forum.common.Code;
import com.wbm.forum.common.Result;
import com.wbm.forum.entity.Comment;
import com.wbm.forum.entity.Post;
import com.wbm.forum.exception.MyServiceException;
import com.wbm.forum.mapper.PostMapper;
import com.wbm.forum.service.CommentService;
import com.wbm.forum.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.websocket.server.PathParam;
import java.util.Map;

/**
 * @Author：Ming
 * @Date: 2022/11/19 14:39
 */
@RestController
@RequestMapping(value = "/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private RedisUtils redisUtils;


    @GetMapping(value = "/{pid}")
    @PreAuthorize("hasAuthority('system:comment:get')")
    public Result getComment(@PathVariable Integer pid,@PathParam("pageNum") Integer pageNum, @PathParam("pageSize") Integer pageSize){
        Map<String, Object> comment = commentService.getComment(pid, pageNum, pageSize);
        return  Result.success(Code.SUCCESS.getCode(),Code.SUCCESS.getMsg(),comment);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('system:comment:post')")
    public Result doComment(@RequestBody JSONObject jsonObject){
        Integer pid = jsonObject.getInteger("pid");
        String content = jsonObject.getString("content");
        Integer uid = jsonObject.getInteger("uid");
        if (pid == null || uid == null || StrUtil.isBlank(content)){
            throw new MyServiceException(Code.ERROR.getCode(),"回复帖子失败");
        }
        Comment comment = new Comment();
        comment.setContent(content).setPid(pid).setReplyId(uid).setLikes(0L);
        Boolean res = commentService.doComment(comment);
        if (res){
            Post post = new Post();
            post.setPid(pid).setUpdateTime(LocalDateTimeUtil.now());
            postMapper.updateById(post);
            return  Result.success(Code.SUCCESS.getCode(),Code.SUCCESS.getMsg());
        }else {
            return  Result.success(Code.ERROR_ADD.getCode(),"回复失败");
        }
    }


    @DeleteMapping(value = "/{cid}")
    @PreAuthorize("hasAuthority('system:comment:delete')")
    public Result deleteComment(@PathVariable Integer cid){
        commentService.removeById(cid);
//        commentService.deleteComment(cid);
        return  Result.success(Code.SUCCESS.getCode(),"删除成功");
    }

    @PutMapping("/doLike/{cid}/{uid}")
    public Result doLike(@PathVariable("cid") Integer cid,@PathVariable("uid")Integer uid){
        boolean b = redisUtils.sHasKey("commentLikes" + cid, uid);
        if (b){
            throw new MyServiceException(Code.ERROR.getCode(),"已点赞");
        }else {
            redisUtils.sSet("commentLikes" + cid,uid);
            return Result.success(Code.SUCCESS.getCode(),"点赞成功");
        }
    }
    @PutMapping("/unDoLike/{cid}/{uid}")
    public Result unDoLike(@PathVariable("cid") Integer cid,@PathVariable("uid")Integer uid){
        boolean b = redisUtils.sHasKey("commentLikes" + cid, uid);
        if (b) {
            redisUtils.setRemove("commentLikes" + cid, uid);
            return Result.success(Code.SUCCESS.getCode(),"取消成功");
        }else {
            throw new MyServiceException(Code.ERROR.getCode(),"没有点赞");
        }
    }
}
