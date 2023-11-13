package csc.hooks;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

public class jdbcShare {

    public String host;
    public int port;
    public String jdbcConnection;
    public String dbname;
    public String username;
    public String password;
    public String connectionUrl;
    public ResultSet resultSet;
    public String sql;
    public int field2;
    public String field;
    public String name;
    public String nTableName;
    public String columnName;
    public String secondColumnName;
    public Connection connection;
    public ResultSetMetaData meta;
    public String schema;
    public ArrayList<String> cols;
    public ArrayList<String> cols2;
    public int i;
    public ResultSet secondResultSet;
    public String sql2;
    public ProcessBuilder service;
    public String instance;
    public String x;
    public boolean results;
    public Statement statement;
}
