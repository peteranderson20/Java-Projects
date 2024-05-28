package edu.miracosta.cs112.capstone.Controller;

import edu.miracosta.cs112.capstone.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Controller {
    private static Controller instance;
    private ObservableList<User> allUsers;
    private ObservableList<String> bannedUsernames;
    private ObservableList<Post> allPosts;
    private ObservableList<Comment> allComments;
    private User currentUser;

    private Controller() {}

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();

            if (Model.binaryFileHasData(Model.BINARY_FILE)) {
                instance.allUsers = Model.populateListFromBinaryFile();
            } else {
                instance.allUsers = Model.populateListFromCSVFile();
                if (instance.allUsers.isEmpty()) {
                    instance.allUsers.add(new Admin("Peter", "Anderson", "peterA", "password", "admin"));
                    instance.allUsers.add(new Admin("John", "Doe", "johnD", "password", "moderator"));
                }
                Model.writeDataToBinaryFile(instance.allUsers);
            }

            if (Model.binaryFileHasData(Model.BANNED_BINARY_FILE)) {
                instance.bannedUsernames = Model.populateBannedUsernamesFromBinaryFile();
            } else {
                instance.bannedUsernames = FXCollections.observableArrayList();
            }

            if (Model.binaryFileHasData(Model.POSTS_BINARY_FILE)) {
                instance.allPosts = Model.populatePostsFromBinaryFile();
            } else {
                instance.allPosts = FXCollections.observableArrayList();
            }

            if (Model.binaryFileHasData(Model.COMMENTS_BINARY_FILE)) {
                instance.allComments = Model.populateCommentsFromBinaryFile();
            } else {
                instance.allComments = FXCollections.observableArrayList();
            }
        }
        return instance;
    }

    public ObservableList<User> getAllUsers() {
        return allUsers;
    }

    public void addUser(String firstName, String lastName, String username, String password, String adminLevel) {
        if (bannedUsernames.contains(username)) {
            System.out.println("Cannot add user. The username is banned.");
            return;
        }

        if (adminLevel == null || adminLevel.isEmpty()) {
            User newUser = new User(firstName, lastName, username, password);
            allUsers.add(newUser);
        } else {
            Admin newAdmin = new Admin(firstName, lastName, username, password, adminLevel);
            allUsers.add(newAdmin);
        }
    }

    public void addUser(User user) {
        if (bannedUsernames.contains(user.getUsername())) {
            System.out.println("Cannot add user. The username is banned.");
            return;
        }
        allUsers.add(user);
        saveData();
    }

    public boolean isUsernameBanned(String username) {
        return bannedUsernames.contains(username);
    }

    public void removeUser(User u) {
        System.out.println(allUsers.size());
        allUsers.remove(u);
        System.out.println(allUsers.size());
        saveData();
    }

    public void banUser(String username) {
        bannedUsernames.add(username);
        saveData();
    }

    public ObservableList<Post> getAllPosts() {
        return allPosts;
    }

    public void addPost(Post post) {
        allPosts.add(post);
        savePosts();
    }

    public void removePost(Post post) {
        allPosts.remove(post);
        savePosts();
    }

    private void savePosts() {
        System.out.println("Saving posts...");
        Model.writePostsToBinaryFile(allPosts);
    }

    public ObservableList<Comment> getAllComments() {
        return allComments;
    }

    public void addComment(Comment comment) {
        allComments.add(comment);
        saveComments();
    }

    public void removeComment(Comment comment) {
        System.out.println("Removing comment: " + comment.getContent());
        allComments.remove(comment);
        System.out.println("Comment removed. Current number of comments in allComments: " + allComments.size());
        saveComments();
    }

    private void saveComments() {
        System.out.println("Saving comments... Current number of comments: " + allComments.size());
        Model.writeCommentsToBinaryFile(allComments);
        System.out.println("Comments saved.");
    }

    public void saveCommentDeletion(Comment comment) {
        System.out.println("Deleting comment: " + comment.getContent());
        allComments.removeIf(c -> c.getContent().equals(comment.getContent()) && c.getAuthor().equals(comment.getAuthor()));
        saveComments();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void saveData() {
        Model.writeDataToBinaryFile(allUsers);
        Model.writeCommentsToBinaryFile(allComments);
        Model.writeBannedUsernamesToBinaryFile(bannedUsernames);
        Model.writePostsToBinaryFile(allPosts);
        savePosts();
        saveComments();
    }
}






