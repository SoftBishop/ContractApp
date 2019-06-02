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
import modeltables.Tableview_Estimate;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Controller_Estimate implements Initializable {


    @FXML
    private TableView<Tableview_Estimate> estimateTableView;

    @FXML
    private TableColumn<Tableview_Estimate,String> estimateIdCol;

    @FXML
    private TableColumn<Tableview_Estimate,String> dateOfCreationCol;

    @FXML
    private TableColumn<Tableview_Estimate,String> typeEstimateCol;

    @FXML
    private TableColumn<Tableview_Estimate,String> totalPriceCol;

    @FXML
    private TableColumn<Tableview_Estimate, String> contractIdCol;

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

    private ObservableList<Tableview_Estimate> olist = FXCollections.observableArrayList();
    private void FillEstimateTable()
    {
        try
        {
            Connection connection;

            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT estimates.estimateid as \n" +
                    "ESTID,TO_CHAR(estimates.datecreation :: DATE, 'dd.mm.yyyy') as CRETIONDATE,\n" +
                    "typeestimate.nametype as NAMETYPE, SUM(scopeofworkestimates.PRICE) as FINALPRICE,\n" +
                    "contracts.dogovorid as CONTRACTID\n" +
                    "FROM estimates \n" +
                    "join typeestimate on estimates.typeestimatetypeestimateid = typeestimate.typeestimateid\n" +
                    "join scopeofworkestimates on scopeofworkestimates.estimates = estimates.estimateid\n" +
                    "join contracts ON contracts.dogovorid = estimates.contracts\n" +
                    "GROUP BY estimates.estimateid, nametype,contracts.dogovorid\n" +
                    "ORDER BY estimates.estimateid asc");

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next())
            {
                olist.add(new Tableview_Estimate(rs.getString("ESTID"),
                                            rs.getString("CRETIONDATE"),
                                            rs.getString("NAMETYPE"),
                                            rs.getString("FINALPRICE"),
                                            rs.getString("CONTRACTID")));


            }
            rs.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
        estimateIdCol.setCellValueFactory(new PropertyValueFactory<Tableview_Estimate,String>("estimateID"));
        dateOfCreationCol.setCellValueFactory(new PropertyValueFactory<Tableview_Estimate,String>("dateCreation"));
        typeEstimateCol.setCellValueFactory(new PropertyValueFactory<Tableview_Estimate,String>("typeName"));
        totalPriceCol.setCellValueFactory(new PropertyValueFactory<Tableview_Estimate,String>("totalPrice"));
        contractIdCol.setCellValueFactory(new PropertyValueFactory<Tableview_Estimate,String>("contractID"));

        estimateTableView.setItems(olist);
    }

    private void DoubleClick()
    {
        estimateTableView.setRowFactory( tv -> {
            TableRow<Tableview_Estimate> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Tableview_Estimate rowData = row.getItem();
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
