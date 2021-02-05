package controller;

import enumeration.IngredientType;
import enumeration.RecipeType;
import enumeration.UserType;
import factory.IngredientManagerFACTORY;
import factory.RecipeManagerFACTORY;
import factory.UserManagerFACTORY;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Ingredient;
import model.Recipe;
import model.User;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.assertNotEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isInvisible;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import reto2crud.Reto2CRUD;
import static reto2crud.Reto2CRUD.configFile;
import security.Ciphering;

/**
 * TestFX test class for RecipeViewController.
 * @author Aitor Garcia
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RecipeViewControllerTest extends ApplicationTest{
    
    private TableView recipeTable;
    
    public RecipeViewControllerTest(){
    }

    
    /**
     * Method that creates a new Recipe, its User and its list of ingredients.
     * @throws Exception generic exception in case of any error.
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception{
        //Get target server
        
        Reto2CRUD.configFile = ResourceBundle.getBundle("config.config");
        Reto2CRUD.BASE_URI = configFile.getString("URL");
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Reto2CRUD.class);
        
        Recipe testRecipe = new Recipe();
        Recipe testRecipe2 = new Recipe();
        User testUser = new User();
        User testUser2 = new User();
        Ingredient testIngredient = new Ingredient();
        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
        Ciphering cypher = new Ciphering();
        
        testIngredient = IngredientManagerFACTORY.getIngredientManager().find(Long.valueOf(1));
        
        //Create test users
        testUser.setLogin("tester");
        testUser.setEmail("testemail@tester.test");
        testUser.setFullName("Testy McTestface");
        testUser.setStatus(true);
        testUser.setResetPassword(false);
        testUser.setType(UserType.Normal);
        testUser.setLastAccess(new Date());
        testUser.setLastsPasswordChange(new Date());
        testUser.setPassword(cypher.cifrarTexto("Tester1"));
        UserManagerFACTORY.getUserManager().create(testUser);
        
        testUser2.setLogin("tester2");
        testUser2.setEmail("testemail2@tester.test");
        testUser2.setFullName("Testy McTestface2");
        testUser2.setStatus(true);
        testUser2.setResetPassword(false);
        testUser2.setType(UserType.Normal);
        testUser2.setLastAccess(new Date());
        testUser2.setLastsPasswordChange(new Date());
        testUser2.setPassword(cypher.cifrarTexto("Tester2"));
        UserManagerFACTORY.getUserManager().create(testUser2);
        
        
        //Get all users
        List<User> users = (List<User>) UserManagerFACTORY.getUserManager().findAll();
        
        //Get al IDs (Users)
        List<Long> userIds = new ArrayList();
        users.forEach((user) -> {
            userIds.add(user.getId());
        });
        
        //Create test recipe
        testRecipe.setName("Test recipe");
        testRecipe.setType(RecipeType.Main);
        testRecipe.setKcal(Float.valueOf(250)); 
        testRecipe.setSteps("This recipe is done like this... this... and this");
        testRecipe.setVerified(true);
        ingredients.add(testIngredient);
        Set<Ingredient> foo = new HashSet<Ingredient>(ingredients);
        testRecipe.setIngredients(foo);
        testRecipe.setUser(UserManagerFACTORY.getUserManager().find((Collections.max(userIds)) - 1));
        RecipeManagerFACTORY.getRecipeManager().create(testRecipe);
        
        testRecipe2.setName("Test recipe2");
        testRecipe2.setType(RecipeType.Drink);
        testRecipe2.setKcal(Float.valueOf(500)); 
        testRecipe2.setSteps("This recipe is done like the first one");
        testRecipe.setVerified(true);
        ingredients.add(testIngredient);
        testRecipe2.setIngredients(foo);
        testRecipe2.setUser(UserManagerFACTORY.getUserManager().find(Collections.max(userIds)));
        RecipeManagerFACTORY.getRecipeManager().create(testRecipe2);
        
        
        
    }
    
    /**
     * Method that deletes the objects used for this test.
     * @throws Exception generic exception in case of any error.
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception{
        //Get all users
        List<User> users = (List<User>) UserManagerFACTORY.getUserManager().findAll();
        
        //Get all IDs (Users)
        List<Long> userIds = new ArrayList();
        users.forEach((user) -> {
            userIds.add(user.getId());
        });
        
        //Delete test user
        UserManagerFACTORY.getUserManager().remove(Collections.max(userIds));
        UserManagerFACTORY.getUserManager().remove((Collections.max(userIds)) - 1);
        
        //Get all recipes
        List<Recipe> recipes = (List<Recipe>) RecipeManagerFACTORY.getRecipeManager().getAllRecipes();
        
        //Get all IDs (Recipes)
        List<Long> recipeIds = new ArrayList();
        recipes.forEach((recipe) -> {
            recipeIds.add(recipe.getId());
        });
        
        //Delete test recipe
        RecipeManagerFACTORY.getRecipeManager().remove(Collections.max(recipeIds));
        RecipeManagerFACTORY.getRecipeManager().remove((Collections.max(recipeIds)) - 1);
    }
    
    /**
     * Method that gets executed before every test.
     */
    @Before
    public void beforeTest(){
        recipeTable = lookup("#recipeTable").queryTableView();
        
    }
    
    /**
     * Tests the initial state of the window.
     * @throws Exception generic exception in case of any error.
     */
    @Test
    public void testA_initialState()throws Exception{
        clickOn("#signInUsername");
        write("tester");
        clickOn("#signInPWD");
        write("Tester1");
        clickOn("#signInBtn");
        sleep(1000);
        clickOn("#btnShowRecipes");
        sleep(150);
        verifyThat("Test recipe", isVisible());
        verifyThat("Test recipe2", isVisible());
    }
    
    /**
     * Tests the window that displays the current user's recipes.
     * @throws Exception generic exception in case of any error.
     */
    @Test
    public void testB_filterMyRecipes()throws Exception{
        int rows = recipeTable.getSelectionModel().getTableView().getItems().size();
        clickOn("Mis Recetas");
        recipeTable = lookup("#recipeTable").queryTableView();
        int rowsAfterChange = recipeTable.getSelectionModel().getTableView().getItems().size();
        verifyThat("Test recipe", isVisible());
        assertNotEquals(rows, rowsAfterChange);        
    }
    
    /**
     * Tests if pressing the "create a recipe" button leads to the correct window.
     * @throws Exception generic exception in case of any error.
     */
    @Test
    public void testC_createNewRecipe()throws Exception{
        clickOn("#btnNewRecipe");
        sleep(500);
        verifyThat("Nueva Receta", isVisible());
    }
}
