package com.wbm.forum.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wbm.forum.entity.Post;
import com.wbm.forum.entity.vo.PostVO;
import java.util.List;
import java.util.Map;

/**
* @author Ming
* @description 针对表【post】的数据库操作Service
* @createDate 2022-11-10 21:47:48
*/
public interface PostService extends IService<Post> {
    PostVO getPost(Integer pid);

    Map<String, Object> getPostByUid(Integer uid,Integer pageNum,Integer pageSize);

    Map<String, Object> getAllPost(Integer pageNum, Integer pageSize);

    List<PostVO> getTopPost();

    Integer addPost(Post post);

    Integer deletePost(Integer pid);

    List<PostVO>  getPostByTitle(String key);
}
