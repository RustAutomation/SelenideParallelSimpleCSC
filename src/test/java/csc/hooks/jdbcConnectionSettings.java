package csc.hooks;

public class jdbcConnectionSettings {
    private static jdbcShare r;

    public jdbcConnectionSettings(jdbcShare r) {
        this.r = r;
    }

    public static String setPpTitanConnection() {
        String jdbcConnection = "jdbc:sqlserver://";
        String host = "10.48.34.220:";
        String port = "1433";
        String dbname = "databaseName=";
        String username = "teamcityCI";
        String password = "phaLawraHA";
//        return jdbcConnection + host + port + ";" + "integratedSecurity=true;" + dbname;
        String connectionSring = jdbcConnection + host + port + ";" + "user="+ username + ";" + "password=" + password + ";" + "integratedSecurity=false;" +  dbname;
        return connectionSring;
    }

    public static String setPpSaturnConnection() {
        String jdbcConnection = "jdbc:sqlserver://";
        String host = "10.48.34.221:";
        String port = "1433";
        String dbname = "databaseName=";
        String username = "teamcityCI";
        String password = "phaLawraHA";
        String connectionSring = jdbcConnection + host + port + ";" + "user="+ username + ";" + "password=" + password + ";" + "integratedSecurity=false;" +  dbname;
        return connectionSring;
    }

    public static String setPp2TitanConnection() {
        String jdbcConnection = "jdbc:sqlserver://";
        String host = "10.48.36.220:";
        String port = "1433";
        String dbname = "databaseName=";
        String username = "teamcityCI";
        String password = "phaLawraHA";
        String connectionSring = jdbcConnection + host + port + ";" + "user="+ username + ";" + "password=" + password + ";" + "integratedSecurity=false;" +  dbname;
        return connectionSring;
    }

    public static String setTtTitanConnection() {
        String jdbcConnection = "jdbc:sqlserver://";
        String host = "10.48.34.216:"; //r.jdbcConnection + r.host + r.port + ";" + "integratedSecurity=true;" + r.dbname;
        String port = "1433";
        String dbname = "databaseName=";
        String username = "teamcityCI";
        String password = "phaLawraHA";
//        return jdbcConnection + host + port + ";" + "integratedSecurity=true;" +  dbname;
        String connectionSring = jdbcConnection + host + port + ";" + "user="+ username + ";" + "password=" + password + ";" + "integratedSecurity=false;" +  dbname;
        return connectionSring;
    }

    public static String setTtSaturnConnection() {
        String jdbcConnection = "jdbc:sqlserver://";
        String host = "10.48.34.217:";
        String port = "1433";
        String dbname = "databaseName=";
        String username = "teamcityCI";
        String password = "phaLawraHA";
        String connectionSring = jdbcConnection + host + port + ";" + "user="+ username + ";" + "password=" + password + ";" + "integratedSecurity=false;" +  dbname;
        return connectionSring;
    }

    public static String setDvTitanConnection() {
        String jdbcConnection = "jdbc:sqlserver://";
        String host = "10.48.34.218:";
        String port = "1433";
        String dbname = "databaseName=";
        String username = "teamcityCI";
        String password = "phaLawraHA";
        String connectionSring = jdbcConnection + host + port + ";" + "user="+ username + ";" + "password=" + password + ";" + "integratedSecurity=false;" +  dbname;
        return connectionSring;
    }

    public static String setDvSaturnConnection() {
        String jdbcConnection = "jdbc:sqlserver://";
        String host = "10.48.34.219:";
        String port = "1433";
        String dbname = "databaseName=";
        String username = "teamcityCI";
        String password = "phaLawraHA";
        String connectionSring = jdbcConnection + host + port + ";" + "user="+ username + ";" + "password=" + password + ";" + "integratedSecurity=false;" +  dbname;
        return connectionSring;
    }

    public static String setUtTitanConnection() {
        String jdbcConnection = "jdbc:sqlserver://";
        String host = "10.48.36.220:";
        String port = "1433";
        String dbname = "databaseName=";
        String username = "teamcityCI";
        String password = "phaLawraHA";
        String connectionSring = jdbcConnection + host + port + ";" + "user="+ username + ";" + "password=" + password + ";" + "integratedSecurity=false;" +  dbname;
        return connectionSring;
    }

}
