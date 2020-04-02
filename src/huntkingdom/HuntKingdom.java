package huntkingdom;

import Entities.appointments;
import Services.appointmentsService;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HuntKingdom extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("HuntKingdom.fxml"));
        
      
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
       
    }

    public static void main(String[] args) throws ParseException, SQLException {
        launch(args);
           appointmentsService aps = new appointmentsService();
           
          
           
/*String dateString;
        dateString = "2020-04-02 07:25:00";
DateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
Date myDate = formatter.parse(dateString);
*/
        appointments ap = new appointments (1,"Second event","description","2022-04-02 07:25:00","2022-04-02 07:25:00");
        appointments apm = new appointments (4,"event modifié","Echape","2002-04-02 08:20:00","2002-04-02 07:25:00");

      //  aps.ajoutA(ap);
      //  aps.afficheEvents();
       // aps.supprimeEvent(2);
        //aps.modifierEvent(apm);
       // aps.afficheEvents();
        String F = "Where Title like '%Fir%' ";
        aps.displayClause(F);

                /*select from user,reclamation where reclamation.id=? and user.id=reclamation.id_user*/;
    }
    
}
