package huntkingdom;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HuntKingdom extends Application {
    
    public static Stage stage=null;
    
    @Override
    public void start(Stage s) throws Exception {
        stage=s;
        stage.setTitle("Hunt Kingdom");
        Parent root = FXMLLoader.load(getClass().getResource("/gui/MenuAdmin.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("/gui/login/Login.fxml"));
        Scene scene = new Scene(root, 1100, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
