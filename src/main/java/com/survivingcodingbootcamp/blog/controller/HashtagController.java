package com.survivingcodingbootcamp.blog.controller;

import com.survivingcodingbootcamp.blog.model.Hashtag;
import com.survivingcodingbootcamp.blog.repository.HashtagRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/hashtags")
public class HashtagController {
    private HashtagRepository hashtagRepo;

    public HashtagController(HashtagRepository hashtagRepo) {
        this.hashtagRepo = hashtagRepo;
    }
    @GetMapping("/{id}")
    public String displaySingleHashtag(Model model, @PathVariable long id){
        model.addAttribute("hashtag", hashtagRepo.findById(id).get());
        return "single-hashtag-template";
    }
    @GetMapping
    public String displayAllHashtags(Model model){
        model.addAttribute("hashtags", hashtagRepo.findAll());
        return "all-hashtags-template";
    }
    @PostMapping("/addHashtag")
    public String addHashtag(@RequestParam String tag) {
        Optional<Hashtag> hashtagOptional = hashtagRepo.findByTagIgnoreCase(tag);
        if (!hashtagOptional.isPresent()) {
            Hashtag hashtag = new Hashtag(tag);
            hashtagRepo.save(hashtag);
        }
        return "redirect:/hashtags/";
//        else{ return "Hashtag already exists";}
    }
}
