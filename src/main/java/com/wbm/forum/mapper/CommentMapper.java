package com.wbm.forum.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wbm.forum.entity.Comment;
import org.springframework.stereotype.Repository;

/**
* @author Ming
* @description 针对表【comment】的数据库操作Mapper
* @createDate 2022-11-20 00:07:51
* @Entity com.wbm.forum.entity.Comment
*/
@Repository
public interface CommentMapper extends BaseMapper<Comment> {

}




