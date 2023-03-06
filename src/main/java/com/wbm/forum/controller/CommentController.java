package com.wbm.forum.controller;

import com.wbm.forum.common.Code;
import com.wbm.forum.common.Result;
import com.wbm.forum.common.ResultCode;
import com.wbm.forum.entity.vo.CommentVO;
import com.wbm.forum.entity.Comment;
import com.wbm.forum.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author：Ming
 * @Date: 2022/11/19 14:39
 */
@RestController
@RequestMapping(value = "/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;


    @GetMapping(value = "/{pid}")
    @PreAuthorize("hasAuthority('system:comment:get')")
    public Result secComment(@PathVariable Integer pid){
        List<CommentVO> comment = commentService.getComment(pid);
        return  Result.success(Code.SUCCESS.getCode(),Code.SUCCESS.getMsg(),comment);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('system:comment:post')")
    public Result doComment(@RequestBody Comment comment){
        System.out.println(comment);
        commentService.doComment(comment);
        return  Result.success(Code.SUCCESS.getCode(),Code.SUCCESS.getMsg(),null);
    }

    @DeleteMapping(value = "/{cid}")
    @PreAuthorize("hasAuthority('system:comment:delete')")
    public Result deleteComment(@PathVariable Integer cid){
        commentService.deleteComment(cid);
        return  Result.success(Code.SUCCESS.getCode(),"删除成功",null);
    }

    @DeleteMapping(value = "/ids/{ids}")
    @PreAuthorize("hasAuthority('system:comment:delete')")
    public Result deleteComments(@PathVariable List<Integer> ids){
        commentService.deleteComments(ids);
        return  Result.success(Code.SUCCESS.getCode(),"删除成功",null);
    }
}
