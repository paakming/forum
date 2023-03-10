package com.wbm.forum.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wbm.forum.entity.vo.CommentVO;
import com.wbm.forum.entity.Comment;

import java.util.List;

/**
* @author Ming
* @description 针对表【comment】的数据库操作Service
* @createDate 2022-11-12 13:10:05
*/
public interface CommentService extends IService<Comment> {

    List<CommentVO> getComment(Integer pid);

    Integer doComment(Comment comment);

    Integer deleteComments(List<Integer> ids);

    Integer deleteComment(Integer cid);
}
