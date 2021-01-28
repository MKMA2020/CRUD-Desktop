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
import reto2crud.Reto2CRUD;
import static reto2crud.Reto2CRUD.configFile;
import security.Ciphering;

/**
 *
 * @author Martin
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MenuViewControllerTest extends ApplicationTest {

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

        Menu menu = new Menu();

        // Create TEST menu
        menu.setDescription("This is a test Menu");
        menu.setName("AAAAAAAAAAAAAAAAAAAAAAAAA");
        menu.setType(MenuType.Lunch);

        MenuManagerFACTORY.getMenuManager().create(menu);
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
    }
    /**
     * Test thar verifies that tha the table isn't empty
     */
    @Test
    public void testB_TtableInfo() {
        int rowCount = menuTable.getColumns().size();
        assertNotNull(rowCount);
        //Click on row 0
        Node row = lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        //verifyThat(nodeQuery, nodeMatcher);
    }
    /**
     * Test that chooses a row and verifies that the delete menu button is activated
     */
    @Test
    public void testC_DeleteMenuIsEnabled() {
        //get row count
        int rowCount = menuTable.getColumns().size();
        assertNotEquals("Table has no data: Cannot test.", rowCount, 0);

        // Select row and get menu.
        Node cell = lookup(".table-row-cell").nth(0).query();
        clickOn(cell);
        verifyThat("#btnDeleteMenu", isEnabled());

        
    }
    /**
     * Test that verifies that the user status has really changed
     */

    @Test
 
    public void testD_ChangeUserStatus() {


        Menu menu = (Menu) menuTable.getSelectionModel().getSelectedItem();
        String descriptionBefore = menu.getDescription();

        // Click on cell 4 and change Status
        Node cell = lookup("#clmnDescription").nth(1).query();
        doubleClickOn(cell).write("Cambio").type(KeyCode.ENTER);

        // Select row and get user.
        cell = lookup(".table-row-cell").nth(0).query();
        clickOn(cell);
        Menu menu2 = (Menu) menuTable.getSelectionModel().getSelectedItem();
        assertNotEquals(descriptionBefore, menu2.getDescription());

    }
    @Test
    /**
     * Test thar chooses our menu created on the beforeclass
     * and proceeds to delete it, verifies that the table size is smaller after
     * deleting it
     */

    public void testE_deleteMenu() {

        int row = 0;
        int row2= 0;
        Menu menu = (Menu) menuTable.getSelectionModel().getSelectedItem();
        String descriptionBefore = menu.getDescription();
        
        //gets the rows
        row=menuTable.getSelectionModel().getTableView().getItems().size();

        // Click, select and deletes it
        Node cell = lookup("#clmnDescription").nth(1).query();
        clickOn("#btnDeleteMenu");
        clickOn("Aceptar");

        //gets the new rows
        row2=menuTable.getSelectionModel().getTableView().getItems().size();
        assertNotEquals(row, row2);

    }
    /**
     * Test that clicks add menu button and closes it
     */
    public void testF_addMenu() {

        clickOn("#btnCreateMenu");
        verifyThat("#choiceMain", isVisible());
        clickOn("#btnCancel");
        verifyThat("#windowMenu", isVisible());

    }
    @Test
    public void testG_ServerError() {
        clickOn("#signInUsername");
        write("MartinG");
        clickOn("#signInPWD");
        write("Aa12345!");
        clickOn("#signInBtn");
        verifyThat("Error en la conexion con la base de datos", isVisible());
        clickOn("Aceptar");
    }
}
