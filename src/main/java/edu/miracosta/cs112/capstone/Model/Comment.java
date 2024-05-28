package edu.miracosta.cs112.capstone.Model;

import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class Comment implements Serializable, Comparable<Comment> {
    private static final long serialVersionUID = 1L;

    private String content;
    private String author;
    private String postId;

    public Comment(String content, String author, String postId) {
        this.content = content;
        this.author = author;
        this.postId = postId;
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

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(postId);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        postId = (String) in.readObject();
        if (postId == null) {
            postId = "";
        }
    }

    @Override
    public int compareTo(Comment other) {
        return this.content.compareTo(other.content);
    }
}




