package sample;

/*
MS Paint like project made for educational purposes only.
Author: Kalin Tomanov
Email: kalin_tomanov@abv.bg
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage pStage;

    public static Stage getPrimaryStage(){
        return pStage;
    }

    public static void setPrimaryStage(Stage pStage){
        Main.pStage = pStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        setPrimaryStage(primaryStage);
        Parent root = FXMLLoader.load(getClass().getResource("paint_gui.fxml"));
        primaryStage.setTitle("Paint");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
