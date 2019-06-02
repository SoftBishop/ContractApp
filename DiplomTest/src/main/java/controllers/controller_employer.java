package controllers;

import SqlClasses.ConnectionPool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modeltables.Tableview_Employer;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Controller_Employer implements Initializable {


    @FXML
    private TableView<Tableview_Employer> employerTableView;

    @FXML
    private TableColumn<Tableview_Employer, String> FioCol;

    @FXML
    private TableColumn<Tableview_Employer, String> dateHiringCol;

    @FXML
    private TableColumn<Tableview_Employer, String> telephoneNumberCol;

    @FXML
    private TableColumn<Tableview_Employer, String> positionCol;

    @FXML
    private TextField telephoneNumberTextField;

    @FXML
    private ComboBox<?> FIOComoboBox;

    @FXML
    private DatePicker dateHiringDatePicker;

    @FXML
    private ComboBox<?> positionComboBox;

    @FXML
    private Button addEmployerButton;

    @FXML
    private Button deleteEmployerButton;

    @FXML
    private Button editEmployerButton;

    @FXML
    private MenuBar employerMenuBar;

    @FXML
    private Menu positionMenu;

    @FXML
    private MenuItem openClientFormMenuItem;
    @FXML
    void AddEmployer(ActionEvent event) {

    }

    @FXML
    void DeleteEmployer(ActionEvent event) {

    }

    @FXML
    void EditEmployer(ActionEvent event) {

    }

    @FXML
    void OpenClientForm(ActionEvent event) {
        
    }


    private ObservableList<Tableview_Employer> employerList = FXCollections.observableArrayList();
    private void FillEmployerTableView() {

        try
        {
            Connection connection;

            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT employers.fio as FIO,TO_CHAR(employers.datehiring :: DATE, 'dd.mm.yyyy')  as DATEHIRING,\n" +
                    "employers.telephonenumber as TELNUMBER, positions.nameposition as NAMEPOSITION\n" +
                    "FROM employers\n" +
                    "JOIN positions ON positions.positionid = employers.positionsemp");

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next())
            {
                employerList.add(new Tableview_Employer(rs.getString("FIO"),
                                                rs.getString("DATEHIRING"),
                                                rs.getString("TELNUMBER"),
                                                rs.getString("NAMEPOSITION")));

            }
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
        FioCol.setCellValueFactory(new PropertyValueFactory<Tableview_Employer,String>("FIO"));
        dateHiringCol.setCellValueFactory(new PropertyValueFactory<Tableview_Employer,String>("dateHiring"));
        telephoneNumberCol.setCellValueFactory(new PropertyValueFactory<Tableview_Employer,String>("telephoneNumber"));
        positionCol.setCellValueFactory(new PropertyValueFactory<Tableview_Employer,String>("position"));


        employerTableView.setItems(employerList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FillEmployerTableView();

    }
}

