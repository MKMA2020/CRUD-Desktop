/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ResourceBundle;
import javafx.stage.Stage;
import org.junit.After;
import reto2crud.Reto2CRUD;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import static reto2crud.Reto2CRUD.configFile;


/**
 *
 * @author Martin
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignUpControllerTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        Reto2CRUD.configFile = ResourceBundle.getBundle("config.config");
        Reto2CRUD.BASE_URI = configFile.getString("URL");
        new Reto2CRUD().start(stage);
        clickOn("#SignInBtnSignUp");

    }
    
    @After
    public void afterTest () {
        clickOn("#SignUpBtnBack");
    }


    /**
     * This method will verify the stage on launch. TextBoxes will be empty by
     * default. SignUpButton will be disabled as default. Back button will be
     * enabled by default.
     */
    @Test
    public void testA_initialstate() {
        verifyThat("#SignUpUsername", hasText(""));
        verifyThat("#SignUpPWD", hasText(""));
        verifyThat("#SignUpPWD2", hasText(""));
        verifyThat("#SignUpEmail", hasText(""));
        verifyThat("#SignUpFN", hasText(""));
        verifyThat("#SignUpBtn", isDisabled());
        verifyThat("#SignUpBtnBack", isEnabled());
        
    }

    /**
     * This method will verify that SignUpButton is only enabled when all fields
     * contain text. If there is at least one empty field SignUpButton should be
     * disabled.
     */  
    @Test
    public void testB_SignUpButtonEnabled() {

        clickOn("#SignUpUsername");
        write("asd");
        verifyThat("#SignUpBtn", isDisabled());

        clickOn("#SignUpPWD");
        write("asd");
        verifyThat("#SignUpBtn", isDisabled());

        clickOn("#SignUpPWD2");
        write("asd");
        verifyThat("#SignUpBtn", isDisabled());

        clickOn("#SignUpEmail");
        write("asd");
        verifyThat("#SignUpBtn", isDisabled());

        clickOn("#SignUpFN");
        write("asd");
        verifyThat("#SignUpBtn", isEnabled());

    }

    /**
     * This method will verify that an alert is thrown if the user is too short.
     */
    @Test
    public void testC_UsernameShort() {
        clickOn("#SignUpUsername");
        write("a");
        clickOn("#SignUpPWD");
        write("Alberto!Garcia8");
        clickOn("#SignUpPWD2");
        write("Alberto!Garcia8");
        clickOn("#SignUpEmail");
        write("albertogarcia@gmail.com");
        clickOn("#SignUpFN");
        write("Alberto García");
        clickOn("#SignUpBtn");
        verifyThat("El nombre de usuario es demasiado corto.\n", isVisible());
        clickOn("Aceptar");
    }
    /**
     * This method checks if the username is too long
     */
    @Test
    public void testD_UsernameLong() {
        clickOn("#SignUpUsername");
        write("aaaaaaaaaaaaaaaaaaaaa");
        clickOn("#SignUpPWD");
        write("Alberto!Garcia8");
        clickOn("#SignUpPWD2");
        write("Alberto!Garcia8");
        clickOn("#SignUpEmail");
        write("albertogarcia@gmail.com");
        clickOn("#SignUpFN");
        write("Alberto García");
        clickOn("#SignUpBtn");
        verifyThat("El nombre de usuario es demasiado largo.\n", isVisible());
        clickOn("Aceptar");
    }

    /**
     * This method will verify that an alert is thrown if the password is
     * too short    
     */
    @Test
    public void testE_PasswordShort() {
        clickOn("#SignUpUsername");
        write("Alberto");
        clickOn("#SignUpPWD");
        write("1Aq");
        clickOn("#SignUpPWD2");
        write("1Aq");
        clickOn("#SignUpEmail");
        write("albertogarcia@gmail.com");
        clickOn("#SignUpFN");
        write("Alberto García");
        clickOn("#SignUpBtn");
        verifyThat("La contraseña es demasiado corta\n", isVisible());
        clickOn("Aceptar");
    }
    /**
     * This method checks if the password's format is valid.
     */
    @Test
    public void testF_PasswordFormat() {
        clickOn("#SignUpUsername");
        write("Alberto");
        clickOn("#SignUpPWD");
        write("aaaaa");
        clickOn("#SignUpPWD2");
        write("aaaaa");
        clickOn("#SignUpEmail");
        write("albertogarcia@gmail.com");
        clickOn("#SignUpFN");
        write("Alberto García");
        clickOn("#SignUpBtn");
        verifyThat("La contraseña debe de incluir una mayúscula, una minúscula y un numero\n", isVisible());
        clickOn("Aceptar");
    }
    
    /**
     * This method will verify that an alert is thrown if the password does not match.
     */
    @Test
    public void testG_PasswordNoMatch(){
        clickOn("#SignUpUsername");
        write("Alberto");
        clickOn("#SignUpPWD");
        write("Alberto!Garcia8");
        clickOn("#SignUpPWD2");
        write("Alberto!Garcia81");
        clickOn("#SignUpEmail");
        write("albertogarcia@gmail.com");
        clickOn("#SignUpFN");
        write("Alberto García");
        clickOn("#SignUpBtn");
        verifyThat("Las contraseñas no coinciden\n",isVisible()); 
        clickOn("Aceptar");
    }
    
    /**
     * This method will verify that an alert is thrown if the Email format is incorrect.
     */
    @Test
    public void testH_EmailWrong(){
        clickOn("#SignUpUsername");
        write("Alberto");
        clickOn("#SignUpPWD");
        write("Alberto!Garcia8");
        clickOn("#SignUpPWD2");
        write("Alberto!Garcia8");
        clickOn("#SignUpEmail");
        write("albertogarciagmail.com");
        clickOn("#SignUpFN");
        write("Alberto García");
        clickOn("#SignUpBtn");
        verifyThat("Formato incorrecto de correo electrónico\n",isVisible()); 
        clickOn("Aceptar");
    }

    /**
     * This method will verify that once pushed the button Sign Up changes to
     * Signed Up.
     */
    @Test
    public void testI_SignUpButtonChanged() {
        clickOn("#SignUpUsername");
        write("Alberto");
        clickOn("#SignUpPWD");
        write("Alberto!Garcia8");
        clickOn("#SignUpPWD2");
        write("Alberto!Garcia8");
        clickOn("#SignUpEmail");
        write("albertogarcia@gmail.com");
        clickOn("#SignUpFN");
        write("Alberto García");
        clickOn("#SignUpBtn");
        verifyThat("#SignUpBtn", isDisabled());
    }

    /**
     * This methods check that an alert shows when the user tries to register
     * an existing user
     */
    @Test
    public void testJ_existingUser() {
        clickOn("#SignUpUsername");
        write("Martin");
        clickOn("#SignUpPWD");
        write("Alberto!Garcia8");
        clickOn("#SignUpPWD2");
        write("Alberto!Garcia8");
        clickOn("#SignUpEmail");
        write("albertogarcia@gmail.com");
        clickOn("#SignUpFN");
        write("Alberto García");
        clickOn("#SignUpBtn");
        verifyThat("El usuario ya existe",isVisible()); 
        clickOn("Aceptar");
    }

}