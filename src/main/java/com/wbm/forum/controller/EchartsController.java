package com.wbm.forum.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import com.wbm.forum.common.Code;
import com.wbm.forum.common.Result;
import com.wbm.forum.entity.Comment;
import com.wbm.forum.entity.Post;
import com.wbm.forum.entity.SubComment;
import com.wbm.forum.entity.User;
import com.wbm.forum.service.CommentService;
import com.wbm.forum.service.PostService;
import com.wbm.forum.service.SubCommentService;
import com.wbm.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author：Ming
 * @Date: 2023/2/25 15:30
 */
@RestController
@RequestMapping(value = "/echarts")
@PreAuthorize("hasAuthority('system:management')")
public class EchartsController {
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private SubCommentService subCommentService;

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
        List<String> collect = list.stream().map(User::getSex).collect(Collectors.toList());
        int man = 0;
        int women = 0;
        int other = 0;
        for (String s : collect) {
            if (s.equals("男")){
                man +=1;
            }else if (s.equals("女")){
                women +=1;
            }else {
                other +=1;
            }
        }
        ArrayList<Integer> sex = CollUtil.newArrayList(man, women, other);
        Map<String, Object> map = new HashMap<>();
        map.put("num",arrayList);
        map.put("sex",sex);
        return Result.success(Code.SUCCESS.getCode(),"",map);
    }
    @GetMapping(value = "/post")
    public Result getPostNum(){
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
        return Result.success(Code.SUCCESS.getCode(),"",arrayList);
    }
    @GetMapping(value = "/comment")
    public Result commentNum(){
        List <Comment> comments = commentService.list();
        List<SubComment> subComments = subCommentService.list();
        int count = comments.size() + subComments.size();
        return Result.success(Code.SUCCESS.getCode(),"",count);
    }
}
//    @GetMapping(value = "/user")
//    public Result getUserNum(){
//        List<User> list = userService.list();
//        int q1 = (int) list.stream()
//                .filter(user -> DateUtil.quarterEnum(Convert.toDate(user.getCreateTime())).equals(Quarter.Q1)).count();
//        int q2 = (int) list.stream()
//                .filter(user -> DateUtil.quarterEnum(Convert.toDate(user.getCreateTime())).equals(Quarter.Q2)).count();
//        int q3 = (int) list.stream()
//                .filter(user -> DateUtil.quarterEnum(Convert.toDate(user.getCreateTime())).equals(Quarter.Q3)).count();
//        int q4 = (int) list.stream()
//                .filter(user -> DateUtil.quarterEnum(Convert.toDate(user.getCreateTime())).equals(Quarter.Q4)).count();
//        ArrayList<Integer> arrayList = CollUtil.newArrayList(q1,q2,q3,q4);
//        Map<String, List<User>> sexMap = list.stream().collect(Collectors.groupingBy(User::getSex));
//        int man = sexMap.get("男").size();
//        int women = sexMap.get("女").size();
//        int other = sexMap.get("保密").size();
//        ArrayList<Integer> sex = CollUtil.newArrayList(man, women, other);
//        Map<String, Object> map = new HashMap<>();
//        map.put("num",arrayList);
//        map.put("sex",sex);
//        return Result.success(Code.SUCCESS.getCode(),"",map);
//    }
//
//    @GetMapping(value = "/post")
//    public Result getPostNum(){
//        List<Post> postList = postService.list();
//        int q1 = (int) postList.stream()
//                .filter(post -> DateUtil.quarterEnum(Convert.toDate(post.getCreateTime())).equals(Quarter.Q1)).count();
//        int q2 = (int) postList.stream()
//                .filter(post -> DateUtil.quarterEnum(Convert.toDate(post.getCreateTime())).equals(Quarter.Q2)).count();
//        int q3 = (int) postList.stream()
//                .filter(post -> DateUtil.quarterEnum(Convert.toDate(post.getCreateTime())).equals(Quarter.Q3)).count();
//        int q4 = (int) postList.stream()
//                .filter(post -> DateUtil.quarterEnum(Convert.toDate(post.getCreateTime())).equals(Quarter.Q4)).count();
//        ArrayList<Integer> arrayList = CollUtil.newArrayList(q1,q2,q3,q4);
//        return Result.success(Code.SUCCESS.getCode(),"",arrayList);
//    }
//
