/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import enumeration.IngredientType;
import enumeration.MenuType;
import enumeration.RecipeType;
import enumeration.UserType;
import factory.MenuManagerFACTORY;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import model.Ingredient;
import model.Menu;
import model.Recipe;
import model.User;
import org.junit.AfterClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import reto2crud.Reto2CRUD;
import static reto2crud.Reto2CRUD.configFile;
import security.Ciphering;

/**
 *
 * @author 2dam
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NewMenuControllerTest extends ApplicationTest {

    private TableView menuTable;

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
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Reto2CRUD.class);

    }

    @Before
    public void beforeTest() {
        menuTable = lookup("#menuTable").queryTableView();
    }

    /**
     * Test of initial state of managerTableView.
     */
    @Test
    public void testA_initialState() {
        write("marting");
        clickOn("#signInPWD");
        write("Aa12345!");
        clickOn("#signInBtn");
        verifyThat("#RecipeView", isVisible());
        clickOn("#btnShowMenus");
        verifyThat("#windowMenu", isVisible());
        menuTable = lookup("#menuTable").queryTableView();
        verifyThat("#btnDeleteMenu", isDisabled());
        verifyThat("#btnCreateMenu", isEnabled());
        clickOn("#btnCreateMenu");
        verifyThat("#btnMenuCancel", isVisible());
        verifyThat("#btnMenuCancel", isVisible());

    }
    @Test
    public void testB_emptyFields() {
        
    }
}
