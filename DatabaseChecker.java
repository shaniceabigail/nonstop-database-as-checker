import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseChecker {
    String propertiesFile = "";
    public DatabaseChecker(String _propertiesFile) {
        propertiesFile = _propertiesFile;
    }

    public void run(String command, int id, String desc) {
        checkJdbcDrivers();
        System.out.println("--- JDBC Drivers are available");

        //check command
        if (command.equals("create")) {
            int isValidCustId = checkCustIdFromSQLMX(id);
            if (isValidCustId == 0) {
                System.out.println("--- Customer ID does not exist in SQLMX database - create customer first");
            } else if (isValidCustId == 1) {
                System.out.println("--- Customer ID exists in SQLMX database");
                createOrderWithCustId(id, desc);
            }
        } else if (command.equals("edit")) {
            editOrderDescWithOrderId(id, desc);
        } else {
            System.out.println("!!! Invalid command");
        }

    }

    private void editOrderDescWithOrderId(int orderId, String newDesc) {
        String url = "";
        String user = "";
        String password = "";

        // get mysql credentials
        try (InputStream input = DatabaseChecker.class.getClassLoader().getResourceAsStream(propertiesFile)) {        
            Properties prop = new Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                System.exit(1);
            }

            prop.load(input);
            url = prop.getProperty("mySqlUrl") + prop.getProperty("mySQLDatabase");
            user = prop.getProperty("mySQLUser");
            password = prop.getProperty("mySQLPassword");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        // connect to mysql
        try (Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
        ) {
            String strUpdate = "update ORDERS set OrderDesc = '" + newDesc + "', edited_timestamp = CURRENT_TIMESTAMP where OrderId = " + orderId + ";";
            int countUpdated = stmt.executeUpdate(strUpdate);
            if (countUpdated == 0) {
                System.out.println("!!! Order Id does not exist. Choose an existing order id.");
                return;
            }
            
            System.out.println("--- Order edited with order id: " + orderId + ", new desc: " + newDesc);
        } catch (SQLException e) {
            System.out.println("Exception quitting - unable to update after editing");
            e.printStackTrace();
        }   


    }

    private void createOrderWithCustId(int custId, String desc) {
        String url = "";
        String user = "";
        String password = "";

        // get mysql credentials
        try (InputStream input = DatabaseChecker.class.getClassLoader().getResourceAsStream(propertiesFile)) {        
            Properties prop = new Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                System.exit(1);
            }

            prop.load(input);
            url = prop.getProperty("mySqlUrl") + prop.getProperty("mySQLDatabase");
            user = prop.getProperty("mySQLUser");
            password = prop.getProperty("mySQLPassword");

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        
        try (Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
        ) {
            String strUpdate = "Insert into ORDERS (CustId, OrderDesc) values (" + custId + ", '" + desc +"');";
            int countUpdated = stmt.executeUpdate(strUpdate);
            System.out.println(countUpdated + " record(s) affected");
        } catch (SQLException e) {
            System.out.println("Exception quitting - unable to update");
            e.printStackTrace();
        }

        System.out.println("--- added into MySQL database");

    }

    private int checkCustIdFromSQLMX(int custId) {
        String url = "";
        String user = "";
        String password = "";

        // get sqlmx credentials
        try (InputStream input = DatabaseChecker.class.getClassLoader().getResourceAsStream(propertiesFile)) {        
            Properties prop = new Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find " + propertiesFile);
                System.exit(1);
            }

            prop.load(input);
            url = prop.getProperty("sqlmxUrl") + "catalog=" + prop.getProperty("sqlmxCatalog") + ";schema=" + prop.getProperty("sqlmxSchema");
            user = prop.getProperty("sqlmxUser");
            password = prop.getProperty("sqlmxPassword");

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
        ) {
            String strSelect = "select count(*) as isValidCustId from customers where custid = " + custId + ";";
            ResultSet rset = stmt.executeQuery(strSelect);
            while (rset.next()) {
                return Integer.valueOf(rset.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("Exception quitting - unable to select");
            e.printStackTrace();
        }

        return -1;

    }

    private void checkJdbcDrivers() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
            System.exit(1);
        }
        
        try {
            Class.forName("com.tandem.t4jdbc.SQLMXDriver");
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
            System.exit(1);
        }
    }
}
