package cal.info.donnes;

import cal.info.modele.Etudiant;
import cal.info.modele.Hackathon;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HackathonDAO {

    DataSource dataSource;

    public HackathonDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void ajouterHackathon(Hackathon hackathon) throws SQLException {
        String insertSQL = "INSERT INTO Hackathon (" +
                "nomHackathon, dateHackathon, lieuHackathon, urlHackathon) " +
                "VALUES (?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, hackathon.getNomHackathon());
            pstmt.setString(2, hackathon.getDateHackathon());
            pstmt.setString(3, hackathon.getLieuHackathon());
            pstmt.setString(4, hackathon.getUrlHackathon());
            pstmt.executeUpdate();
        }
    }

    public List<Hackathon> retrouverHackathons() throws SQLException {
        List<Hackathon> hackathons = new ArrayList<>();
        String selectSQL = "SELECT * FROM Hackathon";
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {
            while (rs.next()) {
                Hackathon hackathon = new Hackathon();
                hackathon.setId(rs.getInt("id"));
                hackathon.setNomHackathon(rs.getString("nomHackathon"));
                hackathon.setDateHackathon(rs.getString("dateHackathon"));
                hackathon.setLieuHackathon(rs.getString("lieuHackathon"));
                hackathon.setUrlHackathon(rs.getString("urlHackathon"));
                hackathons.add(hackathon);
            }
        }
        return hackathons;
    }

    public void modifierHackathon(Hackathon hackathon) {

    }

    public void creerEtudiants(List<Etudiant>  etudiants) {

    }

    public void formerEquipe(List<Etudiant> etudiants, String nomEquipe) {

    }


}
