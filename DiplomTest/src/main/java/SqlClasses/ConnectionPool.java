package SqlClasses;
import com.zaxxer.hikari.*;
import org.slf4j.Logger;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.SQLData;

public class ConnectionPool {

    private static final String DB_USERNAME="db.username";
    private static final String DB_PASSWORD="db.password";
    private static final String DB_URL ="db.url";
    private static final String DB_DRIVER_CLASS="driver.class.name";

    private static Properties properties = null;
    private static HikariDataSource dataSource;
    static{
        try {
            properties = new Properties();


            properties.load(new FileInputStream("D:\\Diplom\\DiplomTest\\src\\main\\java\\SqlClasses\\database.properties"));

            dataSource = new HikariDataSource();
            dataSource.setDriverClassName(properties.getProperty(DB_DRIVER_CLASS));


            dataSource.setJdbcUrl(properties.getProperty(DB_URL));
            dataSource.setUsername(properties.getProperty(DB_USERNAME));
            dataSource.setPassword(properties.getProperty(DB_PASSWORD));


            dataSource.setMinimumIdle(100);
            dataSource.setMaximumPoolSize(2000);
            dataSource.setAutoCommit(false);
            dataSource.setLoginTimeout(3);

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static DataSource getDataSource(){
        return dataSource;
    }




}