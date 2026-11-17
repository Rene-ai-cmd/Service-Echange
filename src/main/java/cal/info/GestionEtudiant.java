package cal.info;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestionEtudiant implements DaoGenerique<Etudiant, Integer> {

    public static void initialiserTable(Statement stmt) throws SQLException {
        stmt.execute("""
                CREATE TABLE IF NOT EXISTS Etudiant (
                matricule INT PRIMARY KEY,
                nom VARCHAR(255) NOT NULL,
                programme VARCHAR(255) NOT NULL);"""
        ); // NB. Le matricule d'un étudiant n'est _pas_ marqué AUTO_INCREMENT car il n'est pas choisi par la DB
    }

    @Override
    public List<Etudiant> trouverTous() throws SQLException {
        List<Etudiant> etudiants = new ArrayList<>();
        String selectSQL = "SELECT * FROM Etudiant";
        try (Connection conn = ConnectionPool.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(selectSQL);
            while (rs.next()) {
                Etudiant etudiant = new Etudiant();
                etudiant.setMatricule(rs.getInt("matricule"));
                etudiant.setNom(rs.getString("nom"));
                etudiant.setProgramme(rs.getString("programme"));
                etudiants.add(etudiant);
            }
        }
        return etudiants;
    }

    @Override
    public Etudiant trouverParId(Integer matricule) throws SQLException  {
        String selectSQL = "SELECT * FROM Etudiant WHERE matricule=?";
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(selectSQL)) {
            stmt.setInt(1, matricule);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Etudiant etudiant = new Etudiant();
                etudiant.setMatricule(rs.getInt("matricule"));
                etudiant.setNom(rs.getString("nom"));
                etudiant.setProgramme(rs.getString("programme"));
                return etudiant;
            }
        }
        return null;
    }

    @Override
    public Etudiant sauvegarder(Etudiant etudiant) throws SQLException {
        String mergeSQL = "MERGE INTO Etudiant (matricule, nom, programme) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(mergeSQL)) {
            stmt.setInt(1, etudiant.getMatricule());
            stmt.setString(2, etudiant.getNom());
            stmt.setString(3, etudiant.getProgramme());
            stmt.executeUpdate();
        }
        return etudiant;
    }

    @Override
    public boolean supprimer(Integer matricule) throws SQLException {
        String deleteSQL = "DELETE FROM Etudiant WHERE matricule=" + matricule;
        try (Connection conn = ConnectionPool.getConnection();
             Statement stmt = conn.createStatement()) {
            int nbSuppressions = stmt.executeUpdate(deleteSQL);
            return nbSuppressions > 0;
        }
    }

    @Override
    public boolean existe(Integer matricule) throws SQLException {
        String selectSQL = "SELECT 1 FROM Etudiant WHERE matricule=" + matricule;
        try (Connection conn = ConnectionPool.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(selectSQL);
            return rs.next();
        }
    }
}
