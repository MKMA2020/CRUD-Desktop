package controller;

import java.util.ResourceBundle;
import javafx.stage.Stage;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import reto2crud.Reto2CRUD;
import static reto2crud.Reto2CRUD.configFile;

/**
 * Testing class for SideMenu controller. Tests Sidemenu button behavior using
 * TestFX framework.
 *
 * @author Martin Valiente Ainz
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SideMenuControllerTest extends ApplicationTest {

    public SideMenuControllerTest() {
    }
    
    /**
     * Starts application to be tested.
     * @param stage Primary Stage object
     * @throws Exception If there is any error
     */
    @Override
    public void start(Stage stage) throws Exception {
        Reto2CRUD.configFile = ResourceBundle.getBundle("config.config");
        Reto2CRUD.BASE_URI = configFile.getString("URL");
        new Reto2CRUD().start(stage);
    }

    /**
     * Test test that button Show My Recipes opens Recipe View window.
     */
    @Test
    public void TestA_btnShowMyRecipes() {
        clickOn("#btnShowMyRecipes");
        verifyThat("#RecipeView", isVisible());
    }
    
    /**
     * Test test that button Show Recipes opens Recipe View window.
     */
    @Test
    public void TestB_btnShowRecipes() {
        clickOn("#btnShowRecipes");
        verifyThat("#RecipeView", isVisible());
    }
    
    /**
     * Test test that button Show Menus opens Menu View window.
     */
    @Test
    public void TestC_btnShowMenus() {
        clickOn("#btnShowMenus");
        verifyThat("#windowMenu", isVisible());
    }
    
    /**
     * Test test that button Show Admin opens Administration window.
     */
    @Test
    public void TestD_btnShowAdmin() {
        clickOn("#btnShowAdmin");
        verifyThat("#AdminUserWindow", isVisible());
    }
    
    /**
     * Test test that button Exit opens Sign In window.
     */
    @Test
    public void TestE_btnExit() {
        clickOn("#btnExit");
        verifyThat("#windowSignIn", isVisible());
    }

}
