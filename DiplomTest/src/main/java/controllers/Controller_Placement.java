package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class Controller_Placement {

    @FXML
    private TableView<?> nameWorkTypeCol;

    @FXML
    private TableColumn<?, ?> placementNumCol;

    @FXML
    private TableColumn<?, ?> squareCol;

    @FXML
    private TableColumn<?, ?> runOutCol;

    @FXML
    private TextField placementNumTextField;

    @FXML
    private TextField squareTextField;

    @FXML
    private TextField rounOutTextField;

    @FXML
    private ComboBox<?> typeComboBox;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButtton;

    @FXML
    private Button addButton;

    @FXML
    void AddPlacement(ActionEvent event) {

    }

    @FXML
    void DeletePlacement(ActionEvent event) {

    }

    @FXML
    void EditPlacement(ActionEvent event) {

    }

}
