package com.wbm.forum.controller;

import com.wbm.forum.common.Result;
import com.wbm.forum.common.ResultCode;
import com.wbm.forum.dto.CommentDTO;
import com.wbm.forum.entity.Comment;
import com.wbm.forum.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Result secComment(@PathVariable Integer pid){
        List<CommentDTO> comment = commentService.getComment(pid);
        return  Result.success(ResultCode.CODE_200,"",comment);
    }
    @PostMapping
    public Result doComment(@RequestBody Comment comment){
        commentService.doComment(comment);
        return  Result.success(ResultCode.CODE_200,"success",null);
    }
    @DeleteMapping(value = "/{cid}")
    private Result deleteComment(@PathVariable Integer cid){
        commentService.deleteComment(cid);
        return  Result.success(ResultCode.CODE_200,"success",null);
    }

    @DeleteMapping(value = "/ids/{ids}")
    private Result deleteComments(@PathVariable List<Integer> ids){
        commentService.deleteComments(ids);
        return  Result.success(ResultCode.CODE_200,"success",null);
    }
}
