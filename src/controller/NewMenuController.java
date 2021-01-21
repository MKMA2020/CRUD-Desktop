/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Menu;

/**
 *
 * @author Kerman
 */
public class NewMenuController extends GlobalController {
    
    @FXML
    public SideMenuController sideMenuController;
    
    
    public void initStage(Parent root) {
        //Initializes the stage and sets its attributes.
        Scene scene = new Scene(root);
        stage = new Stage();    
        stage.setScene(scene);
        stage.setTitle("Creación de menú");
        stage.setResizable(false);
        sideMenuController.setStage(stage);
        sideMenuController.initStage();

        //Show window.
        stage.show();
    }
    
}
