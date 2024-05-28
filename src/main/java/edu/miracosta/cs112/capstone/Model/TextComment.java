package edu.miracosta.cs112.capstone.Model;

import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TextComment extends Comment implements Serializable, Comparable<Comment> {
    private static final long serialVersionUID = 1L;

    public TextComment(String content, String author, String postId) {
        super(content, author, postId);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(getPostId());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        setPostId((String) in.readObject());
        if (getPostId() == null) {
            setPostId("");
        }
    }

    @Override
    public int compareTo(Comment other) {
        return super.compareTo(other);
    }
}



