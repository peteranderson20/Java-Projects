package edu.miracosta.cs112.capstone.View;

import edu.miracosta.cs112.capstone.Controller.Controller;
import edu.miracosta.cs112.capstone.Model.User;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class AddScene extends Scene {
    private Scene previousScene;
    public static final int WIDTH = 500;
    public static final int HEIGHT = 250;
    private Controller controller = Controller.getInstance();
    private TextField firstNameTF = new TextField();
    private Label firstNameErrLabel = new Label("First name is required.");

    private TextField lastNameTF = new TextField();
    private Label lastNameErrLabel = new Label("Last name is required.");

    private TextField usernameTF = new TextField();
    private Label usernameErrLabel = new Label("Username is required.");

    private TextField passwordTF = new TextField();
    private Label passwordErrLabel = new Label("Password is required.");

    private TextField cPasswordTF = new TextField();
    private Label cPasswordErrLabel = new Label("Passwords must match.");

    private Button saveButton = new Button("Save");
    private Button cancelButton = new Button("Cancel");

    private Label usernameAlreadyTaken = new Label("Username taken");
    private Label usernameBannedLabel = new Label("Username is banned");

    public AddScene(Scene previousScene) {
        super(new GridPane(), WIDTH, HEIGHT);
        this.previousScene = previousScene;

        GridPane pane = new GridPane();
        pane.setHgap(10.0);
        pane.setVgap(5);
        pane.setPadding(new Insets(5));

        pane.add(new Label("First name:"), 0, 0);
        pane.add(firstNameTF, 1, 0);
        pane.add(firstNameErrLabel, 2, 0);
        firstNameErrLabel.setTextFill(Color.RED);
        firstNameErrLabel.setVisible(false);

        pane.add(new Label("Last name:"), 0, 1);
        pane.add(lastNameTF, 1, 1);
        pane.add(lastNameErrLabel, 2, 1);
        lastNameErrLabel.setTextFill(Color.RED);
        lastNameErrLabel.setVisible(false);

        pane.add(new Label("Username:"), 0, 2);
        pane.add(usernameTF, 1, 2);
        pane.add(usernameErrLabel, 2, 2);
        usernameErrLabel.setTextFill(Color.RED);
        usernameErrLabel.setVisible(false);

        pane.add(new Label("Password:"), 0, 3);
        pane.add(passwordTF, 1, 3);
        pane.add(passwordErrLabel, 2, 3);
        passwordErrLabel.setTextFill(Color.RED);
        passwordErrLabel.setVisible(false);

        pane.add(new Label("Confirm Password:"), 0, 4);
        pane.add(cPasswordTF, 1, 4);
        pane.add(cPasswordErrLabel, 2, 4);
        cPasswordErrLabel.setTextFill(Color.RED);
        cPasswordErrLabel.setVisible(false);

        pane.add(cancelButton, 0, 7);
        pane.add(usernameAlreadyTaken, 0, 8);
        usernameAlreadyTaken.setTextFill(Color.RED);
        usernameAlreadyTaken.setVisible(false);
        pane.add(usernameBannedLabel, 0, 9);
        usernameBannedLabel.setTextFill(Color.RED);
        usernameBannedLabel.setVisible(false);
        pane.add(saveButton, 1, 7);

        saveButton.setOnAction(e -> save());
        cancelButton.setOnAction(e -> goBackToPrevScene());
        this.setRoot(pane);
    }

    private void goBackToPrevScene() {
        ViewNavigator.loadScene("Login", previousScene);
    }

    private void save() {
        String firstName = firstNameTF.getText();
        String lastName = lastNameTF.getText();
        String tempUsername = usernameTF.getText();
        String password = passwordTF.getText();
        String cPassword = cPasswordTF.getText();

        boolean firstNameEmpty = firstName.isEmpty();
        firstNameErrLabel.setVisible(firstNameEmpty);

        boolean lastNameEmpty = lastName.isEmpty();
        lastNameErrLabel.setVisible(lastNameEmpty);

        boolean isUsernameTaken = false;
        for (User user : controller.getAllUsers()) {
            if (user.getUsername().equals(tempUsername)) {
                isUsernameTaken = true;
                break;
            }
        }
        usernameAlreadyTaken.setVisible(isUsernameTaken);

        boolean isUsernameBanned = controller.isUsernameBanned(tempUsername);
        usernameBannedLabel.setVisible(isUsernameBanned);

        boolean passwordEmpty = password.isEmpty();
        passwordErrLabel.setVisible(passwordEmpty);

        boolean doPasswordsMatch = password.equals(cPassword);
        cPasswordErrLabel.setVisible(!doPasswordsMatch);

        if (firstNameEmpty || lastNameEmpty || isUsernameTaken || isUsernameBanned || passwordEmpty || !doPasswordsMatch) {
            return;
        }

        User newUser = new User(firstName, lastName, tempUsername, password);
        controller.addUser(newUser);

        goBackToPrevScene();
    }
}


