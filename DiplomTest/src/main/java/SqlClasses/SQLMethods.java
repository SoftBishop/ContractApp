package SqlClasses;

import java.sql.*;

public class SQLMethods{

    Connection connection;

    int privilegeID;
    public int setPrivelege(int privilegeID)
    {
        return this.privilegeID = privilegeID;

    }
    public int getPrivelege()
    {
        return privilegeID;
    }
    public void getUsernameData(String user, String pass) throws SQLException {
        connection = ConnectionPool.getDataSource().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT username,userpassword,privilegesprivilegeid from Users where username = ?");
        preparedStatement.setString(1,user);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next())
        {
            if(user.equals(resultSet.getString("username")) && pass.equals(resultSet.getString("userpassword")))
            {
                setPrivelege(resultSet.getInt("privilegesprivilegeid"));
            }

        }
    }
}
