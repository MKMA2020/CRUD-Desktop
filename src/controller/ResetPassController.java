package controller;

import static controller.GlobalController.LOGGER;
import exception.TimeoutException;
import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import model.User;

/**
 * Controller class for the window used to change the password of a user.
 * @author Aitor Garcia
 */
public class ResetPassController extends GlobalController {

    /**
     * TextView in which the userName will be introduced.
     */
    @FXML
    private TextField txtResetUsername;

    /**
     * TextView in which the email will be introduced.
     */
    @FXML
    private TextField txtResetEmail;

    /**
     * Button to go back to the previous page.
     */
    @FXML
    private Button resetBtnBack;

    /**
     * Button to confirm the introduced information and continue.
     */
    @FXML
    private Button resetBtnOk;

    /**
     * Method for initializing password reset stage.
     * @param root
     */
    @FXML
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setTitle("Restablecer contraseña");
        stage.setScene(scene);
        stage.setResizable(false);

        LOGGER.info("Displaying password reset window.");
        //stage.setOnShowing(this::handleWindowShowing);
        //Set focus on the username text field.
        txtResetUsername.requestFocus();
        //TODO fill with information?

        resetBtnOk.setOnAction(e -> {
            handleButtonConfirm();
        });
        
        resetBtnBack.setOnAction(e -> {
            try {
                handleButtonBack();
            } catch (Exception ex) {
                LOGGER.severe("Error going back.");
            }
        });
        
        stage.show();
    }

    /**
     * Window event method handler. Informs about the window being displayed.
     *
     * @param event The window event
     */
    /*private void handleWindowShowing(WindowEvent event) {
        LOGGER.info("Displaying password reset window.");
    }*/
    
    /**
     * In case there are no errors, after pressing this button the user's
     * password will be reset.
     *
     * @param event current event.
     * @throws IOException in case there are input/output errors.
     */
    @FXML
    private void handleButtonReset() throws TimeoutException, Exception{
        Boolean windowError = false, alertNeeded = false;
        String alertError = null;
        
        if(txtResetUsername.getText().trim().length() < 5){
            Alert alertUserTooShort = new Alert(Alert.AlertType.WARNING, "El nombre de usuario es demasiado corto", ButtonType.OK);
            Button btnAlertOk = (Button) alertUserTooShort.getDialogPane().lookupButton(ButtonType.OK);
            btnAlertOk.setId("btnOkShort");
            alertUserTooShort.showAndWait();
            LOGGER.info("Error attempting to reset password: User " + txtResetUsername.getText() + " is too short.");
            windowError = true;
        }else if(txtResetUsername.getText().trim().length() > 20){
            Alert alertUserTooLong = new Alert(Alert.AlertType.WARNING, "El nombre de usuario es demasiado largo");
            Button btnAlertOk = (Button) alertUserTooLong.getDialogPane().lookupButton(ButtonType.OK);
            btnAlertOk.setId("btnOkLong");
            alertUserTooLong.showAndWait();
            LOGGER.info("Error attempting to reset password: User " + txtResetUsername.getText() + " is too long.");
            windowError = true;
        }
        if(!windowError){
            Boolean found = false;
            List<User> userList = getUserManager().findAll();
            for (User user : userList) {
                if((user.getLogin().equals(txtResetUsername.getText()) && (user.getEmail().equals(txtResetEmail.getText())))){
                    getUserManager().resetPassword(txtResetUsername.getText(), txtResetEmail.getText());
                    found = true;
                    resetBtnOk.setDisable(true);
                    break;
                }
            }
            if(!found){
                JOptionPane optionPane = new JOptionPane("La información introducida es incorrecta", JOptionPane.ERROR_MESSAGE);    
                    JDialog dialog = optionPane.createDialog("ERROR");
                    dialog.setAlwaysOnTop(true);
                    dialog.setVisible(true);
                    LOGGER.severe("Finding user for password reset: Failed.");
            }
        }
    }

    private void handleButtonBack(){
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignIn.fxml"));
        try{
            root = loader.load();
        }catch(IOException IOEx){
            LOGGER.severe(IOEx.getMessage());
        }
        SignInController controller = loader.getController();
        Stage stage = new Stage();
        controller.setStage(stage);
        controller.initStage(root);
        this.stage.close();
    }

    private void handleButtonConfirm() {
        try {
                handleButtonReset();
            } catch (TimeoutException timeEx){
                JOptionPane optionPane = new JOptionPane("There has been an error trying to reach the server.", JOptionPane.ERROR_MESSAGE);
                JDialog dialog = optionPane.createDialog("Failure");
                dialog.setAlwaysOnTop(true);
                dialog.setVisible(true);
                LOGGER.severe("There has been an error trying to reach the server.");
            } catch (Exception ex) {
                JOptionPane optionPane = new JOptionPane("There has been an error trying to reset your password.", JOptionPane.ERROR_MESSAGE);
                JDialog dialog = optionPane.createDialog("Failure");
                dialog.setAlwaysOnTop(true);
                dialog.setVisible(true);
                LOGGER.severe("There has been an error trying to reset your password.");
            }
    }
}
