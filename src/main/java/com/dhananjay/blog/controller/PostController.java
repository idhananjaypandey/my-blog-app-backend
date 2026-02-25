package com.dhananjay.blog.controller;

import com.dhananjay.blog.dto.PostRequest;
import com.dhananjay.blog.dto.PostResponse;
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
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    // ✅ CREATE POST
    @PostMapping
    public ResponseEntity<PostResponse> create(@RequestBody PostRequest request,
                                               Principal principal) {

        PostResponse response =
                postService.createPost(request, principal.getName());

        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<List<PostResponse>> getMyPosts(Principal principal) {
        return ResponseEntity.ok(
                postService.getMyPosts(principal.getName())
        );
    }

    // ✅ UPDATE POST
    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> update(@PathVariable Long id,
                                               @RequestBody PostRequest request,
                                               Principal principal) {

        PostResponse response =
                postService.updatePost(id, request, principal.getName());

        return ResponseEntity.ok(response);
    }

    // ✅ DELETE POST
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       Principal principal) {

        postService.deletePost(id, principal.getName());
        return ResponseEntity.noContent().build();
    }
}