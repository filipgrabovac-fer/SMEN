package com.smen.Controllers;

import com.smen.DTO.ActivityLog.ActivityLogDto;
import com.smen.DTO.Post.PostCreateDto;
import com.smen.Models.Post;
import com.smen.Services.ActivityLogService;
import com.smen.Services.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/post")
@CrossOrigin("*")
public class PostController {

    private final PostService postService;
    private ActivityLogService activityLogService;
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts(){
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @PostMapping()
    public ResponseEntity<Post> createPost(@RequestBody PostCreateDto post){
        ActivityLogDto activityLogDto= new ActivityLogDto("c","oglas",userId);
        activityLogService.saveActivityLog(activityLogDto);
        return ResponseEntity.ok(postService.createPost(post));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> updatePost(@RequestBody PostCreateDto post, @PathVariable Long id){
        Post existingPost = postService.getPostById(id).orElse(null);
        if (existingPost == null) return ResponseEntity.badRequest().build();
        existingPost.setUserId(post.getUserId());
        existingPost.setTitle(post.getTitle());
        existingPost.setDescription(post.getDescription());
        postService.updatePost(existingPost);
        ActivityLogDto activityLogDto= new ActivityLogDto("e","oglas",userId);
        activityLogService.saveActivityLog(activityLogDto);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePost(@PathVariable Long id){
        postService.deletePost(id);
        ActivityLogDto activityLogDto= new ActivityLogDto("d","oglas",userId);
        activityLogService.saveActivityLog(activityLogDto);
        return ResponseEntity.ok(true);
    }
}
