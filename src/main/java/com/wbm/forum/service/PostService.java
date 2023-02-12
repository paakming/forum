package com.wbm.forum.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wbm.forum.dto.PostDTO;
import com.wbm.forum.entity.Post;
import java.util.List;
import java.util.Map;

/**
* @author Ming
* @description 针对表【post】的数据库操作Service
* @createDate 2022-11-10 21:47:48
*/
public interface PostService extends IService<Post> {
    PostDTO getPost(Integer pid);
    Map<String, Object> getAllPost(Integer pageNum, Integer pageSize);
    List<PostDTO> getTopPost();
    Integer addPost(Post post);
}
