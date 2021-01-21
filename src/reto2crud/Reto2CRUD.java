/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2crud;

import controller.AdminUserWindowController;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 *
 * @author Martin Valiente Ainz
 */
public class Reto2CRUD extends Application {
    
    public static ResourceBundle configFile;
    public static String BASE_URI;
    
    @Override
    public void start(Stage stage) throws IOException {
      
       //Load node graph from fxml file
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/view/AdminUserWindow.fxml"));
        Parent root = (Parent)loader.load();
        //Get controller for graph 
        AdminUserWindowController primaryStageController = ((AdminUserWindowController)loader.getController());

        //Set a reference for Stage
        primaryStageController.setStage(stage);
        
        //Initializes primary stage
        primaryStageController.initStage(root);
        
        
        }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Config file at config Package.
        configFile  = ResourceBundle.getBundle("config.config");
        BASE_URI = configFile.getString("URL");
        
        launch(args);
        
    }
    
}

