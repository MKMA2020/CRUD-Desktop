package controller;

import enumeration.RecipeType;
import exception.TimeoutException;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Recipe;
import model.User;
import reto2crud.Reto2CRUD;

/**
 * Controller class for the window used to display the recipes.
 * @author Martin Valiente Ainz and Aitor Garcia
 */
public class RecipeViewController extends GlobalController {

    /**
     * TableView that will contain the information about recipes.
     */
    @FXML
    private TableView<Recipe> recipeTable;

    /**
     * Column from the TableView that will contain the Title of the recipe.
     */
    @FXML
    public TableColumn<Recipe, String> tclTitle;

    /**
     * Column from the TableView that will contain the Type of the recipe.
     */
    @FXML
    public TableColumn<Recipe, RecipeType> tclType;

    /**
     * Column from the TableView that will contain the amount of kcal of the
     * recipe.
     */
    @FXML
    public TableColumn<Recipe, Float> tclKcal;

    /**
     * Button used to create a new recipe.
     */
    @FXML
    public Button btnNewRecipe;

    @FXML
    private SideMenuController sideMenuController;

    /**
     * Recipe table data model.
     */
    private ObservableList<Recipe> recipes;

    /**
     * This will decide whether or not to search the recipes by user
     */
    private Boolean personal = null;
    
    /**
     * Information of the logged user.
     */
    private User loggedUser = Reto2CRUD.getUser();

    /**
     * InitStage Method for Recipes window.
     *
     * @param root The Parent object representing root node of view graph.
     * @param personal This will decide whether or not to search the recipes by
     * user.
     */
    public void initStage(Parent root, Boolean personal) {

        this.personal = personal;

        sideMenuController.setStage(stage);
        sideMenuController.initStage();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Recetas");
        stage.setResizable(false);

        //Set factories for cell values in users table columns.
        tclTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
        tclType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tclKcal.setCellValueFactory(new PropertyValueFactory<>("kcal"));

        //Set table model.
        recipeTable.setItems(fillTable());

        btnNewRecipe.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    createNewRecipe(event);
                } catch (Exception ex) {
                    showError("Error trying to create a recipe.");
                    LOGGER.severe("Error trying to create a recipe.");
                }
            }
        });

        //Show window.
        try {
            stage.show();
        } catch (Exception e) {
            showWarning(e.getMessage());
        }
    }

    /**
     * Leads to the recipe creation screen.
     *
     * @param event
     */
    public void createNewRecipe(ActionEvent event) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Add_Recipe.fxml"));
        root = loader.load();
        AddRecipeController controller = loader.getController();
        Stage newRecipeDialog = new Stage();
        newRecipeDialog.initModality(Modality.APPLICATION_MODAL);
        controller.setStage(newRecipeDialog);
        controller.initStage(root, stage);
        //TODO wait until the window gets closed and refresh.
    }
    
    /**
     * Fills a list with the recipes from the database.
     * @return List of recipes of the database.
     */
    private ObservableList<Recipe> fillTable() {
        ObservableList<Recipe> recipes = null;
        try {
            //Create and fill the table.
            recipes = FXCollections.observableArrayList(getRecipeManager().getAllRecipes());
        } catch (TimeoutException ex) {
            LOGGER.severe("ERROR: Timeout.");
        } catch (NullPointerException nullEx) {
            LOGGER.warning("The list is empty.");
        }
        if (personal && !(recipes.isEmpty())) {
            stage.setTitle("Mis recetas");
            try {
                //Recipe filtering
                Boolean exists = false;
                for(Recipe recipe : recipes){
                    if(!(recipe.getUser().getId().equals(loggedUser.getId()))){
                        exists = true;
                        break;
                    }
                }
                if(exists){
                    for(int cont = 0; cont < recipes.size();){
                        if(!(recipes.get(cont).getUser().getId().equals(loggedUser.getId()))){
                            recipes.remove(cont);
                        }else{
                            cont++;
                        }
                    }
                }
            } catch (NullPointerException nullEx) {
                LOGGER.severe("Received null element.");
            }
        }
        return recipes;
    }
}
