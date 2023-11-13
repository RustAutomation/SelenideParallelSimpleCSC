package csc.stepdefs;

import csc.hooks.Helpers;
import csc.hooks.jdbcConnectionSettings;
import csc.hooks.jdbcShare;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MssqlSteps {
    private jdbcShare r;
    private String schema;
    private String testName;
    private int i;

    public void jdbcTest(jdbcShare r) {
        this.r = r;
    }

    @Given("^jdbc connection with server: \"(.+)\" and database name \"(.+)\"$")
    public void jdbcGetConnectionSetup(String server, String dbName) throws SQLException {
        r.dbname = dbName;
        switch (server) {
            case "bd-vm-pp-titan":
                r.connectionUrl = (jdbcConnectionSettings.setPpTitanConnection()) + dbName;
                break;
            case "bd-vm-pp-saturn":
                r.connectionUrl = (jdbcConnectionSettings.setPpSaturnConnection()) + dbName;
                break;
            case "bd-vm-tt-titan":
                r.connectionUrl = (jdbcConnectionSettings.setTtTitanConnection()) + dbName;
                break;
            case "bd-vm-tt-saturn":
                r.connectionUrl = (jdbcConnectionSettings.setTtSaturnConnection()) + dbName;
                break;
            case "bd-vm-dv-titan":
                r.connectionUrl = (jdbcConnectionSettings.setDvTitanConnection()) + dbName;
                break;
            case "bd-vm-dv-saturn":
                r.connectionUrl = (jdbcConnectionSettings.setDvSaturnConnection()) + dbName;
                break;
            case "bd-vm-pp2-titan":
                r.connectionUrl = (jdbcConnectionSettings.setPp2TitanConnection()) + dbName;
                break;
            case "bd-vm-ut-titan":
                r.connectionUrl = (jdbcConnectionSettings.setUtTitanConnection()) + dbName;
                break;
        }
        String connectionUrl = r.connectionUrl; //+ dbName;
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            r.schema = connection.getSchema();
            r.connection = connection;
        } catch (SQLException e) {
            System.out.println();
            e.printStackTrace();
        }
        if ((r.connection == null) || r.connection.isClosed()) {
            r.connection = DriverManager.getConnection(connectionUrl);
            r.connection.setAutoCommit(true);
        }
    }

    @Given("^jdbc connection with host:// \"(.+)\" port:\"(\\d+)\" database name \"(.+)\"$")
    public void jdbcConnectionSetup(String host, int port, String dbName) throws SQLException {

        r.jdbcConnection = "jdbc:sqlserver://";
        r.host = host + ":";
        r.port = port;
        r.dbname = "databaseName=" + dbName;
        r.connectionUrl = r.jdbcConnection + r.host + r.port + ";" + "integratedSecurity=true;" + r.dbname;

        try (Connection connection = DriverManager.getConnection(r.connectionUrl)) {
            r.schema = connection.getSchema();
            r.connection = connection;
        } catch (SQLException e) {
            System.out.println();
            e.printStackTrace();
        }
        if ((r.connection == null) || r.connection.isClosed()) {
            r.connection = DriverManager.getConnection(r.connectionUrl);
            r.connection.setAutoCommit(true);
        }
    }

    @And("^set query source as file: \"(.*)\"$")
    public void setSqlQuerryFile(String name) throws IOException {
        r.sql = Files.readAllLines(Paths.get("src/test/resources/testData/" + name)).get(0);
    }

    @And("^set new query source as file: \"(.*)\"$")
    public void setSecondSqlQuerryFile(String name) throws IOException {
        r.sql2 = Files.readAllLines(Paths.get("src/test/resources/testData/" + name)).get(0);
    }

    @And("^execute new SQL statement$")
    public void newStatement() {
        try {
            Statement statement = r.connection.createStatement();
            r.resultSet = statement.executeQuery(r.sql);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    @And("^execute second SQL statement$")
    public void executeSecondStatement() {

        try {
            Statement statement = r.connection.createStatement();
            r.secondResultSet = statement.executeQuery(r.sql2);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    @And("^execute query from file: \"(.*)\"$")
    public void StatementFromFile(String name) throws IOException {
        r.sql = Files.readAllLines(Paths.get("src/test/resources/testData/" + name)).get(0);

        try {
            r.statement = r.connection.createStatement();
            r.resultSet = r.statement.executeQuery(r.sql);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    @And("^got data from multiple result set: \"(.*)\"$")
    public void MRSetStatementFromFile(String name) {
        try {
            int count = 0;
            do {
                r.resultSet = r.statement.getResultSet();
                count++;
                System.out.println(r.resultSet.getMetaData().getColumnCount() + " Columns received");
                System.out.println(r.resultSet.getMetaData().getColumnLabel(1));
                System.out.println(r.resultSet.getMetaData().getColumnLabel(2));
                System.out.println("Result set # " + count);
//                while (r.resultSet.next()) {
//                    count++;
//                    System.out.println(r.resultSet.getMetaData().getColumnCount());//тащим содержимое столбцов внутри резалт сетов
//                    System.out.println("Result set # " + count);
//                }
                System.out.println();
            } while(r.statement.getMoreResults());
            r.resultSet.close();
            r.statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @And("^get all values from column: \"(.*)\"$")
    public void getAllValuesFromColumn(String column) throws SQLException {
        r.cols = new ArrayList<String>();
        while (r.resultSet.next()) {
            r.cols.add(r.resultSet.getObject(column).toString());//387
        }
    }

    @And("^second query, get all values from column: \"(.*)\"$")
    public void getAllValuesFromSecondQuerysColumn(String column) throws SQLException {
        r.cols2 = new ArrayList<String>();
        while (r.secondResultSet.next()) {
            r.cols2.add(r.secondResultSet.getObject(column).toString());//387
        }
    }

    @And("^compare two recent columns content$")
    public void compareTwoRecentColumns() {
        Helpers.equalLists(r.cols, r.cols2);
        System.out.println("Column 1: " + r.cols.toString() + "\n" + "And\n" + "Column 2: "  + r.cols2.toString() + "\nSuccessfully Compared");
    }

    @And("^get values from column names: string type \"(.*)\", int type \"(.*)\", string type \"(.*)\"$")
    public void getFieldsValues(String label, String label1, String label2) throws SQLException {
        while(r.resultSet.next()) {
            r.field = r.resultSet.getString(label);
            r.field2 = r.resultSet.getInt(label1);
            System.out.println(label + ": " + r.field);
            System.out.println(label1 + ": " + r.field2);

            String comments = r.resultSet.getString(label2);
            System.out.println(label2 + ": " + comments + "\n");
        }
    }

    @And("^create notification table with name \"(.*)\" and int column name \"(.*)\" and varchar column name \"(.*)\"$")
    public void notificationTable(String nTableName, String columnName, String secondColumnName) throws SQLException {
        r.nTableName = nTableName;
        r.columnName = columnName;
        r.secondColumnName = secondColumnName;

        r.sql = "CREATE TABLE " + r.nTableName + " (" + columnName + " INT, [" + secondColumnName + "] VARCHAR(32)); INSERT INTO " + r.nTableName +
                " (" + columnName + "," + secondColumnName + ") VALUES (" + r.field2 + ", '" + r.field + "')";

        try {
            Statement statement = r.connection.createStatement();
            r.resultSet = statement.executeQuery(r.sql);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    @And("^send notification table to email \"(.*)\" with subject \"(.*)\" and text \"(.*)\"$")
    public void sendNotificationTable(String email, String subject, String mailText) throws SQLException {

        r.sql = "exec dbo.util_send_table @p_table = '" + r.nTableName +
                "', @p_columns = '" + r.columnName + "|" + r.secondColumnName + "',  @p_email = '" +
                email + "', @p_subject = '" + subject + "', @p_text = '<br/><br/>" + mailText + "';";

        try {
            Statement statement = r.connection.createStatement();
            r.resultSet = statement.executeQuery(r.sql);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    @And("^delete notification table \"(.*)\"$")
    public void deleteNotificationTable(String nTableName) {
        r.nTableName = nTableName;

        String sql = "DROP TABLE " + nTableName + ";";  //#rpt2;";
        Statement statement = null;
        try {
            statement = r.connection.createStatement();
            Boolean isRetrieved =    statement.execute(sql);
            System.out.println("\nIs data retrieved: " + isRetrieved);
            r.resultSet = statement.executeQuery(sql);
        } catch(SQLException sqle){
            sqle.printStackTrace();
        }
    }

    @And("^the test's name is \"(.*)\"$")
    public void testName(String testName) {
        System.out.println("\nTest: " + testName + "\n");
        this.testName = testName;
    }

    @And("^compare two tables$")
    public void mappingTable() throws SQLException {
        while(r.resultSet.next() && r.secondResultSet.next()) {
            if(r.resultSet.getString(1).equals(r.secondResultSet.getString(1))) {
                String x = "Table A: " + r.resultSet.getString(1) + " \nequals table B: " + r.secondResultSet.getString(1);
                System.out.println(x);
            } else {
                String x = "Table A: " + r.resultSet.getString(1) + " \nNOT equals table B: " + r.secondResultSet.getString(1);
                System.out.println(x);
            }
        }
    }

    @And("^get number of records with column label \"(.*)\"$")
    public void getPTNotCreationResult(String label) throws SQLException {
        ArrayList clients = new ArrayList();
        while(r.resultSet.next()) {
            int code = r.resultSet.getInt(label);
            clients.add(code);
        }
        System.out.println("Количество записей: " + clients.size());
    }


}
