package com.smen.Services;

import com.smen.DTO.Post.PostCreateDto;
import com.smen.Models.Post;
import com.smen.Repositories.IMentorRequestStatusRepository;
import com.smen.Repositories.IPostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final IPostRepository postRepository;

    public PostService(IPostRepository postRepository) {
        this.postRepository = postRepository;
    }


    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    public Post createPost(PostCreateDto postCreateDto){
        Post post = new Post();
        post.setTitle(postCreateDto.getTitle());
        post.setDescription(postCreateDto.getDescription());
        post.setUserId(postCreateDto.getUserId());
        return postRepository.save(post);
    }

    public Optional<Post> getPostById(Long id){
        return postRepository.findById(id);
    }

    public Post updatePost(Post post){
        return postRepository.save(post);
    }

    public void deletePost(Long id){
        postRepository.deleteById(id);
    }
}
