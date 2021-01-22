package controller;

import java.util.Date;
import java.util.logging.Level;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.WindowEvent;
import model.User;

/**
 *
 * @author Martin Valiente Ainz
 */
public class AdminUserWindowController extends GlobalController {

    @FXML
    private SideMenuController sideMenuController;

    /**
     * TableView that will contain the information about users.
     */
    @FXML
    private TableView<User> managerTable;

    /**
     * Column from the TableView that will contain the id of the user.
     */
    @FXML
    private TableColumn<User, Long> tclAdminId;

    /**
     * Column from the TableView that will contain the login of the user.
     */
    @FXML
    private TableColumn<User, String> tclAdminLogin;

    /**
     * Column from the TableView that will contain the email of the user.
     */
    @FXML
    private TableColumn<User, String> tclAdminEmail;

    /**
     * Column from the TableView that will contain the status of the user.
     */
    @FXML
    private TableColumn<User, Boolean> tclAdminStatus;

    /**
     * Column from the TableView that will contain the Last Access Date of the
     * user.
     */
    @FXML
    private TableColumn<User, Date> tclAdminLastAccess;

    /**
     * Column from the TableView that will contain the reset password CheckBox
     * of the user.
     */
    @FXML
    private TableColumn<User, Boolean> tclAdminResetPassword;

    /**
     * Column from the TableView that will contain the Last Password Change Date
     * of the user.
     */
    @FXML
    private TableColumn<User, Date> tclAdminLastPasswordChange;

    /**
     * Button that will Open window that generates the Form.
     */
    @FXML
    private Button btnUserListGenerateForm;

    /**
     *
     * @param root
     */
    public void initStage(Parent root) {

        // Load Stage into SideMenu
        sideMenuController.setStage(stage);
        // Load Menu Components
        sideMenuController.initStage();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Administrar");
        stage.setResizable(false);

        stage.setOnShowing(this::handleWindowShowing);

        managerTable.setEditable(true);

        tclAdminStatus.setCellFactory(CheckBoxTableCell.<User>forTableColumn(tclAdminStatus));
        tclAdminResetPassword.setCellFactory(CheckBoxTableCell.<User>forTableColumn(tclAdminResetPassword));

        //Set factories for cell values in users table columns.
        tclAdminId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tclAdminLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        tclAdminEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tclAdminStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tclAdminLastAccess.setCellValueFactory(new PropertyValueFactory<>("lastAccess"));
        tclAdminResetPassword.setCellValueFactory(new PropertyValueFactory<>("resetPassword"));
        tclAdminLastPasswordChange.setCellValueFactory(new PropertyValueFactory<>("lastsPasswordChange"));

        // ContextMenu for rows on Table.
        managerTable.setRowFactory((TableView<User> tableView) -> {
            TableRow<User> row = new TableRow<>();
            ContextMenu rowMenu = new ContextMenu();
            MenuItem editItem = new MenuItem("Reset");
            MenuItem removeItem = new MenuItem("Enable/Disable");
            rowMenu.getItems().addAll(editItem, removeItem);
            // When ItemProperty from row is NotNull show rowMenu ContextMenu.
            // When ItemProperty from row is Null show Null ContextMenu.
            row.contextMenuProperty().bind(Bindings.when(Bindings.isNotNull(row.itemProperty())).then(rowMenu).otherwise((ContextMenu) null));
            return row;
        });

        loadTable();

        //Show window.
        stage.show();
    }

    /**
     * Method implements actions on WINDOW_SHOWING event.
     *
     * @param event The window event
     */
    private void handleWindowShowing(WindowEvent event) {

    }

    /**
     * Method will load Table data and add listeners for resetPassword and
     * Status changes.
     */
    public void loadTable() {
        //Create an obsrvable list for recipes table.
        ObservableList<User> allUsers = null;
        try{
        allUsers = FXCollections.observableArrayList(getUserManager().findAll());
        
        // Status Listeners
        allUsers.forEach(user -> user.statusProperty().addListener((observable, oldValue, newValue) -> {
            LOGGER.log(Level.INFO, "Setting {0} status to user: {1}", new Object[]{user.getStatus().toString(), user.getLogin()});
            user.setStatus(newValue);
            getUserManager().edit(user);

        }));

        // ResetPassword Listeners
        allUsers.forEach(user -> {
            user.resetPasswordProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == Boolean.TRUE) {
                    LOGGER.log(Level.INFO, "Resetting password to user: {0}", user.getLogin());
                    getUserManager().resetPassword(user.getLogin(), user.getEmail());
                    // Refresh LastPasswordChange from the server
                    user.setLastsPasswordChange(getUserManager().find(user.getId()).getLastsPasswordChange());
                    // Refresh Table to see the new date.
                    managerTable.refresh();
                } else {
                    LOGGER.log(Level.INFO, "Password alredy reset to user: {0}", user.getLogin());
                }
            });
        });
        
        //Set table model.
        managerTable.setItems(allUsers);
        
        }catch(NullPointerException ex){
            showError("No hay respuesta del Servidor.");
        }catch(Exception ex){
            showError("Error inesperado.");
        }
        
    }
}
