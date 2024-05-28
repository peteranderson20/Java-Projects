module edu.miracosta.cs112.capstone {
    requires javafx.controls;
    requires javafx.fxml;
    requires jbcrypt;
    requires javafx.media;


    opens edu.miracosta.cs112.capstone to javafx.fxml;
    //exports edu.miracosta.cs112.capstone;
    exports edu.miracosta.cs112.capstone.Controller;
    opens edu.miracosta.cs112.capstone.Controller to javafx.fxml;
    exports edu.miracosta.cs112.capstone.View;
    opens edu.miracosta.cs112.capstone.View to javafx.fxml;
}