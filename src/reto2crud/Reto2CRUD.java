/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2crud;

import controller.RecipeViewController;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import manager.RecipeManagerImplementation;
import model.Recipe;

/**
 *
 * @author 2dam
 */
public class Reto2CRUD extends Application {
    
    public static ResourceBundle configFile;
    public static String BASE_URI;
    
    @Override
    public void start(Stage stage) throws Exception {
                       
        
        
        //Load node graph from fxml file
        FXMLLoader loader=new FXMLLoader(
                getClass().getResource("/view/RecipeView.fxml"));
        Parent root = (Parent)loader.load();
        //Get controller for graph 
        RecipeViewController primaryStageController=
                ((RecipeViewController)loader.getController());
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
        
        //RecipeManagerImplementation r = new RecipeManagerImplementation();
        //List<Recipe> l=(List<Recipe>) r.getAllRecipes();
    }
    
}
