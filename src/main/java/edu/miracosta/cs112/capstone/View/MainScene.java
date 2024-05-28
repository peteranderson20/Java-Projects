package edu.miracosta.cs112.capstone.View;

import edu.miracosta.cs112.capstone.Controller.Controller;
import edu.miracosta.cs112.capstone.Model.Admin;
import edu.miracosta.cs112.capstone.Model.Model;
import edu.miracosta.cs112.capstone.Model.User;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;


public class MainScene extends Scene {
    public static final int WIDTH = 850;
    public static final int HEIGHT = 600;

    private Label label;
    private Label loginLabel;
    private Label usernameLabel;
    private TextField usernameTF;
    private Label passwordLabel;
    private PasswordField passwordTF;
    private HBox hboxUsernameTF;
    private HBox hboxUsernameLabel;
    private HBox hboxPasswordLabel;
    private HBox hboxPasswordTF;
    private HBox hboxSignIn;
    private GridPane pane;
    private ColumnConstraints columnConstraints;
    private RowConstraints rowConstraintsNullPointer;
    private RowConstraints rowConstraintsLoginLabel;
    private RowConstraints rowConstraintsUsernameLabel;
    private RowConstraints rowConstraintsUsernameTF;
    private RowConstraints rowConstraintsPasswordLabel;
    private RowConstraints rowConstraintsPasswordTF;

    private RowConstraints rowConstraintsLoginButton;
    private RowConstraints rowConstraintsSignInButton;

    private Button loginButton;
    private HBox hboxLoginButton;
    private Label signIn;
    private Button signInButton;
    private Label errorMessage;

    private Controller controller = Controller.getInstance();

    public MainScene() {
        super(new GridPane(), WIDTH, HEIGHT);

        label = new Label("NullPointer");
        label.setFont(new Font("Arial", 48));
        label.setStyle("-fx-font-weight: bold;");

        loginLabel = new Label("Login");
        loginLabel.setFont(new Font("Arial", 30));
        loginLabel.setStyle("-fx-font-weight: bold;");

        usernameLabel = new Label("Username");
        usernameLabel.setFont(new Font("Arial", 20));
        usernameLabel.setStyle("-fx-font-weight: bold;");

        passwordLabel = new Label("Password");
        passwordLabel.setFont(new Font("Arial", 20));
        passwordLabel.setStyle("-fx-font-weight: bold;");

        signIn = new Label("Don't have an account? ");
        signInButton = new Button("Sign Up");

        loginButton = new Button("Login");
        hboxLoginButton = new HBox(loginButton);
        hboxLoginButton.setAlignment(Pos.CENTER);

        usernameTF = new TextField();
        usernameTF.setPrefWidth(300);

        passwordTF = new PasswordField();
        passwordTF.setPrefWidth(300);

        hboxUsernameTF = new HBox(usernameTF);
        hboxUsernameTF.setAlignment(Pos.CENTER);

        hboxUsernameLabel = new HBox(usernameLabel);
        hboxUsernameLabel.setAlignment(Pos.CENTER);

        hboxPasswordLabel = new HBox(passwordLabel);
        hboxPasswordLabel.setAlignment(Pos.CENTER);

        hboxPasswordTF = new HBox(passwordTF);
        hboxPasswordTF.setAlignment(Pos.CENTER);

        hboxSignIn = new HBox(signIn, signInButton);
        hboxSignIn.setAlignment(Pos.CENTER);

        errorMessage = new Label();
        errorMessage.setStyle("-fx-text-fill: red;");
        errorMessage.setVisible(false);

        pane = new GridPane();

        columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(100);
        pane.getColumnConstraints().add(columnConstraints);

        rowConstraintsNullPointer = new RowConstraints();
        rowConstraintsNullPointer.setPercentHeight(30);
        rowConstraintsLoginLabel = new RowConstraints();
        rowConstraintsLoginLabel.setPercentHeight(15);
        rowConstraintsUsernameLabel = new RowConstraints();
        rowConstraintsUsernameLabel.setPercentHeight(10);
        rowConstraintsUsernameTF = new RowConstraints();
        rowConstraintsUsernameTF.setPercentHeight(5);
        rowConstraintsPasswordLabel = new RowConstraints();
        rowConstraintsPasswordLabel.setPercentHeight(10);
        rowConstraintsPasswordTF = new RowConstraints();
        rowConstraintsPasswordTF.setPercentHeight(5);
        rowConstraintsLoginButton = new RowConstraints();
        rowConstraintsLoginButton.setPercentHeight(10);
        rowConstraintsSignInButton = new RowConstraints();
        rowConstraintsSignInButton.setPercentHeight(10);
        pane.getRowConstraints().addAll(rowConstraintsNullPointer, rowConstraintsLoginLabel, rowConstraintsUsernameLabel, rowConstraintsUsernameTF, rowConstraintsPasswordLabel, rowConstraintsPasswordTF, rowConstraintsLoginButton, rowConstraintsSignInButton);

        pane.add(label, 0, 0);
        GridPane.setHalignment(label, HPos.CENTER);

        pane.add(loginLabel, 0, 1);
        GridPane.setHalignment(loginLabel, HPos.CENTER);

        pane.add(hboxUsernameLabel, 0, 2);
        GridPane.setHalignment(hboxUsernameLabel, HPos.CENTER);

        pane.add(hboxUsernameTF, 0, 3);
        GridPane.setHalignment(hboxUsernameTF, HPos.CENTER);

        pane.add(hboxPasswordLabel, 0, 4);
        GridPane.setHalignment(hboxPasswordLabel, HPos.CENTER);

        pane.add(hboxPasswordTF, 0, 5);
        GridPane.setHalignment(hboxPasswordTF, HPos.CENTER);

        pane.add(hboxLoginButton, 0, 6);
        GridPane.setHalignment(hboxLoginButton, HPos.CENTER);

        pane.add(hboxSignIn, 0, 7);
        GridPane.setHalignment(hboxSignIn, HPos.CENTER);

        pane.add(errorMessage, 0, 8);
        GridPane.setHalignment(errorMessage, HPos.CENTER);

        loginButton.setOnAction(e -> login());
        signInButton.setOnAction(e -> signUp());

        this.setRoot(pane);
    }

    private void signUp() {
        ViewNavigator.loadScene("Sign Up", new AddScene(this));
        usernameTF.clear();
        passwordTF.clear();
    }

    private void login() {
        String inputUsername = usernameTF.getText();
        String inputPassword = passwordTF.getText();
        errorMessage.setVisible(false);

        if (controller.isUsernameBanned(inputUsername)) {
            errorMessage.setText("You are banned from logging in.");
            errorMessage.setVisible(true);
            return;
        }

        System.out.println("Loaded users: ");
        for (User user : controller.getAllUsers()) {
            System.out.println("Username: " + user.getUsername() + ", Password: " + user.getPassword());
        }

        for (User user : controller.getAllUsers()) {
            if (user.getUsername().equals(inputUsername) && user.getPassword().equals(inputPassword)) {
                if (user instanceof Admin) {
                    Admin admin = (Admin) user;
                    controller.setCurrentUser(admin);
                    ViewNavigator.loadScene("Admin View", new AdminView(this, admin));
                } else {
                    controller.setCurrentUser(user);
                    ViewNavigator.loadScene("User View", new UserView(this));
                }
                usernameTF.clear();
                passwordTF.clear();
                return;
            }
        }

        errorMessage.setText("Invalid username or password.");
        errorMessage.setVisible(true);

    }
}





