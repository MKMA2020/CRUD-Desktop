package controller;

import java.util.ResourceBundle;
import javafx.stage.Stage;
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

/**
 * TestFX test class for ResetPassController.
 * @author Aitor garcia
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ResetPassControllerTest extends ApplicationTest{
    
//    User needed for the test:
//    Login: tester
//    Email: testemail@tester.test
//    Full name: Testy McTestface
//    Password: Tester1
    
    
    public void start(Stage stage) throws Exception {
        //Get target server
        Reto2CRUD.configFile = ResourceBundle.getBundle("config.config");
        Reto2CRUD.BASE_URI = configFile.getString("URL");
        
        new Reto2CRUD().start(stage);
    }
    
    /**
     * Method for the case in which the user doesn't exist beforehand.
     * @throws Exception generic exception in case of any error.
     */
    /*@BeforeClass
    public static void setUpBeforeClass() throws Exception {
        
        
        //Create test user
        Date current = new Date();
        Ciphering cypher = new Ciphering();
        current.setTime(System.currentTimeMillis());
        
        
        testUser.setLogin("tester");
        testUser.setEmail("testemail@tester.test");
        testUser.setFullName("Testy McTestface");
        testUser.setStatus(true);
        testUser.setPassword(cypher.cifrarTexto("Tester1"));
        testUser.setLastAccess(current);
        testUser.setLastsPasswordChange(current);
        testUser.setType(UserType.Normal);
        
        UserManagerFACTORY.getUserManager().create(testUser);
        
    }*/
    
    /**
     * Method that checks that the user exists.
     */
    @Test
    public void testA_initialState(){
        clickOn("#SignInBtnResetPwd");
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
        clickOn("#SignInBtnResetPwd");
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
        clickOn("#SignInBtnResetPwd");
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
        clickOn("#SignInBtnResetPwd");
        sleep(1000);
        clickOn("#txtResetUsername");
        write("tester");
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
        clickOn("#SignInUsername");
        write("tester");
        clickOn("#SignInPWD");
        write("Tester1");
        clickOn("#SignInBtn");
        sleep(1000);
        verifyThat("Nombre de usuario o contraseñas erroneas", isVisible());
        clickOn("Aceptar");
    }
}
