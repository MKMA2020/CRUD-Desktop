package controller;

import factory.MenuManagerFACTORY;
import factory.RecipeManagerFACTORY;
import java.io.IOException;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import manager.MenuManager;
import manager.RecipeManager;

/**
 *
 * @author MartinValiente
 */
class GlobalController {

    protected static final Logger LOGGER = Logger.getLogger("controller");

    /**
     * The Stage object associated to the Scene controlled by this controller.
     * This is an utility method reference that provides quick access inside the
     * controller to the Stage object in order to make its initialization. Note
     * that this makes Application, Controller and Stage being tightly coupled.
     */
    protected Stage stage;

    /**
     * Gets the Stage object related to this controller.
     *
     * @return The Stage object initialized by this controller.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Sets the Stage object related to this controller.
     *
     * @param stage The Stage object to be initialized.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Shows an error message in an alert dialog.
     *
     * @param msg The error message to be shown.
     */
    protected void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        //CSS HAS TO BE ADDED
        alert.showAndWait();
    }

    /**
     * Shows a warning message in an alert dialog.
     *
     * @param msg The warning message to be shown.
     */
    protected void showWarning(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING, msg, ButtonType.OK);
        //CSS HAS TO BE ADDED
        alert.showAndWait();
    }

    /**
     * Shows an information message in an alert dialog.
     *
     * @param msg The information message to be shown.
     */
    protected void showInformation(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        //CSS HAS TO BE ADDED
        alert.showAndWait();
    }

    /**
     * Shows a confirmation message in an alert dialog.
     *
     * @param msg The confirmation message to be shown.
     */
    protected void showConfirmation(String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, msg, ButtonType.OK);
        //CSS HAS TO BE ADDED
        alert.showAndWait();
    }

    /**
     * Maximum text default fields length.
     */
    protected final int MAX_LENGTH = 255;

    /**
     * Maximum Ingredient Name field length.
     */
    protected final int INGREDIENT_NAME_MAX_LENGTH = 20;
    
    protected RecipeManager recipeManager;
    protected MenuManager menuManager;
    
    public RecipeManager getRecipesManager() {
        return RecipeManagerFACTORY.getRecipeManager();
    }
    
    public MenuManager getMenuManager() {
       return MenuManagerFACTORY.getMenuManager();
    }
    
    @FXML
    private void handleButtonRecipes (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/NewMenu.fxml"));
        Parent root = (Parent) loader.load();
        NewMenuController controller = (loader.getController());
        controller.setStage(stage);
        controller.initStage(root);
    }

}
