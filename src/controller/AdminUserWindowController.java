/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.Date;
import java.util.logging.Level;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import model.User;

/**
 *
 * @author Martin Valiente Ainz
 */
public class AdminUserWindowController extends GlobalController {

    /**
     * TableView that will contain the information about users.
     */
    @FXML
    TableView<User> managerTable;

    /**
     * Column from the TableView that will contain the id of the user.
     */
    @FXML
    TableColumn<User, Long> tclAdminId;

    /**
     * Column from the TableView that will contain the login of the user.
     */
    @FXML
    TableColumn<User, String> tclAdminLogin;

    /**
     * Column from the TableView that will contain the email of the user.
     */
    @FXML
    TableColumn<User, String> tclAdminEmail;

    /**
     * Column from the TableView that will contain the status of the user.
     */
    @FXML
    TableColumn<User, Boolean> tclAdminStatus;

    /**
     * Column from the TableView that will contain the Last Access Date of the
     * user.
     */
    @FXML
    TableColumn<User, Date> tclAdminLastAccess;

    /**
     * Column from the TableView that will contain the reset password CheckBox
     * of the user.
     */
    @FXML
    TableColumn<User, Boolean> tclAdminResetPassword;

    /**
     * Column from the TableView that will contain the Last Password Change Date
     * of the user.
     */
    @FXML
    TableColumn<User, Date> tclAdminLastPasswordChange;

    public void initStage(Parent root) {
        Scene scene = new Scene(root);

        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Administrar");
        stage.setResizable(false);

        stage.setOnShowing(this::handleWindowShowing);

        managerTable.setEditable(true);

        tclAdminStatus.setCellFactory(CheckBoxTableCell.<User>forTableColumn(tclAdminStatus));
        tclAdminResetPassword.setCellFactory(CheckBoxTableCell.<User>forTableColumn(tclAdminResetPassword));
        tclAdminStatus.setOnEditCommit(this::tclAdminStatusOnEditCommit);

        tclAdminResetPassword.setCellFactory(CheckBoxTableCell.forTableColumn(tclAdminResetPassword));
        tclAdminResetPassword.setOnEditCommit((CellEditEvent<User, Boolean> t) -> {
            // GET EDITED USER
            User user = ((User) t.getTableView().getItems().get(t.getTablePosition().getRow()));
            // UPDATE EDITED VALUE
            user.setResetPassword(t.getNewValue());
            // SHOW UPDATED VALUE
            LOGGER.log(Level.INFO, user.getLogin() + " updated.");

        });

        //Set factories for cell values in users table columns.
        tclAdminId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tclAdminLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        tclAdminEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tclAdminStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tclAdminLastAccess.setCellValueFactory(new PropertyValueFactory<>("lastAccess"));
        tclAdminResetPassword.setCellValueFactory(new PropertyValueFactory<>("resetPassword"));
        tclAdminLastPasswordChange.setCellValueFactory(new PropertyValueFactory<>("lastsPasswordChange"));

        //Show window.
        stage.show();
    }

    private void tclAdminStatusOnEditCommit(CellEditEvent<User, Boolean> t) {
        // GET EDITED USER
        User user = ((User) t.getTableView().getItems().get(t.getTablePosition().getRow()));
        // UPDATE EDITED VALUE
        user.setStatus(t.getNewValue());
        // SHOW UPDATED VALUE
        LOGGER.log(Level.INFO, user.getLogin() + " updated.");
    }

    /**
     * Method implements actions on WINDOW_SHOWING event.
     *
     * @param event The window event
     */
    private void handleWindowShowing(WindowEvent event) {
        //Create an obsrvable list for recipes table.
        ObservableList<User> allUsers = FXCollections.observableArrayList(getUserManager().findAll());
        allUsers.forEach(user -> user.statusProperty().addListener((observable, oldValue, newValue) -> {
            LOGGER.log(Level.INFO, "Status property changed: New value {0}", oldValue.toString());
            LOGGER.log(Level.INFO, "User modified: {0}", user.getLogin());
        }));

        //Set table model.
        managerTable.setItems(allUsers);
    }

}
