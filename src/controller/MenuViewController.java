package controller;

import enumeration.MenuType;
import exception.DatabaseException;
import java.io.IOException;
import java.util.logging.Level;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import model.Menu;

/**
 * Controller class for the menu table window.
 *
 * @author Kerman
 */
public class MenuViewController extends GlobalController {

    /**
     * TableView showing the menus sent by the server.
     */
    @FXML
    private TableView<Menu> menuTable;

    /**
     * Column from the table that shows each menu's name.
     */
    @FXML
    public TableColumn<Menu, String> clmnMenuName;

    /**
     * Column from the table that shows each menu's type.
     */
    @FXML
    public TableColumn<Menu, MenuType> clmnMenuType;

    /**
     * Column from the table that shows each menu's description.
     */
    @FXML
    public TableColumn<Menu, String> clmnDescription;

    /**
     * Combobox including every menu type available.
     */
    @FXML
    public ComboBox selectMenuType;

    /**
     * Button used to create a menu
     */
    @FXML
    public Button btnCreateMenu;

    /**
     * Button used to delete the selected menu
     */
    @FXML
    public Button btnDeleteMenu;
    
    /**
     * Context menu for the table.
     */
    @FXML
    public ContextMenu contextMenu;
    
    /**
     * Item of the context menu that erases the chosen row.
     */
    @FXML
    public MenuItem itemDelete;
    
    @FXML
    public SideMenuController sideMenuController;
    
    /**
     * List containing every menu from the database.
     */
    private ObservableList<Menu> everyMenu;

    /**
     * Method that initializes the window, sets its item's properties ans listeners and shows
     * it.
     *
     * @param root
     */
    public void initStage(Parent root) {
        //STAGE PROPERTIES
        //Initializes the stage and sets its attributes.
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Lista de menús");
        stage.setResizable(false);
        sideMenuController.setStage(stage);
        sideMenuController.initStage();
        
        //CONTROL PROPERTIES
        //Disables the delete button
        btnDeleteMenu.setDisable(true);
        
        //TABLE PROPERTIES
        //Creates an observable list containing every menu, in order to show it in the table.
        try {
        everyMenu = FXCollections.observableArrayList(getMenuManager().findAll());
        //Set table model.
        menuTable.setItems(everyMenu);
        //Sets the table to editable
        menuTable.setEditable(true);
        
        //Sets the factories for the  values of every cell in each column.
        clmnMenuName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmnMenuType.setCellValueFactory(new PropertyValueFactory<>("type"));
        clmnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        //Sets the cell factory for it to be editable
        clmnDescription.setCellFactory(TextFieldTableCell.forTableColumn());

        //Sets the method to start when editing a cell
        clmnDescription.setOnEditCommit(t -> {
            updateMenuDescription(t);
        });

        //Sets a listener for the chosen row
        menuTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                btnDeleteMenu.setDisable(false);
                LOGGER.log(Level.INFO, "chosen");
            } else {
                btnDeleteMenu.setDisable(true);
                LOGGER.log(Level.INFO, "unchosen");
            }
        });
        //CONTEXT MENU PROPERTIES
        //Sets a method for when the context menu option is clicked.
        itemDelete.setOnAction(new EventHandler<ActionEvent>() {  
                    @Override  
                    public void handle(ActionEvent event) {  
                         deleteMenu(event);
                    }  
                }); 
        
        //COMBOBOX LOADING
        //Creates an array with every available type in the app
        ObservableList<String> types =
        FXCollections.observableArrayList("Desayuno","Comida","Aperitivo","Cena","Todos");
        //Sets the array to the combo box
        selectMenuType.setItems(types);
        //Sets a listener for the change of type
        selectMenuType.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
           String type = null;
           
           //Changes the array to only contain certain menus
           switch (newValue.toString()) {
               case "Desayuno":
                   everyMenu = FXCollections.observableArrayList(getMenuManager().findByType(MenuType.Breakfast));
                   break;
               case "Comida":
                   everyMenu = FXCollections.observableArrayList(getMenuManager().findByType(MenuType.Lunch));
                   break;
                case "Aperitivo":
                   everyMenu = FXCollections.observableArrayList(getMenuManager().findByType(MenuType.Snack));
                   break;
                case "Cena":
                  everyMenu = FXCollections.observableArrayList(getMenuManager().findByType(MenuType.Dinner));
                   break;
                case "Todos":
                   everyMenu = FXCollections.observableArrayList(getMenuManager().findAll());
                   break;
           }
           //Refreshes the table.
           menuTable.setItems(everyMenu);
           menuTable.refresh();
    }
    ); 
        } catch (Exception ex) {
              showError("Ha ocurrido un error inesperado con el servidor.");  
          }
        
        //Show window.
        stage.show();
    }

    /**
     * This method opens the new menu window and waits until it is closed.
     * It then refreshes the table.
     * @param event
     * @throws IOException 
     */
    @FXML
    private void handleButtonMenuCreate(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/NewMenu.fxml"));
        Parent root = (Parent) loader.load();
        NewMenuController controller = (loader.getController());
        controller.setStage(stage);
        controller.initStage(root);
        //Waits for the window to be closed
        try {
        //Finds every menu back and refreshes the table
        everyMenu = FXCollections.observableArrayList(getMenuManager().findAll());
        menuTable.setItems(everyMenu);
        menuTable.refresh();
        } catch (Exception ex) {
            showError("Ha ocurrido un error inesperado con el servidor.");
        }
        
    }
    
    /**
     * This method erases the menu from the server, asking for a confirmation 
     * of the user
     * @param event 
     */
    @FXML
    private void deleteMenu(ActionEvent event) {      
        try {
            boolean confirm = showConfirmation("Seguro que quieres borrar el menu "+
            menuTable.getSelectionModel().getSelectedItem().getName()+"?");
            if (confirm) {
                try {
                getMenuManager().delete(menuTable.getSelectionModel().getSelectedItem().getId());
                everyMenu.remove(menuTable.getSelectionModel().getSelectedItem());
                menuTable.refresh();
                } catch (DatabaseException ex) {
                    showError("Ha ocurrido un error inesperado con el servidor.");
                } catch (Exception ex) {
                    showError("No ha podido eliminarse el menú.");
                }
            }
        } catch (NullPointerException ex) {
            showError("No hay menú seleccionado!");
        }
    }
    
    /**
     * Listens to the edit of the description column, and updates the value
     * when onEditCommit() is fired.
     * @param t 
     */
    private void updateMenuDescription(CellEditEvent <Menu,String> t) {
        if (t.getNewValue().length()>50)
            showError("La nueva descripción es demasiado larga!");
        else {
        Menu m = t.getRowValue();           
        m.setDescription(t.getNewValue());
        try {
        getMenuManager().edit(m);
        } catch (DatabaseException ex) {
            showError("Ha ocurrido un error inesperado con el servidor.");
        } catch (Exception ex) {
            showError("No ha podido editarse el menú.");
        }
        menuTable.refresh();
        }
    }
    
}
