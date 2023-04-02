package com.wbm.forum.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wbm.forum.entity.Comment;
import com.wbm.forum.entity.vo.CommentVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
* @author Ming
* @description 针对表【comment】的数据库操作Service
* @createDate 2022-11-12 13:10:05
*/

public interface CommentService extends IService<Comment> {

    Map<String,Object> getComment(Integer pid, Integer pageNum, Integer pageSize);

    Boolean doComment(Comment comment);


}
