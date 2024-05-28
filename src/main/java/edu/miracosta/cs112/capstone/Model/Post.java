package edu.miracosta.cs112.capstone.Model;

import java.io.Serializable;
import java.util.UUID;

public abstract class Post implements Serializable, Comparable<Post> {
    private String content;
    private String author;
    private String id;

    public Post(String content, String author) {
        this.content = content;
        this.author = author;
        this.id = UUID.randomUUID().toString();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getId() { // Add this method
        return id;
    }

    @Override
    public int compareTo(Post other) {
        return this.content.compareTo(other.content);
    }
}
