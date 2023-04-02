package com.wbm.forum.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wbm.forum.common.Code;
import com.wbm.forum.common.Result;
import com.wbm.forum.entity.Post;
import com.wbm.forum.entity.SecurityUser;
import com.wbm.forum.entity.SubComment;
import com.wbm.forum.entity.User;
import com.wbm.forum.entity.vo.SubCommentVO;
import com.wbm.forum.exception.MyServiceException;
import com.wbm.forum.mapper.PostMapper;
import com.wbm.forum.mapper.SubCommentMapper;
import com.wbm.forum.mapper.UserMapper;
import com.wbm.forum.service.SubCommentService;
import com.wbm.forum.utils.BeanCopyUtils;
import com.wbm.forum.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
* @author Ming
* @description 针对表【sub_comment】的数据库操作Service实现
* @createDate 2023-03-06 23:14:34
*/
@Service
public class SubCommentServiceImpl extends ServiceImpl<SubCommentMapper, SubComment>
    implements SubCommentService{

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private PostMapper postMapper;

    @Override
    public Map<String,Object> getSubComment(Integer cid, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<SubComment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SubComment::getRootId,cid);
        Page<SubComment> subCommentPage = page(new Page<>(pageNum, pageSize),wrapper);
        int total = (int) subCommentPage.getTotal();
        List<SubComment> subComments = subCommentPage.getRecords();
        List<SubCommentVO> subCommentVOS = BeanCopyUtils.copyBeanList(subComments, SubCommentVO.class);
        for (SubCommentVO subCommentVO : subCommentVOS) {
            User user = getUserInfo(subCommentVO.getReplyId());
            subCommentVO.setAvatar(user.getAvatar()).setReplyName(user.getNickname()).setLikes(getLikes(subCommentVO.getSubId()));
            Boolean like = isLike(subCommentVO.getSubId());
            if (like){
                subCommentVO.setIsLike("1");
            }
            if (subCommentVO.getTargetId()!=null){
                user = getUserInfo(subCommentVO.getTargetId());
                subCommentVO.setTargetName(user.getNickname());
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("subComment",subCommentVOS);
        return map;
    }

    @Override
    public Boolean postSubComment(JSONObject jsonObject) {
        Integer uid = jsonObject.getInteger("uid");
        Integer cid = jsonObject.getInteger("cid");
        Integer pid = jsonObject.getInteger("pid");
        String content = jsonObject.getString("content");
        if(uid==null || cid==null || StrUtil.isBlank(content) || pid==null){
            throw new MyServiceException(Code.ERROR.getCode(),"回复失败");
        }
        SubComment subComment = new SubComment();
        subComment.setContent(content).setRootId(cid).setReplyId(uid).setLikes(0L);
        Integer targetId = jsonObject.getInteger("targetId");
        if (targetId!=null){
            subComment.setTargetId(targetId);
        }
        boolean save = save(subComment);
        if (save){
            Post post = new Post();
            post.setPid(pid).setUpdateTime(LocalDateTimeUtil.now());
            postMapper.updateById(post);
            return true;
        }else {
            throw new MyServiceException(Code.ERROR.getCode(),"回复失败");
        }

    }

    public Integer getLikes(Integer subId){
        Set<Object> objects = redisUtils.setMembers("sucCommentLikes" + subId);
        return objects.size();
    }
    public Boolean isLike(Integer subId){
        SecurityUser principal = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<Object> objects = redisUtils.setMembers("sucCommentLikes" + subId);
        return objects.contains(principal.getUser().getUid());
    }

    public User getUserInfo(Integer uid){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUid,uid);
        User user = userMapper.selectOne(wrapper);
        return user;
    }
}




