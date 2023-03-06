package com.wbm.forum.mapper;

import com.wbm.forum.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author Ming
* @description 针对表【menu】的数据库操作Mapper
* @createDate 2023-02-28 21:17:38
* @Entity com.wbm.forum.entity.Menu
*/
@Repository
public interface MenuMapper extends BaseMapper<Menu> {
    List<String> selectPermissionByUid(Integer uid);
    List<String> selectPathByUid(Integer uid);
    List<String> selectComponentByUid(Integer uid);


}




