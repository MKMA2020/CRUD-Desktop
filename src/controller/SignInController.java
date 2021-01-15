/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.GlobalController.LOGGER;
import java.io.IOException;
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
import manager.UserManager;
import model.Recipe;
import model.User;

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
        start_signup(stage);
    }

    @FXML
    private void handleButtonSignIn(ActionEvent event) throws IOException {
        signIn();

    }

    @Override
    protected void showError(String msg) {
        super.showError(msg); //To change body of generated methods, choose Tools | Templates.
    }

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Iniciar Sesion");
        stage.setResizable(false);
        //Set window's events handlers
        stage.setOnShowing(this::handleWindowShowing);
        SignInUsername.textProperty().addListener((this::textchanged));
        SignInPWD.textProperty().addListener((this::textchanged));
        stage.show();

    }

    /**
     * When the window's first launched, sets the logIn button to disabled and
     * adds 2 tooltips.
     */
    private void handleWindowShowing(WindowEvent event) {
        LOGGER.info("Beginning LoginController::handleWindowShowing");
        //Aceptar button is disabled.
        SignInUsername.setText("");
        SignInPWD.setText("");
        SignInBtn.setDisable(true);
        SignInBtnSignUp.setTooltip(new Tooltip("Click to sign up!"));
        SignInBtn.setTooltip(new Tooltip("Click to log in!"));
    }

    /**
     * This method's always looking whether the user's typing in both fields in
     * order to enable or disable the log in button.
     *
     * @param obv parameter used to observe the text fields.
     */
    private void textchanged(Observable obv) {

        if (this.SignInUsername.getText().trim().equals("") || this.SignInPWD.getText().trim().equals("")) {
            SignInBtn.setDisable(true);
        } else {
            SignInBtn.setDisable(false);
        }

    }

    private void start_signup(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignUp.fxml"));
        Parent root = (Parent) loader.load();
        SignUpController controller = (loader.getController());
        controller.setStage(primaryStage);
        controller.initStage(root);
    }

    private void signIn() {
        boolean error = false;
        LOGGER.log(Level.INFO, "Attempt to sign in started");

        if (this.SignInUsername.getText().trim().length() < 5) {
            showWarning("El nombre de usuario es muy corto");
            error = true;
        }
        if (this.SignInUsername.getText().trim().length() > 20) {
            showWarning("El nombre de usuario es demasiado largo");
            error = true;
        }
        if (!error) {
            User user = new User();
            user.setLogin(SignInBtn.getText());
            user.setPassword(SignInPWD.getText());
            getUserManager().login(SignInUsername.getText(), SignInPWD.getText());

        }
    }

}
