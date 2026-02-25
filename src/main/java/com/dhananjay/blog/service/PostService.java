package com.dhananjay.blog.service;

import com.dhananjay.blog.dto.PostRequest;
import com.dhananjay.blog.dto.PostResponse;
import com.dhananjay.blog.entity.Post;
import com.dhananjay.blog.entity.User;
import com.dhananjay.blog.repository.PostRepository;
import com.dhananjay.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;


    // ✅ CREATE POST
    public PostResponse createPost(PostRequest request, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setUser(user);

        Post saved = postRepository.save(post);

        return new PostResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getContent(),
                user.getName(),
                user.getEmail(),
                saved.getCreatedAt()
        );
    }


    // ✅ GET ALL POSTS (Newest First 🔥)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(post -> new PostResponse(
                        post.getId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getUser().getName(),
                        post.getUser().getEmail(),
                        post.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }


    // ✅ UPDATE POST
    public PostResponse updatePost(Long id, PostRequest request, String email) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Not authorized");
        }

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());

        Post updated = postRepository.save(post);

        return new PostResponse(
                updated.getId(),
                updated.getTitle(),
                updated.getContent(),
                updated.getUser().getName(),
                updated.getUser().getEmail(),
                updated.getCreatedAt()
        );
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