package controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import model.User;

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
            Boolean found = false;
            List<User> userList = getUserManager().findAll();
            for (User user : userList) {
                if((user.getLogin().equals(txtUserName.getText()) && (user.getEmail().equals(txtEmail.getText())))){
                    getUserManager().resetPassword(txtUserName.getText(), txtEmail.getText());
                    found = true;
                    break;
                }
            }
            if(!found){
                JOptionPane optionPane = new JOptionPane("User not found.", JOptionPane.ERROR_MESSAGE);    
                    JDialog dialog = optionPane.createDialog("Failure");
                    dialog.setAlwaysOnTop(true);
                    dialog.setVisible(true);
                    LOGGER.severe("Finding user for password reset: Failed.");
            }
        }
    }

    private void handleButtonBack(ActionEvent event) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignIn.fxml"));
        root = loader.load();
        SignInController controller = loader.getController();
        Stage stage = new Stage();
        controller.setStage(stage);
        controller.initStage(root);
        this.stage.close();
    }

    /**
     * Method for initializing password reset stage.
     * @param root
     */
    @FXML
    public void initStage(Parent root) {
        Scene scene = new Scene(root);

        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Restablecer contraseña");
        stage.setResizable(false);

        stage.setOnShowing(this::handleWindowShowing);
        //Set focus on the username text field.
        txtUserName.requestFocus();
        //TODO fill with information?

        BtnConfirm.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try {
                    handleButtonReset(event);
                } catch (IOException ex) {
                    JOptionPane optionPane = new JOptionPane("There has been an error trying to reset your password.", JOptionPane.ERROR_MESSAGE);    
                    JDialog dialog = optionPane.createDialog("Failure");
                    dialog.setAlwaysOnTop(true);
                    dialog.setVisible(true);
                    LOGGER.severe("There has been an error trying to reset your password.");
                }
            } 
        });
        
        BtnGoBack.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try {
                    handleButtonBack(event);
                } catch (IOException ex) {
                    LOGGER.severe("Error going back.");
                }
            }
            
        });
        
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
