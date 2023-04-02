package com.wbm.forum.service;

import com.alibaba.fastjson.JSONObject;
import com.wbm.forum.entity.SubComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wbm.forum.entity.vo.SubCommentVO;

import java.util.List;
import java.util.Map;

/**
* @author Ming
* @description 针对表【sub_comment】的数据库操作Service
* @createDate 2023-03-06 23:14:34
*/
public interface SubCommentService extends IService<SubComment> {
    Map<String,Object> getSubComment(Integer cid, Integer pageNum, Integer pageSize);
    Boolean postSubComment(JSONObject jsonObject);
}
