package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

/**
 *
 * @author Martin Valiente Ainz
 */
public class SideMenuController extends GlobalController {

    @FXML
    private Button btnShowMyRecipes;

    @FXML
    private Button btnShowRecipes;

    @FXML
    private Button btnShowMenus;

    @FXML
    private Button btnShowAdmin;

    @FXML
    private Button btnExit;

    public void initStage() {

        LOGGER.log(Level.INFO, "Initialising SideMenu Buttons.");

        btnShowMyRecipes.setOnAction((event) -> {
            LOGGER.log(Level.INFO, "BtnShowMyRecipes Clicked.");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RecipeView.fxml"));
            Parent root = null;
            try {
                root = (Parent) loader.load();
            } catch (IOException ex) {
                Logger.getLogger(SideMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
            RecipeViewController controller = (loader.getController());
            controller.setStage(stage);
            controller.initStage(root, true);
        });

        btnShowRecipes.setOnAction((event) -> {
            LOGGER.log(Level.INFO, "BtnShowRecipes Clicked.");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RecipeView.fxml"));
            Parent root = null;
            try {
                root = (Parent) loader.load();
            } catch (IOException ex) {
                Logger.getLogger(SideMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
            RecipeViewController controller = (loader.getController());
            controller.setStage(stage);
            controller.initStage(root, false);
        });

        btnShowMenus.setOnAction((event) -> {
            LOGGER.log(Level.INFO, "BtnShowMenus Clicked.");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MenuView.fxml"));
            Parent root = null;
            try {
                root = (Parent) loader.load();
            } catch (IOException ex) {
                Logger.getLogger(SideMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
            //Get controller for graph 
            MenuViewController controller = ((MenuViewController) loader.getController());
            //Set a reference for Stage
            controller.setStage(stage);
            //Initializes primary stage
            controller.initStage(root);
        });

        btnShowAdmin.setOnAction((event) -> {
            LOGGER.log(Level.INFO, "BtnShowAdmin Clicked.");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminUserWindow.fxml"));
            Parent root = null;
            try {
                root = (Parent) loader.load();
            } catch (IOException ex) {
                Logger.getLogger(SideMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
            //Get controller for graph 
            AdminUserWindowController primaryStageController = ((AdminUserWindowController) loader.getController());
            //Set a reference for Stage
            primaryStageController.setStage(stage);
            //Initializes primary stage
            primaryStageController.initStage(root);
        });

        btnExit.setOnAction((event) -> {
            LOGGER.log(Level.INFO, "BtnExit Clicked.");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignIn.fxml"));
            Parent root = null;
            try {
                root = (Parent) loader.load();
            } catch (IOException ex) {
                Logger.getLogger(SideMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
            //Get controller for graph 
            SignInController primaryStageController = ((SignInController) loader.getController());
            //Set a reference for Stage
            primaryStageController.setStage(stage);
            //Initializes primary stage
            primaryStageController.initStage(root);
        });

        LOGGER.log(Level.INFO, "Finished Initialising SideMenu Buttons.");
    }
}
