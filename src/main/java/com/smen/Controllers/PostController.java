package com.smen.Controllers;

import com.smen.DTO.ActivityLog.ActivityLogDto;
import com.smen.DTO.Post.PostCreateDto;
import com.smen.DTO.Post.PostGetDTO;
import com.smen.Models.Post;
import com.smen.Services.ActivityLogService;
import com.smen.Models.User;
import com.smen.Services.PostService;
import com.smen.Services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/post")
@CrossOrigin("*")
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final ActivityLogService activityLogService;

    public PostController(PostService postService, UserService userService, ActivityLogService activityLogService) {
        this.postService = postService;
        this.userService = userService;
        this.activityLogService = activityLogService;
    }

    @GetMapping
    public ResponseEntity<List<PostGetDTO>> getAllPosts(){

        List<Post> posts = postService.getAllPosts();

        return ResponseEntity.ok(posts.stream().map(post ->{
            PostGetDTO postGetDTO = new PostGetDTO();
            User user = userService.getById(post.getUserId()).get();

            postGetDTO.setAuthor(user.getFirstName() + " " + user.getLastName());
            postGetDTO.setId(post.getId());
            postGetDTO.setCreatedAt(post.getCreatedAt().toString());
            postGetDTO.setTitle(post.getTitle());
            postGetDTO.setDescription(post.getDescription());

            return postGetDTO;
        }).collect(Collectors.toList()));
    }

    @PostMapping("user/{userId}")
    public ResponseEntity<Post> createPost(@RequestBody PostCreateDto post,@PathVariable Long userId){
        ActivityLogDto activityLogDto= new ActivityLogDto("c","oglas",userId);
        activityLogService.saveActivityLog(activityLogDto);
        return ResponseEntity.ok(postService.createPost(post));
    }

    @PutMapping("/{id}/user/{userId}")
    public ResponseEntity<Boolean> updatePost(@RequestBody PostCreateDto post, @PathVariable Long id,@PathVariable Long userId){
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

    @DeleteMapping("/{id}/user/{userId}")
    public ResponseEntity<Boolean> deletePost(@PathVariable Long id,@PathVariable Long userId){
        postService.deletePost(id);
        ActivityLogDto activityLogDto= new ActivityLogDto("d","oglas",userId);
        activityLogService.saveActivityLog(activityLogDto);
        return ResponseEntity.ok(true);
    }
}
