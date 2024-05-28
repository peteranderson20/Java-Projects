package edu.miracosta.cs112.capstone.View;

import edu.miracosta.cs112.capstone.Controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class View extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        ViewNavigator.setStage(primaryStage);
        ViewNavigator.loadScene("Login", new MainScene());
    }

    public void stop() throws Exception{
        Controller.getInstance().saveData();
    }

    public static void main(String[] args) {
        launch();
    }
}