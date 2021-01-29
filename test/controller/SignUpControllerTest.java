/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import factory.UserManagerFACTORY;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.stage.Stage;
import model.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import reto2crud.Reto2CRUD;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import static reto2crud.Reto2CRUD.configFile;

/**
 * Testing class for SignUp controller. Tests the behavior using TestFX
 * framework.
 *
 * @author Martin Gros
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignUpControllerTest extends ApplicationTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        Reto2CRUD.configFile = ResourceBundle.getBundle("config.config");
        Reto2CRUD.BASE_URI = configFile.getString("URL");
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Reto2CRUD.class);
    }

    @Before
    public void beforeTest() {
        clickOn("#signInBtnSignUp");
    }

    @After
    public void afterTest() {
        clickOn("#signUpBtnBack");
    }

    /**
     * Method will delete TEST_USER after all tests have been executed.
     *
     * @throws Exception If there is any error
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
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
     * This method will verify the stage on launch. TextBoxes will be empty by
     * default. SignUpButton will be disabled as default. Back button will be
     * enabled by default.
     */
    @Test
    public void testA_initialstate() {
        verifyThat("#signUpUsername", hasText(""));
        verifyThat("#signUpPWD", hasText(""));
        verifyThat("#signUpPWD2", hasText(""));
        verifyThat("#signUpEmail", hasText(""));
        verifyThat("#signUpFN", hasText(""));
        verifyThat("#signUpBtn", isDisabled());
        verifyThat("#signUpBtnBack", isEnabled());

    }

    /**
     * This method will verify that SignUpButton is only enabled when all fields
     * contain text. If there is at least one empty field SignUpButton should be
     * disabled.
     */
    @Test
    public void testB_SignUpButtonEnabled() {

        clickOn("#signUpUsername");
        write("asd");
        verifyThat("#signUpBtn", isDisabled());

        clickOn("#signUpPWD");
        write("asd");
        verifyThat("#signUpBtn", isDisabled());

        clickOn("#signUpPWD2");
        write("asd");
        verifyThat("#signUpBtn", isDisabled());

        clickOn("#signUpEmail");
        write("asd");
        verifyThat("#signUpBtn", isDisabled());

        clickOn("#signUpFN");
        write("asd");
        verifyThat("#signUpBtn", isEnabled());

    }

    /**
     * This method will verify that an alert is thrown if the user is too short.
     */
    @Test
    public void testC_UsernameShort() {
        clickOn("#signUpUsername");
        write("a");
        clickOn("#signUpPWD");
        write("Alberto!Garcia8");
        clickOn("#signUpPWD2");
        write("Alberto!Garcia8");
        clickOn("#signUpEmail");
        write("albertogarcia@gmail.com");
        clickOn("#signUpFN");
        write("Alberto García");
        clickOn("#signUpBtn");
        verifyThat("El nombre de usuario es demasiado corto.\n", isVisible());
        clickOn("Aceptar");
    }

    /**
     * This method checks if the username is too long
     */
    @Test
    public void testD_UsernameLong() {
        clickOn("#signUpUsername");
        write("aaaaaaaaaaaaaaaaaaaaa");
        clickOn("#signUpPWD");
        write("Alberto!Garcia8");
        clickOn("#signUpPWD2");
        write("Alberto!Garcia8");
        clickOn("#signUpEmail");
        write("albertogarcia@gmail.com");
        clickOn("#signUpFN");
        write("Alberto García");
        clickOn("#signUpBtn");
        verifyThat("El nombre de usuario es demasiado largo.\n", isVisible());
        clickOn("Aceptar");
    }

    /**
     * This method will verify that an alert is thrown if the password is too
     * short
     */
    @Test
    public void testE_PasswordShort() {
        clickOn("#signUpUsername");
        write("Alberto");
        clickOn("#signUpPWD");
        write("1Aq");
        clickOn("#signUpPWD2");
        write("1Aq");
        clickOn("#signUpEmail");
        write("albertogarcia@gmail.com");
        clickOn("#signUpFN");
        write("Alberto García");
        clickOn("#signUpBtn");
        verifyThat("La contraseña es demasiado corta\n", isVisible());
        clickOn("Aceptar");
    }

    /**
     * This method checks if the password's format is valid.
     */
    @Test
    public void testF_PasswordFormat() {
        clickOn("#signUpUsername");
        write("Alberto");
        clickOn("#signUpPWD");
        write("aaaaa");
        clickOn("#signUpPWD2");
        write("aaaaa");
        clickOn("#signUpEmail");
        write("albertogarcia@gmail.com");
        clickOn("#signUpFN");
        write("Alberto García");
        clickOn("#signUpBtn");
        verifyThat("La contraseña debe de incluir una mayúscula, una minúscula y un numero\n", isVisible());
        clickOn("Aceptar");
    }

    /**
     * This method will verify that an alert is thrown if the password does not
     * match.
     */
    @Test
    public void testG_PasswordNoMatch() {
        clickOn("#signUpUsername");
        write("Alberto");
        clickOn("#signUpPWD");
        write("Alberto!Garcia8");
        clickOn("#signUpPWD2");
        write("Alberto!Garcia81");
        clickOn("#signUpEmail");
        write("albertogarcia@gmail.com");
        clickOn("#signUpFN");
        write("Alberto García");
        clickOn("#signUpBtn");
        verifyThat("Las contraseñas no coinciden\n", isVisible());
        clickOn("Aceptar");
    }

    /**
     * This method will verify that an alert is thrown if the Email format is
     * incorrect.
     */
    @Test
    public void testH_EmailWrong() {
        clickOn("#signUpUsername");
        write("Alberto");
        clickOn("#signUpPWD");
        write("Alberto!Garcia8");
        clickOn("#signUpPWD2");
        write("Alberto!Garcia8");
        clickOn("#signUpEmail");
        write("albertogarciagmail.com");
        clickOn("#signUpFN");
        write("Alberto García");
        clickOn("#signUpBtn");
        verifyThat("Formato incorrecto de correo electrónico\n", isVisible());
        clickOn("Aceptar");
    }

    /**
     * This method will verify that once pushed the button Sign Up changes to
     * Registradp.
     */
    @Test
    public void testI_SignUpButtonChanged() {
        clickOn("#signUpUsername");
        write("Alberto");
        clickOn("#signUpPWD");
        write("Alberto!Garcia8");
        clickOn("#signUpPWD2");
        write("Alberto!Garcia8");
        clickOn("#signUpEmail");
        write("albertogarcia@gmail.com");
        clickOn("#signUpFN");
        write("Alberto García");
        clickOn("#signUpBtn");
        verifyThat("Registrado", isVisible());
        verifyThat("#signUpBtn", isDisabled());
    }

    /**
     * This methods check that an alert shows when the user tries to register an
     * existing user
     */
    @Test
    public void testJ_existingUser() {
        clickOn("#signUpUsername");
        write("Marting");
        clickOn("#signUpPWD");
        write("Alberto!Garcia8");
        clickOn("#signUpPWD2");
        write("Alberto!Garcia8");
        clickOn("#signUpEmail");
        write("albertogarcia@gmail.com");
        clickOn("#signUpFN");
        write("Alberto García");
        clickOn("#signUpBtn");
        verifyThat("El usuario ya existe", isVisible());
        clickOn("Aceptar");
    }

    /**
     * Test that will only be valid if the server is out of reach
     */

    @Test
    public void testI_ServerError() {
        clickOn("#signInUsername");
        write("MartinG");
        clickOn("#signInPWD");
        write("Aa12345!");
        clickOn("#signInBtn");
        verifyThat("Error en la conexion con la base de datos", isVisible());
        clickOn("Aceptar");
    }

}
