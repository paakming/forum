package com.wbm.forum.mapper;

import com.wbm.forum.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author Ming
* @description 针对表【comment】的数据库操作Mapper
* @createDate 2023-03-15 19:47:37
* @Entity com.wbm.forum.entity.Comment
*/
@Repository
public interface CommentMapper extends BaseMapper<Comment> {

}




