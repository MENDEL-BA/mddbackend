package com.openclassrooms.mddapi.services.impl;

import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.PostRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.services.PostService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Post> getAllPostByUserSubscription() {
        User currentUser = new User();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            currentUser = userRepository.findByEmail(currentUserName).orElseThrow(() -> new RestClientException("User with this Mail not found"));
        }
        return this.postRepository.findBySubjectInOrderByCreatedAt(currentUser.getSubjects());
    }

    @Override
    public Post create(Post post) {
        post.setCreatedAt(LocalDateTime.now());
        return this.postRepository.save(post);
    }

    @Override
    public Post getById(Long postId) {
        return this.postRepository.findById(postId).orElse(null);
    }
}
