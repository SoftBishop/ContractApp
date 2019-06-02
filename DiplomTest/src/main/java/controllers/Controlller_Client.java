package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

public class Controlller_Client {

    @FXML
    private TableColumn<?, ?> fioCol;

    @FXML
    private TableColumn<?, ?> nameOrgCol;

    @FXML
    private TextField fioTextField;

    @FXML
    private ComboBox<?> nameOrgComboBox;

    @FXML
    private Button deleteClientButton;

    @FXML
    private Button editClientButton;

    @FXML
    private Button addClientButton;

    @FXML
    void AddClient(ActionEvent event) {

    }

    @FXML
    void DeleteClient(ActionEvent event) {

    }

    @FXML
    void EditClient(ActionEvent event) {

    }

}
