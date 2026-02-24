package com.dhananjay.blog.controller;

import com.dhananjay.blog.dto.PostRequest;
import com.dhananjay.blog.entity.Post;
import com.dhananjay.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    // ✅ GET ALL POSTS
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    // ✅ CREATE POST
    @PostMapping
    public ResponseEntity<Post> create(@RequestBody PostRequest request,
                                       Principal principal) {
        Post post = postService.createPost(request, principal.getName());
        return ResponseEntity.status(201).body(post); // 201 CREATED
    }

    // ✅ UPDATE POST
    @PutMapping("/{id}")
    public ResponseEntity<Post> update(@PathVariable Long id,
                                       @RequestBody PostRequest request,
                                       Principal principal) {
        Post updatedPost =
                postService.updatePost(id, request, principal.getName());
        return ResponseEntity.ok(updatedPost);
    }

    // ✅ DELETE POST
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id,
                                    Principal principal) {
        postService.deletePost(id, principal.getName());
        return ResponseEntity.noContent().build(); // 204 NO CONTENT
    }
}