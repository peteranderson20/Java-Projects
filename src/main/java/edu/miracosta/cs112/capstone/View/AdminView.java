package edu.miracosta.cs112.capstone.View;

import edu.miracosta.cs112.capstone.Controller.Controller;
import edu.miracosta.cs112.capstone.Model.Admin;
import edu.miracosta.cs112.capstone.Model.User;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class AdminView extends Scene {
    private Scene previousScene;
    public static final int WIDTH = 850;
    public static final int HEIGHT = 600;
    private Controller controller = Controller.getInstance();

    private ListView<User> usersLV = new ListView<>();
    private Label usersLabel;

    private ObservableList<User> userList;
    private Button deleteUser = new Button("Delete");
    private Button banUser = new Button("Ban");
    private Button addUser = new Button("Add new");
    private Button logout = new Button("Log Out");

    private Admin currentAdmin;

    public AdminView(Scene previousScene, Admin currentAdmin) {
        super(new GridPane(), WIDTH, HEIGHT);
        this.previousScene = previousScene;
        this.currentAdmin = currentAdmin;

        GridPane pane = new GridPane();
        pane.setHgap(10.0);
        pane.setVgap(10);
        pane.setPadding(new Insets(10));

        usersLabel = new Label("Users");
        usersLabel.setFont(new Font("Arial", 30));
        usersLabel.setStyle("-fx-font-weight: bold;");

        userList = controller.getAllUsers();
        usersLV.setItems(userList);
        usersLV.setPrefWidth(WIDTH);

        logout.setOnAction(e -> logoutMethod());

        if (currentAdmin.canDeleteUser()) {
            deleteUser.setOnAction(e -> deleteUserMethod());
        } else {
            deleteUser.setDisable(true);
        }

        if (currentAdmin.canBanUser()) {
            banUser.setOnAction(e -> banUserMethod());
        } else {
            banUser.setDisable(true);
        }

        if (currentAdmin.canAddUser()) {
            addUser.setOnAction(e -> addUserMethod());
        } else {
            addUser.setDisable(true);
        }

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(deleteUser, addUser, banUser);

        VBox bottomBox = new VBox(10);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.getChildren().addAll(buttonBox, logout);

        pane.add(usersLabel, 0, 0, 3, 1);
        GridPane.setHalignment(usersLabel, HPos.CENTER);
        pane.add(usersLV, 0, 1, 3, 1);
        pane.add(bottomBox, 0, 2, 3, 1);

        this.setRoot(pane);
    }

    private void logoutMethod() {
        ViewNavigator.loadScene("Login", previousScene);
    }

    private void deleteUserMethod() {
        User selectedUser = usersLV.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            controller.removeUser(selectedUser);
            userList.remove(selectedUser);
        }
    }

    private void banUserMethod() {
        User selectedUser = usersLV.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            controller.banUser(selectedUser.getUsername());
            userList.remove(selectedUser);
        }
    }

    private void addUserMethod() {
        ViewNavigator.loadScene("Add User", new AddUserScene(this, currentAdmin));
    }
}

