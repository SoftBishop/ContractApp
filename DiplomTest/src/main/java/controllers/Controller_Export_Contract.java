package controllers;

import officeclasses.WordsContext;
import SqlClasses.ConnectionPool;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.wickedsource.docxstamper.DocxStamper;
import org.wickedsource.docxstamper.DocxStamperConfiguration;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Controller_Export_Contract {

    private String wordFile;

    @FXML
    private ComboBox<?> exportContractComboBox;

    @FXML
    private Button exportContractButton;

    @FXML
    private Button ChooseTemplateButton;

    @FXML
    void ChooseTemplate(ActionEvent event) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        File selectedTemplate = fileChooser.showOpenDialog(stage);
        if(selectedTemplate == null){
            //No Directory selected
        }else{
            wordFile = selectedTemplate.getAbsolutePath();
        }
    }

    @FXML
    void ExportContract(ActionEvent event) {
        try {
            WordsContext context = new WordsContext();
            Connection connection;

            connection = ConnectionPool.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT contracts.dogovorid AS NUMCONTRACTID, \n" +
                            "TO_CHAR(contracts.DateOfCreationContract :: DATE, 'dd.mm.yyyy') AS DATECREATECONTRACT,\n" +
                            "TO_CHAR(contracts.DateExecution :: DATE, 'dd.mm.yyyy') AS DATEEXECUITONCONTRACT, \n" +
                            "TO_CHAR(contracts.DateExpire :: DATE, 'dd.mm.yyyy') AS DATEEXPIRECONTRACT,  \n" +
                            "contracts.Price AS CONTRACTPRICE, typecontracts.NameTypeContract AS NAMETYPECONTRACT, \n" +
                            "clients.FIO as CLIENTFIO, organizations.NameOrganization AS NAMEORGANIZATION,\n" +
                            "Employers.FIO as EMPLOYERFIO , placements.placementid as NUMPLACE\n" +
                            "FROM contracts join typecontracts \n" +
                            "on contracts.typecontract = typecontracts.typecontractid \n" +
                            "join clients on contracts.Clients = clients.Client_ID\n" +
                            "join Organizations on contracts.Organizations = Organizations.organizationid\n" +
                            "join Employers on contracts.Employers = Employers.EmployerID\n" +
                            "join placements on placements.placementid = contracts.placement" +
                            " where contracts.dogovorid = ?");

            preparedStatement.setInt(1,Integer.parseInt(exportContractComboBox.getEditor().getText()));
            DocxStamper stamper = new DocxStamper(new DocxStamperConfiguration());

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next())
            {
                context.setContractID(rs.getString("NUMCONTRACTID"));
                context.setContractType(rs.getString("NAMETYPECONTRACT"));
                context.setContractDateOfCreation(rs.getString("DATECREATECONTRACT"));
                context.setContractDateExec(rs.getString("DATEEXECUITONCONTRACT"));
                context.setContractDateFinished(rs.getString("DATEEXPIRECONTRACT"));
                context.setNameClient(rs.getString("CLIENTFIO"));
                context.setNameOrganization(rs.getString("NAMEORGANIZATION"));
                context.setNameEmployer(rs.getString("NAMEORGANIZATION"));
                context.setPlacementID(rs.getString("NUMPLACE"));
                context.setContractPrice(rs.getString("CONTRACTPRICE"));

            }
                rs.close();

            InputStream template = new FileInputStream(wordFile);

            OutputStream out = new FileOutputStream("Contract.docx");
            stamper.stamp(template, context, out);
            out.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }



    }



}
