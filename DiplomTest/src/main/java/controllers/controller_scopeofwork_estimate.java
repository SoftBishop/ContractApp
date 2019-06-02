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
import modeltables.Tableview_Scopeofwork;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Controller_Scopeofwork_Estimate implements Initializable {

    @FXML
    private TableView<Tableview_Scopeofwork> scopeOfWorkTableView;

    @FXML
    private TableColumn<Tableview_Scopeofwork, String> nameWorkCol;

    @FXML
    private TableColumn<Tableview_Scopeofwork, String> quantityCol;

    @FXML
    private TableColumn<Tableview_Scopeofwork, String> nameTypeWorkCol;

    @FXML
    private TableColumn<Tableview_Scopeofwork, String> measureUnitsCol;

    @FXML
    private TableColumn<Tableview_Scopeofwork, String> estimateIDCol;

    @FXML
    private TableColumn<Tableview_Scopeofwork, String> employerFIOCol;

    @FXML
    private TableColumn<Tableview_Scopeofwork, String> dateExecCol;

    @FXML
    private TableColumn<Tableview_Scopeofwork, String> priceOfWorkCol;

    @FXML
    private TableColumn<Tableview_Scopeofwork, String> placmentIdCol;

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
    private MenuItem typeWorkMenu;


    @FXML
    private MenuItem workerMenu;


    @FXML
    private MenuItem measureUnitsMenu;


    @FXML
    void AddWork(ActionEvent event) {

    }

    @FXML
    void DeleteWork(ActionEvent event) {

    }

    @FXML
    void OpenMeasureUnit(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml_measure_units.fxml"));
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

    @FXML
    void OpenTypeWork(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml_type_works.fxml"));
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

    @FXML
    void OpenWorker(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml_employer.fxml"));
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

        private ObservableList<Tableview_Scopeofwork> olist = FXCollections.observableArrayList();
    public void FillEstimateTable()
    {
        String query ="SELECT scopeofworkestimates.namework AS NAMEWORK, scopeofworkestimates.quantity AS QUANTITY,\n" +
                "typeworks.typeworkname, measureunits.namemeasureunit AS MEASURE, estimates.estimateid, employers.fio AS EMPFIO,\n" +
                "scopeofworkestimates.dateexecution AS DATEEXECUTION ,\n" +
                "scopeofworkestimates.price AS price, placement.placementid AS placementid\n" +
                "FROM scopeofworkestimates \n" +
                "JOIN typeworks ON typeworks.typeworkid = scopeofworkestimates.typeworks\n" +
                "JOIN measureunits ON scopeofworkestimates.measureunits = measureunits.mesureunitid\n" +
                "JOIN estimates ON scopeofworkestimates.estimates = estimates.estimateid  \n" +
                "JOIN employers ON employers.employerid = scopeofworkestimates.employers\n" +
                "JOIN placement ON placement.placementid = scopeofworkestimates.placement\n" +
                " ";

        String condition = "where estimateid = ?";

        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement;
            if(estimateId ==0)
            {
                preparedStatement = connection.prepareStatement(query);

            }
            else
            {
                preparedStatement = connection.prepareStatement(query+condition);
                preparedStatement.setInt(1,estimateId);
            }

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next())
            {
                olist.add(new Tableview_Scopeofwork(rs.getString("NAMEWORK"),
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
            estimateId =0;
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
        nameWorkCol.setCellValueFactory(new PropertyValueFactory<Tableview_Scopeofwork,String>("nameWork"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<Tableview_Scopeofwork,String>("quantity"));
        nameTypeWorkCol.setCellValueFactory(new PropertyValueFactory<Tableview_Scopeofwork,String>("nameTypeWork"));
        measureUnitsCol.setCellValueFactory(new PropertyValueFactory<Tableview_Scopeofwork,String>("measureUnit"));
        estimateIDCol.setCellValueFactory(new PropertyValueFactory<Tableview_Scopeofwork,String>("estimateId"));
        employerFIOCol.setCellValueFactory(new PropertyValueFactory<Tableview_Scopeofwork,String>("employerFIO"));
        dateExecCol.setCellValueFactory(new PropertyValueFactory<Tableview_Scopeofwork,String>("dateExec"));
        priceOfWorkCol.setCellValueFactory(new PropertyValueFactory<Tableview_Scopeofwork,String>("price"));
        placmentIdCol.setCellValueFactory(new PropertyValueFactory<Tableview_Scopeofwork,String>("placementId"));


        scopeOfWorkTableView.setItems(olist);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //FillEstimateTable();
    }

    private Controller_Estimate controller_estimate;
    public void SetControllerEstimate(Controller_Estimate controller_estimate)
    {
        this.controller_estimate = controller_estimate;
    }

    private int estimateId;

    public void SetEstimateId(int estimateId)
    {
        this.estimateId = estimateId;
    }
}
