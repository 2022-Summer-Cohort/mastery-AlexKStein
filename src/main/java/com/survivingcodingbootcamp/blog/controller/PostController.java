package com.survivingcodingbootcamp.blog.controller;

import com.survivingcodingbootcamp.blog.model.Hashtag;
import com.survivingcodingbootcamp.blog.model.Post;
import com.survivingcodingbootcamp.blog.model.Topic;
import com.survivingcodingbootcamp.blog.repository.HashtagRepository;
import com.survivingcodingbootcamp.blog.repository.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/posts")
public class PostController {
    private PostRepository postRepo;
    private HashtagRepository hashtagRepo;

    public PostController(PostRepository postRepo, HashtagRepository hashtagRepo) {
        this.postRepo = postRepo;
        this.hashtagRepo = hashtagRepo;
    }


    @GetMapping("/{id}")
    public String displaySinglePost(@PathVariable long id, Model model) {
        model.addAttribute("post", postRepo.findById(id).get());
        return "single-post-template";
    }
    @PostMapping("/{id}/addHashtag")
    public String addHashtagToPost(@PathVariable Long id, @RequestParam String tag) {
        Post post = postRepo.findById(id).get();
        Optional<Hashtag> hashtagOptional = hashtagRepo.findByTagIgnoreCase(tag);
        if (hashtagOptional.isPresent()) {
            if (!post.getHashtags().contains(hashtagOptional.get())) {
                post.addHashtag(hashtagOptional.get());
            }
        } else {
            Hashtag hashtag = new Hashtag(tag);
            post.addHashtag(hashtag);
            hashtagRepo.save(hashtag);
        }
        postRepo.save(post);
        return "redirect:/posts/"+id;
    }

//    @PostMapping("/addPost")
//    public String addPost( @RequestParam String title, @RequestParam String author, @RequestParam String content, @RequestParam String hashtag){
//        Optional<Hashtag> hashtagOptional = hashtagRepo.findByTagIgnoreCase(hashtag);
//        Hashtag newPostTag;
//        if (!hashtagOptional.isPresent()) {
//            newPostTag = new Hashtag(hashtag);
//            hashtagRepo.save(newPostTag);
//        } else{
//            newPostTag = hashtagOptional.get();
//        }
//        Post newPost = new Post(title, author, "topic", content, newPostTag);
//
//        return "redirect:/topics/"+;
//    }

}
