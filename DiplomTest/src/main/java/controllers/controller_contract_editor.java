package controllers;

import SqlClasses.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class controller_contract_editor implements Initializable {

    @FXML
    private TextArea contractTextField;

    @FXML
    private DatePicker dateOfCreationContractDatePicker;

    @FXML
    private DatePicker dateOfExecContractDatePicker;

    @FXML
    private DatePicker dateOfFinishContractDatePicker;

    @FXML
    private ComboBox<?> typeContractComboBox;

    @FXML
    private ComboBox<?> clientComboBox;

    @FXML
    private ComboBox<?> organizationComboBox;

    @FXML
    private ComboBox<?> employerComboBox;

    @FXML
    private ComboBox<?> estimateCombobox;

    @FXML
    private ComboBox<?> placemenCombobox;

    @FXML
    private Button clientFormButton;

    @FXML
    private Button organizationFormButton;

    @FXML
    private Button employersFormButton;

    @FXML
    private Button estimateFormButton;

    @FXML
    private Button PlacementFormButton;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FillComboBox(sqlQueryFIOAllEmp,clientComboBox, fioEmpCol);
        FillComboBox(sqlQueryNameTypeContracts,typeContractComboBox, nameTypeContractCol );
    }

    private ObservableList olist = FXCollections.observableArrayList();

    private String sqlQueryFIOAllEmp = "SELECT fio FROM employers"; String fioEmpCol = "fio";
    private String sqlQueryNameTypeContracts = "SELECT nametypecontract FROM typecontracts";
    String nameTypeContractCol = "nametypecontract";


    private void FillComboBox(String sqlQuery, ComboBox comboBox, String colName)
    {

        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
            olist.add(rs.getString(colName));
            }
            connection.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);

        }
        comboBox.setItems(olist);
        olist.removeAll();
    }
}
