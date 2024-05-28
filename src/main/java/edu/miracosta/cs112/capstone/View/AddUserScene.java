package edu.miracosta.cs112.capstone.View;

import edu.miracosta.cs112.capstone.Controller.Controller;
import edu.miracosta.cs112.capstone.Model.Admin;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class AddUserScene extends Scene {
    private AdminView previousScene;
    private Controller controller = Controller.getInstance();
    private Admin currentAdmin;

    private TextField firstNameField = new TextField();
    private TextField lastNameField = new TextField();
    private TextField usernameField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private CheckBox adminCheckBox = new CheckBox("Add as Admin");
    private TextField adminLevelField = new TextField();
    private Button addUserButton = new Button("Add User");
    private Button cancelButton = new Button("Cancel");
    private Label errorMessage = new Label();

    public AddUserScene(AdminView previousScene, Admin currentAdmin) {
        super(new GridPane(), 400, 350);
        this.previousScene = previousScene;
        this.currentAdmin = currentAdmin;

        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(20));
        pane.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Add New User");
        titleLabel.setFont(new Font("Arial", 20));
        pane.add(titleLabel, 0, 0, 2, 1);

        pane.add(new Label("First Name:"), 0, 1);
        pane.add(firstNameField, 1, 1);

        pane.add(new Label("Last Name:"), 0, 2);
        pane.add(lastNameField, 1, 2);

        pane.add(new Label("Username:"), 0, 3);
        pane.add(usernameField, 1, 3);

        pane.add(new Label("Password:"), 0, 4);
        pane.add(passwordField, 1, 4);

        pane.add(adminCheckBox, 0, 5, 2, 1);
        adminLevelField.setPromptText("Admin Level (admin/moderator)");
        adminLevelField.setVisible(false);
        pane.add(adminLevelField, 0, 6, 2, 1);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(addUserButton, cancelButton);
        pane.add(buttonBox, 0, 7, 2, 1);

        errorMessage.setStyle("-fx-text-fill: red;");
        pane.add(errorMessage, 0, 8, 2, 1);

        adminCheckBox.setOnAction(e -> adminLevelField.setVisible(adminCheckBox.isSelected()));
        addUserButton.setOnAction(e -> addUser());
        cancelButton.setOnAction(e -> cancel());

        this.setRoot(pane);
    }

    private void addUser() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String adminLevel = adminCheckBox.isSelected() ? adminLevelField.getText() : "";

        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty() || (adminCheckBox.isSelected() && adminLevel.isEmpty())) {
            errorMessage.setText("All fields must be filled out.");
            return;
        }

        controller.addUser(firstName, lastName, username, password, adminLevel);
        ViewNavigator.loadScene("Admin View", previousScene);
    }

    private void cancel() {
        ViewNavigator.loadScene("Admin View", previousScene);
    }
}

