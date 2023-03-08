package com.sgu.postsservice.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;
@Entity
@Table(name="posts_image")
public class PostImage {
    @Id
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Posts.class)
    @JoinColumn(name = "id", insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Posts posts;
    private String url;
    private String createdAt;
}
