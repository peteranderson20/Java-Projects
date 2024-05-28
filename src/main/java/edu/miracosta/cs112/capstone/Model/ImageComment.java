package edu.miracosta.cs112.capstone.Model;

import java.io.Serializable;

public class ImageComment extends Comment implements Serializable, Comparable<Comment> {
    private static final long serialVersionUID = 1L;

    private String imagePath;
    private String textComment;

    public ImageComment(String content, String author, String postId, String imagePath, String textComment) {
        super(content, author, postId);
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
    public int compareTo(Comment other) {
        if (other instanceof ImageComment) {
            return this.imagePath.compareTo(((ImageComment) other).imagePath);
        }
        return super.getContent().compareTo(other.getContent());
    }
}






