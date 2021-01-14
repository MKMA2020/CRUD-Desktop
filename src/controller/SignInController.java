/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.GlobalController.LOGGER;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Recipe;

/**
 *
 * @author 2dam
 */
public class SignInController extends GlobalController{
    /**
     * Textfield for the user
     */
    @FXML
    private TextField SignInUsername;
    /**
     * Textfield for the password
     */
    @FXML
    private PasswordField SignInPWD;
    /**
     * Button to sign in the user
     */
    @FXML
    private Button SignInBtn;
    /**
     * Button to go to the sign up
     */
    @FXML
    private Button SignInBtnSignUp;
    /**
     * Button to go to the reset  up
     */
   
    
    
    
    
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage = new Stage();    
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Recetas");
        stage.setResizable(false);
        //Set window's events handlers
        stage.setOnShowing(this::handleWindowShowing);
        
        stage.show();
        
    }
       /**
     * Window event method handler. It implements behavior for WINDOW_SHOWING type 
     * event.
     * @param event  The window event 
     */
    private void handleWindowShowing(WindowEvent event){
        LOGGER.info("Beginning LoginController::handleWindowShowing");
        //Aceptar button is disabled.
        SignInBtnSignUp.setDisable(true);
    }
    
}
