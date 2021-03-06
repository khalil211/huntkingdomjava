package huntkingdom;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class HuntKingdom extends Application {
    
    public static Stage stage=null;
    public static int idClient=1;
    
    @Override
    public void start(Stage s) throws Exception {
        stage=s;
        stage.setTitle("Hunt Kingdom");
        //Parent root = FXMLLoader.load(getClass().getResource("/gui/MenuAdmin.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/gui/login/Login.fxml"));
        Scene scene = new Scene(root, 1250, 700);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
