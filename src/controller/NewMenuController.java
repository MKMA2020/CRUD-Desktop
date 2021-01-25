package controller;

import enumeration.MenuType;
import enumeration.Menu_recipeType;
import enumeration.RecipeType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Menu;
import model.Menu_Recipe;
import model.Recipe;

/**
 *
 * @author Kerman
 */
public class NewMenuController extends GlobalController {
    
    /**
     * Field for entering the menu´s name
     */
    @FXML
    public TextField menuName;
    
    /**
     * ChoiceBox to choose menu type
     */
    @FXML
    public ChoiceBox choiceType;
    
    /**
     * ChoiceBox to choose starter recipe.
     */
    @FXML
    public ChoiceBox choiceStarter;
    
    /**
     * ChoiceBox to choose main recipe.
     */
    @FXML
    public ChoiceBox choiceMain;
    
    /**
     * ChoiceBox to choose secondary recipe.
     */
    @FXML
    public ChoiceBox choiceSecondary;
    
    /**
     * ChoiceBox to choose dessert recipe.
     */
    @FXML
    public ChoiceBox choiceDessert;
    
    /**
     * ChoiceBox to choose sides recipe.
     */
    @FXML
    public ChoiceBox choiceSides;
    
    /**
     * ChoiceBox to choose drink recipe.
     */
    @FXML
    public ChoiceBox choiceDrink;
    
    @FXML
    public Button btnMenuCreate;
    
    @FXML
    public Button btnMenuCancel;
    
    
    public void initStage(Parent root) {
        //STAGE PROPERTIES
        //Initializes the stage and sets its attributes.
        Scene scene = new Scene(root);
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Creación de menú");
        stage.setResizable(false);  
        
        //CHOICEBOX PROPERTIES
        //Creates a series of arrays for the choiceboxes
        ObservableList<String> types =
        FXCollections.observableArrayList("Desayuno","Comida","Aperitivo","Cena");
        ObservableList<Recipe> starter = 
        FXCollections.observableArrayList(getRecipeManager().getRecipesByType(RecipeType.Starter));
        ObservableList<Recipe> main = 
        FXCollections.observableArrayList(getRecipeManager().getRecipesByType(RecipeType.Main));
        ObservableList<Recipe> secondary = 
        FXCollections.observableArrayList(getRecipeManager().getRecipesByType(RecipeType.Secondary));
        ObservableList<Recipe> dessert = 
        FXCollections.observableArrayList(getRecipeManager().getRecipesByType(RecipeType.Dessert));
        ObservableList<Recipe> sides = 
        FXCollections.observableArrayList(getRecipeManager().getRecipesByType(RecipeType.Sides));
        ObservableList<Recipe> drink = 
        FXCollections.observableArrayList(getRecipeManager().getRecipesByType(RecipeType.Drink));
        
        //Sets the values of the arrays to the choiceboxes
        choiceType.setItems(types);
        choiceStarter.setItems(starter);
        choiceMain.setItems(main);
        choiceSecondary.setItems(secondary);
        choiceDessert.setItems(dessert);
        choiceSides.setItems(sides);
        choiceDrink.setItems(drink);
        
        //Sets listeners for a change of their selected item
        choiceType.getSelectionModel().selectedItemProperty().addListener(this::selectionChanged);
        choiceStarter.getSelectionModel().selectedItemProperty().addListener(this::selectionChanged);
        choiceMain.getSelectionModel().selectedItemProperty().addListener(this::selectionChanged);
        choiceSecondary.getSelectionModel().selectedItemProperty().addListener(this::selectionChanged);
        choiceDessert.getSelectionModel().selectedItemProperty().addListener(this::selectionChanged);
        choiceSides.getSelectionModel().selectedItemProperty().addListener(this::selectionChanged);
        choiceDrink.getSelectionModel().selectedItemProperty().addListener(this::selectionChanged);
        menuName.textProperty().addListener(this::selectionChanged);
        
        btnMenuCreate.setDisable(true);
        //Show window.
        stage.showAndWait();
    }
    
    private void selectionChanged (Observable obs) {
        LOGGER.info("cambio");
        if (menuName.getText().equals("") ||
            choiceType.getSelectionModel().getSelectedItem() == null ||
            choiceStarter.getSelectionModel().getSelectedItem() == null ||
            choiceMain.getSelectionModel().getSelectedItem() == null ||
            choiceSecondary.getSelectionModel().getSelectedItem() == null ||
            choiceDessert.getSelectionModel().getSelectedItem() == null ||
            choiceSides.getSelectionModel().getSelectedItem() == null ||
            choiceDrink.getSelectionModel().getSelectedItem() == null   
           ) {
            btnMenuCreate.setDisable(true);
        } else {
            btnMenuCreate.setDisable(false);
        }
    }
    
    @FXML
    private void validateMenu(ActionEvent event)  {
        if (menuName.getLength()>20)
            showError("El nombre del menú es demasiado largo!");
        else {
            createNewMenu();
        }
    }
    
    @FXML
    private void cancelNewMenu (ActionEvent event) {
        stage.close();
    }

    private void createNewMenu() {
        //Creates an empty menu object and sets its data
        Menu menu = new Menu();
        menu.setName(menuName.getText().toString());
        menu.setDescription("Haz click para cambiarme!!");
        //Sets the type of the menu depending of the one chosen
        switch (choiceType.getSelectionModel().getSelectedItem().toString()) {
            case "Desayuno":
                menu.setType(MenuType.Breakfast);
                break;
            case "Comida":
                menu.setType(MenuType.Lunch);
                break;
            case "Aperitivo":
                menu.setType(MenuType.Snack);
                break;
            case "Cena":
                menu.setType(MenuType.Dinner);
                break;
        }
        getMenuManager().create(menu);
        //This should add the recipe-menu relation, but has many issues
        /**List<Menu> allMenus = getMenuManager().findAll();
        Menu editMenu = allMenus.get(allMenus.size()-1);
        //Creates a list to add to the menu 
        ArrayList <Menu_Recipe> menurecipes = new ArrayList();
        //Starter recipe
        menurecipes.add(new Menu_Recipe(
        choiceStarter.getSelectionModel().getSelectedItem(),
        Menu_recipeType.Starter, editMenu));
        //Main recipe
        menurecipes.add(new Menu_Recipe(
        choiceMain.getSelectionModel().getSelectedItem(),
        Menu_recipeType.Main,editMenu));
        //Secondary recipe
        menurecipes.add(new Menu_Recipe(
        choiceSecondary.getSelectionModel().getSelectedItem(),
        Menu_recipeType.Secondary,editMenu));
        //Dessert recipe
        menurecipes.add(new Menu_Recipe(
        choiceDessert.getSelectionModel().getSelectedItem(),
        Menu_recipeType.Dessert,editMenu));
        //Drink recipe
        menurecipes.add(new Menu_Recipe(
        choiceDrink.getSelectionModel().getSelectedItem(),
        Menu_recipeType.Drink,editMenu));
        //Sides recipe
        menurecipes.add(new Menu_Recipe(
        choiceSides.getSelectionModel().getSelectedItem(),
        Menu_recipeType.Side,editMenu));
        Set <Menu_Recipe> recipeSet = new HashSet<Menu_Recipe> (menurecipes);
        //Changes the arraylist to a set in order to add it to the menu
        editMenu.setMenurecipes(recipeSet);       
        //Sends the created menu to the server to add it
        getMenuManager().edit(editMenu);**/
        LOGGER.info("Menu successfully created");
    }
        
    }
    

