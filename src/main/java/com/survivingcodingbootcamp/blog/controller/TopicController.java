package com.survivingcodingbootcamp.blog.controller;

import com.survivingcodingbootcamp.blog.model.Hashtag;
import com.survivingcodingbootcamp.blog.model.Post;
import com.survivingcodingbootcamp.blog.model.Topic;
import com.survivingcodingbootcamp.blog.repository.HashtagRepository;
import com.survivingcodingbootcamp.blog.repository.PostRepository;
import com.survivingcodingbootcamp.blog.repository.TopicRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/topics")
public class TopicController {

    private TopicRepository topicRepo;
    private HashtagRepository hashtagRepo;
    private PostRepository postRepo;

    public TopicController(TopicRepository topicRepo, HashtagRepository hashtagRepo, PostRepository postRepo) {
        this.topicRepo = topicRepo;
        this.hashtagRepo = hashtagRepo;
        this.postRepo = postRepo;
    }


    public TopicRepository getTopicRepo() {
        return topicRepo;
    }
    public HashtagRepository getHashtagRepo() {
        return hashtagRepo;
    }
    public PostRepository getPostRepo() {
        return postRepo;
    }

    @GetMapping("/{id}")
    public String displaySingleTopic(@PathVariable long id, Model model) {
        model.addAttribute("topic", topicRepo.findById(id).get());
        return "single-topic-template";
    }
    @PostMapping("{id}/addPost")
    public String addPost(@PathVariable long id, @RequestParam String title, @RequestParam String author, @RequestParam String content, @RequestParam String hashtag){
        Topic topic = topicRepo.findById(id).get();
        Optional<Hashtag> hashtagOptional = hashtagRepo.findByTagIgnoreCase(hashtag);
        Hashtag newPostTag;
        if (!hashtagOptional.isPresent()) {
            newPostTag = new Hashtag(hashtag);
            hashtagRepo.save(newPostTag);
        } else{
            newPostTag = hashtagOptional.get();
        }
        Post newPost = new Post(title, author, topic, content, newPostTag);
        postRepo.save(newPost);
        return "redirect:/topics/"+id;
    }
}
