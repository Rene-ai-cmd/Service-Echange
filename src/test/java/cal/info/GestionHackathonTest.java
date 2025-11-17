package cal.info;

import cal.info.util.BaseDeDonneesUtil;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GestionHackathonTest {

    private HackathonDAO gestionHackathon;

    @BeforeAll
    public void setupAll() {
        // Activer le mode test
        System.setProperty("testMode", "true");
    }

    @BeforeEach
    public void setup() throws SQLException {
        // Réinitialiser la base de données avant chaque test
        try (Connection conn = BaseDeDonneesUtil.getConnection()) {
            conn.createStatement().execute("DROP TABLE IF EXISTS Hackathon;");
        }
        // Réinitialiser la base de données (recréer les tables)
        BaseDeDonneesUtil.initialiserBase();
        gestionHackathon = new HackathonDAO();
    }

    @AfterAll
    public void teardown() {
        // Optionnel : fermer les connexions ou nettoyer
        System.clearProperty("testMode");
    }

    @Test
    public void testAjouterHackathon() throws SQLException {
        Hackathon hackathon = new Hackathon();
        hackathon.setNomHackathon("Hackathon AI");
        hackathon.setDateHackathon("2026-11-15");
        hackathon.setLieuHackathon("Montreal");
        hackathon.setUrlHackathon("http://hackathonai.com");

        gestionHackathon.ajouterHackathon(hackathon);

        List<Hackathon> hackathons = gestionHackathon.retrouverHackathons();
        assertEquals(1, hackathons.size());
        Hackathon retrieved = hackathons.get(0);
        assertEquals("Hackathon AI", retrieved.getNomHackathon());
        assertEquals("2026-11-15", retrieved.getDateHackathon());
        assertEquals("Montreal", retrieved.getLieuHackathon());
        assertEquals("http://hackathonai.com", retrieved.getUrlHackathon());
    }

    @Test
    public void testRetrouverHackathons_Vide() throws SQLException {
        List<Hackathon> hackathons = gestionHackathon.retrouverHackathons();
        assertTrue(hackathons.isEmpty());
    }

    @Test
    public void testRetrouverHackathons_AvecDonnees() throws SQLException {
        Hackathon h1 = new Hackathon();
        h1.setNomHackathon("Hackathon AI");
        h1.setDateHackathon("2026-11-15");
        h1.setLieuHackathon("Montreal");
        h1.setUrlHackathon("http://hackathonai.com");
        gestionHackathon.ajouterHackathon(h1);

        Hackathon h2 = new Hackathon();
        h2.setNomHackathon("Hackathon Dev");
        h2.setDateHackathon("2026-12-20");
        h2.setLieuHackathon("Quebec");
        h2.setUrlHackathon("http://hackathondev.com");
        gestionHackathon.ajouterHackathon(h2);

        List<Hackathon> hackathons = gestionHackathon.retrouverHackathons();
        assertEquals(2, hackathons.size());
    }
}
