package controllers;

import SqlClasses.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modeltables.Tableview_Placement;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Controller_Placement implements Initializable {

    @FXML
    private TableView<Tableview_Placement> placementTableView;

    @FXML
    private TableColumn<Tableview_Placement, String> placementNumCol;

    @FXML
    private TableColumn<Tableview_Placement, String> nameTypeCol;

    @FXML
    private TableColumn<Tableview_Placement, String> squareCol;

    @FXML
    private TableColumn<Tableview_Placement, String> runOutCol;

    @FXML
    private TextField placementNumTextField;

    @FXML
    private TextField squareTextField;

    @FXML
    private TextField runOutTextField;

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

    private ObservableList<Tableview_Placement> placementList = FXCollections.observableArrayList();
    private void FillPlacementTableView() {

        try
        {
            Connection connection;

            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT placement.placementid AS PLCID,\n" +
                    " placement.runoutpercent AS RNOUT, \n" +
                    " placement.square AS SQUARE,  \n" +
                    " typeplacement.nameplacementtype AS TYPENAMEPLACEMENT\n" +
                    " from placement \n" +
                    " join typeplacement ON typeplacement.typeplacementid = placement.typeplacement");

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next())
            {
                placementList.add(new Tableview_Placement(rs.getString("PLCID"),
                        rs.getString("RNOUT"),
                        rs.getString("SQUARE"),
                        rs.getString("TYPENAMEPLACEMENT")
                        ));
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
        placementNumCol.setCellValueFactory(new PropertyValueFactory<Tableview_Placement,String>("numPlacement"));
        nameTypeCol.setCellValueFactory(new PropertyValueFactory<Tableview_Placement,String>("typePlacement"));
        squareCol.setCellValueFactory(new PropertyValueFactory<Tableview_Placement,String>("square"));
        runOutCol.setCellValueFactory(new PropertyValueFactory<Tableview_Placement,String>("runOut"));


        placementTableView.setItems(placementList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FillPlacementTableView();
    }
}
