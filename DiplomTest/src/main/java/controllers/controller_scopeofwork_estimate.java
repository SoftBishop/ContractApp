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
    private ComboBox<?> placementComboBox;

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
    private TextField priceTextField;

    @FXML
    private MenuItem typeWorkMenu;


    @FXML
    private MenuItem workerMenu;


    @FXML
    private MenuItem measureUnitsMenu;


    @FXML
    void AddWork(ActionEvent event) {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "CALL insertScopeOfWork(?,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1,nameWorkTextField.getText());
            preparedStatement.setInt(2,Integer.parseInt(quantityTextField.getText()));
            preparedStatement.setInt(3,Integer.parseInt(priceTextField.getText()));
            java.sql.Date sqlDateExec = java.sql.Date.valueOf( dateExecDatePicker.getValue() );
            preparedStatement.setDate(4,sqlDateExec);
            preparedStatement.setString(5,estimateIDComboBox.getEditor().getText());
            preparedStatement.setString(6,employerComboBox.getEditor().getText());
            preparedStatement.setString(7,measureUnitsComboBox.getEditor().getText());
            preparedStatement.setString(8,typeWorkCombobBox.getEditor().getText());
            preparedStatement.setInt(9,Integer.parseInt(placementComboBox.getEditor().getText()));

            ResultSet rs = preparedStatement.executeQuery();

            rs.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    @FXML
    void DeleteWork(ActionEvent event) {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "CALL deleteScopeOfWork(?,?)");
            preparedStatement.setString(1,nameWorkTextField.getText());
            java.sql.Date sqlDateExec = java.sql.Date.valueOf( dateExecDatePicker.getValue() );
            preparedStatement.setDate(2,sqlDateExec);


            ResultSet rs = preparedStatement.executeQuery();

            rs.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }


    @FXML
    void EditWork(ActionEvent event) {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "CALL editScopeOfWork(?,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1,nameWorkTextField.getText());
            preparedStatement.setInt(2,Integer.parseInt(quantityTextField.getText()));
            preparedStatement.setInt(3,Integer.parseInt(priceTextField.getText()));
            java.sql.Date sqlDateCreation = java.sql.Date.valueOf( dateExecDatePicker.getValue() );
            preparedStatement.setDate(4,sqlDateCreation);
            preparedStatement.setString(5,estimateIDComboBox.getEditor().getText());
            preparedStatement.setString(6,employerComboBox.getEditor().getText());
            preparedStatement.setString(7,measureUnitsComboBox.getEditor().getText());
            preparedStatement.setString(8,typeWorkCombobBox.getEditor().getText());
            preparedStatement.setInt(9,Integer.parseInt(placementComboBox.getEditor().getText()));

            ResultSet rs = preparedStatement.executeQuery();

            rs.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
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
        String query ="" +
                "SELECT scopeofworkestimates.namework AS NAMEWORK, scopeofworkestimates.quantity AS QUANTITY,\n" +
                "typeworks.typeworkname, measureunits.namemeasureunit AS MEASURE, estimates.estimateid, employers.fio AS EMPFIO,\n" +
                "scopeofworkestimates.dateexecution AS DATEEXECUTION ,\n" +
                "scopeofworkestimates.price AS price, placements.placementid AS placementid\n" +
                "FROM scopeofworkestimates \n" +
                "JOIN typeworks ON typeworks.typeworkid = scopeofworkestimates.typeworks\n" +
                "JOIN measureunits ON scopeofworkestimates.measureunits = measureunits.measureunitid\n" +
                "JOIN estimates ON scopeofworkestimates.estimates = estimates.estimateid  \n" +
                "JOIN employers ON employers.employerid = scopeofworkestimates.employers\n" +
                "JOIN placements ON placements.placementid = scopeofworkestimates.placement" +
                " ";

        String conditionEst = "where estimateid = ?";
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
                preparedStatement = connection.prepareStatement(query+conditionEst);
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



    private ObservableList measureUnitList = FXCollections.observableArrayList();
    private ObservableList typeWorkList = FXCollections.observableArrayList();
    private ObservableList estimateList = FXCollections.observableArrayList();
    private ObservableList employerList = FXCollections.observableArrayList();
    private ObservableList placementList = FXCollections.observableArrayList();

    public void FillMeasureUnitsComboBox()
    {
        measureUnitList.clear();
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT NAMEMEASUREUNIT FROM MEASUREUNITS");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                measureUnitList.add(rs.getString("NAMEMEASUREUNIT"));
            }
            connection.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);

        }
        measureUnitsComboBox.setItems(measureUnitList);
    }

    public void FillTypeWorkCombobBox()
    {
        typeWorkList.clear();
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT NAMETYPE FROM TYPEESTIMATE");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                typeWorkList.add(rs.getString("NAMETYPE"));
            }
            connection.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);

        }
        typeWorkCombobBox.setItems(typeWorkList);
    }

    public void FillEstimateIDComboBox()
    {
        estimateList.clear();
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT ESTIMATEID FROM ESTIMATES");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                estimateList.add(rs.getString("ESTIMATEID"));
            }
            connection.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);

        }
        estimateIDComboBox.setItems(estimateList);
    }


    public void FillEmployerComboBox()
    {
        employerList.clear();
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT FIO FROM EMPLOYERS");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                employerList.add(rs.getString("FIO"));
            }
            connection.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);

        }
        employerComboBox.setItems(employerList);
    }

    public void FillPlacementComboBox()
    {
        placementList.clear();
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT PLACEMENTID FROM PLACEMENTS");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                placementList.add(rs.getString("PLACEMENTID"));
            }
            connection.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);

        }
        placementComboBox.setItems(placementList);
    }

   /* @FXML
    private ComboBox<?> placementComboBox;*/

    public void FillAllElements()
    {
        FillEstimateTable();
        FillEmployerComboBox();
        FillEstimateIDComboBox();
        FillMeasureUnitsComboBox();
        FillTypeWorkCombobBox();
        FillPlacementComboBox();
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //FillEstimateTable();
    }


}
