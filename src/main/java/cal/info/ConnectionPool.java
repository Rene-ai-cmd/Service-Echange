package cal.info;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionPool {
    private static HikariDataSource dataSource;

    /**
     * Initialiser le pool de connexion (à appeler UNE SEULE FOIS au démarrage)
     * Note: pourrait aussi être placé dans un bloc `static` pour être exécuté automatiquement
     */
    public static void initialiserPool() {
        HikariConfig config = new HikariConfig();

        // Configuration de la base de données H2
        config.setJdbcUrl("jdbc:h2:./data/hackathonDB");
        config.setUsername("sa");
        config.setPassword("");
        config.setDriverClassName("org.h2.Driver");

        // Configuration du pool
        config.setMaximumPoolSize(10);      // Maximum 10 connexions ouvertes
        config.setMinimumIdle(1);           // Minimum 1 connexion en attente
        config.setConnectionTimeout(30000); // Timeout de 30 secondes
        config.setIdleTimeout(600000);      // Fermer après 10 minutes d'inactivité
        config.setMaxLifetime(1800000);     // Durée de vie maximale : 30 minutes

        // Nom du pool (pour le debugging)
        config.setPoolName("PoolH2");

        // Créer le pool
        dataSource = new HikariDataSource(config);

        System.out.println("Pool de connexions initialisé");
    }

    /**
     * Initialiser la base de données en créant les tables si elles n'existent pas
     */
    public static void initialiserBase() throws SQLException {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            GestionHackathon.initialiserTable(stmt);
        } // finally { stmt.close(); conn.close(); }
    }

    /**
     * Obtenir le DataSource (pool de connexion)
     */
    public static DataSource obtenirDataSource() {
        if (dataSource == null)
            throw new IllegalStateException("Le pool n'est pas initialisé. Appelez initialiser() d'abord.");
        return dataSource;
    }

    /**
     * Fermer le pool (à appeler à la fin de l'application)
     */
    public static void fermer() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            System.out.println("Pool de connexions fermé");
        }
    }

    /**
     * Obtenir une connexion en attente depuis le pool
     */
    public static Connection getConnection() throws SQLException {
        return obtenirDataSource().getConnection();
    }
}
