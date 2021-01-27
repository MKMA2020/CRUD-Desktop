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
import reto2crud.Reto2CRUD;

/**
 *
 * @author Martin Valiente Ainz & Aitor Garcia
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
    Boolean personal = null;

    /**
     * InitStage Method for Recipes window.
     *
     * @param root The Parent object representing root node of view graph.
     * @param personal This will decide whether or not to search the recipes by user.
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
        
        btnNewRecipe.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                try{
                    createNewRecipe(event);
                }catch(Exception ex){
                    showError("Error trying to create a recipe.");
                }
            }
        });
        
        //Show window.
        try{
            stage.show();
        }catch(Exception e){
            showWarning(e.getMessage());
        }
    }

    /**
     * Leads to the recipe creation screen.
     * @param event
     */
    public void createNewRecipe(ActionEvent event) throws IOException{
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Add_Recipe.fxml"));
        root = loader.load();
        AddRecipeController controller = loader.getController();
        Stage newRecipeDialog = new Stage();
        newRecipeDialog.initModality(Modality.APPLICATION_MODAL);
        newRecipeDialog.setAlwaysOnTop(true);
        controller.setStage(newRecipeDialog);
        controller.initStage(root);
        //TODO wait until the window gets closed and refresh.
    }
    
    private ObservableList<Recipe> fillTable() {
        ObservableList<Recipe> recipes = null;
        try {
            //Create and fill the table.
            recipes = FXCollections.observableArrayList(getRecipeManager().getAllRecipes());
        } catch (TimeoutException ex) {
            LOGGER.severe("ERROR: Timeout.");
        }
        if(personal){
            stage.setTitle("Mis recetas");
            //Recipe filtering.
            for(Recipe recipe: recipes){
                if(!(recipe.getUser().getId().equals(Reto2CRUD.getUser().getId()))){
                    recipes.remove(recipe);
                }
            }
        }
        return recipes;
    }
}