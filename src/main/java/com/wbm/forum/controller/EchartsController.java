package com.wbm.forum.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.date.Quarter;
import com.wbm.forum.common.Result;
import com.wbm.forum.common.ResultCode;
import com.wbm.forum.entity.Comment;
import com.wbm.forum.entity.Post;
import com.wbm.forum.entity.User;
import com.wbm.forum.service.CommentService;
import com.wbm.forum.service.PostService;
import com.wbm.forum.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author：Ming
 * @Date: 2023/2/25 15:30
 */
@RestController
@RequestMapping(value = "/echarts")

public class EchartsController {
    @Autowired
    UserService userService;
    @Autowired
    PostService postService;
    @Autowired
    CommentService commentService;

    @GetMapping(value = "/user")
    public Result getUserNum(){
        List<User> list = userService.list();
        int q1 = 0;
        int q2 = 0;
        int q3 = 0;
        int q4 = 0;
        for (User user : list) {
            LocalDateTime createTime = user.getCreateTime();
            Quarter quarter = DateUtil.quarterEnum(Convert.toDate(createTime));
            if (quarter.equals(Quarter.Q1)){
                q1 += 1;
            }
            if (quarter.equals(Quarter.Q2)){
                q2 += 1;
            }
            if (quarter.equals(Quarter.Q3)){
                q3 += 1;
            }
            if (quarter.equals(Quarter.Q4)){
                q4 += 1;
            }
        }
        ArrayList<Integer> arrayList = CollUtil.newArrayList(q1,q2,q3,q4);
        return Result.success(ResultCode.CODE_200,"",arrayList);
    }
    @GetMapping(value = "/post")
    public Result getPrescriptionNum(){
        List<Post> postList = postService.list();
        int q1 = 0;
        int q2 = 0;
        int q3 = 0;
        int q4 = 0;
        for (Post post : postList) {
            LocalDateTime date = post.getCreateTime();
            Quarter quarter = DateUtil.quarterEnum(Convert.toDate(date));
            if (quarter.equals(Quarter.Q1)){
                q1 += 1;
            }
            if (quarter.equals(Quarter.Q2)){
                q2 += 1;
            }
            if (quarter.equals(Quarter.Q3)){
                q3 += 1;
            }
            if (quarter.equals(Quarter.Q4)){
                q4 += 1;
            }
        }
        ArrayList<Integer> arrayList = CollUtil.newArrayList(q1,q2,q3,q4);
        return Result.success(ResultCode.CODE_200,"",arrayList);
    }
    @GetMapping(value = "/comment")
    public Result getRegistrationNum(){
        List <Comment> comments = commentService.list();
        int q1 = 0;
        int q2 = 0;
        int q3 = 0;
        int q4 = 0;
        for (Comment comment : comments) {
            LocalDateTime date = comment.getCreateTime();
            Quarter quarter = DateUtil.quarterEnum(Convert.toDate(date));
            if (quarter.equals(Quarter.Q1)){
                q1 += 1;
            }
            if (quarter.equals(Quarter.Q2)){
                q2 += 1;
            }
            if (quarter.equals(Quarter.Q3)){
                q3 += 1;
            }
            if (quarter.equals(Quarter.Q4)){
                q4 += 1;
            }

        }
        ArrayList<Integer> arrayList = CollUtil.newArrayList(q1,q2,q3,q4);
        return Result.success(ResultCode.CODE_200,"",arrayList);
    }
}
