package tikape.vuokraamo_vko5;

import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author mcsieni
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:vuokraamo.db");
        Scanner input = new Scanner(System.in);
        
        tehtava6(connection);
        tehtava7(connection);
//        tehtava8(connection, input);
        tehtava9(connection);

        connection.close();
    }

    public static void tehtava6(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Varaaja;");

        System.out.println("Tehtävä 6: ");
        while (rs.next()) {
            int asiakasnumero = rs.getInt("asiakasnumero");
            String etunimi = rs.getString("etunimi");
            String sukunimi = rs.getString("sukunimi");
            String osoite = rs.getString("osoite");
            String puhelinnumero = rs.getString("puhelinnumero");

            System.out.println(asiakasnumero + "\t" + etunimi + "\t" + sukunimi + "\t" + osoite + "\t" + puhelinnumero);
        }

        stmt.close();
        rs.close();
    }

    public static void tehtava7(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Varaus;");

        System.out.println("Tehtävä 7: ");
        while (rs.next()) {
            String varaustunnus = rs.getString("varaustunnus");
            String varaaja = rs.getString("varaaja");
            String pyora = rs.getString("pyora");
            Timestamp varaus_alkaa = rs.getTimestamp("varaus_alkaa");
            Timestamp varaus_loppuu = rs.getTimestamp("varaus_loppuu");

            System.out.println(varaustunnus + "\t" + varaaja + "\t" + pyora + "\t" + varaus_alkaa + "\t" + varaus_loppuu);
        }

        stmt.close();
        rs.close();
    }

    public static void tehtava8(Connection connection, Scanner scanner) throws SQLException {
        Statement stmt = connection.createStatement();
        String sql = "SELECT asiakasnumero FROM Varaaja ORDER BY asiakasnumero DESC LIMIT 1;";
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        int asiakasnumero = rs.getInt(1) + 1;

        System.out.println("Tehtävä 8: ");
        System.out.println("Etunimi: ");
        String etunimi = scanner.nextLine();
        
        System.out.println("Sukunimi: ");
        String sukunimi = scanner.nextLine();
        
        System.out.println("Osoite: ");
        String osoite = scanner.nextLine();
        
        System.out.println("Puhelinnumero: ");
        String puhelinnumero = scanner.nextLine();
        
        sql = "INSERT INTO Varaaja (asiakasnumero, etunimi, sukunimi, osoite, puhelinnumero) "
                + "VALUES (" + asiakasnumero + ", '" + etunimi + "', '" + sukunimi + "', '" + osoite + "', '" + puhelinnumero + "');";
        stmt.executeUpdate(sql);
        
        stmt.close();
        rs.close();
    }
    
    public static void tehtava9(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        String sql = "SELECT Varaus.varaustunnus, Varaaja.etunimi, Varaaja.sukunimi, Varaus.pyora, Pyora.merkki, Varaus.varaus_alkaa, Varaus.varaus_loppuu FROM Varaus "
                + "LEFT JOIN Pyora ON Varaus.pyora = Pyora.rekisterinumero "
                + "LEFT JOIN Varaaja ON Varaus.varaaja=Varaaja.asiakasnumero;";
        ResultSet rs = stmt.executeQuery(sql);

        System.out.println("Tehtävä 7: ");
        while (rs.next()) {
            String varaustunnus = rs.getString("varaustunnus");
            String varaaja = rs.getString("etunimi") + " " + rs.getString("sukunimi");
            String pyora = rs.getString("pyora") + " " + rs.getString("merkki");
            Timestamp varaus_alkaa = rs.getTimestamp("varaus_alkaa");
            Timestamp varaus_loppuu = rs.getTimestamp("varaus_loppuu");

            System.out.println(varaustunnus + "\t" + varaaja + "\t" + pyora + "\t" + varaus_alkaa + "\t" + varaus_loppuu);
        }

        stmt.close();
        rs.close();
    }
}
