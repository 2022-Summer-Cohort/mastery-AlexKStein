package com.survivingcodingbootcamp.blog.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Arrays;
import java.util.Collection;

@Entity
public class Hashtag {
    @Id
    @GeneratedValue
    private long id;
    private String tag;
    @ManyToMany(mappedBy = "hashtags")
    private Collection<Post> posts;

    public Hashtag(String tag, Post... posts) {
        this.tag = tag;
        this.posts = Arrays.asList(posts);
    }
    public Hashtag() {
    }

    public long getId() {
        return id;
    }
    public String getTag() {
        return tag;
    }
    public Collection<Post> getPosts() {
        return posts;
    }
}
