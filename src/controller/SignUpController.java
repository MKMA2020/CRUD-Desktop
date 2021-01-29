/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.GlobalController.LOGGER;
import exception.DatabaseException;
import exception.IncorrectCredentialsException;
import exception.TimeoutException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Recipe;
import model.User;
import security.Ciphering;

/**
 * Class of the SignUp that gets executed when the user clicks the sign up button
 * in the login
 * @author Martin Gros
 */
public class SignUpController extends GlobalController {

    /**
     * This class is the controller for the sign-up window. It contains methods
     * that control it, like checks of the text fields or to define the buttons.
     * Textfield for the user
     */
    @FXML
    private TextField signUpUsername;
    /**
     * Textfield for the email
     */
    @FXML
    private TextField signUpEmail;
    /**
     * Textfield for the full name
     */
    @FXML
    private TextField signUpFN;
    /**
     * Textfield for the password
     */
    @FXML
    private PasswordField signUpPWD;
    /**
     * Textfield for the password verification
     */
    @FXML
    private PasswordField signUpPWD2;
    /**
     * Button to sign in the user
     */
    @FXML
    private Button signUpBtn;
    /**
     * Button to go to the sign sign
     */
    @FXML
    private Button signUpBtnBack;
 /**
     * Handler to go back to the sign in window
     */
    @FXML
    private void handleButtonBack(ActionEvent event) throws IOException {
        start_SignIn(stage);
    }
 /**
     * Handler to start the signup
     */
    @FXML
    private void handleButtonSignUp(ActionEvent event) throws IOException, DatabaseException, IncorrectCredentialsException {
        SignUp();
    }
     /**
     * Method that initializes the window. It sets its properties and shows it
     *
     * @param root the parent of the window
     */

    public void initStage(Parent root) {
        LOGGER.info("SignUp");
        Scene scene = new Scene(root);
        stage.setTitle("Registro");
        stage.setResizable(false);
        stage.setScene(scene);
        signUpBtn.setDisable(true);
        //Set window's events handlers
        stage.setOnShowing(this::handleWindowShowing);
        //Listeners
        signUpUsername.textProperty().addListener(this::textChanged);
        signUpPWD.textProperty().addListener(this::textChanged);
        signUpPWD2.textProperty().addListener(this::textChanged);
        signUpEmail.textProperty().addListener(this::textChanged);
        signUpFN.textProperty().addListener(this::textChanged);

        stage.show();

    }

    /**
     * When the window's first launched, sets the Signup button to disabled and
     * adds 2 tooltips.
     */
    private void handleWindowShowing(WindowEvent event) {
        LOGGER.info("Beginning LoginController::handleWindowShowing");
        //Default texts
        signUpUsername.setText("");
        signUpPWD.setText("");
        signUpPWD2.setText("");
        signUpFN.setText("");
        signUpEmail.setText("");
        //Few tooltips
        signUpBtn.setTooltip(new Tooltip("Click para registrarte "));
        signUpBtnBack.setTooltip(new Tooltip("Click para volver al inicio de sesion "));
        signUpUsername.setTooltip(new Tooltip("Entre 5 y 20 carácteres"));
        signUpPWD.setTooltip(new Tooltip("Debe de contener al menos una letra minuúscula, otra mayúscula y un caracter"));
        signUpPWD2.setTooltip(new Tooltip("Repetir contraseña"));
        signUpEmail.setTooltip(new Tooltip("E-mail en formato valido :)"));
        signUpFN.setTooltip(new Tooltip("Nombre y apellido"));
    }

    /**
     * Method to launch back the sign in window
     *
     * @param stage
     * @throws IOException
     */
    private void start_SignIn(Stage stage) throws IOException {
        LOGGER.info("Going back to SignIn");
        //It gets the FXML of the sign-in window
        //Load node graph from fxml file
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/view/SignIn.fxml"));
        Parent root = (Parent) loader.load();
        //Get controller for graph 
        SignInController primaryStageController
                = ((SignInController) loader.getController());

        //Set a reference for Stage
        primaryStageController.setStage(stage);
        //Initializes primary stage
        primaryStageController.initStage(root);
        //stage.close();
    }
    /**
     * Method that runs when you click the sign-up button. .
     *
     * @param event it is the clicking event of the button
     */

    private void SignUp() {
        LOGGER.info("Attempt to SignUp");
        boolean existe = false;
        boolean error = validate();
        String alertError = null;
        boolean alertNeeded = false;
        ArrayList<User> listadeUsuarios = new ArrayList<User>();
        User user = new User();
        Ciphering encrypter = new Ciphering();

        try {
            listadeUsuarios = (ArrayList<User>) getUserManager().findAll();
        } catch (TimeoutException e) {
            showWarning("Error en la base de datos, por favor prueba mas tarde.");
            error = true;
        }
        if (!error) {

            for (int i = 0; i < listadeUsuarios.size(); i++) {
                if (listadeUsuarios.get(i).getLogin().equalsIgnoreCase(signUpUsername.getText())) {
                    existe = true;
                    LOGGER.info("Username: " + signUpUsername.getText() + " already exists.");
                    break;
                }
            }
        }

        if (!existe && !error) {
            LOGGER.info("SignIng Up");
            user.setLogin(signUpUsername.getText());
            user.setPassword(encrypter.cifrarTexto(signUpPWD.getText()));
            user.setEmail(signUpEmail.getText());
            user.setFullName(signUpFN.getText());
            getUserManager().create(user);
            signUpBtn.setText("Registrado");
            signUpBtn.setDisable(true);

        } else {
            if (existe) {
                showWarning("El usuario ya existe");
            }

        }

    }

    /**
     * Method that checks all the time the various fields
     *
     * @param observable
     */
    private void textChanged(Observable observable) {
        if (signUpUsername.getText().trim().equals("") || signUpPWD.getText().trim().equals("")
                || signUpPWD2.getText().trim().equals("") || signUpEmail.getText().trim().equals("")
                || signUpFN.getText().trim().equals("")) {
            signUpBtn.setDisable(true);
        } else {
            signUpBtn.setDisable(false);
        }
    }

    private boolean validate() {
        LOGGER.info("Validating...");
        boolean error = false;
        String alertList = "";
        //Checks if the user is long enough
        if (signUpUsername.getText().length() < 5) {
            error = true;
            alertList = alertList.concat("El nombre de usuario es demasiado corto.\n");
        }
        //Checks if the user is too long
        if (signUpUsername.getText().length() > 20) {
            error = true;
            alertList = alertList.concat("El nombre de usuario es demasiado largo.\n");
        }
        //Checks if the password meets the requirements
        if (isValidPass(signUpPWD.getText()) == false) {
            error = true;
            alertList = alertList.concat("La contraseña debe de incluir una mayúscula, una minúscula y un numero\n");
        }
        //Checks if the password is too short
        if (signUpPWD.getText().length() < 5) {
            error = true;
            alertList = alertList.concat("La contraseña es demasiado corta\n");
        }
        //Checks if the password and its confirmation are the same
        if (!signUpPWD.getText().equals(signUpPWD2.getText())) {
            error = true;
            alertList = alertList.concat("Las contraseñas no coinciden\n");
        }
        //Checks if the email has a valid format
        if (!isValidEmail(signUpEmail.getText())) {
            error = true;
            alertList = alertList.concat("Formato incorrecto de correo electrónico\n");
        }
        //Shows an alert with any errors there might have been
        if (error) {
            Alert listAllAlerts = new Alert(Alert.AlertType.ERROR,
                    alertList, ButtonType.OK);
            Button aceptar = (Button) listAllAlerts.getDialogPane().lookupButton(ButtonType.OK);
            aceptar.setId("Aceptar");
            listAllAlerts.showAndWait();
        }

        return error;
    }

    /**
     * This method receives a password and checks if it meets the requirements.
     *
     * @param s the password to check
     * @return a boolean telling if the password is valid
     */
    private boolean isValidPass(String pwd) {
        boolean valid = true;
        //Checks if it has any numbers
        Pattern pNumber = Pattern.compile("[0-9]");
        Matcher m = pNumber.matcher(pwd);
        if (!m.find()) {
            valid = false;
        }
        //Checks if there are any upper-case letters
        Pattern pUpper = Pattern.compile("[A-Z]");
        m = pUpper.matcher(pwd);
        if (!m.find()) {
            valid = false;
        }
        //Checks if there are any lower-case letters
        Pattern pLower = Pattern.compile("[a-z]");
        m = pLower.matcher(pwd);
        if (!m.find()) {
            valid = false;
        }
        return valid;
    }

    /**
     * This method receives an email and checks if it is valid using a Regular
     * Expression
     *
     * @param email the email to check
     * @return a boolean telling if the email is valid
     */
    static boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

}
