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
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Recipe;

/**
 *
 * @author 2dam
 */
public class SignUpController extends GlobalController{
    /**
     * Textfield for the user
     */
    @FXML
    private TextField SignUpUsername;
    /**
     * Textfield for the email
     */
    @FXML
    private TextField SignUpEmail;
    /**
     * Textfield for the full name
     */
    @FXML
    private TextField SignUpFN;
    /**
     * Textfield for the password
     */
    @FXML
    private PasswordField SignUpPWD;
    /**
     * Textfield for the password verification
     */
    @FXML
    private PasswordField SignUpPWD2;
    /**
     * Button to sign in the user
     */
    @FXML
    private Button SignUpBtn;
    /**
     * Button to go to the sign sign
     */
    @FXML
    private Button SignUpBtnBack;
    
   
    
    
    
    
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setTitle("Registro");
        stage.setResizable(false);
        stage.setScene(scene);
        //Set window's events handlers
        stage.setOnShowing(this::handleWindowShowing);
        
        
        stage.show();
        
    }
     
    /**
     * When the window's first launched, sets the logIn button to disabled and
     * adds 2 tooltips.
     */

    private void handleWindowShowing(WindowEvent event) {
        LOGGER.info("Beginning LoginController::handleWindowShowing");
        //Aceptar button is disabled.
        SignUpUsername.setText("");
        SignUpPWD.setText("");
        SignUpPWD2.setText("");
        SignUpFN.setText("");
        SignUpEmail.setText("");
    }
    
}
