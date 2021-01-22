package controller;

import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.Test;
import static org.junit.Assert.fail;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Ignore;
import static org.testfx.api.FxAssert.verifyThat;
import reto2crud.Reto2CRUD;
import static reto2crud.Reto2CRUD.BASE_URI;
import static reto2crud.Reto2CRUD.configFile;

/**
 *
 * @author Martin Valiente Ainz
 */
public class AdminUserWindowControllerTest extends ApplicationTest {
    
    private TableView managerTable;
    private Button btnUserListGenerateForm;



    public AdminUserWindowControllerTest() {
    }

    @Override
    public void start(Stage stage) throws Exception {
        Reto2CRUD.configFile = ResourceBundle.getBundle("config.config");
        Reto2CRUD.BASE_URI = configFile.getString("URL");
        new Reto2CRUD().start(stage);
        managerTable=lookup("#managerTable").queryTableView();
        btnUserListGenerateForm=lookup("#btnUserListGenerateForm").queryButton();

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
    }
    
    /**
     * Test user creation.
      */
    @Test
    //@Ignore
    public void testB_ChangeUserStatus() { 
        //get row count
        int rowCount=managerTable.getItems().size();       
        assertNotEquals("Table has no data: Cannot test.",rowCount,0);
        //Click on row 0
        Node row=lookup(".table-row-cell").nth(1).query();
        assertNotNull("Row is null: table has not that row. ",row);
        clickOn(row);
        // Click on cell 0
        Node cell=lookup(".table-cell").nth(2).query();
        clickOn(cell);
    }   

   

}
