package controllers;

import SqlClasses.ConnectionPool;
import errorpack.ErrorFormClass;
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
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Controller_Employer implements Initializable {

    ErrorFormClass errorFormClass;

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
        LocalDate localDate = dateHiringDatePicker.getValue();
        String strDateHiring = localDate.toString() ;
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "BEGIN;\n" +
                    "INSERT INTO employers(employerid,fio,datehiring,telephonenumber,positionsemp)\n" +
                    "VALUES\n" +
                    "(DEFAULT,\n" +
                    " ?,\n" +
                    " ?,\n" +
                    " TO_DATE(?,'YYYY-MM-DD'),\n" +
                    "(select PositionID from Positions \n" +
                    "where Positions.namePosition=?));\n" +
                    "COMMIT;");
            preparedStatement.setString(1,FIOComoboBox.getEditor().getText());
            preparedStatement.setString(2,strDateHiring);
            preparedStatement.setString(3,telephoneNumberTextField.getText());
            preparedStatement.setString(4,positionComboBox.getEditor().getText());
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
    void DeleteEmployer(ActionEvent event) {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "BEGIN;\n" +
                    "DELETE FROM EMPLOYERS\n" +
                    "WHERE employerID = ? ;\n" +
                    "COMMIT;\n");
            preparedStatement.setInt(1,empID);

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
    void EditEmployer(ActionEvent event) {
        LocalDate localDate = dateHiringDatePicker.getValue();
        String strDateHiring = localDate.toString();

        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "BEGIN;\n" +
                    "UPDATE\n" +
                    "    Employers\n" +
                    "SET\n" +
                    "    Fio = ?,\n" +
                    "\tdateHiring = TO_DATE(?,'YYYY-MM-DD'),\n" +
                    "\ttelephoneNumber = ?,\n" +
                    "    positionsEmp = posTable.positionid\n" +
                    "\t\n" +
                    "FROM\n" +
                    "    Employers AS empTable\n" +
                    "    JOIN positions AS posTable\n" +
                    "        ON empTable.positionsemp = posTable.positionid\n" +
                    "WHERE\n" +
                    "    posTable.nameposition  = ? and employerID = ?;\n" +
                    "\tCOMMIT;");
            preparedStatement.setString(1,FIOComoboBox.getEditor().getText());
            preparedStatement.setString(2,strDateHiring);
            preparedStatement.setString(3,telephoneNumberTextField.getText());
            preparedStatement.setString(4,positionComboBox.getEditor().getText());
            preparedStatement.setInt(5,empID);
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
    void OpenClientForm(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml_positions.fxml"));
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
    void UpdateTable(ActionEvent event) {
        FillEmployerTableView();
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
            errorFormClass = new ErrorFormClass();
            errorFormClass.OpenErrorForm(ex);
        }
        FioCol.setCellValueFactory(new PropertyValueFactory<Tableview_Employer,String>("FIO"));
        dateHiringCol.setCellValueFactory(new PropertyValueFactory<Tableview_Employer,String>("dateHiring"));
        telephoneNumberCol.setCellValueFactory(new PropertyValueFactory<Tableview_Employer,String>("telephoneNumber"));
        positionCol.setCellValueFactory(new PropertyValueFactory<Tableview_Employer,String>("position"));


        employerTableView.setItems(employerList);
    }

    private int empID;
    private void GetDataByClick()
    {
        employerTableView.setRowFactory( tv -> {
            TableRow<Tableview_Employer> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    Tableview_Employer rowData = row.getItem();
                    try {
                        Connection connection;
                        connection = ConnectionPool.getDataSource().getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("" +
                                "SELECT employerId FROM EMPLOYERS\n" +
                                "where fio = ? and telephoneNumber = ?");
                        preparedStatement.setString(1,rowData.getFIO());
                        preparedStatement.setString(2,rowData.getTelephoneNumber());

                        ResultSet rs = preparedStatement.executeQuery();
                        while(rs.next())
                        {
                            empID = rs.getInt("employerId");
                        }
                        preparedStatement.close();
                        rs.close();
                        connection.close();

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
        FillEmployerTableView();
        GetDataByClick();
    }
}

