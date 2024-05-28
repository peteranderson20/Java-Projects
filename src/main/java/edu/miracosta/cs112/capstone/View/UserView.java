package edu.miracosta.cs112.capstone.View;

import edu.miracosta.cs112.capstone.Controller.Controller;
import edu.miracosta.cs112.capstone.Model.*;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class UserView extends Scene {

    public static final int WIDTH = 850;
    public static final int HEIGHT = 600;
    private Scene previousScene;
    private Controller controller = Controller.getInstance();
    private VBox postsContainer;
    private ObservableList<Comment> allComments;

    public UserView(Scene previousScene) {
        super(new AnchorPane(), WIDTH, HEIGHT);
        this.previousScene = previousScene;
        AnchorPane root = (AnchorPane) getRoot();


        allComments = Model.populateCommentsFromBinaryFile();
        System.out.println("Initial number of comments loaded: " + allComments.size());


        String currentUsername = controller.getCurrentUser().getUsername();
        Text title = new Text("Hello, " + currentUsername);
        title.setStyle("-fx-font-size: 20px;");
        HBox titleContainer = new HBox(title);
        titleContainer.setPadding(new Insets(20, 10, 20, 10));
        titleContainer.setAlignment(Pos.CENTER);


        HBox topRightButtons = new HBox(20);
        topRightButtons.setPadding(new Insets(20, 10, 20, 10));
        topRightButtons.setAlignment(Pos.CENTER);

        Button createTextPostButton = new Button("Create Text Post");
        Button createImagePostButton = new Button("Create Image Post");
        Button createVideoPostButton = new Button("Create Video Post");
        Button logoutButton = new Button("Logout");

        createTextPostButton.setOnAction(event -> createTextPost());
        createImagePostButton.setOnAction(event -> createImagePost());
        createVideoPostButton.setOnAction(event -> createVideoPost());
        logoutButton.setOnAction(event -> logout());

        topRightButtons.getChildren().addAll(createTextPostButton, createImagePostButton, createVideoPostButton, logoutButton);


        VBox topContainer = new VBox(10);
        topContainer.setPadding(new Insets(10));
        topContainer.setSpacing(20);
        topContainer.setAlignment(Pos.CENTER);
        topContainer.getChildren().addAll(titleContainer, topRightButtons);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefSize(WIDTH - 20, HEIGHT - 70);

        postsContainer = new VBox(10);
        postsContainer.setPadding(new Insets(10));
        postsContainer.setSpacing(20);
        scrollPane.setContent(postsContainer);


        for (Post post : controller.getAllPosts()) {
            postsContainer.getChildren().add(createPostBox(post));
        }


        root.getChildren().addAll(topContainer, scrollPane);
        AnchorPane.setTopAnchor(topContainer, 10.0);
        AnchorPane.setLeftAnchor(topContainer, 10.0);
        AnchorPane.setRightAnchor(topContainer, 10.0);

        AnchorPane.setTopAnchor(scrollPane, 150.0);
        AnchorPane.setLeftAnchor(scrollPane, 10.0);
        AnchorPane.setRightAnchor(scrollPane, 10.0);
        AnchorPane.setBottomAnchor(scrollPane, 10.0);
    }

    private VBox createPostBox(Post post) {
        VBox postBox = new VBox(10);
        postBox.setPadding(new Insets(10));
        postBox.setStyle("-fx-border-color: black; -fx-border-width: 1;");

        HBox headerBox = new HBox(10);
        headerBox.setAlignment(Pos.CENTER_LEFT);

        Text usernameText = new Text(post.getAuthor());
        Text contentText = new Text(post.getContent());

        headerBox.getChildren().addAll(usernameText);


        if (post.getAuthor().equals(controller.getCurrentUser().getUsername())) {
            Button deletePostButton = new Button("X");
            deletePostButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            deletePostButton.setOnAction(event -> deletePost(post));
            headerBox.getChildren().add(deletePostButton);
        }

        postBox.getChildren().add(headerBox);

        if (post instanceof ImagePost) {
            ImagePost imagePost = (ImagePost) post;
            ImageView imageView = createImageView(imagePost.getImagePath());
            Text textComment = new Text(imagePost.getTextComment());
            postBox.getChildren().addAll(imageView, textComment);
        } else if (post instanceof VideoPost) {
            VideoPost videoPost = (VideoPost) post;
            VBox mediaBox = createMediaBox(videoPost.getVideoPath());
            Text textComment = new Text(videoPost.getTextComment());
            postBox.getChildren().addAll(mediaBox, textComment);
        } else {
            postBox.getChildren().add(contentText);
        }

        Button commentButton = new Button("Comment");
        commentButton.setOnAction(event -> commentOnPost(post));

        VBox commentsBox = new VBox();
        commentsBox.setVisible(false);

        Button toggleCommentsButton = new Button("Show Comments");
        toggleCommentsButton.setOnAction(event -> {
            commentsBox.setVisible(!commentsBox.isVisible());
            if (commentsBox.isVisible()) {
                toggleCommentsButton.setText("Hide Comments");
                loadCommentsForPost(post, commentsBox);
            } else {
                toggleCommentsButton.setText("Show Comments");
            }
        });

        postBox.getChildren().addAll(commentButton, toggleCommentsButton, commentsBox);
        return postBox;
    }

    private ImageView createImageView(String imagePath) {
        Image image;
        try {
            image = new Image(new FileInputStream(imagePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new ImageView();
        }

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(400);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    private VBox createMediaBox(String videoPath) {
        VBox mediaBox = new VBox(10);
        mediaBox.setAlignment(Pos.CENTER);

        try {
            Media media = new Media(new File(videoPath).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            MediaView mediaView = new MediaView(mediaPlayer);
            mediaView.setFitWidth(400);
            mediaView.setPreserveRatio(true);

            Button playButton = new Button("Play");
            playButton.setOnAction(event -> mediaPlayer.play());

            Button stopButton = new Button("Stop");
            stopButton.setOnAction(event -> mediaPlayer.stop());

            HBox controlsBox = new HBox(10, playButton, stopButton);
            controlsBox.setAlignment(Pos.CENTER);

            mediaBox.getChildren().addAll(mediaView, controlsBox);
        } catch (MediaException e) {
            Label errorLabel = new Label("Unsupported media type: " + videoPath);
            mediaBox.getChildren().add(errorLabel);
            System.err.println("MediaException: " + e.getMessage());
        }

        return mediaBox;
    }

    private void createTextPost() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create Text Post");
        dialog.setHeaderText("Enter your post content:");
        dialog.setContentText("Content:");

        dialog.showAndWait().ifPresent(content -> {
            if (!content.isEmpty()) {
                String currentUsername = controller.getCurrentUser().getUsername();
                Post newPost = new TextPost(content, currentUsername);
                controller.addPost(newPost);
                postsContainer.getChildren().add(createPostBox(newPost));
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Post content cannot be empty!", ButtonType.OK);
                alert.showAndWait();
            }
        });
    }

    private void createImagePost() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        File file = fileChooser.showOpenDialog(this.getWindow());

        if (file != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add a comment to your image");
            dialog.setHeaderText("Enter your comment:");
            dialog.setContentText("Comment:");

            dialog.showAndWait().ifPresent(comment -> {
                String currentUsername = controller.getCurrentUser().getUsername();
                Post newPost = new ImagePost(file.getAbsolutePath(), currentUsername, file.getAbsolutePath(), comment);
                controller.addPost(newPost);
                postsContainer.getChildren().add(createPostBox(newPost));
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No file chosen!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void createVideoPost() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Video File");
        File file = fileChooser.showOpenDialog(this.getWindow());

        if (file != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add a comment to your video");
            dialog.setHeaderText("Enter your comment:");
            dialog.setContentText("Comment:");

            dialog.showAndWait().ifPresent(comment -> {
                String currentUsername = controller.getCurrentUser().getUsername();
                Post newPost = new VideoPost(file.getAbsolutePath(), currentUsername, comment);
                controller.addPost(newPost);
                postsContainer.getChildren().add(createPostBox(newPost));
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No file chosen!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void deletePost(Post post) {
        controller.removePost(post);
        postsContainer.getChildren().clear();
        controller.getAllPosts().forEach(p -> postsContainer.getChildren().add(createPostBox(p)));
    }

    private void deleteComment(Comment comment) {
        System.out.println("Attempting to delete comment: " + comment.getContent());
        if (comment != null && comment.getAuthor().equals(controller.getCurrentUser().getUsername())) {
            System.out.println("User is authorized to delete this comment.");
            controller.saveCommentDeletion(comment);
            allComments.remove(comment);
            System.out.println("Comment removed from the local list. Current number of comments in local list: " + allComments.size());
            postsContainer.getChildren().clear();
            controller.getAllPosts().forEach(post -> postsContainer.getChildren().add(createPostBox(post)));
            System.out.println("Comment deleted.");
        } else {
            System.out.println("User is not authorized to delete this comment.");
            Alert alert = new Alert(Alert.AlertType.WARNING, "Comment not found or you are not the author!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void saveDeletion(Comment comment) {
        System.out.println("Saving deletion for comment: " + comment.getContent());
        controller.saveCommentDeletion(comment);
    }

    private void logout() {
        ViewNavigator.loadScene("Login", previousScene);
    }

    private void commentOnPost(Post post) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Comment on Post");
        dialog.setHeaderText("Choose comment type:");

        ButtonType textButtonType = new ButtonType("Text", ButtonBar.ButtonData.OK_DONE);
        ButtonType imageButtonType = new ButtonType("Image", ButtonBar.ButtonData.OK_DONE);
        ButtonType videoButtonType = new ButtonType("Video", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(textButtonType, imageButtonType, videoButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> dialogButton);

        dialog.showAndWait().ifPresent(type -> {
            if (type == textButtonType) {
                createTextComment(post);
            } else if (type == imageButtonType) {
                createImageComment(post);
            } else if (type == videoButtonType) {
                createVideoComment(post);
            }
        });
    }

    private void createTextComment(Post post) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create Text Comment");
        dialog.setHeaderText("Enter your comment:");
        dialog.setContentText("Comment:");

        dialog.showAndWait().ifPresent(comment -> {
            if (!comment.isEmpty()) {
                String currentUsername = controller.getCurrentUser().getUsername();
                Comment newComment = new TextComment(comment, currentUsername, post.getId());
                controller.addComment(newComment);
                allComments.add(newComment);
                System.out.println("New text comment added: " + comment);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Comment added!", ButtonType.OK);
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Comment content cannot be empty!", ButtonType.OK);
                alert.showAndWait();
            }
        });
    }

    private void createImageComment(Post post) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        File file = fileChooser.showOpenDialog(this.getWindow());

        if (file != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add a comment to your image");
            dialog.setHeaderText("Enter your comment:");
            dialog.setContentText("Comment:");

            dialog.showAndWait().ifPresent(comment -> {
                String currentUsername = controller.getCurrentUser().getUsername();
                Comment newComment = new ImageComment(file.getAbsolutePath(), currentUsername, post.getId(), file.getAbsolutePath(), comment);
                controller.addComment(newComment);
                allComments.add(newComment);
                System.out.println("New image comment added: " + file.getAbsolutePath());
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Comment added!", ButtonType.OK);
                alert.showAndWait();
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No file chosen!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void createVideoComment(Post post) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Video File");
        File file = fileChooser.showOpenDialog(this.getWindow());

        if (file != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add a comment to your video");
            dialog.setHeaderText("Enter your comment:");
            dialog.setContentText("Comment:");

            dialog.showAndWait().ifPresent(comment -> {
                String currentUsername = controller.getCurrentUser().getUsername();
                Comment newComment = new VideoComment(file.getAbsolutePath(), currentUsername, post.getId(), file.getAbsolutePath(), comment);
                controller.addComment(newComment);
                allComments.add(newComment);
                System.out.println("New video comment added: " + file.getAbsolutePath());
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Comment added!", ButtonType.OK);
                alert.showAndWait();
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No file chosen!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void loadCommentsForPost(Post post, VBox commentsBox) {
        commentsBox.getChildren().clear();
        for (Comment comment : allComments) {
            if (comment.getPostId().equals(post.getId())) {
                HBox commentBox = new HBox(10);
                if (comment instanceof ImageComment) {
                    ImageComment imageComment = (ImageComment) comment;
                    ImageView imageView = createImageView(imageComment.getImagePath());
                    Text textComment = new Text(imageComment.getTextComment());
                    commentBox.getChildren().addAll(imageView, textComment);
                } else if (comment instanceof VideoComment) {
                    VideoComment videoComment = (VideoComment) comment;
                    VBox mediaBox = createMediaBox(videoComment.getVideoPath());
                    Text textComment = new Text(videoComment.getTextComment());
                    commentBox.getChildren().addAll(mediaBox, textComment);
                } else {
                    Label commentLabel = new Label(comment.getContent() + " - " + comment.getAuthor());
                    commentBox.getChildren().add(commentLabel);
                }

                Button deleteCommentButton = new Button("Delete");
                deleteCommentButton.setOnAction(event -> deleteComment(comment));

                commentBox.getChildren().add(deleteCommentButton);
                commentsBox.getChildren().add(commentBox);
            }
        }
    }

}




















