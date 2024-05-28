package edu.miracosta.cs112.capstone.Model;

import java.io.Serializable;

public class VideoPost extends Post implements Serializable, Comparable<Post> {
    private static final long serialVersionUID = 1L;

    private String videoPath;
    private String textComment;

    public VideoPost(String videoPath, String author, String textComment) {
        super("Video Post", author);
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
    public String getContent() {
        return "Video: " + videoPath + "\nComment: " + textComment;
    }

    @Override
    public int compareTo(Post other) {
        if (other instanceof VideoPost) {
            return this.videoPath.compareTo(((VideoPost) other).videoPath);
        }
        return super.compareTo(other);
    }
}



