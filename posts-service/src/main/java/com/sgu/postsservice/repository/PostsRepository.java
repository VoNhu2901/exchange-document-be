package com.sgu.postsservice.repository;

import com.sgu.postsservice.dto.response.PostsResponse;
import com.sgu.postsservice.model.Category;
import com.sgu.postsservice.model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostsRepository extends JpaRepository<Posts,Long> {
    @Query(value = "SELECT * FROM posts p WHERE p.category_id=?1",nativeQuery = true)
    public List<Posts> getByCategoryId(Long category_id);

    @Query(value = "SELECT * FROM posts p WHERE p.account_id=?1",nativeQuery = true)
    List<Posts> getByAccountId(Long accountId);
}
