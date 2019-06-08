package controllers;

import SqlClasses.ConnectionPool;
import errorpack.ErrorFormClass;
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


    ErrorFormClass errorFormClass;
    @FXML
    void AddPlacement(ActionEvent event) {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "BEGIN;\n" +
                    "INSERT INTO placements(placementid,runoutpercent,square,typeplacement)\n" +
                    "VALUES(\n" +
                    "?,\n" +
                    "?,\n" +
                    "?,\n" +
                    "(select TypePlacementID from typeplacements \n" +
                    "where namePlacementType = ?));\n" +
                    "COMMIT;");
            preparedStatement.setInt(1,Integer.parseInt(placementNumTextField.getText()));
            preparedStatement.setInt(2,Integer.parseInt(runOutTextField.getText()));
            preparedStatement.setInt(3,Integer.parseInt(squareTextField.getText()));
            preparedStatement.setString(4,typeComboBox.getEditor().getText());

            preparedStatement.execute();

            preparedStatement.close();
            connection.close();
        }
        catch (Exception ex)
        {
            errorFormClass = new ErrorFormClass();
            errorFormClass.OpenErrorForm(ex);
        }
    }

    @FXML
    void DeletePlacement(ActionEvent event) {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "BEGIN;\n" +
                    "DELETE FROM placements\n" +
                    "WHERE PlacementID = ?;\n" +
                    "COMMIT;");
            preparedStatement.setInt(1,Integer.parseInt(placementNumTextField.getText()));

            preparedStatement.execute();

            preparedStatement.close();
            connection.close();


        }
        catch (Exception ex)
        {
            errorFormClass = new ErrorFormClass();
            errorFormClass.OpenErrorForm(ex);
        }
    }

    @FXML
    void EditPlacement(ActionEvent event) {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "BEGIN;\n" +
                    "UPDATE\n" +
                    "    placements\n" +
                    "SET\n" +
                    "    placementID = ?,\n" +
                    "\trunoutpercent = ?,\n" +
                    "\tsquare = ?,\n" +
                    "    typePlacement = typeTable.typeplacementid\n" +
                    "FROM\n" +
                    "    placements AS placementTable\n" +
                    "    JOIN typeplacements AS typeTable\n" +
                    "        ON placementTable.typePlacement = typeTable.typeplacementid\n" +
                    "WHERE\n" +
                    "    typeTable.namePlacementType = ? and placementID = ?;\n" +
                    "COMMIT;\n" +
                    "\n" +
                    "\n");
            preparedStatement.setInt(1,Integer.parseInt(placementNumTextField.getText()));
            preparedStatement.setInt(2,Integer.parseInt(runOutTextField.getText()));
            preparedStatement.setInt(3,Integer.parseInt(squareTextField.getText()));
            preparedStatement.setString(4,typeComboBox.getEditor().getText());
            preparedStatement.setInt(4,placementID);

            preparedStatement.execute();

            preparedStatement.close();
            connection.close();
        }
        catch (Exception ex)
        {
            errorFormClass = new ErrorFormClass();
            errorFormClass.OpenErrorForm(ex);
        }
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
            errorFormClass = new ErrorFormClass();
            errorFormClass.OpenErrorForm(ex);
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
            errorFormClass = new ErrorFormClass();
            errorFormClass.OpenErrorForm(ex);
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
            preparedStatement.close();
            connection.close();
        }
        catch (Exception ex)
        {
            errorFormClass = new ErrorFormClass();
            errorFormClass.OpenErrorForm(ex);
        }
        typeComboBox.setItems(typePlacementList);
    }

    private int placementID;
    private void GetDataByClick()
    {
        placementTableView.setRowFactory( tv -> {
            TableRow<Tableview_Placement> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    Tableview_Placement rowData = row.getItem();
                    try {
                        placementID = Integer.parseInt(rowData.getNumPlacement());

                        placementNumTextField.setText(rowData.getNumPlacement());
                        typeComboBox.getEditor().setText(rowData.getTypePlacement());
                        squareTextField.setText(rowData.getSquare());
                        runOutTextField.setText(rowData.getRunOut());
                    }
                    catch (Exception ex)
                    {
                        errorFormClass = new ErrorFormClass();
                        errorFormClass.OpenErrorForm(ex);
                    }
                }
            });
            return row ;
        });
    }


    public void FillAllElements()
    {
        FillPlacementTableView();
        FillTypeComboBox();
        GetDataByClick();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FillAllElements();
    }
}
