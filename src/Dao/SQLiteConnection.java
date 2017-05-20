package Dao;

import Model.*;
import Ozlympic.Game;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLiteConnection{

    private static Connection conn;
    private static List<Athlete> athletes;
    private static List<Official> officials;

    public static boolean haveDB(){
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:testDB");
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM participants;" );

            String sql = "DROP TABLE IF EXISTS results";
            stmt.executeUpdate(sql);
            //1 -Game ID, 2 -Official ID, 3 -Athlete  ID,  4 ¨CResult,  5-Score.
            sql = "CREATE TABLE results" +
                    "(GameID   CHAR(3)    NOT NULL," +
                    " OfficialID    CHAR(6)    NOT NULL, " +
                    " AthleteID     CHAR(6)     NOT NULL, " +
                    " Result        INT(3)     NOT NULL, " +
                    " Score         INT(3)     NOT NULL)";
            stmt.executeUpdate(sql);
            stmt.close();

        } catch ( Exception e ) {
            return false;
        }
        athletes = new ArrayList<>();
        officials = new ArrayList<>();
        return true;
    }

    public static void readFromDB(){
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery( "SELECT * FROM participants;" );
            while ( rs.next() ) {
                String id = rs.getString("ID");
                String type = rs.getString("TYPE");
                String name = rs.getString("NAME");
                int age  = rs.getInt("AGE");
                String  state = rs.getString("STATE");
                switch (type){
                    case "officer":
                        officials.add(new Official(id,name,String.valueOf(age),state));
                        break;
                    case "sprinter":
                        athletes.add(new sprinter(id,name,String.valueOf(age),state));
                        break;
                    case "swimmer":
                        athletes.add(new swimmer(id,name,String.valueOf(age),state));
                        break;
                    case "cyclist":
                        athletes.add(new cyclist(id,name,String.valueOf(age),state));
                        break;
                    case "super":
                        athletes.add(new superAthlete(id,name,String.valueOf(age),state));
                        break;
                }
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //1 -Game ID, 2 -Official ID, 3 -Athlete  ID,  4 ¨CResult,  5-Score.
    public static void writeToDB(Game game){
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            String sql;
            for(int i=0; i<game.getAthletes().size();i++){
                sql = "INSERT INTO results VALUES ('"+
                        game.getGID()+"','"+
                        game.getOffical().getId()+"','"+
                        game.getAthletes().get(i).getID()+"',"+
                        game.getAthletes().get(i).getTime()+","+
                        game.getAthletes().get(i).getPoint()+");";
                System.out.println(sql);
                stmt.executeUpdate(sql);
            }
            stmt.close();
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Athlete> getAthletes() {
        return athletes;
    }

    public static List<Official> getOfficials() {
        return officials;
    }
}