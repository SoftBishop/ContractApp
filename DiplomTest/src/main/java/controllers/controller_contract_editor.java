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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.sql.CallableStatement;

public class Controller_Contract_Editor implements Initializable {


    @FXML
    private Controller_Contract controller_contract;

    @FXML
    public void setController_contract(Controller_Contract controller_contract) {
        this.controller_contract = controller_contract;
    }

    @FXML
    private TextArea contractTextField;

    private void SetTextContractTextField(String text)
    {
        contractTextField.setText(text);
    }

    @FXML
    private DatePicker dateOfCreationContractDatePicker;

    private void SetDateOfCreationContractDatePicker(String text)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate = LocalDate.parse(text,formatter);
        dateOfCreationContractDatePicker.setValue(localDate);
    }

    @FXML
    private DatePicker dateOfExecContractDatePicker;

    private void SetDateOfExecContractDatePickerString (String text)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate = LocalDate.parse(text,formatter);
        dateOfExecContractDatePicker.setValue(localDate);
    }

    @FXML
    private DatePicker dateOfFinishContractDatePicker;

    private void SetDateOfFinishContractDatePicker (String text)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate = LocalDate.parse(text,formatter);
        dateOfFinishContractDatePicker.setValue(localDate);
    }

    @FXML
    private ComboBox<?> typeContractComboBox;

    private void SetTextTypeContractComboBox(String text)
    {
        typeContractComboBox.getEditor().setText(text);
    }

    @FXML
    private ComboBox<?> clientComboBox;

    private void SetTextClientComboBox(String text)
    {
        clientComboBox.getEditor().setText(text);
    }

    @FXML
    private ComboBox<?> organizationComboBox;

    private void SetTextOrganizationComboBox(String text)
    {
        organizationComboBox.getEditor().setText(text);
    }

    @FXML
    private ComboBox<?> employerComboBox;

    private void SetTextEmployerComboBox(String text)
    {
        employerComboBox.getEditor().setText(text);
    }

    @FXML
    private ComboBox<?> placementCombobox;

    private void SetTextPlacementCombobox(String text)
    {
        placementCombobox.getEditor().setText(text);
    }

    @FXML
    private TextField priceTextField;

    private void SetTextTextField(String text)
    {
        priceTextField.setText(text);
    }

    public void SetElements(String textContract, String creationDate,
                            String dateExec, String dateFinish,String nameType,
                            String clientName, String nameOrg, String nameEmployer,
                            String namePlacement,String price)
    {
        SetTextContractTextField(textContract);
        SetDateOfCreationContractDatePicker(creationDate);
        SetDateOfExecContractDatePickerString(dateExec);
        SetDateOfFinishContractDatePicker(dateFinish);
        SetTextTypeContractComboBox(nameType);
        SetTextClientComboBox(clientName);
        SetTextOrganizationComboBox(nameOrg);
        SetTextEmployerComboBox(nameEmployer);
        SetTextPlacementCombobox(namePlacement);
        SetTextTextField(price);
    }

    @FXML
    private Button clientFormButton;

    @FXML
    private Button organizationFormButton;

    @FXML
    private Button employersFormButton;

    @FXML
    private Button placementFormButton;

    @FXML
    private Button deleteContractButton;

    @FXML
    private Button EditContractButton;

    @FXML
    private Button AddContractButton;

    @FXML
    private Button estimateOpenButton;

    @FXML
    void AddContract(ActionEvent event) {
        LocalDate localDateCretion = dateOfCreationContractDatePicker.getValue();
        String strDateCretion = localDateCretion.toString();
        LocalDate localDateExec = dateOfExecContractDatePicker.getValue();
        String strExecDate = localDateExec.toString();
        LocalDate localDateFinish = dateOfFinishContractDatePicker.getValue();
        String strDateFinish = localDateFinish.toString();
        System.out.println(strDateCretion + strExecDate + strDateFinish);
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "BEGIN;\n" +
                    "INSERT INTO Contracts(\n" +
                    "\tdogovorid,\n" +
                    "\tdateofcreationcontract,\n" +
                    "\tdateexecution,\n" +
                    "\tdateexpire,\n" +
                    "\tprice,\n" +
                    "\torganizations,\n" +
                    "\tclients,\n" +
                    "\temployers,\n" +
                    "\ttypecontract,\n" +
                    "\tplacement)\n" +
                    "VALUES\n" +
                    "(?, \n" +
                    " TO_DATE(?,'YYYY-MM-DD'),\n" +
                    " TO_DATE(?,'YYYY-MM-DD'),\n" +
                    " TO_DATE(?,'YYYY-MM-DD'),\n" +
                    " ?,\n" +
                    " (Select organizationId from organizations where nameOrganization = ?),\n" +
                    " (Select Client_ID from clients where FIO = ?),\n" +
                    " (Select employerID from employers where employers.fio = ?),\n" +
                    " (Select typeContractID from typecontracts where nameTypeContract = ?),\n" +
                    "?);\n" +
                    "\tCOMMIT;");
            preparedStatement.setInt(1,Integer.parseInt(contractTextField.getText()));

            preparedStatement.setString(2,strDateCretion);

            preparedStatement.setString(3,strExecDate);

            preparedStatement.setString(4,strDateFinish);
            preparedStatement.setInt(5,Integer.parseInt(priceTextField.getText()));
            preparedStatement.setString(6,organizationComboBox.getEditor().getText());
            preparedStatement.setString(7,clientComboBox.getEditor().getText());
            preparedStatement.setString(8,employerComboBox.getEditor().getText());
            preparedStatement.setString(9,typeContractComboBox.getEditor().getText());
            preparedStatement.setInt(10,Integer.parseInt(placementCombobox.getEditor().getText()));
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }
  /*  CALL insertContract(
	5, номер контракта
	TO_DATE('06.06.2019','DD.MM.YYYY'), дата
	TO_DATE('06.06.2019','DD.MM.YYYY'),
	TO_DATE('06.06.2019','DD.MM.YYYY'),
	12512,
	'Июнь',
	'Семен Платонович Созураков',
	'Ревенский Дмитрий Григорьевич',
	'Аренда',
	1
	)*/

    @FXML
    void DeleteContract(ActionEvent event) {
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "BEGIN;\n" +
                    "DELETE FROM contracts\n" +
                    "WHERE  dogovorID = ?;\n" +
                    "COMMIT;");
            preparedStatement.setInt(1,Integer.parseInt(contractTextField.getText()));
            ResultSet rs = preparedStatement.executeQuery();
            rs.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    private int clientID,orgID,empID,typeContractId;
    @FXML
    void EditContract(ActionEvent event) {
        LocalDate localDateCretion = dateOfCreationContractDatePicker.getValue();
        String strDateCretion = localDateCretion.toString();
        LocalDate localDateExec = dateOfExecContractDatePicker.getValue();
        String strExecDate = localDateExec.toString();
        LocalDate localDateFinish = dateOfFinishContractDatePicker.getValue();
        String strDateFinish = localDateFinish.toString();


        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();

            PreparedStatement idClient = connection.prepareStatement("" +
                    "SELECT client_id from clients where fio = ?");
            idClient.setString(1,clientComboBox.getEditor().getText());
            ResultSet resultSetIdClient = idClient.executeQuery();
            while(resultSetIdClient.next())
            {
                clientID = resultSetIdClient.getInt("client_id");

            }

            PreparedStatement orgIdStatement = connection.prepareStatement("" +
                    "SELECT organizationid FROM organizations WHERE nameorganization = ?");

            orgIdStatement.setString(1,organizationComboBox.getEditor().getText());
            ResultSet resultSetIdOrg = orgIdStatement.executeQuery();

            while(resultSetIdOrg.next())
            {
                orgID = resultSetIdOrg.getInt("organizationid");
            }

           PreparedStatement employerIdStatement = connection.prepareStatement("" +
                   "Select employerID from employers where employers.fio = ?");

            employerIdStatement.setString(1,employerComboBox.getEditor().getText());

            ResultSet resultSetIdEmp = employerIdStatement.executeQuery();
            while(resultSetIdEmp.next())
            {
                empID = resultSetIdEmp.getInt("employerID");
            }

            PreparedStatement typeIdContractStatement=connection.prepareStatement("" +
                    "Select typeContractID from typecontracts " +
                    "where nameTypeContract = ?");

            typeIdContractStatement.setString(1,typeContractComboBox.getEditor().getText());

            ResultSet resultSetIdTypeContract = typeIdContractStatement.executeQuery();

            while(resultSetIdTypeContract.next())
            {
                typeContractId = resultSetIdTypeContract.getInt("typeContractID");
            }

            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "begin;\n" +
                    "UPDATE\n" +
                    "    Contracts\n" +
                    "SET\n" +
                    "\tdateOFCreationContract = TO_DATE(?,'YYYY-MM-DD'),\n" +
                    "\tdateExecution =  TO_DATE(?,'YYYY-MM-DD'),\n" +
                    "\tdateExpire =  TO_DATE(?,'YYYY-MM-DD'),\n" +
                    "\tprice =  ?,\n" +
                    "\torganizations = ?,\n" +
                    "\tclients = ?,\n" +
                    "\temployers = ?,\n" +
                    "\tTypeContract = ?,\n" +
                    "\tPlacement = ?\n" +
                    "WHERE dogovorID = ?;\n" +
                    "commit;");

            preparedStatement.setString(1,strDateCretion);

            preparedStatement.setString(2,strExecDate);

            preparedStatement.setString(3,strDateFinish);

            preparedStatement.setInt(4,Integer.parseInt(priceTextField.getText()));
            preparedStatement.setInt(5,orgID);
            preparedStatement.setInt(6,clientID);
            preparedStatement.setInt(7,empID);
            preparedStatement.setInt(8,typeContractId);
            preparedStatement.setInt(9,Integer.parseInt(placementCombobox.getEditor().getText()));

            preparedStatement.setInt(10,Integer.parseInt(contractTextField.getText()));

            preparedStatement.execute();
            preparedStatement.close();



            employerIdStatement.close();
            resultSetIdEmp.close();

            orgIdStatement.close();
            resultSetIdOrg.close();

            typeIdContractStatement.close();
            resultSetIdTypeContract.close();

            connection.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    @FXML
    void OpenClientForm(ActionEvent event) {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml_client.fxml"));
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
    void OpenEmployersForm(ActionEvent event) {
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

    private Controller_Scopeofwork_Estimate controller_scopeofwork_estimate;



    @FXML
    void OpenEstimates(ActionEvent event) {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml_estimate.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        }catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    @FXML
    void OpenOrganizationForm(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml_oraganization.fxml"));
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
    void OpenPlacementForm(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml_placement.fxml"));
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
    private ObservableList employersList = FXCollections.observableArrayList();
    private ObservableList typesList = FXCollections.observableArrayList();
    private ObservableList organizationsList = FXCollections.observableArrayList();
    private ObservableList placementList = FXCollections.observableArrayList();
    private ObservableList clientList = FXCollections.observableArrayList();

    private void FillEmployerCombobox()
    {
        employersList.clear();
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT fio FROM employers");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                employersList.add(rs.getString("fio"));
            }
            connection.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);

        }
        employerComboBox.setItems(employersList);
    }
    private void FillOrganizationComboBox()
    {
        organizationsList.clear();
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT nameorganization FROM ORGANIZATIONS");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                organizationsList.add(rs.getString("nameorganization"));
            }
            connection.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);

        }
        organizationComboBox.setItems(organizationsList);
    }

    private void FillTypesComboBox()
    {
        typesList.clear();
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT nametypecontract FROM TYPECONTRACTS");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                typesList.add(rs.getString("nametypecontract"));
            }
            connection.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);

        }
        typeContractComboBox.setItems(typesList);
    }

    private void FillPlacementComboBox()
    {
        placementList.clear();
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT placementid FROM placements");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                placementList.add(rs.getString("placementid"));
            }
            connection.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);

        }
        placementCombobox.setItems(placementList);
    }

    private void FillClientComboBox()
    {
        clientList.clear();
        try
        {
            Connection connection;
            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT fio FROM clients");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                clientList.add(rs.getString("fio"));
            }
            connection.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);

        }
        clientComboBox.setItems(clientList);
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FillEmployerCombobox();
        FillTypesComboBox();
        FillOrganizationComboBox();
        FillPlacementComboBox();
        FillClientComboBox();
    }
}
