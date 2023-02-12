package com.wbm.forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wbm.forum.entity.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author Ming
* @description 针对表【menu】的数据库操作Mapper
* @createDate 2022-11-09 20:11:27
* @Entity com.ss.security.entity.Menu
*/
@Repository
public interface MenuMapper extends BaseMapper<Menu> {
    List<String> selectTestByUid(Integer uid);
}




