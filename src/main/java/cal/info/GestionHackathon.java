package cal.info;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestionHackathon implements DaoGenerique<Hackathon, Integer> {

    public static void initialiserTable(Statement stmt) throws SQLException {
        stmt.execute("""
                CREATE TABLE IF NOT EXISTS Hackathon (
                id INT AUTO_INCREMENT PRIMARY KEY,
                nom VARCHAR(255) NOT NULL,
                date VARCHAR(50) NOT NULL,
                lieu VARCHAR(255) NOT NULL,
                url VARCHAR(255) NOT NULL);"""
        );
    }

    @Override
    public List<Hackathon> trouverTous() throws SQLException {
        List<Hackathon> hackathons = new ArrayList<>();
        String selectSQL = "SELECT * FROM Hackathon";
        try (Connection conn = ConnectionPool.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(selectSQL);
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

    @Override
    public Hackathon trouverParId(Integer id) throws SQLException  {
        /* Fonctionnel, mais inefficace (pourquoi?) :
        return trouverTous().stream()
                .filter(hackathon -> hackathon.getId() == id)
                .findFirst().orElse(null); */
        String selectSQL = "SELECT * FROM Hackathon WHERE id=?";
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(selectSQL)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Hackathon hackathon = new Hackathon();
                hackathon.setId(rs.getInt("id"));
                hackathon.setNom(rs.getString("nom"));
                hackathon.setDate(rs.getString("date"));
                hackathon.setLieu(rs.getString("lieu"));
                hackathon.setUrl(rs.getString("url"));
                return hackathon;
            }
        }
        return null;
    }

    @Override
    public Hackathon sauvegarder(Hackathon hackathon) throws SQLException {
        String upsertSQL = (hackathon.getId() == 0)
             ? "INSERT INTO Hackathon (nom, date, lieu, url) VALUES (?, ?, ?, ?)"
             : "UPDATE Hackathon SET nom=?, date=?, lieu=?, url=? WHERE id=" + hackathon.getId();

        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(upsertSQL, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, hackathon.getNom());
            stmt.setString(2, hackathon.getDate());
            stmt.setString(3, hackathon.getLieu());
            stmt.setString(4, hackathon.getUrl());
            stmt.executeUpdate();

            // On va ajouter l'id généré par la DB dans l'objet hackathon avant de le retourner
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) // On vérifie si le hackathon a bien été ajouté (s'il respecte les contraintes de la table)
                hackathon.setId(rs.getInt("id")); // ou encore `hackathon = trouverParId(rs.getInt("id"));`
        }
        return hackathon;
    }

    @Override
    public boolean supprimer(Integer id) throws SQLException {
        String deleteSQL = "DELETE FROM Hackathon WHERE id=" + id;
        try (Connection conn = ConnectionPool.getConnection();
             Statement stmt = conn.createStatement()) {
            int nbSuppressions = stmt.executeUpdate(deleteSQL);
            return nbSuppressions > 0;
        }
    }

    @Override
    public boolean existe(Integer id) throws SQLException {
        String selectSQL = "SELECT 1 FROM Hackathon WHERE id=" + id;
        try (Connection conn = ConnectionPool.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(selectSQL);
            return rs.next();
        }
    }
}
