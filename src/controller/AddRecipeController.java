/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import enumeration.IngredientType;
import enumeration.RecipeType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Ingredient;

/**
 *
 * @author 2dam
 */
public class AddRecipeController extends GlobalController {

    /**
     * MenuBar for the user
     */
    @FXML
    private MenuBar menuBarRecipe;
    /**
     * Menu for the user first option
     */
    @FXML
    private Menu menuRecipe;
    /**
     * MEenubar Second option
     */
    @FXML
    private Menu menuRecipeVolvera;
    /**
     * Menubar salir option
     */
    @FXML
    private Menu menuRecipeSalir;
    /**
     * TextField for the name of the cepe
     */
    @FXML
    private TextField txtRecipeName;
    /**
     * Textarea to write the steps
     */
    @FXML
    private TextField txtRecipeKCal;
    /**
     * Textarea to write the steps
     */
    @FXML
    private TextArea txtareaRecipeSteps;
    /**
     * ChoiceBox for the recipe type
     */
    
    @FXML
    private ChoiceBox choiceRecipeType;

    /**
     * TableView for the table of ingredients
     */
    @FXML
    private TableView<Ingredient> recipeIngredientTable;
    /**
     * TableView for the table of ingredients
     */
    @FXML
    private TableColumn<Ingredient, String> tableColumnIngredient;
    /**
     * TableView for the table of ingredients
     */
    @FXML
    private TableColumn<Ingredient, IngredientType> tableColumnType;
    /**
     * Button to create the recipe
     */
    @FXML
    private Button btnAddRecipe;
    /**
     * Button to cancel the recipe
     */
    @FXML
    private Button btnCancelAddRecipe;
    /**
     * Button to add rows to the table
     */
    @FXML
    private Button addRow;
    /**
     * Button to delete the selected row from the table
     */
    @FXML
    private Button deleteRow;

    /**
     * If the button is clicked a row will be added
     */
    @FXML
    private void handleButtonAddRow() {
      // addRow();
    }

    /**
     * If the button is clicked the selected row will be deleted
     */
    @FXML
    private void handleButtonDeleteRow() {
        //deleteRow();
    }
    private final ObservableList<Ingredient> usedIngredients = FXCollections.observableArrayList();
    
    public void initStage(Parent root) {
        
        Scene scene = new Scene(root);
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Iniciar Sesion");
        stage.setResizable(false);

        //Set window's events handlers
        stage.setOnShowing(this::handleWindowShowing);
        //SignInUsername.textProperty().addListener((this::textchanged));
        //SignInPWD.textProperty().addListener((this::textchanged));
        stage.show();
        
    }

    /**
     * When the window's first launched, sets the addRecipe button to disabled
     * and adds ,tooltips and specifies the column cells.
     */
    private void handleWindowShowing(WindowEvent event) {
        LOGGER.info("Beginning LoginController::handleWindowShowing");
        //addRecipe button is disabled.
        btnAddRecipe.setDisable(true);
        //table is editable

        recipeIngredientTable.setEditable(true);
        //tooltips
        txtRecipeName.setTooltip(new Tooltip("Nombre de la receta"));
        txtareaRecipeSteps.setTooltip(new Tooltip("Escribe los pasos!"));
        btnAddRecipe.setTooltip(new Tooltip("Click para añadir la receta!"));
        btnCancelAddRecipe.setTooltip(new Tooltip("Click para cancelar."));
        //choicebox
        choiceRecipeType.getItems().add(RecipeType.Dessert);
        choiceRecipeType.getItems().add(RecipeType.Drink);
        choiceRecipeType.getItems().add(RecipeType.Main);
        choiceRecipeType.getItems().add(RecipeType.Secondary);
        choiceRecipeType.getItems().add(RecipeType.Sides);
        choiceRecipeType.getItems().add(RecipeType.Snack);
        choiceRecipeType.getItems().add(RecipeType.Starter);
        recipeIngredientTable.setItems(usedIngredients);
        //recipeIngredientTable.getColumns().addAll( tableColumnIngredient, tableColumnType);

        //factories for ingredient table cell values
        tableColumnIngredient.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnIngredient.setCellValueFactory(new PropertyValueFactory<>("type"));
        tableColumnIngredient.setCellFactory(ChoiceBoxTableCell.forTableColumn("hola", "patata"));
        tableColumnIngredient.setOnEditCommit(data -> {
            data.getRowValue().setName(data.getNewValue());
        });
        
        deleteRow.setFocusTraversable(false);
         addRow.setOnAction(e -> {
            Ingredient selectedItem = new Ingredient();
            recipeIngredientTable.getItems().add(selectedItem);
        });
        deleteRow.setOnAction(e -> {
            Ingredient selectedItem = recipeIngredientTable.getSelectionModel().getSelectedItem();
            recipeIngredientTable.getItems().remove(selectedItem);
        });

        // switch to edit mode on keypress
        // this must be KeyEvent.KEY_PRESSED so that the key gets forwarded to the editing cell; it wouldn't be forwarded on KEY_RELEASED
    }
    
    private void addRow() {
        Ingredient ingredient = new Ingredient();
        recipeIngredientTable.setItems(usedIngredients);
        
    }
    
    private void deleteRow() {
        recipeIngredientTable.getItems().removeAll(recipeIngredientTable.getSelectionModel().getSelectedItems());

        // table selects by index, so we have to clear the selection or else items with that index would be selected 
        recipeIngredientTable.getSelectionModel().clearSelection();
    }
    /**
     * This method's always looking whether the user's typing in both fields in
     * order to enable or disable the log in button.
     *
     * @param obv parameter used to observe the text fields.
     *//*
    private void textchanged(Observable obv) {

        if (this.SignInUsername.getText().trim().equals("") || this.SignInPWD.getText().trim().equals("")) {
            SignInBtn.setDisable(true);
        } else {
            SignInBtn.setDisable(false);
        }

    }*/
    
}
