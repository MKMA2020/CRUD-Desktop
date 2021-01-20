package controller;

import static controller.GlobalController.LOGGER;
import enumeration.RecipeType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Recipe;

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
     * Button that leads to the selected recipe.
     */
    @FXML
    public Button btnDetailRecipe;

    /**
     * Recipe table data model.
     */
    private ObservableList<Recipe> recipes;
    

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
    public void handleRecipeTableSelectionChanged(ObservableValue observable, Object oldValue, Object newValue) {
        if (newValue != null) {
            //If there is a row selected
            LOGGER.info("Evento disparado");
            LOGGER.info(recipeTable.getSelectionModel().getSelectedItem().getName());
            btnDetailRecipe.setDisable(false);
        } else {
            btnDetailRecipe.setDisable(true);
        }
    }
    
    public void createNewRecipe(){
        
    }
    
    public void handleDetailRecipe(){
        
    }
    
    /**
     * InitStage Method for Recipes window.
     *
     * @param root The Parent object representing root node of view graph.
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage = new Stage();    
        stage.setScene(scene);
        stage.setTitle("Recetas");
        stage.setResizable(false);
        
        btnDetailRecipe.setDisable(true);
        
        //Create an obsrvable list for recipes table.
        ObservableList<Recipe> allRecipes = FXCollections.observableArrayList(recipeManager.getAllRecipes());
        //Set table model.
        recipeTable.setItems(allRecipes);

        //Set factories for cell values in users table columns.
        tclTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
        tclType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tclKcal.setCellValueFactory(new PropertyValueFactory<>("kcal"));

        //Show window.
        stage.show();
    }

}
