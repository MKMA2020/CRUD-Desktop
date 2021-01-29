package reto2crud;

import controller.SignInController;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import model.User;

/**
 * Main Class for the PocketChef desktop App.
 *
 * @author Martin Valiente Ainz
 */
public class Reto2CRUD extends Application {

    /**
     * The configFile ResourceBundle.
     */
    public static ResourceBundle configFile;
    
    /**
     * The BASE_URI is the target server.
     */
    public static String BASE_URI;
    
    /**
     * The logged in User.
     */
    private static User user;

    /**
     * Entry point for the PocketChef application. Loads, sets and shows login
     * window.
     *
     * @param stage The primary window of the application
     * @throws IOException if there is an Input Output exception.
     */
    @Override
    public void start(Stage stage) throws IOException {

        //Load node graph from fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignIn.fxml"));
        Parent root = (Parent) loader.load();
        //Get controller for graph 
        SignInController primaryStageController = ((SignInController) loader.getController());

        //Set a reference for Stage
        primaryStageController.setStage(stage);

        //Initializes primary stage
        primaryStageController.initStage(root);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Config file at config Package.
        configFile = ResourceBundle.getBundle("config.config");
        BASE_URI = configFile.getString("URL");

        launch(args);

    }
    
    /**
     * Getter for the logged user attribute.
     * @return the user.
     */
    public static User getUser() {
        return user;
    }
    
    /**
     * Setter for the logged user attribute.
     * @param user The user
     */
    public static void setUser(User user) {
        Reto2CRUD.user = user;
    }

}
