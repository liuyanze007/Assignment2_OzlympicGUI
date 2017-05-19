package Dao;

import java.sql.*;

public class SQLiteConnection{
    public static void main( String args[] )
    {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:testDB");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM participants;" );
            while ( rs.next() ) {
                String id = rs.getString("ID");
                String type = rs.getString("TYPE");
                String name = rs.getString("NAME");
                int age  = rs.getInt("AGE");
                String  state = rs.getString("STATE");
                System.out.println( "ID = " + id );
                System.out.println( "TYPE = " + type );
                System.out.println( "NAME = " + name );
                System.out.println( "AGE = " + age );
                System.out.println( "STATE = " + state );
                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }
}