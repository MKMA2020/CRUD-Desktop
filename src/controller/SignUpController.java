/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.GlobalController.LOGGER;
import exception.DatabaseException;
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
 *
 * @author Martin Gros
 */
public class SignUpController extends GlobalController {

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

    @FXML
    private void handleButtonBack(ActionEvent event) throws IOException {
        start_SignIn(stage);
    }

    @FXML
    private void handleButtonSignUp(ActionEvent event) throws IOException, DatabaseException {
        SignUp();
    }

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setTitle("Registro");
        stage.setResizable(false);
        stage.setScene(scene);
        //Set window's events handlers
        stage.setOnShowing(this::handleWindowShowing);
        //Listeners
        SignUpUsername.textProperty().addListener(this::textChanged);
        SignUpPWD.textProperty().addListener(this::textChanged);
        SignUpPWD2.textProperty().addListener(this::textChanged);
        SignUpEmail.textProperty().addListener(this::textChanged);
        SignUpFN.textProperty().addListener(this::textChanged);

        stage.show();

    }

    /**
     * When the window's first launched, sets the logIn button to disabled and
     * adds 2 tooltips.
     */
    private void handleWindowShowing(WindowEvent event) {
        LOGGER.info("Beginning LoginController::handleWindowShowing");
        //Default texts
        SignUpUsername.setText("");
        SignUpPWD.setText("");
        SignUpPWD2.setText("");
        SignUpFN.setText("");
        SignUpEmail.setText("");
        //SignUp button disabled
        SignUpBtn.setDisable(true);
        //Few tooltips
        SignUpBtn.setTooltip(new Tooltip("Click para registrarte "));
        SignUpBtnBack.setTooltip(new Tooltip("Click para volver al inicio de sesion "));
        SignUpUsername.setTooltip(new Tooltip("Entre 5 y 20 carácteres"));
        SignUpPWD.setTooltip(new Tooltip("Debe de contener al menos una letra minuúscula, otra mayúscula y un caracter"));
        SignUpPWD2.setTooltip(new Tooltip("Repetir contraseña"));
        SignUpEmail.setTooltip(new Tooltip("E-mail en formato valido :)"));
        SignUpFN.setTooltip(new Tooltip("Nombre y apellido"));
    }

    /**
     * Method to launch back the sign in window
     *
     * @param stage
     * @throws IOException
     */
    private void start_SignIn(Stage stage) throws IOException {
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

    private void SignUp() throws DatabaseException {
        boolean existe = false;
        boolean error = validate();
        String alertError = null;
        boolean alertNeeded = false;
        ArrayList<User> listadeUsuarios = new ArrayList<User>();
        User user = new User();
        Ciphering encrypter = new Ciphering();

        try {
            listadeUsuarios = (ArrayList<User>) getUserManager().findAll();
        } catch (Exception e) {
            throw new DatabaseException();
        }
        try{
        if (listadeUsuarios!=null) {
            for (int i = 0; i < listadeUsuarios.size(); i++) {
                if (listadeUsuarios.get(i).getLogin().contentEquals(SignUpUsername.getText())) {
                    existe = true;
                    break;
                }
            }
        }
        else{
            error=true;
        }
        } catch (Exception e) {
            throw new DatabaseException();
        }

        if (!existe && !error) {

            //user.setPassword(Arrays.toString(encrypter.cifrarTexto(SignUpPWD.getText())));
            try {

                user.setLogin(SignUpUsername.getText());
                System.out.println(SignUpPWD.getText());
                user.setPassword(encrypter.cifrarTexto(SignUpPWD.getText()));
                System.out.println(user.getPassword());
                user.setEmail(SignUpEmail.getText());
                user.setFullName(SignUpFN.getText());

                getUserManager().create(user);
                SignUpBtn.setText("Signed Up");
                SignUpBtn.setDisable(true);
            } catch (Exception e) {
                throw new DatabaseException();
            }

        } else {
            if(existe){
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
        if (SignUpUsername.getText().trim().equals("") || SignUpPWD.getText().trim().equals("")
                || SignUpPWD2.getText().trim().equals("") || SignUpEmail.getText().trim().equals("")
                || SignUpFN.getText().trim().equals("")) {
            SignUpBtn.setDisable(true);
        } else {
            SignUpBtn.setDisable(false);
        }
    }

    private boolean validate() {
        boolean error = false;
        String alertList = "";
        //Checks if the user is long enough
        if (SignUpUsername.getText().length() < 5) {
            error = true;
            alertList = alertList.concat("El nombre de usuario es demasiado corto.\n");
        }
        //Checks if the user is too long
        if (SignUpUsername.getText().length() > 20) {
            error = true;
            alertList = alertList.concat("El nombre de usuario es demasiado largo.\n");
        }
        //Checks if the password meets the requirements
        if (isValidPass(SignUpPWD.getText()) == false) {
            error = true;
            alertList = alertList.concat("La contraseña debe de incluir una mayúscula, una minúscula y un numero\n");
        }
        //Checks if the password is too short
        if (SignUpPWD.getText().length() < 5) {
            error = true;
            alertList = alertList.concat("La contraseña es demasiado corta\n");
        }
        //Checks if the password and its confirmation are the same
        if (!SignUpPWD.getText().equals(SignUpPWD2.getText())) {
            error = true;
            alertList = alertList.concat("Las contraseñas no coinciden\n");
        }
        //Checks if the email has a valid format
        if (!isValidEmail(SignUpEmail.getText())) {
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
