/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.WindowEvent;

/**
 *
 * @author Martin Valiente Ainz
 */
public class NewIngredientController extends GlobalController {

    /**
     * Ingredient's name UI text field.
     */
    @FXML
    private TextField txtName;

    /**
     * Ingredient's type UI text field.
     */
    @FXML
    private ComboBox cbxType;

    /**
     * Button to fire action to create a new Ingredient.
     */
    @FXML
    private Button btnCreate;

    /**
     * Button to fire action to close the window.
     */
    @FXML
    private Button btnCancel;

    /**
     * Method for initializing Login Stage.
     *
     * @param root The Parent object representing root node of view graph.
     */
    public void initStage(Parent root) {
        LOGGER.info("Initializing Login stage.");
        //Create a scene associated to the node graph root.
        Scene scene = new Scene(root);
        //Associate scene to primaryStage(Window)
        stage.setScene(scene);
        //Set window properties
        stage.setTitle("New Ingredient");
        stage.setResizable(false);
        btnCancel.setDisable(true);
        //Set window's events handlers
       // stage.setOnShowing(this::handleWindowShowing);
        //Set control events handlers (if not set by FXML)
       // txtName.textProperty().addListener(this::textChanged);
        // Set focus on txtName
       // txtName.requestFocus();
        //cbxType.setItems(FXCollections.observableArrayList(enumeration.IngredientType.values()));
        //cbxType.textProperty().addListener(this::textChanged);

        //Show primary window
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
        txtName.setDisable(true);
    }

    /**
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void textChanged(ObservableValue observable, String oldValue, String newValue) {
        //If text fields values are too long, show error message and disable 
        //accept button
        if (txtName.getText().trim().length() > this.INGREDIENT_NAME_MAX_LENGTH) {
            showWarning("La longitud máxima del Nombre es de 20 caracteres.");
            showInformation("");
            btnCreate.setDisable(true);
        } //If text fields are empty disable accept buttton
        else if (txtName.getText().trim().isEmpty() || cbxType.getValue().toString().equalsIgnoreCase("")) {
            btnCreate.setDisable(true);
        } //Else, enable accept button
        else {
            btnCreate.setDisable(false);
        }

    }
}
