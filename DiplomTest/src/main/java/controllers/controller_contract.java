package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class controller_contract implements Initializable {

    private ObservableList<ObservableList> data;
    @FXML
    private TableView<?> contractTable;

    @FXML
    private TableColumn<?, ?> numberContractColumnTable;

    @FXML
    private TableColumn<?, ?> typeContractColTable;

    @FXML
    private TableColumn<?, ?> dateCreationColTable;

    @FXML
    private TableColumn<?, ?> dateofExecColTable;

    @FXML
    private TableColumn<?, ?> dateFinishedColTable;

    @FXML
    private TableColumn<?, ?> clinetColTable;

    @FXML
    private TableColumn<?, ?> organizationColTable;

    @FXML
    private TableColumn<?, ?> employerColTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
