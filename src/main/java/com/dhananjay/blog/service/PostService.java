package com.dhananjay.blog.service;

import com.dhananjay.blog.dto.PostRequest;
import com.dhananjay.blog.entity.Post;
import com.dhananjay.blog.entity.User;
import com.dhananjay.blog.repository.PostRepository;
import com.dhananjay.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;


    // ✅ CREATE POST
    public Post createPost(PostRequest request, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setUser(user);

        // createdAt will be auto-set by @PrePersist in entity

        return postRepository.save(post);
    }


    // ✅ GET ALL POSTS (Newest First 🔥)
    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }


    // ✅ UPDATE POST
    public Post updatePost(Long id, PostRequest request, String email) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Not authorized");
        }

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());

        return postRepository.save(post);
    }


    // ✅ DELETE POST
    public String deletePost(Long id, String email) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Not authorized");
        }

        postRepository.delete(post);

        return "Deleted successfully";
    }
}