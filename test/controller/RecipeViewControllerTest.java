package controller;

import enumeration.RecipeType;
import factory.UserManagerFACTORY;
import java.util.ResourceBundle;
import javafx.stage.Stage;
import model.Recipe;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.testfx.framework.junit.ApplicationTest;
import reto2crud.Reto2CRUD;
import static reto2crud.Reto2CRUD.configFile;

/**
 * TestFX test class for RecipeViewController.
 * @author Aitor Garcia
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RecipeViewControllerTest extends ApplicationTest{
    public void start(Stage stage) throws Exception {
        //Get target server
        Reto2CRUD.configFile = ResourceBundle.getBundle("config.config");
        Reto2CRUD.BASE_URI = configFile.getString("URL");
        
        new Reto2CRUD().start(stage);
    }
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception{
        Recipe testRecipe = new Recipe();
        
        //Create test recipe
        testRecipe.setKcal(Float.valueOf(250));
        testRecipe.setName("Test recipe");
        testRecipe.setSteps("This recipe is done like this... this... and this");
        testRecipe.setType(RecipeType.Main);
        testRecipe.setVerified(true);
        testRecipe.setUser(UserManagerFACTORY.getUserManager().find(Long.valueOf(0)));
    }
    
    @Test
    public void testA_initialState(){
        
    }
}
