package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Aitor Garcia
 */
public class ResetPassController extends GlobalController {

    /**
     * TextView in which the userName will be introduced.
     */
    @FXML
    private TextField txtUserName;

    /**
     * TextView in which the email will be introduced.
     */
    @FXML
    private TextField txtEmail;

    /**
     * Button to go back to the previous page.
     */
    @FXML
    private Button BtnGoBack;

    /**
     * Button to confirm the introduced information and continue.
     */
    @FXML
    private Button BtnConfirm;

    /**
     * In case there are no errors, after pressing this button the user's
     * password will be reset.
     *
     * @param event current event.
     * @throws IOException in case there are input/output errors.
     */
    @FXML
    private void handleButtonReset(ActionEvent event) throws IOException {
        Boolean windowError = false, alertNeeded = false;
        String alertError = null;
        
        if(txtUserName.getText().trim().length() < 5){
            Alert alertUserTooShort = new Alert(Alert.AlertType.ERROR, "Username is too short", ButtonType.OK);
            Button btnAlertOk = (Button) alertUserTooShort.getDialogPane().lookupButton(ButtonType.OK);
            btnAlertOk.setId("btnOkShort");
            alertUserTooShort.showAndWait();
            LOGGER.info("Error attempting to reset password: User " + txtUserName.getText() + " is too short.");
            windowError = true;
        }else if(txtUserName.getText().trim().length() > 20){
            Alert alertUserTooLong = new Alert(Alert.AlertType.ERROR, "Username is too long");
            Button btnAlertOk = (Button) alertUserTooLong.getDialogPane().lookupButton(ButtonType.OK);
            btnAlertOk.setId("btnOkLong");
            alertUserTooLong.showAndWait();
            LOGGER.info("Error attempting to reset password: User " + txtUserName.getText() + " is too long.");
            windowError = true;
        }
        if(!windowError){
            //TODO Comprobar que El usuario existe y reiniciar la contraseña
        }
    }

    private void handleButtonBack(ActionEvent event) {

    }

    /**
     * Method for initializing password reset stage.
     * @param root
     */
    @FXML
    public void initStage(Parent root) {
        Scene scene = new Scene(root);

        //TODO ¿Reusar stage anterior?
        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Restablecer contraseña");
        stage.setResizable(false);

        stage.setOnShowing(this::handleWindowShowing);
        //Set focus on the username text field.
        //txtUserName.requestFocus();
        //TODO fill with information?

        stage.show();
    }

    /**
     * Window event method handler. Informs about the window being displayed.
     *
     * @param event The window event
     */
    private void handleWindowShowing(WindowEvent event) {
        LOGGER.info("Displaying password reset window.");
    }
}
