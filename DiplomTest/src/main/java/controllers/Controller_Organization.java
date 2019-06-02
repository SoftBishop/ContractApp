package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class Controller_Organization {

    @FXML
    private TableView<?> nameOrganizationCol;

    @FXML
    private TableColumn<?, ?> nameOr;

    @FXML
    private TableColumn<?, ?> executiveDirNameCol;

    @FXML
    private TextField nameOrganizationTextField;

    @FXML
    private TextField telephoneNumberTextField;

    @FXML
    private TextField execDirNameTextField;

    @FXML
    private Button deleteOrganizatonButton;

    @FXML
    private Button editOrgButton;

    @FXML
    private Button AddOrganizationRow;

    @FXML
    void AddOrgRow(ActionEvent event) {

    }

    @FXML
    void DeleteOrgRow(ActionEvent event) {

    }

    @FXML
    void EditOrgRow(ActionEvent event) {

    }

}
