package com.wbm.forum.controller;

import com.alibaba.fastjson.JSONObject;
import com.wbm.forum.common.Code;
import com.wbm.forum.common.Result;
import com.wbm.forum.exception.MyServiceException;
import com.wbm.forum.service.SubCommentService;
import com.wbm.forum.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.websocket.server.PathParam;
import java.util.Map;

/**
 * @Author：Ming
 * @Date: 2023/3/7 1:33
 */
@RestController
@RequestMapping("/subComment")
public class SubCommentController {
    @Autowired
    private SubCommentService subCommentService;
    @Autowired
    private RedisUtils redisUtils;

    @GetMapping("/{cid}")
    public Result getSubComment(@PathVariable Integer cid, @PathParam("pageNum") Integer pageNum, @PathParam("pageSize") Integer pageSize){
        Map<String, Object> subComment = subCommentService.getSubComment(cid, pageNum, pageSize);
        return  Result.success(Code.SUCCESS.getCode(),Code.SUCCESS.getMsg(),subComment);
    }
    @PostMapping
    public Result postSubComment(@RequestBody JSONObject jsonObject){
        subCommentService.postSubComment(jsonObject);
        return  Result.success(Code.SUCCESS.getCode(),"回复成功");
    }
    @DeleteMapping(value = "/{subId}")
    @PreAuthorize("hasAuthority('system:comment:delete')")
    public Result deleteComment(@PathVariable Integer subId){
        subCommentService.removeById(subId);
        return  Result.success(Code.SUCCESS.getCode(),"删除成功");
    }
    @PutMapping("/doLike/{subId}/{uid}")
    public Result doLike(@PathVariable("subId") Integer subId,@PathVariable("uid")Integer uid){
        boolean b = redisUtils.sHasKey("sucCommentLikes" + subId, uid);
        if (b){
            throw new MyServiceException(Code.ERROR.getCode(),"已点赞");
        }else {
            redisUtils.sSet("sucCommentLikes" + subId,uid);
            return Result.success(Code.SUCCESS.getCode(),"点赞成功");
        }
    }
    @PutMapping("/unDoLike/{subId}/{uid}")
    public Result unDoLike(@PathVariable("subId") Integer subId,@PathVariable("uid")Integer uid){
        boolean b = redisUtils.sHasKey("sucCommentLikes" + subId, uid);
        if (b) {
            redisUtils.setRemove("sucCommentLikes" + subId, uid);
            return Result.success(Code.SUCCESS.getCode(),"取消成功");
        }else {
            throw new MyServiceException(Code.ERROR.getCode(),"没有点赞");
        }
    }

}
