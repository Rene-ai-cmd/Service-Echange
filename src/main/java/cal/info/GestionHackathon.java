package cal.info;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestionHackathon {

    // Ajouter un hackathon
    public void ajouterHackathon(Hackathon hackathon) throws SQLException {
        String insertSQL = "INSERT INTO Hackathon (nom, date, lieu, url) VALUES (?, ?, ?, ?)";
        try (Connection conn = BaseDeDonnees.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, hackathon.getNom());
            pstmt.setString(2, hackathon.getDate());
            pstmt.setString(3, hackathon.getLieu());
            pstmt.setString(4, hackathon.getUrl());
            pstmt.executeUpdate();
        }
    }

    // Récupérer tous les hackathons
    public List<Hackathon> retrouverHackathons() throws SQLException {
        List<Hackathon> hackathons = new ArrayList<>();
        String selectSQL = "SELECT * FROM Hackathon";
        try (Connection conn = BaseDeDonnees.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {
            while (rs.next()) {
                Hackathon hackathon = new Hackathon();
                hackathon.setId(rs.getInt("id"));
                hackathon.setNom(rs.getString("nom"));
                hackathon.setDate(rs.getString("date"));
                hackathon.setLieu(rs.getString("lieu"));
                hackathon.setUrl(rs.getString("url"));
                hackathons.add(hackathon);
            }
        }
        return hackathons;
    }
}
