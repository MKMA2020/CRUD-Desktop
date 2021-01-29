/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ResourceBundle;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
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
 * Testing class for SignIn controller. Tests the behavior using TestFX
 * framework.
 *
 * @author Martin Gros
 */
/**
 * TestFX test class for SignInController.
 *
 * @author Martin Gros
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignInControllerTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        Reto2CRUD.configFile = ResourceBundle.getBundle("config.config");
        Reto2CRUD.BASE_URI = configFile.getString("URL");

        new Reto2CRUD().start(stage);
    }

    @Override
    public void stop() {
    }

    /**
     * Method that checks that the initial characteristics of the window are
     * correct.
     */
    @Test
    public void TestA_initislstate() {
        verifyThat("#signInUsername", hasText(""));
        verifyThat("#signInPWD", hasText(""));
        verifyThat("#signInBtn", isDisabled());
        verifyThat("#signInBtnSignUp", isEnabled());
        verifyThat("#signInBtnResetPwd", isEnabled());

    }

    /**
     * Method that checks if the login button activates without the necessary
     * fields being filled.
     */
    @Test
    public void TestB_signInDisabled() {
        clickOn("#signInUsername");
        write("Sampletext");
        verifyThat("#signInBtn", isDisabled());
        eraseText(18);
        clickOn("#signInPWD");
        write("alsoratherlongpassword");
        verifyThat("#signInBtn", isDisabled());
        eraseText(22);
        verifyThat("#signInBtn", isDisabled());
    }

    /**
     * Method that checks if the login button activates when the necessary
     * fields are filled.
     */
    @Test
    public void TestC_signInEnabled() {
        clickOn("#signInUsername");
        write("validusername");
        clickOn("#signInPWD");
        write("alsovalidpassword");
        verifyThat("#signInBtn", isEnabled());
    }

    /**
     * Method that checks if an alert pops up when a username that is too long
     * is introduced.
     */
    @Test
    public void TestD_userTooLong() {
        clickOn("#signInUsername");
        write("invaliduseeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeer");
        clickOn("#signInPWD");
        write("validpassword");
        clickOn("#signInBtn");
        verifyThat("El nombre de usuario es demasiado largo", isVisible());
        clickOn("Aceptar");
    }

    /**
     * Method that checks if an alert pops up when a username that is too short
     * is introduced.
     */
    @Test
    public void TestE_userTooShort() {
        clickOn("#signInUsername");
        write("user");
        clickOn("#signInPWD");
        write("validpassword");
        clickOn("#signInBtn");
        verifyThat("El nombre de usuario es muy corto", isVisible());
        clickOn("Aceptar");
    }

    /**
     * Method that checks if an alert pops up when a username that is too long
     * is introduced, in case the program couldn't take it.
     */
    @Ignore
    @Test
    public void TestF_userComicallyLong() {
        clickOn("#signInUsername");
        write("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        clickOn("#signInPWD");
        write("validpassword");
        clickOn("#signInBtn");
        verifyThat("El nombre de usuario es demasiado largo", isVisible());
        clickOn("Aceptar");
    }

    /**
     * Method that checks if the program crashes when a password that is too
     * long is introduced, in case the program couldn't take it.
     */
    @Ignore
    @Test
    public void TestG_passComicallyLong() {
        clickOn("#signInUsername");
        write("validusername");
        clickOn("#signInPWD");
        write("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        clickOn("#signInBtn");
        verifyThat("Nombre de usuario o contraseñas erroneas", isVisible());
        clickOn("Aceptar");
    }

    /**
     * Method that introduces correct login information and checks if the next
     * window loads.
     */
    @Test
    public void TestH_SigningIn() {
        clickOn("#signInUsername");
        write("marting");
        clickOn("#signInPWD");
        write("Aa12345!");
        clickOn("#signInBtn");
        verifyThat("#RecipeView", isVisible());
    }

    /**
     * Method that introduces a non-existent username and then checks if there
     * is an error.
     */
    @Test
    public void TestI_UserNotExists() {
        clickOn("#signInUsername");
        write("nonvaliduser");
        clickOn("#signInPWD");
        write("unexistentpass");
        clickOn("#signInBtn");
        verifyThat("Nombre de usuario o contraseñas erroneas", isVisible());
        clickOn("Aceptar");
    }

    /**
     * Method that introduces an incorrect password if there is an error.
     */
    @Test
    public void TestJ_WrongPass() {
        clickOn("#signInUsername");
        write("MartinG");
        clickOn("#signInPWD");
        write("wrongpass");
        clickOn("#signInBtn");
        verifyThat("Nombre de usuario o contraseñas erroneas", isVisible());
        clickOn("Aceptar");
    }

    /**
     * Method that tests wether the SignUp button works well
     */
    @Test
    public void TestK_SignUp() {
        clickOn("#signInBtnSignUp");
        verifyThat("#windowSignUp", isVisible());
        clickOn("#signUpBtnBack");
    }

    /**
     * Method that tests wether the Reset pass button works well
     */
    @Test
    public void TestL_ResetPass() {
        clickOn("#signInBtnResetPwd");
        verifyThat("#windowResetPass", isVisible());
        clickOn("Atras");
    }

    /**
     * Method that introduces information and then checks that it cannot connect
     * to the server.
     */
    @Test
    public void TestM_ServerError() {
        clickOn("#signInUsername");
        write("Martin");
        clickOn("#signInPWD");
        write("Aa12345!");
        clickOn("#signInBtn");
        verifyThat("Error en la conexion con la base de datos", isVisible());
        clickOn("Aceptar");
    }
}
