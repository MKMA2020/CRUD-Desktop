package controller;

import static com.google.common.collect.Multimaps.index;
import enumeration.IngredientType;
import enumeration.RecipeType;
import static enumeration.RecipeType.Dessert;
import static enumeration.RecipeType.Drink;
import static enumeration.RecipeType.Main;
import static enumeration.RecipeType.Secondary;
import static enumeration.RecipeType.Sides;
import static enumeration.RecipeType.Snack;
import static enumeration.RecipeType.Starter;
import enumeration.UserType;
import factory.RecipeManagerFACTORY;
import factory.UserManagerFACTORY;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import manager.RecipeManager;
import model.Ingredient;
import model.Recipe;
import model.User;
import org.eclipse.persistence.internal.jpa.parsing.jpql.antlr.JPQLParser;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import org.testfx.matcher.control.ComboBoxMatchers;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import reto2crud.Reto2CRUD;
import static reto2crud.Reto2CRUD.configFile;
import security.Ciphering;

/**
 * Testing class for AddRecipe controller. Tests the behavior using TestFX
 * framework.
 *
 * @author Martin Gros
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AddRecipeControllerTest extends ApplicationTest {

    private TableView ingredientTable;

    /**
     * Starts application to be tested.
     *
     * @param stage Primary Stage object
     * @throws Exception If there is any error
     */
    @Override
    public void start(Stage stage) throws Exception {
        Reto2CRUD.configFile = ResourceBundle.getBundle("config.config");
        Reto2CRUD.BASE_URI = configFile.getString("URL");
        new Reto2CRUD().start(stage);
        ingredientTable = lookup("#recipeIngredientTable").queryTableView();
    }

    /**
     * Method will SetUp the TEST Class getting the target server URL and
     * creating a new USER to be tested.
     *
     * @throws Exception If there is any error
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // Get target server
        Reto2CRUD.configFile = ResourceBundle.getBundle("config.config");
        Reto2CRUD.BASE_URI = configFile.getString("URL");

        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

        // Create TEST user
        recipe.setName("Test");
        recipe.setType(RecipeType.Dessert);
        recipe.setKcal(Float.parseFloat("44"));
        recipe.setSteps("");
        ingredient.setName("gdfgh");
        ingredient.setId(Long.parseLong("1"));
        ingredient.setType(IngredientType.Dairy);
        ingredients.add(ingredient);
        Set<Ingredient> foo = new HashSet<Ingredient>(ingredients);
        recipe.setIngredients(foo);
        RecipeManagerFACTORY.getRecipeManager().create(recipe);
    }
    /**
     * Method will delete TEST_USER after all tests have been executed.
     *
     * @throws Exception If there is any error
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        // Get all users
        List<Recipe> recipes = (List<Recipe>) RecipeManagerFACTORY.getRecipeManager().getAllRecipes();

        // Get all Id's
        List<Long> ids = new ArrayList();
        recipes.forEach((recipe) -> {
            ids.add(recipe.getId());
        });

        // Delete TEST user    
        RecipeManagerFACTORY.getRecipeManager().remove(Collections.max(ids));
        
    }
    /**
     * Test that tests if the TextFields are empty
     */
    @Test
    public void TestA_emptyTextFields() {

        verifyThat("#txtareaRecipeSteps", hasText(""));
        verifyThat("#txtRecipeKCal", hasText(""));
        verifyThat("#txtRecipeName", hasText(""));
        verifyThat("Añadir", isDisabled());
        verifyThat("#addRow", isEnabled());
        verifyThat("#deleteRow", isDisabled());
    }
    /**
     * Test that tests if the ComboBox has all the items loaded properly
     */

    @Test
    public void TestB_AllItemsRecipeComboType() {
        clickOn("#choiceRecipeType");
        verifyThat("#choiceRecipeType", ComboBoxMatchers.hasItems(7));
        verifyThat("#choiceRecipeType", ComboBoxMatchers.containsExactlyItems(RecipeType.values()));

    }
    /**
     * Test that tests if the add button is disabled if txtfields are filled
     */

    @Test
    public void TestC_CheckRecipeComboType() {
        clickOn("#txtRecipeName");
        write("Sample");
        verifyThat("Añadir", isDisabled());

        clickOn("#txtareaRecipeSteps");
        write("Sample");
        verifyThat("Añadir", isDisabled());

        clickOn("#txtRecipeKCal");
        write("Sample");
        verifyThat("Añadir", isDisabled());
        // verifyThat("#btnAddRecipe", isDisabled());
        //verifyThat("#btnAddRecipe", isDisabled());

    }
    /**
     * Test that tests if the Combobox works properly and has all the right values in it.
     */

    @Test
    public void TestD_CheckRecipeComboType() {

        clickOn("#choiceRecipeType").type(KeyCode.DOWN);
        clickOn("#choiceRecipeType").type(KeyCode.ENTER);
        verifyThat("#choiceRecipeType", ComboBoxMatchers.hasSelectedItem(Starter));
        clickOn("#choiceRecipeType").type(KeyCode.DOWN);
        clickOn("#choiceRecipeType").type(KeyCode.ENTER);
        verifyThat("#choiceRecipeType", ComboBoxMatchers.hasSelectedItem(Main));
        clickOn("#choiceRecipeType").type(KeyCode.DOWN);
        clickOn("#choiceRecipeType").type(KeyCode.ENTER);
        verifyThat("#choiceRecipeType", ComboBoxMatchers.hasSelectedItem(Secondary));
        clickOn("#choiceRecipeType").type(KeyCode.DOWN);
        clickOn("#choiceRecipeType").type(KeyCode.ENTER);
        verifyThat("#choiceRecipeType", ComboBoxMatchers.hasSelectedItem(Dessert));
        clickOn("#choiceRecipeType").type(KeyCode.DOWN);
        clickOn("#choiceRecipeType").type(KeyCode.ENTER);
        verifyThat("#choiceRecipeType", ComboBoxMatchers.hasSelectedItem(Sides));
        clickOn("#choiceRecipeType").type(KeyCode.DOWN);
        clickOn("#choiceRecipeType").type(KeyCode.ENTER);
        verifyThat("#choiceRecipeType", ComboBoxMatchers.hasSelectedItem(Drink));
        clickOn("#choiceRecipeType").type(KeyCode.DOWN);
        clickOn("#choiceRecipeType").type(KeyCode.ENTER);
        verifyThat("#choiceRecipeType", ComboBoxMatchers.hasSelectedItem(Snack));

        
    }
    /**
     * Test that tests if the add recipe button gets enabled once both the txtfields and the recipe
     * type combobox are filled
     */

    @Test
    public void TestE_CheckRecipeComboTypeAndTxtFields() {
        clickOn("#txtRecipeName");
        write("Sample");
        verifyThat("Añadir", isDisabled());

        clickOn("#txtareaRecipeSteps");
        write("Sample");
        verifyThat("Añadir", isDisabled());

        clickOn("#txtRecipeKCal");
        write("Sample");
        verifyThat("Añadir", isDisabled());

        clickOn("#choiceRecipeType").type(KeyCode.DOWN);
        clickOn("#choiceRecipeType").type(KeyCode.ENTER);
        verifyThat("#choiceRecipeType", ComboBoxMatchers.hasSelectedItem(Starter));
        verifyThat("Añadir", isDisabled());

        
    }


    /**
     * Tests that adds a row and deletes it
     */
    @Test
    public void TestF_AddRowDeleteRow() {
        //get row count
        int rowCount = ingredientTable.getItems().size();
        assertEquals("Table has no data: Cannot test.", rowCount, 0);

        clickOn("#addRow");
        verifyThat("#deleteRow", isDisabled());
        rowCount = ingredientTable.getItems().size();
        assertNotEquals("Table has no data: Cannot test.", rowCount, 0);
        
        //Click on row 0
        Node row = lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        clickOn("#deleteRow");

    }
    /**
     * Tests that adds a row and sets an ingredient
     */
    @Test
    public void TestG_AddRowChooseIngredient() {
        clickOn("#addRow");
        verifyThat("#deleteRow", isDisabled());
        //Click on row 0
        Node row = lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        doubleClickOn(row);
        clickOn("gdfgh");
        Ingredient ingredient = (Ingredient)ingredientTable.getSelectionModel().getSelectedItem();
        assertEquals("gdfgh", ingredient.getName());
        

    }
    /**
     * Adds a row and an ingredient to the table and verifies that the selected ingredeint matches the one from the table
     */
    @Test
    public void TestH_AddRowChooseIngredientandVerify() {
        clickOn("#addRow");
        verifyThat("#deleteRow", isDisabled());
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        doubleClickOn(row);
        clickOn("gdfgh");
        Ingredient ingredient = (Ingredient)ingredientTable.getSelectionModel().getSelectedItem();
        assertEquals("gdfgh", ingredient.getName());
        clickOn("#deleteRow");
        

    }
    @Test
    public void TestI_ChooseIngredientVerifyAddRecipeIsDisabled() {
        clickOn("#addRow");
        verifyThat("#deleteRow", isDisabled());
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        doubleClickOn(row);
        clickOn("gdfgh");
        verifyThat("Añadir", isDisabled());
        

    }
    @Test
    public void TestJ_AddRecipeIsEnabled() {
        clickOn("#txtRecipeName");
        write("Sample");
        verifyThat("Añadir", isDisabled());

        clickOn("#txtareaRecipeSteps");
        write("Sample");
        verifyThat("Añadir", isDisabled());

        clickOn("#txtRecipeKCal");
        write("Sample");
        verifyThat("Añadir", isDisabled());

        clickOn("#choiceRecipeType").type(KeyCode.DOWN);
        clickOn("#choiceRecipeType").type(KeyCode.ENTER);
        verifyThat("#choiceRecipeType", ComboBoxMatchers.hasSelectedItem(Starter));
        
        clickOn("#addRow");
        verifyThat("#deleteRow", isDisabled());
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        doubleClickOn(row);
        clickOn("gdfgh");
        
        verifyThat("Añadir", isEnabled());
        

    }
    /**
     * Test that tries to add a new recipe where the kcal is a String
     */
    @Test
    public void TestK_KCalIsString() {
        clickOn("#txtRecipeName");
        write("Sample");
        verifyThat("Añadir", isDisabled());

        clickOn("#txtareaRecipeSteps");
        write("Sample");
        verifyThat("Añadir", isDisabled());

        clickOn("#txtRecipeKCal");
        write("Sample");
        verifyThat("Añadir", isDisabled());

        clickOn("#choiceRecipeType").type(KeyCode.DOWN);
        clickOn("#choiceRecipeType").type(KeyCode.ENTER);
        verifyThat("#choiceRecipeType", ComboBoxMatchers.hasSelectedItem(Starter));
        
        clickOn("#addRow");
        verifyThat("#deleteRow", isDisabled());
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        doubleClickOn(row);
        clickOn("gdfgh");
        clickOn("Añadir");
        verifyThat("Las calorias deben ser un numero!",isVisible());        
        clickOn("Aceptar");
        
        

    }
    /**
     * Test that adds correctly a recipe and checks if the the button gets disabled and changes
     */
    @Test
    public void TestL_CreateRecipe() {
        clickOn("#txtRecipeName");
        write("Sample");
        verifyThat("Añadir", isDisabled());

        clickOn("#txtareaRecipeSteps");
        write("Sample");
        verifyThat("Añadir", isDisabled());

        clickOn("#txtRecipeKCal");
        write("44");
        verifyThat("Añadir", isDisabled());

        clickOn("#choiceRecipeType").type(KeyCode.DOWN);
        clickOn("#choiceRecipeType").type(KeyCode.ENTER);
        verifyThat("#choiceRecipeType", ComboBoxMatchers.hasSelectedItem(Starter));
        
        clickOn("#addRow");
        verifyThat("#deleteRow", isDisabled());
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        doubleClickOn(row);
        clickOn("gdfgh");
        clickOn("Añadir");
        verifyThat("Añadida!", isVisible());
        verifyThat("Añadida!", isDisabled());
        
        

    }
/**
 * Clicks on the cancelar button, checks if my recipes windows shows
 */
    @Test
    public void TestM_ReturnToThePreviousWindow() {
        clickOn("Cancelar");
        verifyThat("#MyRecipes", isVisible());

    }

    @Test
    public void TestN_ClearSelection() {
        
        clickOn("#txtRecipeName");
        write("Sample");
        verifyThat("Añadir", isDisabled());

        clickOn("#txtareaRecipeSteps");
        write("Sample");
        verifyThat("Añadir", isDisabled());

        clickOn("#txtRecipeKCal");
        write("44");
        verifyThat("Añadir", isDisabled());

        clickOn("#choiceRecipeType").type(KeyCode.DOWN);
        clickOn("#choiceRecipeType").type(KeyCode.ENTER);
        verifyThat("#choiceRecipeType", ComboBoxMatchers.hasSelectedItem(Starter));

        clickOn("#addRow");
        verifyThat("#deleteRow", isDisabled());
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        doubleClickOn(row);
        clickOn("gdfgh");
        clickOn("Recetas").type(KeyCode.DOWN).type(KeyCode.ENTER);
        verifyThat("#txtareaRecipeSteps", hasText(""));
        verifyThat("#txtRecipeKCal", hasText(""));
        verifyThat("#txtRecipeName", hasText(""));
        verifyThat("Añadir", isDisabled());
        verifyThat("#addRow", isEnabled());
        verifyThat("#deleteRow", isDisabled());
        int rows =ingredientTable.getSelectionModel().getTableView().getItems().size();
        assertEquals(rows, 0);

    }
    
    /**
     * Clicks on the MenuBar Volver a and chooses mis recetas
     */
    @Test
    public void TestN_MenuBarMisRecetas() {
        clickOn("Volver A").type(KeyCode.DOWN).type(KeyCode.ENTER);
        verifyThat("#RecipeView", isVisible());
    
    }
    /**
     * Clicks on the MenuBar Volver a and chooses recetas
     */
    @Test
    public void TestO_MenuBarRecetas() {
        clickOn("Volver A").type(KeyCode.DOWN).type(KeyCode.DOWN).type(KeyCode.ENTER);
        verifyThat("#RecipeView", isVisible());
    
    }
    /**
     * Clicks on the MenuBar Volver a and chooses Menu
     */
    @Test
    public void TestP_MenuBarRecetas() {
        clickOn("Volver A").type(KeyCode.DOWN).type(KeyCode.DOWN).type(KeyCode.DOWN).type(KeyCode.ENTER);
        verifyThat("#MenuView", isVisible());
    
    }
    /**
     * Clicks on the MenuBar Salir Option
     */
    @Test
    public void TestQ_MenuBarSalir() {
        clickOn("Salir").clickOn("Salir");
        verifyThat("#MyRecipes", isVisible());
    
    }
    /**
     * Creates recipe from the MenuBar GuardarReceta Option
     */
    @Test
    public void TestR_CreateRecipeFromMenuItem() {
        clickOn("#txtRecipeName");
        write("Sample");
        verifyThat("Añadir", isDisabled());

        clickOn("#txtareaRecipeSteps");
        write("Sample");
        verifyThat("Añadir", isDisabled());

        clickOn("#txtRecipeKCal");
        write("44");
        verifyThat("Añadir", isDisabled());

        clickOn("#choiceRecipeType").type(KeyCode.DOWN);
        clickOn("#choiceRecipeType").type(KeyCode.ENTER);
        verifyThat("#choiceRecipeType", ComboBoxMatchers.hasSelectedItem(Starter));
        
        clickOn("#addRow");
        verifyThat("#deleteRow", isDisabled());
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        doubleClickOn(row);
        clickOn("gdfgh");
        clickOn("Recetas").type(KeyCode.DOWN).type(KeyCode.DOWN).type(KeyCode.ENTER);
        verifyThat("Añadida!", isVisible());
        verifyThat("Añadida!", isDisabled());
    
    }
    
}
