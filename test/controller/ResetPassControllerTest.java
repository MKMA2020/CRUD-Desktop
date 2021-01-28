package controller;

import enumeration.UserType;
import factory.UserManagerFACTORY;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.stage.Stage;
import model.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import reto2crud.Reto2CRUD;
import static reto2crud.Reto2CRUD.configFile;
import security.Ciphering;

/**
 * TestFX test class for ResetPassController.
 * @author Aitor garcia
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ResetPassControllerTest extends ApplicationTest{
    
    //private static User testUser = new User();
    
    
    public void start(Stage stage) throws Exception {
        //Get target server
        Reto2CRUD.configFile = ResourceBundle.getBundle("config.config");
        Reto2CRUD.BASE_URI = configFile.getString("URL");
        
        new Reto2CRUD().start(stage);
    }
    
    /**
     * Method for the case in which the user doesn't exist beforehand.
     * @throws Exception generic exception in case the user creation causes an error.
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        
        Reto2CRUD.configFile = ResourceBundle.getBundle("config.config");
        Reto2CRUD.BASE_URI = configFile.getString("URL");
        //Create test user
        Date current = new Date();
        Ciphering cypher = new Ciphering();
        current.setTime(System.currentTimeMillis());
        
        
        Ciphering encrypter = new Ciphering();
        User user = new User();

        // Create TEST user
        user.setLogin("tester");
        user.setEmail("testemail@tester.test");
        user.setFullName("Testy McTestface");
        user.setStatus(Boolean.TRUE);
        user.setResetPassword(Boolean.FALSE);
        user.setType(UserType.Normal);
        user.setLastAccess(new Date());
        user.setLastsPasswordChange(new Date());
        user.setPassword(encrypter.cifrarTexto("Tester1"));
        UserManagerFACTORY.getUserManager().create(user);
        
    }
    /**
     * Method will delete TEST_USER after all tests have been executed.
     *
     * @throws Exception If there is any error
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        Reto2CRUD.configFile = ResourceBundle.getBundle("config.config");
        Reto2CRUD.BASE_URI = configFile.getString("URL");
        // Get all users
        List<User> users = UserManagerFACTORY.getUserManager().findAll();

        // Get all Id's
        List<Long> ids = new ArrayList();
        users.forEach((user) -> {
            ids.add(user.getId());
        });

        // Delete TEST user      
        UserManagerFACTORY.getUserManager().remove(Collections.max(ids));
    }
    
    /**
     * Method that checks that the user exists.
     */
    @Test
    public void testA_initialState(){
        clickOn("#signInBtnResetPwd");
        sleep(1000);
        verifyThat("#txtResetUsername", hasText(""));
        verifyThat("#txtResetEmail", hasText(""));
    }
    
    /**
     * Method that verifies the wrong password does not work.
    
    /**
     * Method that verifies that an error window is displayed when the password is too long.
     */
    @Test
    public void testB_userTooLong(){
        clickOn("#signInBtnResetPwd");
        sleep(1000);
        clickOn("#txtResetUsername");
        write("wowthisissuchalongname");
        clickOn("#txtResetEmail");
        write("testemail@tester.test");
        clickOn("#resetBtnOk");
        verifyThat("El nombre de usuario es demasiado largo",isVisible());        
        clickOn("Aceptar");
    }
    
    /**
     * Method that verifies that an error window is displayed when the password is too short.
     */
    @Test
    public void testC_userTooShort(){
        clickOn("#signInBtnResetPwd");
        sleep(1000);
        clickOn("#txtResetUsername");
        write("test");
        clickOn("#txtResetEmail");
        write("testemail@tester.test");
        clickOn("#resetBtnOk");
        verifyThat("El nombre de usuario es demasiado corto",isVisible());        
        clickOn("Aceptar");
    }
    
    /**
     * Method that changes the password of the test user.
     */
    @Test
    public void testD_resetPassword(){
        clickOn("#signInBtnResetPwd");
        sleep(1000);
        clickOn("#txtResetUsername");
        write("Tester");
        clickOn("#txtResetEmail");
        write("testemail@tester.test");
        clickOn("#resetBtnOk");
        sleep(2500);
        verifyThat("#resetBtnOk", isDisabled());
    }
    
    /**
     * Method that verifies the old password does not work.
     */
    @Test
    public void testE_verifyPaswordIsReset(){
        clickOn("#signInUsername");
        write("tester");
        clickOn("#signInPWD");
        write("Tester1");
        clickOn("#signInBtn");
        sleep(1000);
        verifyThat("Nombre de usuario o contraseñas erroneas", isVisible());
        clickOn("Aceptar");
    }
    /**
     * Test that will only be valid if the server is out of reach
     */
    
    @Test
    public void testF_ServerError() {
        clickOn("#signInUsername");
        write("MartinG");
        clickOn("#signInPWD");
        write("Aa12345!");
        clickOn("#signInBtn");
        verifyThat("Error en la conexion con la base de datos", isVisible());
        clickOn("Aceptar");
    }
}
