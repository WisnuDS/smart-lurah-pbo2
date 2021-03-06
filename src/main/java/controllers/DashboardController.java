package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import helper.Helper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.text.Text;
import models.AdminModel;
import models.ArrangementModel;

public class DashboardController {

    @FXML
    private Text txtProcess;

    @FXML
    private Text txtFinished;

    @FXML
    private Text txtNotVerified;

    @FXML
    private Text txtRejected;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTreeTableView<UserColumn> tableUserDashboard;

    @FXML
    private JFXButton btnAddUser;

    @FXML
    void toAddUserPage(ActionEvent event) throws IOException {
        Helper.changeAnchorPaneScene(event,"add_admin_view");
    }

    @FXML
    void initialize() {
        txtFinished.setText(String.valueOf(ArrangementModel.getArrangements("api_arrangement.status='finished'").size()));
        txtNotVerified.setText(String.valueOf(ArrangementModel.getArrangements("api_arrangement.status='not verified'").size()));
        txtProcess.setText(String.valueOf(ArrangementModel.getArrangements("api_arrangement.status='verified'").size()));
        txtRejected.setText(String.valueOf(ArrangementModel.getArrangements("api_arrangement.status='rejected'").size()));
        JFXTreeTableColumn<UserColumn,String> username = new JFXTreeTableColumn<>("Username");
        username.setPrefWidth(252);
        username.setCellValueFactory(userColumnStringCellDataFeatures -> userColumnStringCellDataFeatures.getValue().getValue().username);
        JFXTreeTableColumn<UserColumn,String> email = new JFXTreeTableColumn<>("Email");
        email.setPrefWidth(252);
        email.setCellValueFactory(userColumnStringCellDataFeatures -> userColumnStringCellDataFeatures.getValue().getValue().email);
        JFXTreeTableColumn<UserColumn,String> firstName = new JFXTreeTableColumn<>("Nama Depan");
        firstName.setPrefWidth(252);
        firstName.setCellValueFactory(userColumnStringCellDataFeatures -> userColumnStringCellDataFeatures.getValue().getValue().firstName);
        JFXTreeTableColumn<UserColumn,String> lastName = new JFXTreeTableColumn<>("Nama Belakang");
        lastName.setPrefWidth(252);
        lastName.setCellValueFactory(userColumnStringCellDataFeatures -> userColumnStringCellDataFeatures.getValue().getValue().lastName);

        ObservableList<UserColumn> userColumns = FXCollections.observableArrayList();
        for (AdminModel model : AdminModel.getUserModels()){
            userColumns.add(new UserColumn(model.getUsername(),model.getFirstName(),model.getLastName(),model.getEmail()));
        }

        final TreeItem<UserColumn> root = new RecursiveTreeItem<UserColumn>(userColumns, RecursiveTreeObject::getChildren);
        tableUserDashboard.getColumns().setAll(username,email,firstName,lastName);
        tableUserDashboard.setRoot(root);
        tableUserDashboard.setShowRoot(false);


    }

    static class UserColumn extends RecursiveTreeObject<UserColumn> {
        StringProperty username;
        StringProperty firstName;
        StringProperty lastName;
        StringProperty email;

        public UserColumn(String username, String firstName, String lastName, String email) {
            this.username = new SimpleStringProperty(username);
            this.firstName = new SimpleStringProperty(firstName);
            this.lastName = new SimpleStringProperty(lastName);
            this.email = new SimpleStringProperty(email);
        }
    }
}
