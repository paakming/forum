package com.wbm.forum.mapper;

import com.wbm.forum.entity.Post;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author Ming
* @description 针对表【post】的数据库操作Mapper
* @createDate 2023-03-14 19:45:35
* @Entity com.wbm.forum.entity.Post
*/
@Repository
public interface PostMapper extends BaseMapper<Post> {

}




