package controller;

import enumeration.RecipeType;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public TableColumn<Recipe, Integer> tclKcal;
    
    /**
     * Button used to create a new recipe.
     */
    @FXML
    public Button btnNewRecipe;
    
    /**
     * Button used to get into an specific recipe.
     */
    @FXML
    public Button btnDetailRecipe;
    
    /**
     * Button used to remove a recipe.
     */
    @FXML
    public Button btnRemoveRecipe;

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
    

    /*@FXML
    public void initialize() {
        LOGGER.info("Initializing Recipe Table.");
        tclTitle.setSortType(TableColumn.SortType.ASCENDING);
        tclTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
        tclType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tclKcal.setCellValueFactory(new PropertyValueFactory<>("kcal"));

        Recipe recipe = new Recipe();
        recipe.setName("Lentejas");
        recipe.setType(RecipeType.Main);
        recipe.setKcal(150);

        Recipe recipe2 = new Recipe();
        recipe2.setName("Patatas Fritas");
        recipe2.setType(RecipeType.Snack);
        recipe2.setKcal(500);

        Recipe recipe3 = new Recipe();
        recipe3.setName("Helado");
        recipe3.setType(RecipeType.Dessert);
        recipe3.setKcal(80);

        LOGGER.info("Loading Recipes.");
        recipeTable.getItems().add(recipe);
        recipeTable.getItems().add(recipe2);
        recipeTable.getItems().add(recipe3);

        // LISTENER PARA LA TABLA
        //recipeTable.setOnMouseClicked(this::MouseClicked);
        recipeTable.getSelectionModel().selectedItemProperty().addListener(this::handleRecipeTableSelectionChanged);

    }*/

    /*public void MouseClicked(MouseEvent event) {
        LOGGER.info(recipeTable.getSelectionModel().getSelectedItem().getName());
    }*/
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
        
        btnDetailRecipe.setVisible(false);
        btnRemoveRecipe.setVisible(false);
        //Set table model.
        recipeTable.setItems(fillTable());
        
        //Set factories for cell values in users table columns.
        tclTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
        tclType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tclKcal.setCellValueFactory(new PropertyValueFactory<>("kcal"));

        
        recipeTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection != null){
                btnDetailRecipe.setVisible(true);
                if(personal)
                    btnRemoveRecipe.setVisible(true);
                LOGGER.info("Recipe chosen");
            }else{
                btnDetailRecipe.setVisible(false);
                btnRemoveRecipe.setVisible(false);
                LOGGER.info("Recipe unchosen");
            }
        });
        
        btnDetailRecipe.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    createNewRecipe(event);
                    recipeTable.setItems(fillTable());
                } catch (IOException ex) {
                    showError("Error trying to create a recipe.");
                }
            }
        });
        
        btnRemoveRecipe.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                try{
                    deleteRecipe(event);
                }catch(Exception ex){
                    showError("Error trying to delete a recipe.");
                }
            }
        });
        
        //Show window.
        stage.show();
    }

    /**
     * Leads to the recipe creation screen.
     * @param event
     */
    public void createNewRecipe(ActionEvent event) throws IOException{
        //TODO Test
        Stage newRecipeDialog = new Stage();
        Parent root = FXMLLoader.load(
                AddRecipeController.class.getResource("Add_Recipe.fxml"));
        newRecipeDialog.setScene(new Scene(root));
        newRecipeDialog.initOwner(stage);
        newRecipeDialog.initModality(Modality.APPLICATION_MODAL);
        newRecipeDialog.showAndWait();
    }
    
    /**
     * Deletes the selected recipe after asking for confirmation.
     * @param event 
     */
    private void deleteRecipe(ActionEvent event) {
        Boolean confirmation = null;
        confirmation = showConfirmation("¿Estás seguro de que deseas eliminar esta receta?");
        if(confirmation){
            LOGGER.info("Removing recipe.");
            getUserManager().remove(recipeTable.getSelectionModel().getSelectedItem().getId());
        }else{
            LOGGER.info("Recipe removal cancelled.");
        }
    }
    
    private ObservableList<Recipe> fillTable() {
        //Create and fill the table.
        ObservableList<Recipe> recipes = FXCollections.observableArrayList(getRecipeManager().getAllRecipes());
        if(personal){
            stage.setTitle("Mis recetas");
            //Recipe filtering.
            for(Recipe recipe: recipes){
                if(!(recipe.getUser().equals(Reto2CRUD.getUser()))){
                    recipes.remove(recipe);
                }
            }
        }
        return recipes;
    }
}