package edu.miracosta.cs112.capstone.Model;

import java.io.Serializable;

public class ImagePost extends Post implements Serializable, Comparable<Post> {
    private String imagePath;
    private String textComment;

    public ImagePost(String content, String author, String imagePath, String textComment) {
        super(content, author);
        this.imagePath = imagePath;
        this.textComment = textComment;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getTextComment() {
        return textComment;
    }

    public void setTextComment(String textComment) {
        this.textComment = textComment;
    }

    @Override
    public String getContent() {
        return "Image: " + imagePath + "\nComment: " + textComment;
    }

    @Override
    public int compareTo(Post other) {
        if (other instanceof ImagePost) {
            return this.imagePath.compareTo(((ImagePost) other).imagePath);
        }
        return super.compareTo(other);
    }
}


