/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 *
 * @author Martin Valiente Ainz
 */
public class SideMenuController extends GlobalController{
    
    @FXML
    private Button btnShowMyRecipes;
    
    @FXML
    private Button btnShowRecipes;
    
    @FXML
    private Button btnShowMenus;
    
    @FXML
    private Button btnShowAdmin;
    
    @FXML
    private Button btnExit;
    
    @FXML  
    public void initialize() {
        
        LOGGER.log(Level.INFO, "Initialising SideMenu Buttons.");
        
        //btnShowMyRecipes.setOnAction((event) -> {
            
        //});
        
       // btnShowRecipes.setOnAction((event) -> {
            
        //});
        
       // btnShowMenus.setOnAction((event) -> {
            
        //});
        
        //btnShowAdmin.setOnAction((event) -> {
            
        //});
        
        //btnExit.setOnAction((event) -> {
            
        //});
        
        LOGGER.log(Level.INFO, "Finished Initialising SideMenu Buttons.");
    }  
}
