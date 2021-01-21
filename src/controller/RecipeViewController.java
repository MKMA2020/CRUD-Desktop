package controller;

import enumeration.RecipeType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
        
        
        
        sideMenuController.setStage(stage);
        sideMenuController.initStage();
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Recetas");
        stage.setResizable(false);
        
        btnDetailRecipe.setDisable(true);
        btnRemoveRecipe.setDisable(true);
        //Create an obsrvable list for recipes table.
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
        //Set table model.
            recipeTable.setItems(recipes);

        //Set factories for cell values in users table columns.
        tclTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
        tclType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tclKcal.setCellValueFactory(new PropertyValueFactory<>("kcal"));

        //Show window.
        stage.show();
    }
    
    
    /**
     * Leads to the recipe creation screen.
     */
    public void createNewRecipe(ActionEvent event){
        
    }
    
    /**
     * Leads to the selected recipe.
     */
    public void goToRecipe(ActionEvent event){
        
    }
    
    public void handleDetailRecipeButton(ActionEvent event){
        recipeTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection != null){
                btnDetailRecipe.setDisable(false);
                if(personal)
                    btnRemoveRecipe.setDisable(false);
                LOGGER.info("Recipe chosen");
            }else{
                btnDetailRecipe.setDisable(true);
                btnRemoveRecipe.setDisable(true);
                LOGGER.info("Recipe unchosen");
            }
        });
    }
}