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
import modeltables.Tableview_Estimate;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Controller_Estimate implements Initializable {

    Controller_Scopeofwork_Estimate controllerScopeofworkEstimate;
    ErrorFormClass errorFormClass;
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
    private Button editEstimateButton;

    @FXML
    private ComboBox<?> nameEstimateCombobox;

    @FXML
    private DatePicker dateofCreationEstimateDatePicker;

    @FXML
    private ComboBox<?> typeEstimate;

    @FXML
    private ComboBox<?> numContract;

    @FXML
    private MenuBar estimateBar;

    @FXML
    private MenuItem typeEstimateMenuItem;



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
            controllerScopeofworkEstimate.FillAllElements();
        }catch (Exception ex)
        {
            errorFormClass = new ErrorFormClass();
            errorFormClass.OpenErrorForm(ex);
        }
    }

    @FXML
    void OpenTypeEstimate(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml_type_estimate.fxml"));
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

    @FXML
    void CreateEstimate(ActionEvent event) {
         LocalDate localDate = dateofCreationEstimateDatePicker.getValue();
         String strDateCreation = localDate.toString() ;
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("BEGIN;\n" +
                    "INSERT INTO estimates(estimateid,datecreation,contracts,typeestimatetypeestimateid)\n" +
                    "VALUES\n" +
                    "(?,\n" +
                    " TO_DATE(?,'YYYY-MM-DD'),\n" +
                    " ?,\n" +
                    "(select TypeEstimateID  from typeEstimate\n" +
                    "where typeEstimate.nameType = ?));\n" +
                    "COMMIT;");
            preparedStatement.setInt(1,Integer.parseInt(estimateNumberComboBox.getEditor().getText()));
            preparedStatement.setString(2,strDateCreation);
            preparedStatement.setInt(3,Integer.parseInt(numContract.getEditor().getText()));
            preparedStatement.setString(4,typeEstimate.getEditor().getText());
            ResultSet rs = preparedStatement.executeQuery();

            rs.close();
        }
        catch (Exception ex)
        {
            errorFormClass = new ErrorFormClass();
            errorFormClass.OpenErrorForm(ex);
        }
    }

    @FXML
    void DeleteEstimate(ActionEvent event) {

        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "BEGIN;\n" +
                    "DELETE FROM estimates\n" +
                    "WHERE estimateId = ?\n" +
                    "COMMIT;\n" +
                    "BEGIN;\n" +
                    "DELETE FROM estimates\n" +
                    "WHERE estimateId = ?\n" +
                    "COMMIT;");
            preparedStatement.setInt(1,Integer.parseInt(estimateNumberComboBox.getEditor().getText()));
            preparedStatement.setInt(2,Integer.parseInt(estimateNumberComboBox.getEditor().getText()));
            ResultSet rs = preparedStatement.executeQuery();

            rs.close();
        }
        catch (Exception ex)
        {
            errorFormClass = new ErrorFormClass();
            errorFormClass.OpenErrorForm(ex);
        }
    }

    @FXML
    void EditEstimate(ActionEvent event) {
        LocalDate localDate = dateofCreationEstimateDatePicker.getValue();
        String strDateCreation = localDate.toString() ;
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "BEGIN;\n" +
                    "UPDATE\n" +
                    "    Estimates\n" +
                    "SET\n" +
                    "\tdateCreation = ?,\n" +
                    "\tContracts = ?,\n" +
                    "    TypeEstimateTypeEstimateID = typeTable.typeestimateid\n" +
                    "\t\n" +
                    "FROM\n" +
                    "    Estimates AS estimatesTable\n" +
                    "    JOIN typeestimate AS typeTable ON \n" +
                    "\ttypeTable.typeestimateid = estimatesTable.typeestimatetypeestimateid \n" +
                    "\t      \n" +
                    "WHERE\n" +
                    "    typeTable.nameType  = ? and estimateID = ?;\n" +
                    "COMMIT;");
            preparedStatement.setString(1,strDateCreation);
            preparedStatement.setInt(2,Integer.parseInt(numContract.getEditor().getText()));
            preparedStatement.setString(3,typeEstimate.getEditor().getText());
            preparedStatement.setString(4,estimateNumberComboBox.getEditor().getText());
            ResultSet rs = preparedStatement.executeQuery();

            rs.close();
        }
        catch (Exception ex)
        {
            errorFormClass = new ErrorFormClass();
            errorFormClass.OpenErrorForm(ex);
        }
    }

    @FXML
    void ExportEstimateExcel(ActionEvent event) {
        try {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT scopeofworkestimates.namework AS NAMEWORK, scopeofworkestimates.quantity AS QUANTITY,\n" +
                    "measureunits.namemeasureunit AS MEASURE, employers.fio AS EMPFIO,\n" +
                    "scopeofworkestimates.dateexecution AS DATEEXECUTION ,\n" +
                    "scopeofworkestimates.price AS price, placements.placementid AS placementid\n" +
                    "FROM scopeofworkestimates \n" +
                    "JOIN measureunits ON scopeofworkestimates.measureunits = measureunits.measureunitid\n" +
                    "JOIN estimates ON scopeofworkestimates.estimates = estimates.estimateid  \n" +
                    "JOIN employers ON employers.employerid = scopeofworkestimates.employers\n" +
                    "JOIN placements ON placements.placementid = scopeofworkestimates.placement\n" +
                    "where estimates.estimateid = ?");
            preparedStatement.setInt(1, Integer.parseInt(estimateNumberComboBox.getEditor().getText()));

            ResultSet rs = preparedStatement.executeQuery();

            int i = 2;

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet spreadsheet = workbook
                    .createSheet("Смета" );
            XSSFRow row = spreadsheet.createRow(1);
            XSSFCell cell;
            cell = row.createCell(1);
            cell.setCellValue("Наименование работ");
            cell = row.createCell(2);
            cell.setCellValue("Количество");
            cell = row.createCell(3);
            cell.setCellValue("Ед.ч.");
            cell = row.createCell(4);
            cell.setCellValue("Рабочий");
            cell = row.createCell(5);
            cell.setCellValue("Дата Выполнения");
            cell = row.createCell(6);
            cell.setCellValue("Помещение");
            cell = row.createCell(7);
            cell.setCellValue("Сделано?");

            while (rs.next()) {
                row = spreadsheet.createRow(i);
                cell = row.createCell(1);
                cell.setCellValue(rs.getString("NAMEWORK"));
                cell = row.createCell(2);
                cell.setCellValue(rs.getInt("QUANTITY"));
                cell = row.createCell(3);
                cell.setCellValue(rs.getString("MEASURE"));
                cell = row.createCell(4);
                cell.setCellValue(rs.getString("EMPFIO"));
                cell = row.createCell(5);
                cell.setCellValue(rs.getString("DATEEXECUTION"));
                cell = row.createCell(6);
                cell.setCellValue(rs.getString("placementid"));
                i++;
            }
            FileOutputStream out = new FileOutputStream(
                    new File("Смета номер-"+estimateNumberComboBox.getEditor().getText() +".xlsx"));
            workbook.write(out);
            out.close();

            preparedStatement.close();
            connection.close();
        }
        catch(Exception ex)
        {

        }

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
            errorFormClass = new ErrorFormClass();
            errorFormClass.OpenErrorForm(ex);
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
                        controllerScopeofworkEstimate.FillAllElements();

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FillEstimateTable();
        DoubleClick();
    }
}
