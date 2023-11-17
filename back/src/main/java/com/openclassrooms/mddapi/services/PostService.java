package com.openclassrooms.mddapi.services;


import com.openclassrooms.mddapi.models.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPostByUserSubscription();

    Post create(Post post);

    Post getById(Long postId);
}
