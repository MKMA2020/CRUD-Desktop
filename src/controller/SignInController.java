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
import java.nio.file.AccessDeniedException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.servlet.UnavailableException;
import javax.ws.rs.ForbiddenException;
import manager.UserManager;
import model.Recipe;
import model.User;
import reto2crud.Reto2CRUD;
import security.Ciphering;

/**
 * This class is the controller for the sign-in window. It has the methods
 * needed to launch the other windows, plus send the data as well.
 *
 * @author Martin Gros
 */
public class SignInController extends GlobalController {

    /**
     * Textfield for the user
     */
    @FXML
    private TextField signInUsername;
    /**
     * Textfield for the password
     */
    @FXML
    private PasswordField signInPWD;
    /**
     * Button to sign in the user
     */
    @FXML
    private Button signInBtn;
    /**
     * Button to go to the sign up
     */
    @FXML
    private Button signInBtnSignUp;
    /**
     * Button to launch de reset pwd window
     */
    @FXML
    private Button signInBtnResetPwd;

    /**
     * Button to go to the reset up
     */
    /**
     * If the button is clicked the sign up window will be launched.
     *
     * @param event event used
     * @throws IOException when there are input/output errors.
     */
    @FXML
    private void handleButtonSignUp(ActionEvent event) throws IOException {
        LOGGER.info("Going to SignUp");
        start_signup(stage);
    }
     /**
     * If the button is clicked the sign up window will be launched.
     *
     * @param event event used
     * @throws IOException when there are input/output errors.
     */

    @FXML
    private void handleButtonSignIn(ActionEvent event) throws IOException, IncorrectCredentialsException, DatabaseException {
        signIn();

    }
/**
     * Initializes and starts the window.
     *
     * @param root Parent of the window
     */
    public void initStage(Parent root) {
        LOGGER.info("SignIn");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Iniciar Sesion");
        stage.setResizable(false);
        //Set window's events handlers
        handleWindowShowing();
        signInUsername.textProperty().addListener((this::textchanged));
        signInPWD.textProperty().addListener((this::textchanged));
        stage.show();

    }

    /**
     * When the window's first launched, sets the logIn button to disabled and
     * adds 2 tooltips.
     */
    private void handleWindowShowing() {
        LOGGER.info("Beginning LoginController::handleWindowShowing");
        //Aceptar button is disabled.
        signInUsername.setText("");
        signInPWD.setText("");
        signInBtn.setDisable(true);
        signInBtnSignUp.setTooltip(new Tooltip("Click to sign up!"));
        signInBtn.setTooltip(new Tooltip("Click to log in!"));
        signInBtnResetPwd.setOnAction(e -> {
            try {
                start_resetPWD(stage);
            } catch (IOException ex) {
                Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });;
    }

    /**
     * This method's always looking whether the user's typing in both fields in
     * order to enable or disable the log in button.
     *
     * @param obv parameter used to observe the text fields.
     */
    private void textchanged(Observable obv) {

        if (this.signInUsername.getText().trim().equals("") || this.signInPWD.getText().trim().equals("")) {
            signInBtn.setDisable(true);
        } else {
            signInBtn.setDisable(false);
        }

    }
  /**
     * Method to invoke the signup window
     *
     * @param primaryStage Main stage
     * @throws IOException IO issues
     */

    private void start_signup(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignUp.fxml"));
        Parent root = (Parent) loader.load();
        SignUpController controller = (loader.getController());
        controller.setStage(primaryStage);
        controller.initStage(root);
    }
 /**
     * Method to invoke the ResetPass window
     *
     * @param primaryStage Main stage
     * @throws IOException IO issues
     */

    private void start_resetPWD(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ResetPass.fxml"));
        Parent root = (Parent) loader.load();
        ResetPassController controller = (loader.getController());
        controller.setStage(primaryStage);
        controller.initStage(root);
    }
        /**
     * This method gets launched whenever the user hits the login button In case
     * the username is either longer than 20 or shorther than 5 chars it will
     * send and alert. IF there is no error and everything goes through the
     * applicattions main window will be launched.
     *
     * @param event current event.
     * @throws IOException when there are input/output errors
     * @throws IncorrectCredentialsException when the loging credentials arent right
     * 
     */

    private void signIn() throws IOException, IncorrectCredentialsException {
        boolean error = false;
        boolean errordb = false;
        User user = new User();
        Ciphering encrypter = new Ciphering();
        LOGGER.log(Level.INFO, "Attempt to sign in started");

        if (this.signInUsername.getText().trim().length() < 5) {
            showWarning("El nombre de usuario es muy corto");
            error = true;
        }
        if (this.signInUsername.getText().trim().length() > 20) {
            showWarning("El nombre de usuario es demasiado largo");
            error = true;
        }
        if (!error) {
            //Try and catch in order to avoid a database no response error.
            try {

                user.setLogin(signInBtn.getText());
                user.setPassword(encrypter.cifrarTexto(signInPWD.getText()));

                user = getUserManager().login(signInUsername.getText(), user.getPassword());
                Reto2CRUD.setUser(user);
                start_app(stage);
            } catch (TimeoutException e) {
                showWarning("Error en la conexion con la base de datos");
            } catch (IncorrectCredentialsException e) {
                showWarning("Nombre de usuario o contraseñas erroneas");
            }
        } else {
        }
    }
    /**
     * MEthod that loads the recipes window
     * @param primaryStage
     * @throws IOException 
     */

    private void start_app(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RecipeView.fxml"));
        Parent root = (Parent) loader.load();
        RecipeViewController controller = (loader.getController());
        controller.setStage(primaryStage);
        controller.initStage(root, false);
    }

}
