package controllers;

import SqlClasses.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modeltables.tableview_scopeofwork;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class controller_scopeofwork_estimate implements Initializable {

    @FXML
    private TableView<tableview_scopeofwork> scopeOfWorkTableView;

    @FXML
    private TableColumn<tableview_scopeofwork, String> nameWorkCol;

    @FXML
    private TableColumn<tableview_scopeofwork, String> quantityCol;

    @FXML
    private TableColumn<tableview_scopeofwork, String> nameTypeWorkCol;

    @FXML
    private TableColumn<tableview_scopeofwork, String> measureUnitsCol;

    @FXML
    private TableColumn<tableview_scopeofwork, String> estimateIDCol;

    @FXML
    private TableColumn<tableview_scopeofwork, String> employerFIOCol;

    @FXML
    private TableColumn<tableview_scopeofwork, String> dateExecCol;

    @FXML
    private TableColumn<tableview_scopeofwork, String> priceOfWorkCol;

    @FXML
    private TableColumn<tableview_scopeofwork, String> placmentIdCol;

    @FXML
    private TextField nameWorkTextField;

    @FXML
    private TextField quantityTextField;

    @FXML
    private ComboBox<?> measureUnitsComboBox;

    @FXML
    private ComboBox<?> typeWorkCombobBox;

    @FXML
    private ComboBox<?> estimateIDComboBox;

    @FXML
    private ComboBox<?> employerComboBox;

    @FXML
    private DatePicker dateExecDatePicker;

    @FXML
    private Button addWorkButton;

    @FXML
    private Button editWorkButton;

    @FXML
    private Button deleteWorkButton;

    @FXML
    private Menu typeWorksMenuButton;

    @FXML
    private Menu employerMenuButton;

    @FXML
    private Menu measureUnitsMenuButoon;

    @FXML
    private ComboBox<?> placementComboBox;

    @FXML
    private TextField priceTextField;

    @FXML
    void AddWork(ActionEvent event) {

    }

    @FXML
    void DeleteWork(ActionEvent event) {

    }

    private ObservableList<tableview_scopeofwork> olist = FXCollections.observableArrayList();
    private void FillEstimateTable()
    {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT scopeofworkestimates.namework AS NAMEWORK, scopeofworkestimates.quantity AS QUANTITY,\n" +
                    "typeworks.typeworkname, measureunits.namemeasureunit AS MEASURE, estimates.estimateid, " +
                    "employers.fio AS EMPFIO,\n" +
                    "scopeofworkestimates.dateexecution AS DATEEXECUTION ,\n" +
                    "scopeofworkestimates.price AS price, placement.placementid AS placementid\n" +
                    "FROM scopeofworkestimates \n" +
                    "JOIN typeworks ON typeworks.typeworkid = scopeofworkestimates.typeworks\n" +
                    "JOIN measureunits ON scopeofworkestimates.measureunits = measureunits.mesureunitid\n" +
                    "JOIN estimates ON scopeofworkestimates.estimates = estimates.estimateid\n" +
                    "JOIN employers ON employers.employerid = scopeofworkestimates.employers\n" +
                    "JOIN placement ON placement.placementid = scopeofworkestimates.placementplacementid");

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next())
            {
                olist.add(new tableview_scopeofwork(rs.getString("NAMEWORK"),
                        rs.getString("QUANTITY"),
                        rs.getString("typeworkname"),
                        rs.getString("MEASURE"),
                        rs.getString("estimateid"),
                        rs.getString("EMPFIO"),
                        rs.getString("DATEEXECUTION"),
                        rs.getString("price"),
                        rs.getString("placementid")
                        ));
            }
            rs.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
        nameWorkCol.setCellValueFactory(new PropertyValueFactory<tableview_scopeofwork,String>("nameWork"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<tableview_scopeofwork,String>("quantity"));
        nameTypeWorkCol.setCellValueFactory(new PropertyValueFactory<tableview_scopeofwork,String>("nameTypeWork"));
        measureUnitsCol.setCellValueFactory(new PropertyValueFactory<tableview_scopeofwork,String>("measureUnit"));
        estimateIDCol.setCellValueFactory(new PropertyValueFactory<tableview_scopeofwork,String>("estimateId"));
        employerFIOCol.setCellValueFactory(new PropertyValueFactory<tableview_scopeofwork,String>("employerFIO"));
        dateExecCol.setCellValueFactory(new PropertyValueFactory<tableview_scopeofwork,String>("dateExec"));
        priceOfWorkCol.setCellValueFactory(new PropertyValueFactory<tableview_scopeofwork,String>("price"));
        placmentIdCol.setCellValueFactory(new PropertyValueFactory<tableview_scopeofwork,String>("placementId"));


        scopeOfWorkTableView.setItems(olist);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FillEstimateTable();
    }
}
