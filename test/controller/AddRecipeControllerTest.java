package controller;

import static com.google.common.collect.Multimaps.index;
import enumeration.RecipeType;
import static enumeration.RecipeType.Main;
import static enumeration.RecipeType.Starter;
import java.util.ResourceBundle;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.eclipse.persistence.internal.jpa.parsing.jpql.antlr.JPQLParser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import org.testfx.matcher.control.ComboBoxMatchers;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import reto2crud.Reto2CRUD;
import static reto2crud.Reto2CRUD.configFile;

/**
 * Testing class for SideMenu controller. Tests Sidemenu button behavior using
 * TestFX framework.
 *
 * @author Martin Valiente Ainz
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AddRecipeControllerTest extends ApplicationTest {
    private ComboBox choiceRecipe;

    /**
     * Starts application to be tested.
     *
     * @param stage Primary Stage object
     * @throws Exception If there is any error
     */
    @Override
    public void start(Stage stage) throws Exception {
        Reto2CRUD.configFile = ResourceBundle.getBundle("config.config");
        Reto2CRUD.BASE_URI = configFile.getString("URL");
        choiceRecipe = lookup("#choiceRecipeType").queryComboBox();
        
        
        new Reto2CRUD().start(stage);
    }

    @Override
    public void stop() {
    }

    /**
     * Test that tests if the TextFields are empty
     */
    @Test
    public void TestA_emptyTextFields() {

        verifyThat("#txtareaRecipeSteps", hasText(""));
        verifyThat("#txtRecipeKCal", hasText(""));
        verifyThat("#txtRecipeName", hasText(""));
        //verifyThat("#btnAddRecipe", isDisabled());
    }

    @Test
    public void TestB_AllItemsRecipeComboType() {
        clickOn(choiceRecipe);
        
        
        verifyThat(choiceRecipe, ComboBoxMatchers.hasItems(7));
        verifyThat(choiceRecipe, ComboBoxMatchers.containsExactlyItems(RecipeType.values()));

    }

    @Test
    public void TestC_CheckRecipeComboType() {
        clickOn("#choiceRecipeType").type(KeyCode.DOWN);
        clickOn("#choiceRecipeType").type(KeyCode.ENTER);
    assertEquals(choiceRecipe.getSelectionModel().getSelectedItem(), RecipeType.Starter);

    }
    @Test
    public void TestD_CheckRecipeComboType() {
        clickOn("#choiceRecipeType").type(KeyCode.DOWN);
        clickOn("#choiceRecipeType").type(KeyCode.ENTER);
    verifyThat("#choiceRecipeType", ComboBoxMatchers.hasSelectedItem(RecipeType.Starter));

    }

    /**
     * Test test that button Show Recipes opens Recipe View window.
     */
}
