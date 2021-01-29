/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.sun.org.apache.xerces.internal.dom.ParentNode;
import static controller.GlobalController.LOGGER;
import enumeration.IngredientType;
import enumeration.RecipeType;
import exception.RecordExistsException;
import exception.TimeoutException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jdk.nashorn.internal.ir.CatchNode;
import model.Ingredient;
import model.Recipe;
import reto2crud.Reto2CRUD;

/**
 *
 * @author Martin Gros
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
     * MenuItem exit
     */
    @FXML
    private MenuItem menuItemExit;
    /**
     * MenuItem exit
     */
    @FXML
    private MenuItem menuItemRecetas;
    /**
     * MenuItem exit
     */
    @FXML
    private MenuItem menuItemMenus;
    /**
     * MenuItem exit
     */
    @FXML
    private MenuItem menuItemMisRecetas;
    /**
     * MenuItem exit
     */
    @FXML
    private MenuItem menuItemNewRecipe;
    /**
     * MenuItem exit
     */
    @FXML
    private MenuItem menuItemSaveRecipe;
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
    private ComboBox choiceRecipeType;

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

    /**
     * All the ingredients retrieved from the database will be loaded into
     *
     */
    private ObservableList<Ingredient> usedIngredients;
    /**
     * First one will only keep track of the
     * ingredients name, used for the first table column. The other one instead
     * of the used ingredients type. Used for the second table column
     */
    ObservableList<String> usedNameIngredientsObservableList;
    ObservableList<IngredientType> usedNameTypeObservableList;
    Boolean tableisselected = false;
    String name = null;
    String choiceSelection = null;
    RecipeType selection = null;
    Ingredient selectedItem = null;
    Boolean startError = false;
    Stage before2 = null;
    Boolean sent = false;

    /**
     * Sets the whole behaviour for the window. sets the addRecipe button to
     * disabled sets the menuItemSaveRecipe button to disabled
     *
     * @param root
     * @param beforeStage the previous opened stage since this is a pop up
     *
     */
    public void initStage(Parent root, Stage beforeStage) {
        LOGGER.info("Add Recipe");
        before2 = beforeStage;
        Scene scene = new Scene(root);
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Añadir Receta");
        stage.setResizable(false);
        btnAddRecipe.setDisable(true);
        menuItemSaveRecipe.setDisable(true);
        //load all ingredients into the observable arraylist
        try {
            usedIngredients = FXCollections.observableArrayList(getIngredientManager().findAll());

            List<String> usedNameIngredients = new ArrayList<String>();
            List<IngredientType> usedTypeIngredients = new ArrayList<IngredientType>();
            for (Ingredient e : usedIngredients) {
                usedNameIngredients.add(e.getName());
                usedTypeIngredients.add(e.getType());
            }
            usedNameIngredientsObservableList = FXCollections.observableArrayList(usedNameIngredients);
            ObservableList<IngredientType> usedNameTypeObservableList = FXCollections.observableArrayList(usedTypeIngredients);
            //Set window's events handlers
            handleWindowShowing();
            //listeners for the imputs
            //choiceRecipeType.getItems().addAll((Object[]) RecipeType.values());

            recipeIngredientTable.getSelectionModel().getTableView().getItems().size();
            txtRecipeName.textProperty().addListener((this::textchanged));
            txtRecipeKCal.textProperty().addListener((this::textchanged));
            txtareaRecipeSteps.textProperty().addListener((this::textchanged));

            choiceRecipeType.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
                try {
                    choiceSelection = newValue.toString();
                    selection = (RecipeType) newValue;
                } catch (Exception e) {
                    //  Block of code to handle errors
                }
                if (newValue != oldValue) {
                    activarboton();

                    LOGGER.info(choiceSelection);
                }

            });
            //activarboton();*/
            stage.show();
        } catch (TimeoutException e) {
            showWarning("Error en la base de datos, por favor prueba mas tarde.");
            LOGGER.warning("Critical error, server is off.");

        }

    }

    /**
     * When the window's first launches, adds tooltips and specifies the column
     * cells, including their factories. Also sets a listener for the chosen
     * row. Plus all the behaviours for the table buttons, and menu items.
     */
    private void handleWindowShowing() {
        LOGGER.info("Beginning AddRecipeController::handleWindowShowing");
        //addRecipe and delete row button are disabled.
        //btnAddRecipe.setDisable(true);
        deleteRow.setDisable(true);
        //table is editable
        recipeIngredientTable.setEditable(true);
        //tooltips
        tooltips();
        //choicebox for the type of recipe
        choiboxType();
        //factories for ingredient table cell values
        tableColumnIngredient.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));

        //factory for the cell
        //tableColumnIngredient.setCellFactory(ComboBoxTableCell.forTableColumn(getNames(usedIngredients)));
        tableColumnIngredient.setCellFactory(ChoiceBoxTableCell.forTableColumn(usedNameIngredientsObservableList));

        //tableColumnType.setCellFactory(TextFieldTableCell.forTableColumn());
        //Sets a listener for the chosen row
        recipeIngredientTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {

                deleteRow.setDisable(false);
                LOGGER.log(Level.INFO, "chosen");

            } else {
                deleteRow.setDisable(true);
                LOGGER.log(Level.INFO, "unchosen");
            }
        });

        //In order to save the changes we need to do this
        tableColumnIngredient.setOnEditCommit(data -> {
            data.getRowValue().setName(data.getNewValue());
            IngredientType hola = findtypebyname(data.getRowValue().getName());
            data.getRowValue().setType(hola);
            recipeIngredientTable.refresh();
            name = data.getNewValue();
            LOGGER.info("Selected: " + name);

        });
        deleteRow.setFocusTraversable(false);

        //buttons on actions
        addRow.setOnAction(e -> {
            Ingredient selectedItem = new Ingredient();
            selectedItem.setName("");
            selectedItem.setType(IngredientType.ChooseIngredient);
            recipeIngredientTable.getItems().add(selectedItem);
            activarboton();
        });
        deleteRow.setOnAction(e -> {
            try {
                selectedItem = recipeIngredientTable.getSelectionModel().getSelectedItem();
                recipeIngredientTable.getItems().remove(selectedItem);
                LOGGER.info("removing " + selectedItem.getName());

            } catch (NullPointerException ex) {

                recipeIngredientTable.getItems().remove(selectedItem);
            }
            activarboton();
        });
        /**
         * On button action to add a recipe.
         */
        btnAddRecipe.setOnAction(e -> {
            LOGGER.info("Adding Recipe");
            addRecipe();
        });
        /**
         * On button action to cancel adding a recipe.
         * Closes the stage.
         */
        btnCancelAddRecipe.setOnAction(e -> {
            LOGGER.info("Cancelling adding recipe");
            stage.close();
        });
        /**
         * On menuItem action to close the popup.
         */
        //menu items on actions
        menuItemExit.setOnAction(e -> {
            LOGGER.info("Bye Bye");
            stage.close();
        });
        /**
         * On menuItem action that clears all the fields and resets the buttons.
         */
        menuItemNewRecipe.setOnAction(e -> {
            limpiar();
        });
        /**
         * On menuItem action that calls addRecipe() in order to
         * save a new recipe.
         */
        menuItemSaveRecipe.setOnAction(e -> {
            LOGGER.info("Saving Recipe");
            addRecipe();
        });
        /**
         * On menuItem That closes the stage and goes back
         * to recetas
         */
        menuItemRecetas.setOnAction(e -> {
            LOGGER.log(Level.INFO, "BtnShowRecipes Clicked.");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RecipeView.fxml"));
            Parent root = null;
            try {
                root = (Parent) loader.load();
            } catch (IOException ex) {
                Logger.getLogger(SideMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
            //Get controller for graph 
            RecipeViewController controller = ((RecipeViewController) loader.getController());
            //Set a reference for Stage
            if (before2 == null) {
                controller.setStage(stage);
                controller.initStage(root, false);
            } else {
                controller.setStage(before2);
                controller.initStage(root, false);
                stage.close();
            }

            //Initializes primary stage
        });
        /**
         * On menuItem action that closes the stage and makes beforeStage
         * go to menus
         */
        menuItemMenus.setOnAction(e -> {

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
            if (before2 == null) {
                controller.setStage(stage);
                controller.initStage(root);
            } else {
                controller.setStage(before2);
                controller.initStage(root);
                stage.close();
            }

        });
        /**
         * On menuItem action that closes the stage and makes beforeStage
         * go to MisRecetas
         */
        menuItemMisRecetas.setOnAction(e -> {
            LOGGER.log(Level.INFO, "BtnShowMyRecipes Clicked.");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RecipeView.fxml"));
            Parent root = null;
            try {
                root = (Parent) loader.load();
            } catch (IOException ex) {
                Logger.getLogger(SideMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
            RecipeViewController controller = (loader.getController());
            if (before2 == null) {
                controller.setStage(stage);
                controller.initStage(root, true);
            } else {
                controller.setStage(before2);
                controller.initStage(root, true);
                stage.close();
            }

        });

        recipeIngredientTable.getSelectionModel().selectedItemProperty().addListener(this::handleclubtableselection);
    }

    /**
     * Method that logs the tables selection through the console
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void handleclubtableselection(Observable observable, Object oldValue, Object newValue) {
        tableisselected = true;
        if (newValue != null) {
            String name = newValue.toString();
            LOGGER.info("Choosen " + name);

        }
    }

    /**
     * Gets the ingredient type by their name
     *
     * @param name
     * @return type
     */
    private IngredientType findtypebyname(String name) {
        IngredientType tipo = null;
        for (Ingredient e : usedIngredients) {
            if (e.getName().equalsIgnoreCase(name)) {
                tipo = e.getType();
                break;

            }
        }
        return tipo;
    }

    /**
     * Sets the tooltips for the TextFields
     */
    private void tooltips() {
        txtRecipeName.setTooltip(new Tooltip("Nombre de la receta"));
        txtareaRecipeSteps.setTooltip(new Tooltip("Escribe los pasos!"));
        btnAddRecipe.setTooltip(new Tooltip("Click para añadir la receta!"));
        btnCancelAddRecipe.setTooltip(new Tooltip("Click para cancelar."));
    }

    /**
     * Fills the choicebox with the enum values
     */
    private void choiboxType() {
        choiceRecipeType.getItems().addAll((Object[]) RecipeType.values());

    }

    /**
     * This method's always looking whether the user's typing in both fields in
     * order to enable or disable the log in button.
     *
     * @param obv parameter used to observe the text fields.
     */
    private void textchanged(Observable obv) {

        if (this.txtRecipeKCal.getText().trim().equals("") || this.txtRecipeName.getText().trim().equals("") || this.txtareaRecipeSteps.getText().trim().equals("")) {
            {

                btnAddRecipe.setDisable(true);
                menuItemSaveRecipe.setDisable(true);
            }

        } else {
            activarboton();
            //btnAddRecipe.setDisable(false);

            //btnAddRecipe.setDisable(false);
        }

    }

    /**
     * Method that takes care of the buttons behaviour, the button for adding
     * the recipe will only be enabled if the sentence bellow is true
     */
    private void activarboton() {
        if (recipeIngredientTable.getSelectionModel().getTableView().getItems().size() != 0 && !this.txtRecipeKCal.getText().trim().equals("") && !this.txtRecipeName.getText().trim().equals("") && !this.txtareaRecipeSteps.getText().trim().equals("") && choiceSelection != null && sent == false) {
            btnAddRecipe.setDisable(false);
            menuItemSaveRecipe.setDisable(false);
            LOGGER.info("Add recipe button enabled.");
        } else {
            menuItemSaveRecipe.setDisable(true);
            btnAddRecipe.setDisable(true);

        }
    }

    /**
     * Method that adds the recipe, Compares the whole ingredient list with the
     * ones used in the table and makes a third array in order to have all the
     * details from the ingredients so it can be succesfully added to the
     * database, also sets the user as the recipe author.
     * Mind that observablelists are turned into arraylist for proper 
     * indexation.
     * A third arraylist is created witht the table ingredienta, since 
     * the ingredientsused on the table lack of id, so the server crashes
     */
    private void addRecipe() {
        LOGGER.info("Adding Recipe");
        boolean seEnVia = true;
        //Getting all the table ingredients and putting them into a list.
        List<Ingredient> listadefNames = recipeIngredientTable.getSelectionModel().getTableView().getItems();
        //Converting the list into an arrayList
        ArrayList<Ingredient> arrayListUSEDNAMES;
        if (listadefNames instanceof ArrayList<?>) {
            arrayListUSEDNAMES = (ArrayList<Ingredient>) listadefNames;
        } else {
            arrayListUSEDNAMES = new ArrayList<>(listadefNames);
        }
        //Converting usedingredients List into arraylist too for avoiding indexation error just in case.
        ArrayList<Ingredient> fullIngredients;
        if (listadefNames instanceof ArrayList<?>) {
            fullIngredients = (ArrayList<Ingredient>) usedIngredients;
        } else {
            fullIngredients = new ArrayList<>(usedIngredients);
        }
        Ingredient aux = null;
        //This will be the definitive list that will be sent to the server
        ArrayList<Ingredient> listadefFullIngredients = new ArrayList<Ingredient>();
        for (int i = 0; i < fullIngredients.size(); i++) {
            for (int j = 0; j < arrayListUSEDNAMES.size(); j++) {
                if (fullIngredients.get(i).getName().equalsIgnoreCase(arrayListUSEDNAMES.get(j).getName())) {
                    aux = new Ingredient();
                    aux = fullIngredients.get(i);
                    listadefFullIngredients.add(aux);
                }
            }
        }
        LOGGER.info("Ingredientes a enviar: ");
        for (int x = 0; x < listadefFullIngredients.size(); x++) {
            LOGGER.info(listadefFullIngredients.get(x).getName());
        }

        Recipe recipe = new Recipe();

        recipe.setName(txtRecipeName.getText());
        try {
            recipe.setKcal(Float.parseFloat(txtRecipeKCal.getText()));
            recipe.setSteps(txtareaRecipeSteps.getText());
            recipe.setType(selection);

            Set<Ingredient> foo = new HashSet<Ingredient>(listadefFullIngredients);

            recipe.setIngredients(foo);
            //USER NEEDS TO BE SET
            recipe.setUser(Reto2CRUD.getUser());
            recipe.setVerified(true);

            getRecipeManager().create(recipe);
            btnAddRecipe.setDisable(true);
            btnAddRecipe.setText("Añadida!");
            sent = true;
        } catch (NumberFormatException ex) {
            showError("Las calorias deben ser un numero!");
            seEnVia = false;
        }
        //activarboton();
    }

    public void limpiar() {
        LOGGER.info("New Recipe");
        sent = false;
        txtRecipeName.setText("");
        txtRecipeKCal.setText("");
        txtareaRecipeSteps.setText("");
        choiceRecipeType.getItems().clear();
        recipeIngredientTable.getSelectionModel().getTableView().getItems().clear();
        btnAddRecipe.setText("Añadir");

        choiboxType();
        activarboton();
    }

}
