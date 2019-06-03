package controllers;

import SqlClasses.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
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
    private MenuItem openFormTypePlacementMenuItem;



    @FXML
    void AddPlacement(ActionEvent event) {

    }

    @FXML
    void DeletePlacement(ActionEvent event) {

    }

    @FXML
    void EditPlacement(ActionEvent event) {

    }

    @FXML
    void OpenFormTypePlacement(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml_type_placement.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    private ObservableList<Tableview_Placement> placementList = FXCollections.observableArrayList();
    private void FillPlacementTableView() {

        try
        {
            Connection connection;

            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT placements.placementid AS PLCID,\n" +
                    " placements.runoutpercent AS RNOUT, \n" +
                    " placements.square AS SQUARE,  \n" +
                    " typeplacements.nameplacementtype AS TYPENAMEPLACEMENT\n" +
                    " from placements \n" +
                    " join typeplacements ON typeplacements.typeplacementid = placements.typeplacement");

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next())
            {
                placementList.add(new Tableview_Placement(rs.getString("PLCID"),
                        rs.getString("TYPENAMEPLACEMENT"),
                        rs.getString("SQUARE"),
                        rs.getString("RNOUT")
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

    private ObservableList typePlacementList = FXCollections.observableArrayList();

    private void FillTypeComboBox()
    {
        typePlacementList.clear();
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT nameplacementtype FROM TYPEPLACEMENTS");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                typePlacementList.add(rs.getString("nameplacementtype"));
            }
            connection.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);

        }
        typeComboBox.setItems(typePlacementList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FillPlacementTableView();
        FillTypeComboBox();
    }
}
