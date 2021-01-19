package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 *
 * @author Aitor Garcia
 */
public class ResetPassController {
    
    /**
     * TextView in which the userName will be introduced.
     */
    @FXML
    private TextField TFUserName;
    
    /**
     * TextView in which the email will be introduced.
     */
    @FXML
    private TextField TFEmail;
    
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
    
    @FXML
    public void initialize(){
    }
}
