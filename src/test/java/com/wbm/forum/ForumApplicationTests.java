package com.wbm.forum;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wbm.forum.common.Code;
import com.wbm.forum.common.Result;
import com.wbm.forum.entity.*;
import com.wbm.forum.entity.vo.*;
import com.wbm.forum.mapper.*;
import com.wbm.forum.service.RoleService;
import com.wbm.forum.service.UserRoleService;
import com.wbm.forum.service.UserService;
import com.wbm.forum.utils.BeanCopyUtils;
import com.wbm.forum.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
class ForumApplicationTests {
    private final RedisUtils redisUtils;
    @Autowired
    ForumApplicationTests(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserService userService;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private SubCommentMapper subCommentMapper;

    @Test
    void contextLoads() {
    }
    @Test
    public void changePwd(){
        User user = new User();
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode("111111");
        user.setUid(1).setPassword(encode);
        userService.updateById(user);
    }
    @Test
    public void test2(){
        String pwd = "$2a$10$GG3Xro1Jrs71FsLFsHxI7eGFRIqUEiE7uFLcF5rrD3YFIWB6gN/Ua";
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean encode = encoder.matches("222",pwd);
        System.out.println(encode);
    }

    @Test
    public void setCommentNum() {
        Page<Post> postPage = postMapper.selectPage(new Page<>(1, 10), null);
        List<PostVO> postVOS = BeanCopyUtils.copyBeanList(postPage.getRecords(), PostVO.class);
        List<Integer> list = postVOS.stream().map(PostVO::getPid).collect(Collectors.toList());

        List<Comment> comments = commentMapper.selectList(Wrappers.lambdaQuery(Comment.class).in(Comment::getPid, list));
        List<Integer> collect = comments.stream().map(Comment::getCid).collect(Collectors.toList());

        List<SubComment> subComments = subCommentMapper
                .selectList(Wrappers.lambdaQuery(SubComment.class).in(SubComment::getRootId, collect));

        Map<Integer, List<SubComment>> subMap = subComments.stream().collect(Collectors.groupingBy(SubComment::getRootId));

        //      Map<Integer, Integer> mapSub = subComments.stream().collect(Collectors.toMap(SubComment::getRootId, SubComment::getSubId));
        Map<Integer, List<Comment>> mapCom = comments.stream().collect(Collectors.groupingBy(Comment::getPid));

        subMap.forEach((integer, subComments1) -> {

        });

        postVOS.forEach(postVO -> {
            List<Comment> comments1 = mapCom.get(postVO.getPid());
            if (comments1!=null){
                postVO.setCommentNum(comments1.size());
            }
        });
        System.out.println(comments);
        System.out.println("==========================");
        postVOS.forEach(System.out::println);
    }
    @Test
    public void testsql(){
        //List<UserRoleVO> userRoleVOS = roleMapper.selectUserRole();
        IPage<UserRoleVO> userRoleVOIPage = roleMapper.selectUserRole(new Page<>(1, 10));
        System.out.println(userRoleVOIPage.getRecords());
        //userRoleVOS.forEach(System.out::println);
    }
}
