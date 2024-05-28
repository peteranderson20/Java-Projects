package edu.miracosta.cs112.capstone.Model;

import java.io.Serializable;

public class VideoComment extends Comment implements Serializable, Comparable<Comment> {
    private static final long serialVersionUID = 1L;

    private String videoPath;
    private String textComment;

    public VideoComment(String content, String author, String postId, String videoPath, String textComment) {
        super(content, author, postId);
        this.videoPath = videoPath;
        this.textComment = textComment;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getTextComment() {
        return textComment;
    }

    public void setTextComment(String textComment) {
        this.textComment = textComment;
    }

    @Override
    public int compareTo(Comment other) {
        if (other instanceof VideoComment) {
            return this.videoPath.compareTo(((VideoComment) other).videoPath);
        }
        return super.getContent().compareTo(other.getContent());
    }
}






