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
import modeltables.tableview_contract;
import modeltables.tableview_estimate;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Controller_Estimate implements Initializable {


    @FXML
    private TableView<tableview_estimate> estimateTableView;

    @FXML
    private TableColumn<tableview_estimate,String> estimateIdCol;

    @FXML
    private TableColumn<tableview_estimate,String> dateOfCreationCol;

    @FXML
    private TableColumn<tableview_estimate,String> typeEstimateCol;

    @FXML
    private TableColumn<tableview_estimate,String> totalPriceCol;

    @FXML
    private Button createEstimateButton;

    @FXML
    private Button deleteEstimateButton;

    @FXML
    private ComboBox<?> estimateNumberComboBox;

    @FXML
    private Button lookAllScopeWorkButton;

    @FXML
    void DeleteEstimate(ActionEvent event) {

    }

    @FXML
    void LookAllScopeOfWork(ActionEvent event) {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml_scopeofwork_estimate.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            controllerScopeofworkEstimate = fxmlLoader.getController();
            controllerScopeofworkEstimate.SetControllerEstimate(this);
            controllerScopeofworkEstimate.FillEstimateTable();
        }catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    @FXML
    void OpenScopeOfWork(ActionEvent event) {


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    FillEstimateTable();
    DoubleClick();
    }

    private ObservableList<tableview_estimate> olist = FXCollections.observableArrayList();
    private void FillEstimateTable()
    {
        try
        {
            Connection connection;

            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("\n" +
                    "SELECT estimates.estimateid as ESTID, estimates.datecreation as CRETIONDATE,\n" +
                    "typeestimate.nametype as NAMETYPE, SUM(scopeofworkestimates.PRICE) as FINALPRICE FROM estimates \n" +
                    "join typeestimate on estimates.typeestimatetypeestimateid = typeestimate.typeestimateid\n" +
                    "join scopeofworkestimates on scopeofworkestimates.estimates = estimates.estimateid\n" +
                    "GROUP BY estimates.estimateid, nametype \n" +
                    "ORDER BY estimates.estimateid asc");

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next())
            {
                olist.add(new tableview_estimate(rs.getString("ESTID"),
                                            rs.getString("CRETIONDATE"),
                                            rs.getString("NAMETYPE"),
                                            rs.getString("FINALPRICE")));


            }
            rs.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
        estimateIdCol.setCellValueFactory(new PropertyValueFactory<tableview_estimate,String>("estimateID"));
        dateOfCreationCol.setCellValueFactory(new PropertyValueFactory<tableview_estimate,String>("dateCreation"));
        typeEstimateCol.setCellValueFactory(new PropertyValueFactory<tableview_estimate,String>("typeName"));
        totalPriceCol.setCellValueFactory(new PropertyValueFactory<tableview_estimate,String>("totalPrice"));


        estimateTableView.setItems(olist);
    }

    private void DoubleClick()
    {
        estimateTableView.setRowFactory( tv -> {
            TableRow<tableview_estimate> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    tableview_estimate rowData = row.getItem();
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml_scopeofwork_estimate.fxml"));
                        Parent root = (Parent) fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.show();
                        controllerScopeofworkEstimate = fxmlLoader.getController();
                        controllerScopeofworkEstimate.SetControllerEstimate(this);
                        controllerScopeofworkEstimate.SetEstimateId(Integer.parseInt(rowData.getEstimateID()));
                        controllerScopeofworkEstimate.FillEstimateTable();
                    }
                    catch (Exception ex)
                    {
                        System.out.println(ex);
                    }
                }
            });
            return row ;
        });
    }

    Controller_Scopeofwork_Estimate controllerScopeofworkEstimate;


}
