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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import manager.RecipeManager;
import model.Ingredient;
import model.Recipe;
import model.User;
import org.eclipse.persistence.internal.jpa.parsing.jpql.antlr.JPQLParser;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
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
    private TextField signin;

    /**
     * Starts application to be tested.
     *
     * @param stage Primary Stage object
     * @throws Exception If there is any error
     */
    /**
     * Method will SetUp the TEST Class getting the target server URL and
     * creating a new USER to be tested.
     *
     * @throws Exception If there is any error
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        Reto2CRUD.configFile = ResourceBundle.getBundle("config.config");
        Reto2CRUD.BASE_URI = configFile.getString("URL");
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Reto2CRUD.class);

        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

        // Create TEST Recipe
        recipe.setName("Test");
        recipe.setType(RecipeType.Dessert);
        recipe.setKcal(Float.parseFloat("44"));
        recipe.setSteps("");
        ingredient.setName("Agua");
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

        //Update a value
        recipes.get(recipes.size() - 1).setName("Test Cambiado");
        RecipeManagerFACTORY.getRecipeManager().update(recipes.get(recipes.size() - 1));

        // Delete TEST recipe    
        RecipeManagerFACTORY.getRecipeManager().remove(Collections.max(ids));

    }

    /**
     * Test that tests if the TextFields are empty
     */
    @Test
    public void TestA_emptyTextFields() {
        write("marting");
        clickOn("#signInPWD");
        write("Aa12345!");
        clickOn("#signInBtn");

        verifyThat("#RecipeView", isVisible());
        clickOn("#btnNewRecipe");

        verifyThat("#txtareaRecipeSteps", hasText(""));
        verifyThat("#txtRecipeKCal", hasText(""));
        verifyThat("#txtRecipeName", hasText(""));
        verifyThat("Añadir", isDisabled());
        verifyThat("#addRow", isEnabled());
        verifyThat("#deleteRow", isDisabled());
        ingredientTable = lookup("#recipeIngredientTable").queryTableView();
        int rows = ingredientTable.getSelectionModel().getTableView().getItems().size();
        assertEquals(rows, 0);

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
        clickOn("#menuRecipe").type(KeyCode.DOWN).type(KeyCode.ENTER);

    }

    /**
     * Test that tests if the Combobox works properly and has all the right
     * values in it.
     */
    @Test
    public void TestD_CheckRecipeComboType() {

        clickOn("#choiceRecipeType").type(KeyCode.DOWN).type(KeyCode.ENTER);
        verifyThat("#choiceRecipeType", ComboBoxMatchers.hasSelectedItem(Starter));
        clickOn("#choiceRecipeType").type(KeyCode.DOWN).type(KeyCode.ENTER);
        verifyThat("#choiceRecipeType", ComboBoxMatchers.hasSelectedItem(Main));
        clickOn("#choiceRecipeType").type(KeyCode.DOWN).type(KeyCode.ENTER);
        verifyThat("#choiceRecipeType", ComboBoxMatchers.hasSelectedItem(Secondary));
        clickOn("#choiceRecipeType").type(KeyCode.DOWN).type(KeyCode.ENTER);
        verifyThat("#choiceRecipeType", ComboBoxMatchers.hasSelectedItem(Dessert));
        clickOn("#choiceRecipeType").type(KeyCode.DOWN).type(KeyCode.ENTER);
        verifyThat("#choiceRecipeType", ComboBoxMatchers.hasSelectedItem(Sides));
        clickOn("#choiceRecipeType").type(KeyCode.DOWN).type(KeyCode.ENTER);
        verifyThat("#choiceRecipeType", ComboBoxMatchers.hasSelectedItem(Drink));
        clickOn("#choiceRecipeType").type(KeyCode.DOWN).type(KeyCode.ENTER);
        verifyThat("#choiceRecipeType", ComboBoxMatchers.hasSelectedItem(Snack));

        clickOn("#menuRecipe").type(KeyCode.DOWN).type(KeyCode.ENTER);

    }

    /**
     * Test that tests if the add recipe button gets enabled once both the
     * txtfields and the recipe type combobox are filled
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

        clickOn("#choiceRecipeType").type(KeyCode.DOWN).type(KeyCode.ENTER);
        verifyThat("#choiceRecipeType", ComboBoxMatchers.hasSelectedItem(Starter));
        verifyThat("Añadir", isDisabled());

        clickOn("#menuRecipe").type(KeyCode.DOWN).type(KeyCode.ENTER);

    }

    /**
     * Tests that adds a row and deletes it
     */
    @Test
    public void TestF_AddRowDeleteRow() {
        //get row count
        ingredientTable = lookup("#recipeIngredientTable").queryTableView();
        int rowCount = ingredientTable.getItems().size();
        assertEquals("Table has no data: Cannot test.", rowCount, 0);

        clickOn("#addRow");
        verifyThat("#deleteRow", isDisabled());
        rowCount = ingredientTable.getItems().size();
        assertNotEquals("Table has no data: Cannot test.", rowCount, 0);

        //Click on row 0
        Node row = lookup("#tableColumnIngredient").nth(1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        clickOn("#deleteRow");

    }

    /**
     * Tests that adds a row and sets an ingredient, also tests size
     */
    @Test
    public void TestG_AddRowChooseIngredient() {
        ingredientTable = lookup("#recipeIngredientTable").queryTableView();
        clickOn("#addRow");
        verifyThat("#deleteRow", isDisabled());
        //Click on row 0
        Node row = lookup("#tableColumnIngredient").nth(1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        doubleClickOn(row);
        clickOn("Agua");
        Ingredient ingredient = (Ingredient) ingredientTable.getSelectionModel().getSelectedItem();
        assertEquals("Agua", ingredient.getName());
        //rowcount must be one
        int rowCount = ingredientTable.getItems().size();
        assertEquals("Table has no data: Cannot test.", rowCount, 1);

    }

    /**
     * Adds a row and an ingredient to the table and verifies that the selected
     * ingredeint matches the one from the table
     */
    @Test
    public void TestH_AddExtraRowChooseIngredientandVerify() {
        ingredientTable = lookup("#recipeIngredientTable").queryTableView();
        clickOn("#addRow");
        Node row = lookup("#tableColumnIngredient").nth(2).query();
        clickOn(row);
        doubleClickOn(row);
        clickOn("Manzana");
        Ingredient ingredient = (Ingredient) ingredientTable.getSelectionModel().getSelectedItem();
        assertEquals("Manzana", ingredient.getName());
        //RowCount must be 2.

        int rowCount = ingredientTable.getItems().size();
        assertEquals("Table has no data: Cannot test.", rowCount, 2);
    }

    /**
     * Vefiries that the add recipe button is disabled plus if the tables row
     * count it's not 0 since it has a row now.
     */
    @Test
    public void TestI_ChooseIngredientVerifyAddRecipeIsDisabled() {
        ingredientTable = lookup("#recipeIngredientTable").queryTableView();
        clickOn("#addRow");
        Node row = lookup("#tableColumnIngredient").nth(3).query();
        clickOn(row);
        doubleClickOn(row);
        clickOn("Pomelo");
        verifyThat("Añadir", isDisabled());

        int rows = ingredientTable.getSelectionModel().getTableView().getItems().size();
        assertNotEquals(rows, 0);

        clickOn("#menuRecipe").type(KeyCode.DOWN).type(KeyCode.ENTER);

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
        Node row = lookup("#tableColumnIngredient").nth(1).query();
        clickOn(row);
        doubleClickOn(row);
        clickOn("Agua");

        verifyThat("Añadir", isEnabled());

    }

    /**
     * Test that tries to add a new recipe where the kcal is a String
     */
    @Test
    public void TestK_KCalIsString() {

        clickOn("Añadir");
        verifyThat("Las calorias deben ser un numero!", isVisible());
        clickOn("Aceptar");

    }

    /**
     * Test that adds correctly a recipe and checks if the the button gets
     * disabled and changes
     */
    @Test
    public void TestL_CreateRecipe() {

        doubleClickOn("#txtRecipeKCal");
        write("44");
        verifyThat("Añadir", isEnabled());

        verifyThat("#choiceRecipeType", ComboBoxMatchers.hasSelectedItem(Starter));

        clickOn("Añadir");
        verifyThat("Añadida!", isVisible());
        verifyThat("Añadida!", isDisabled());

        clickOn("#menuRecipe").type(KeyCode.DOWN).type(KeyCode.ENTER);

    }

    /**
     * Test that adds correctly a recipe with 2 ingredients being the same The
     * own SET java class deletes duplicated entries so it doensn't really
     * matters if the user adds multiple times the same item
     */
    @Test
    public void TestM_AddRecipeSameIngredients() {
        clickOn("#txtRecipeName");
        write("Sample2");
        verifyThat("Añadir", isDisabled());

        clickOn("#txtareaRecipeSteps");
        write("Sample");
        verifyThat("Añadir", isDisabled());

        doubleClickOn("#txtRecipeKCal");
        write("44");
        verifyThat("Añadir", isDisabled());

        clickOn("#choiceRecipeType").type(KeyCode.DOWN);
        clickOn("#choiceRecipeType").type(KeyCode.ENTER);
        verifyThat("#choiceRecipeType", ComboBoxMatchers.hasSelectedItem(Starter));

        clickOn("#addRow");
        verifyThat("#deleteRow", isDisabled());
        Node row = lookup("#tableColumnIngredient").nth(1).query();
        clickOn(row);
        doubleClickOn(row);
        clickOn("Agua");

        clickOn("#addRow");
        verifyThat("#deleteRow", isEnabled());
        row = lookup("#tableColumnIngredient").nth(2).query();
        clickOn(row);
        doubleClickOn(row);
        clickOn("Agua");

        verifyThat("Añadir", isEnabled());

        verifyThat("#choiceRecipeType", ComboBoxMatchers.hasSelectedItem(Starter));

        clickOn("Añadir");
        verifyThat("Añadida!", isVisible());
        verifyThat("Añadida!", isDisabled());

        clickOn("#menuRecipe").type(KeyCode.DOWN).type(KeyCode.ENTER);

    }

    /**
     * Clicks on the cancelar button, checks if my recipes windows shows
     */
    @Test
    public void TestN_ReturnToThePreviousWindow() {
        clickOn("Cancelar");

    }

    @Test
    public void TestO_ClearSelection() {

        verifyThat("#RecipeView", isVisible());
        clickOn("#btnNewRecipe");

        clickOn("#txtRecipeName");
        write("Sample");
        verifyThat("Añadir", isDisabled());

        clickOn("#txtareaRecipeSteps");
        write("Sample");
        verifyThat("Añadir", isDisabled());

        clickOn("#txtRecipeKCal");
        write("44");
        verifyThat("Añadir", isDisabled());

        clickOn("#choiceRecipeType").type(KeyCode.DOWN).type(KeyCode.ENTER);
        verifyThat("#choiceRecipeType", ComboBoxMatchers.hasSelectedItem(Starter));

        clickOn("#addRow");
        verifyThat("#deleteRow", isDisabled());
        Node row = lookup("#tableColumnIngredient").nth(1).query();
        clickOn(row);
        doubleClickOn(row);
        clickOn("Agua");
        clickOn("#menuRecipe").type(KeyCode.DOWN).type(KeyCode.ENTER);
        verifyThat("#txtareaRecipeSteps", hasText(""));
        verifyThat("#txtRecipeKCal", hasText(""));
        verifyThat("#txtRecipeName", hasText(""));
        verifyThat("Añadir", isDisabled());
        verifyThat("#addRow", isEnabled());
        verifyThat("#deleteRow", isDisabled());
        ingredientTable = lookup("#recipeIngredientTable").queryTableView();
        int rows = ingredientTable.getSelectionModel().getTableView().getItems().size();
        assertEquals(rows, 0);

    }

    /**
     * Clicks on the MenuBar Volver a and chooses mis recetas
     */
    @Test
    public void TestP_MenuBarMisRecetas() {

        clickOn("#menuRecipeVolvera").type(KeyCode.DOWN).type(KeyCode.ENTER);
        verifyThat("#RecipeView", isVisible());

    }

    /**
     * Method that verifies that the previously added recipes are added.
     */
    @Test
    public void TestQ_VerifyTheRecipes() {

        verifyThat("Sample", isVisible());
        verifyThat("Sample2", isVisible());

    }

    /**
     * Clicks on the MenuBar Volver a and chooses recetas
     */
    @Test
    public void TestR_MenuBarRecetas() {

        verifyThat("#RecipeView", isVisible());
        clickOn("#btnNewRecipe");

        clickOn("#menuRecipeVolvera").type(KeyCode.DOWN).type(KeyCode.DOWN).type(KeyCode.ENTER);
        verifyThat("#RecipeView", isVisible());

    }

    /**
     * Clicks on the MenuBar Volver a and chooses Menu
     */
    @Test
    public void TestS_MenuBarMenus() {
        verifyThat("#RecipeView", isVisible());
        clickOn("#btnNewRecipe");

        clickOn("#menuRecipeVolvera").type(KeyCode.DOWN).type(KeyCode.DOWN).type(KeyCode.DOWN).type(KeyCode.ENTER);
        verifyThat("#windowMenu", isVisible());

    }

    /**
     * Clicks on the MenuBar Salir Option
     */
    @Test
    public void TestT_MenuBarSalir() {
        verifyThat("#windowMenu", isVisible());
        clickOn("#btnShowRecipes");
        clickOn("#btnNewRecipe");

        clickOn("#menuRecipeSalir").type(KeyCode.DOWN).type(KeyCode.ENTER);

    }

    /**
     * Creates recipe from the MenuBar GuardarReceta Option
     */
    @Test
    public void TestU_CreateRecipeFromMenuItem() {
        verifyThat("#RecipeView", isVisible());
        clickOn("#btnNewRecipe");

        clickOn("#txtRecipeName");
        write("Sample3");
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
        Node row = lookup("#tableColumnIngredient").nth(1).query();
        clickOn(row);
        doubleClickOn(row);
        clickOn("Agua");
        clickOn("#menuRecipe").type(KeyCode.DOWN).type(KeyCode.DOWN).type(KeyCode.ENTER);
        verifyThat("Añadida!", isVisible());
        verifyThat("Añadida!", isDisabled());

    }

    /**
     * Add Recipe button doesn't get enabled with other changes until new recipe
     * button is pressed.
     */
    @Test
    public void TestV_VerifyEnableButtonUntilReset() {

        doubleClickOn("#txtRecipeName");
        write("Sample4");
        doubleClickOn("#txtareaRecipeSteps");
        write("Sample4");
        clickOn("#addRow");
        Node row = lookup("#tableColumnIngredient").nth(2).query();
        clickOn(row);
        doubleClickOn(row);
        clickOn("Cerdo");

        clickOn("#menuRecipe").type(KeyCode.DOWN).type(KeyCode.ENTER);
        clickOn("#txtRecipeName");
        write("Sample3");
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
        row = lookup("#tableColumnIngredient").nth(1).query();
        clickOn(row);
        doubleClickOn(row);
        clickOn("Agua");

        verifyThat("Añadir", isEnabled());

    }

    /**
     * Add another recipe with the same name. As many recipes as the user wants
     * can have the same Recipe name So this test should pass with no problem
     */

    @Test
    public void TestW_AddRecipeSameName() {

        doubleClickOn("#txtRecipeName");
        write("Sample");
        verifyThat("Añadir", isEnabled());
        clickOn("Añadir");
        verifyThat("Añadida!", isVisible());
        verifyThat("Añadida!", isDisabled());

    }

    /**
     * Method that will only pass if the server is off.
     */
    @Test
    public void TestX_ServerError() {
        clickOn("#signInUsername");
        write("Martin");
        clickOn("#signInPWD");
        write("Aa12345!");
        clickOn("#signInBtn");
        verifyThat("Error en la conexion con la base de datos", isVisible());
        clickOn("Aceptar");
    }
    /**
     *
     */

}
