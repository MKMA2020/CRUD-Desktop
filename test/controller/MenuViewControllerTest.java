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
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
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

    @Override
    public void start(Stage stage) throws Exception {

        new Reto2CRUD().start(stage);
        menuTable = lookup("#menuTable").queryTableView();
    }
    /**
     * Method will SetUp the TEST Class getting the target server URL and
     * creating a new USER to be tested.
     * @throws Exception If there is any error
     */

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // Get target server
        Reto2CRUD.configFile = ResourceBundle.getBundle("config.config");
        Reto2CRUD.BASE_URI = configFile.getString("URL");

        Menu menu = new Menu();
        
        

        // Create TEST menu
        menu.setDescription("This is a test Menu");
        menu.setName("This is a test Menu");
        menu.setType(MenuType.Lunch);
        
        
        MenuManagerFACTORY.getMenuManager().create(menu);
    }
    /**
     * Method will delete TEST_MENU after all tests have been executed.
     * @throws Exception If there is any error
     */
    
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        // Get all users
        List<Menu> menus = (List<Menu>) MenuManagerFACTORY.getMenuManager().findAll();

        // Get all Id's
        List<Long> ids = new ArrayList();
        menus.forEach((menu) -> {
            ids.add(menu.getId());
        });

        // Delete TEST user    
        MenuManagerFACTORY.getMenuManager().delete(Collections.max(ids));
        
    }
    /**
     * Test of initial state of managerTableView.
     */
    @Test
    public void testA_initialState() {
        verifyThat("#clmnMenuName", hasText(""));
        verifyThat("#clmnMenuType", hasText(""));
        verifyThat("#clmnDescription", hasText(""));
        
        //Click on row 0
        Node row = lookup(".table-row-cell").nth(1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
    }
    @Test
    //@Ignore
    public void testB_ChangeUserStatus() {
        //get row count
        int rowCount = menuTable.getColumns().size();
        assertNotEquals("Table has no data: Cannot test.", rowCount, 0);

        // Select row and get menu.
        Node cell = lookup(".table-row-cell").nth(1).query();
        clickOn(cell);
        
        Menu menu = (Menu) menuTable.getSelectionModel().getSelectedItem();
        String descriptionBefore = menu.getDescription();

        // Click on cell 4 and change Status
        cell = lookup("#clmnDescription").nth(2).query();
        doubleClickOn(cell).write("Cambio").type(KeyCode.ENTER);

        // Select row and get user.
        cell = lookup(".table-row-cell").nth(1).query();
        clickOn(cell);
        Menu menu2 = (Menu) menuTable.getSelectionModel().getSelectedItem();
        assertNotEquals(descriptionBefore, menu2.getDescription());

    }
}
