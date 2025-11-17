package cal.info;

import cal.info.util.BaseDeDonneesUtil;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class HackathonDAO implements IGestionHackathon{

    Map<Programme, Set<Etudiant>> etudiantsParProg = new HashMap<>();

    DataSource dataSource;

    public HackathonDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void test() {
       etudiantsParProg.entrySet().stream().forEach(
               entree -> System.out.println(entree.getKey()));
//        etudiantsParProg.get(new Programme),add
    }


    @Override
    public void ajouterEtudiant(List<Etudiant> etudiants) {

        etudiants.stream().forEach(etudiant -> {
            Programme prog = new Programme(etudiant.getProgramme());
//            etudiantsParProg.putIfAbsent(new Programme(etudiant.getProgramme()), new HashSet<Etudiant>());
            if (!etudiantsParProg.containsKey(prog)) {
                etudiantsParProg.put(prog, new HashSet<Etudiant>());
            }
            etudiantsParProg.get(prog).add(etudiant);
        });




        System.out.println("Juste pour le test");
    }

    public void afficheEtudiants(){
        etudiantsParProg.entrySet().stream().forEach(entry -> {
            System.out.println("Programme : " + entry.getKey() + " - Etudiants : " + entry.getValue());
        });
    }



    public void ajouterHackathon(Hackathon hackathon) throws SQLException {
        String insertSQL = "INSERT INTO Hackathon (" +
                "nomHackathon, dateHackathon, lieuHackathon, urlHackathon) " +
                "VALUES (?, ?, ?, ?)";
        try (Connection conn = BaseDeDonneesUtil.getConnection();
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
        try (Connection conn = BaseDeDonneesUtil.getConnection();
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
