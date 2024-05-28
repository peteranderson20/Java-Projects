package edu.miracosta.cs112.capstone.Model;
import java.io.Serializable;

public class TextPost extends Post implements Serializable, Comparable<Post> {

    public TextPost(String content, String author) {
        super(content, author);
    }

    @Override
    public int compareTo(Post other) {
        return super.compareTo(other);
    }
}