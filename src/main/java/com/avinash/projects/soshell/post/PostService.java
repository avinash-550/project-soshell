package com.avinash.projects.soshell.post;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.CannotCreateTransactionException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PostService {
    private PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post createPost(String username, String content) {
        try {
            Post post = postRepository.save(Post.builder().username(username).content(content).build());
            log.info("new post {} created for username {}", content, username);
            return post;
        } catch (CannotCreateTransactionException e) {
            log.error("error while creating new post for user {}", e.getMessage(), username);
        }
        return null;
    }

    public Post getPostById(String postId) {
        return postRepository.findById(UUID.fromString(postId)).get();
    }

}
