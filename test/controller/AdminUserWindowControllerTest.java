package controller;

import enumeration.UserType;
import factory.UserManagerFACTORY;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
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
 * @author Martin Valiente Ainz
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AdminUserWindowControllerTest extends ApplicationTest {

    private TableView managerTable;
    private Button btnUserListGenerateForm;

    public AdminUserWindowControllerTest() {
    }
    
    /**
     * 
     * @param stage Primary Stage object
     * @throws Exception If there is any error
     */
    @Override
    public void start(Stage stage) throws Exception {
        new Reto2CRUD().start(stage);
        managerTable = lookup("#managerTable").queryTableView();
        btnUserListGenerateForm = lookup("#btnUserListGenerateForm").queryButton();
        clickOn("Id");
        clickOn("Id");

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

        Ciphering encrypter = new Ciphering();
        User user = new User();

        // Create TEST user
        user.setLogin("TEST_USER");
        user.setEmail("user@test.com");
        user.setFullName("TEST USER");
        user.setStatus(Boolean.TRUE);
        user.setResetPassword(Boolean.FALSE);
        user.setType(UserType.Normal);
        user.setLastAccess(new Date());
        user.setLastsPasswordChange(new Date());
        user.setPassword(encrypter.cifrarTexto("TEST USER"));
        UserManagerFACTORY.getUserManager().create(user);
    }
    
    /**
     * Method will delete TEST_USER after all tests have been executed.
     * @throws Exception If there is any error
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        // Get all users
        List<User> users = UserManagerFACTORY.getUserManager().findAll();
        
        // Get all Id's
        List<Long> ids = new ArrayList();
        users.forEach((user) -> {ids.add(user.getId());});

        // Delete TEST user      
        UserManagerFACTORY.getUserManager().remove(Collections.max(ids));
    }

    /**
     * Test of initial state of managerTableView.
     */
    @Test
    public void testA_initialState() {
        verifyThat("#tclAdminId", hasText(""));
        verifyThat("#tclAdminLogin", hasText(""));
        verifyThat("#tclAdminEmail", hasText(""));
        verifyThat("#tclAdminStatus", isEnabled());
        verifyThat("#tclAdminLastAccess", hasText(""));
        verifyThat("#tclAdminResetPassword", isEnabled());
        verifyThat("#tclAdminLastPasswordChange", hasText(""));
        verifyThat("#btnUserListGenerateForm", isEnabled());
        //Click on row 0
        Node row = lookup(".table-row-cell").nth(1).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
    }

    /**
     * Test status CheckBox.
     */
    @Test
    //@Ignore
    public void testB_ChangeUserStatus() {
        //get row count
        int rowCount = managerTable.getItems().size();
        assertNotEquals("Table has no data: Cannot test.", rowCount, 0);

        // Select row and get user.
        Node cell = lookup(".table-cell").nth(0).query();
        clickOn(cell);
        User user = (User) managerTable.getSelectionModel().getSelectedItem();
        Boolean statusBefore = user.getStatus();

        // Click on cell 4 and change Status
        cell = lookup(".table-cell").nth(3).query();
        clickOn(cell);

        // Select row and get user.
        cell = lookup(".table-cell").nth(0).query();
        clickOn(cell);
        user = (User) managerTable.getSelectionModel().getSelectedItem();
        assertNotEquals(statusBefore, user.getStatus());

    }

    /**
     * Test resetPassword CheckBox.
     */
    @Test
    //@Ignore
    public void testC_ResetPassword() {

        //get row count
        int rowCount = managerTable.getItems().size();
        assertNotEquals("Table has no data: Cannot test.", rowCount, 0);

        // Select row and get user.
        Node cell = lookup(".table-cell").nth(0).query();
        clickOn(cell);
        User user = (User) managerTable.getSelectionModel().getSelectedItem();
        Boolean resetBefore = user.getStatus();
        Date lastPasswordChangeBefore = user.getLastsPasswordChange();

        // Click on cell 4 and change Status
        cell = lookup(".table-cell").nth(5).query();
        clickOn(cell);

        // Select row and get user.
        cell = lookup(".table-cell").nth(0).query();
        clickOn(cell);
        user = (User) managerTable.getSelectionModel().getSelectedItem();
        // Check that boolean value updates on resetPassword click
        assertNotEquals(resetBefore, user.getResetPassword());

        sleep(5000);
        user = (User) managerTable.getSelectionModel().getSelectedItem();
        // Check that lastPasswordChange updates on resetPassword click
        assertNotEquals(lastPasswordChangeBefore, user.getLastsPasswordChange());

    }

}