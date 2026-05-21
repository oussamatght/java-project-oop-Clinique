package gestion;

import exception.*;
import modele.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final CliniqueMedicale clinique = new CliniqueMedicale(
            "Clinique El-Chifa", "12 Rue des Acacia, Birkhadem, Alger");

    public static void main(String[] args) {
        System.out.println();
        System.out.println("  ==================================================");
        System.out.println("   SYSTEME DE GESTION - CLINIQUE EL-CHIFA");
        System.out.println("   Programmation Orientee Objet - Java");
        System.out.println("  ==================================================");

        chargerDonneesDemonstration();

        boolean continuer = true;
        while (continuer) {
            afficherMenuPrincipal();
            int choix = lireEntier("  Votre choix : ");
            System.out.println();
            switch (choix) {
                case 1 -> menuPatients();
                case 2 -> menuPersonnel();
                case 3 -> menuConsultations();
                case 4 -> menuOrdonnances();
                case 5 -> menuRecherche();
                case 6 -> clinique.afficherTableauDeBord();
                case 7 -> menuPolymorphisme();
                case 0 -> continuer = false;
                default -> System.out.println("  [!] Choix invalide. Veuillez reessayer.");
            }
        }
        System.out.println("\n  Au revoir ! Clinique El-Chifa vous remercie.\n");
        sc.close();
    }

    private static void afficherMenuPrincipal() {
        System.out.println();
        System.out.println("  --------------------------------------------------");
        System.out.println("               MENU PRINCIPAL");
        System.out.println("  --------------------------------------------------");
        System.out.println("   1. Gestion des Patients");
        System.out.println("   2. Gestion du Personnel Medical");
        System.out.println("   3. Gestion des Consultations");
        System.out.println("   4. Gestion des Ordonnances");
        System.out.println("   5. Recherche");
        System.out.println("   6. Tableau de Bord");
        System.out.println("   7. Afficher tout le personnel (Polymorphisme)");
        System.out.println("   0. Quitter");
        System.out.println("  --------------------------------------------------");
    }

    private static void menuPatients() {
        System.out.println("  === GESTION DES PATIENTS ===");
        System.out.println("  1. Enregistrer un nouveau patient");
        System.out.println("  2. Consulter un dossier medical");
        System.out.println("  3. Ajouter un antecedent medical");
        System.out.println("  4. Lister tous les patients");
        System.out.println("  0. Retour");

        int choix = lireEntier("  Votre choix : ");
        System.out.println();
        switch (choix) {
            case 1 -> enregistrerPatient();
            case 2 -> consulterDossier();
            case 3 -> ajouterAntecedent();
            case 4 -> clinique.listerPatients();
            case 0 -> {
            }
            default -> System.out.println("  [!] Choix invalide.");
        }
    }

    private static void menuPersonnel() {
        System.out.println("  === GESTION DU PERSONNEL MEDICAL ===");
        System.out.println("  1. Ajouter un medecin");
        System.out.println("  2. Ajouter un infirmier");
        System.out.println("  3. Lister les medecins");
        System.out.println("  4. Lister les infirmiers");
        System.out.println("  0. Retour");

        int choix = lireEntier("  Votre choix : ");
        System.out.println();
        switch (choix) {
            case 1 -> ajouterMedecin();
            case 2 -> ajouterInfirmier();
            case 3 -> clinique.listerMedecins();
            case 4 -> clinique.listerInfirmiers();
            case 0 -> {
            }
            default -> System.out.println("  [!] Choix invalide.");
        }
    }

    private static void menuConsultations() {
        System.out.println("  === GESTION DES CONSULTATIONS ===");
        System.out.println("  1. Creer une consultation");
        System.out.println("  2. Afficher toutes les consultations");
        System.out.println("  3. Modifier le statut d'une consultation");
        System.out.println("  0. Retour");

        int choix = lireEntier("  Votre choix : ");
        System.out.println();
        switch (choix) {
            case 1 -> creerConsultation();
            case 2 -> clinique.listerConsultations();
            case 3 -> modifierStatutConsultation();
            case 0 -> {
            }
            default -> System.out.println("  [!] Choix invalide.");
        }
    }

    private static void menuOrdonnances() {
        System.out.println("  === GESTION DES ORDONNANCES ===");
        System.out.println("  1. Creer une ordonnance");
        System.out.println("  2. Ajouter un medicament a une ordonnance");
        System.out.println("  3. Afficher une ordonnance");
        System.out.println("  0. Retour");

        int choix = lireEntier("  Votre choix : ");
        System.out.println();
        switch (choix) {
            case 1 -> creerOrdonnance();
            case 2 -> ajouterMedicament();
            case 3 -> afficherOrdonnance();
            case 0 -> {
            }
            default -> System.out.println("  [!] Choix invalide.");
        }
    }

    private static void menuRecherche() {
        System.out.println("  === RECHERCHE ===");
        System.out.println("  1. Consultations par patient (ID)");
        System.out.println("  2. Consultations par medecin (ID)");
        System.out.println("  3. Historique complet d'un patient");
        System.out.println("  4. Compter consultations d'un medecin");
        System.out.println("  0. Retour");

        int choix = lireEntier("  Votre choix : ");
        System.out.println();
        switch (choix) {
            case 1 -> {
                String pid = lireChaine("  ID du patient : ");
                List<Consultation> res = clinique.rechercherConsultationsParPatient(pid);
                if (res.isEmpty()) {
                    System.out.println("  Aucune consultation trouvee pour ce patient.");
                } else {
                    System.out.println("  " + res.size() + " consultation(s) trouvee(s) :");
                    res.forEach(Consultation::afficher);
                }
            }
            case 2 -> {
                String mid = lireChaine("  ID du medecin : ");
                List<Consultation> res = clinique.rechercherConsultationsParMedecin(mid);
                if (res.isEmpty()) {
                    System.out.println("  Aucune consultation trouvee pour ce medecin.");
                } else {
                    System.out.println("  " + res.size() + " consultation(s) trouvee(s) :");
                    res.forEach(Consultation::afficher);
                }
            }
            case 3 -> {
                String pid = lireChaine("  ID du patient : ");
                try {
                    clinique.afficherHistoriquePatient(pid);
                } catch (PatientInexistantException e) {
                    System.err.println("  [ERREUR] " + e.getMessage());
                }
            }
            case 4 -> {
                String mid = lireChaine("  ID du medecin : ");
                try {
                    int total = clinique.compterConsultationsParMedecin(mid);
                    System.out.println("  Total consultations (hors annulees) : " + total);
                } catch (MedecinInexistantException e) {
                    System.err.println("  [ERREUR] " + e.getMessage());
                }
            }
            case 0 -> {
            }
            default -> System.out.println("  [!] Choix invalide.");
        }
    }

    private static void menuPolymorphisme() {
        System.out.println("  Demonstration de l'appel POLYMORPHE - List<Personne>");
        System.out.println("  Chaque objet appelle sa propre version de afficherProfil().\n");
        clinique.afficherToutLePersonnel();
    }

    private static void enregistrerPatient() {
        System.out.println("  --- Enregistrement d'un nouveau patient ---");
        try {
            String id = lireChaine("  Identifiant    : ");
            String nom = lireChaine("  Nom            : ");
            String prenom = lireChaine("  Prenom         : ");
            LocalDate dn = lireDate("  Date naissance (JJ/MM/AAAA) : ");
            String tel = lireChaine("  Telephone      : ");
            String nss = lireChaine("  N Secu sociale : ");

            System.out.println("  Groupes valides : A+, A-, B+, B-, O+, O-, AB+, AB-");
            String gs = lireChaine("  Groupe sanguin : ");

            Patient p = new Patient(id, nom, prenom, dn, tel, nss, gs);
            clinique.ajouterPatient(p);
            p.afficherProfil();

        } catch (IllegalArgumentException e) {
            System.err.println("  [ERREUR] " + e.getMessage());
        }
    }

    private static void consulterDossier() {
        System.out.println("  --- Consultation du dossier medical ---");
        try {
            String pid = lireChaine("  ID du patient         : ");
            String role = lireChaine("  Votre role (medecin/infirmier) : ");
            clinique.accederDossierPatient(pid, role);

        } catch (PatientInexistantException | DossierMedicalException e) {
            System.err.println("  [ERREUR] " + e.getMessage());
        } finally {
            System.out.println("  [INFO] Tentative d'acces au dossier terminee.");
        }
    }

    private static void ajouterAntecedent() {
        System.out.println("  --- Ajout d'un antecedent medical ---");
        try {
            String pid = lireChaine("  ID du patient    : ");
            Patient p = clinique.rechercherPatient(pid);
            String ant = lireChaine("  Antecedent       : ");
            p.ajouterAntecedent(ant);
            System.out.println("  [OK] Antecedent ajoute.");

        } catch (PatientInexistantException e) {
            System.err.println("  [ERREUR] " + e.getMessage());
        }
    }

    private static void ajouterMedecin() {
        System.out.println("  --- Ajout d'un medecin ---");
        try {
            String id = lireChaine("  Identifiant      : ");
            String nom = lireChaine("  Nom              : ");
            String prenom = lireChaine("  Prenom           : ");
            LocalDate dn = lireDate("  Date naissance (JJ/MM/AAAA) : ");
            String tel = lireChaine("  Telephone        : ");
            String mat = lireChaine("  Matricule        : ");
            LocalDate de = lireDate("  Date embauche (JJ/MM/AAAA)  : ");
            double sal = lireDouble("  Salaire (DA)     : ");
            String spec = lireChaine("  Specialite       : ");
            String ord = lireChaine("  N Ordre          : ");
            double tarif = lireDouble("  Tarif consult(DA): ");

            Medecin m = new Medecin(id, nom, prenom, dn, tel, mat, de, sal, spec, ord, tarif);
            clinique.ajouterMedecin(m);
            m.afficherProfil();

        } catch (IllegalArgumentException e) {
            System.err.println("  [ERREUR] " + e.getMessage());
        }
    }

    private static void ajouterInfirmier() {
        System.out.println("  --- Ajout d'un infirmier ---");
        try {
            String id = lireChaine("  Identifiant       : ");
            String nom = lireChaine("  Nom               : ");
            String prenom = lireChaine("  Prenom            : ");
            LocalDate dn = lireDate("  Date naissance (JJ/MM/AAAA) : ");
            String tel = lireChaine("  Telephone         : ");
            String mat = lireChaine("  Matricule         : ");
            LocalDate de = lireDate("  Date embauche (JJ/MM/AAAA)  : ");
            double sal = lireDouble("  Salaire (DA)      : ");
            String serv = lireChaine("  Service           : ");
            String grade = lireChaine("  Grade             : ");

            Infirmier inf = new Infirmier(id, nom, prenom, dn, tel, mat, de, sal, serv, grade);
            clinique.ajouterInfirmier(inf);
            inf.afficherProfil();

        } catch (IllegalArgumentException e) {
            System.err.println("  [ERREUR] " + e.getMessage());
        }
    }

    private static void creerConsultation() {
        System.out.println("  --- Creation d'une consultation ---");
        System.out.println("  (Laissez le diagnostic vide pour l'ajouter plus tard)");
        try {
            String pid = lireChaine("  ID du patient  : ");
            String mid = lireChaine("  ID du medecin  : ");
            LocalDateTime dt = lireDateHeure("  Date/heure (JJ/MM/AAAA HH:mm) : ");
            String diag = lireChaine("  Diagnostic     : ");
            String notes = lireChaine("  Notes cliniques: ");

            Consultation c = clinique.creerConsultation(pid, mid, dt, diag, notes);
            c.afficher();

        } catch (PatientInexistantException | MedecinInexistantException | MedecinIndisponibleException e) {
            System.err.println("  [ERREUR] " + e.getMessage());
        } finally {
            System.out.println("  [INFO] Traitement de la planification termine.");
        }
    }

    private static void modifierStatutConsultation() {
        System.out.println("  --- Modification du statut ---");
        try {
            String cid = lireChaine("  ID consultation : ");
            Consultation c = clinique.rechercherConsultation(cid);

            System.out.println("  Statuts disponibles : PLANIFIEE / EN_COURS / TERMINEE / ANNULEE");
            String saisie = lireChaine("  Nouveau statut : ").toUpperCase();
            c.setStatut(Consultation.Statut.valueOf(saisie));
            System.out.println("  [OK] Statut mis a jour : " + c.getStatut());

        } catch (OrdonnanceInvalideException | IllegalArgumentException e) {
            System.err.println("  [ERREUR] " + e.getMessage());
        }
    }

    private static void creerOrdonnance() {
        System.out.println("  --- Creation d'une ordonnance ---");
        try {
            String cid = lireChaine("  ID de la consultation : ");
            clinique.creerOrdonnance(cid);

        } catch (OrdonnanceInvalideException e) {
            System.err.println("  [ERREUR] " + e.getMessage());
        } finally {
            System.out.println("  [INFO] Traitement de creation d'ordonnance termine.");
        }
    }

    private static void ajouterMedicament() {
        System.out.println("  --- Ajout d'un medicament a une ordonnance ---");
        try {

            String cid = lireChaine("  ID de la consultation : ");
            Ordonnance ord = trouverOrdonnanceParConsultation(cid);

            if (ord == null) {
                System.out.println("  [ERREUR] Aucune ordonnance trouvee pour cette consultation.");
                return;
            }

            String nomMed = lireChaine("  Nom du medicament    : ");
            String dosage = lireChaine("  Posologie/dosage     : ");
            int duree = lireEntier("  Duree traitement (j) : ");
            String contre = lireChaine("  Contre-indications   : ");

            ord.ajouterMedicament(new Medicament(nomMed, dosage, duree, contre));
            System.out.println("  [OK] Medicament ajoute a l'ordonnance " + ord.getIdOrdonnance());

        } catch (Exception e) {
            System.err.println("  [ERREUR] " + e.getMessage());
        }
    }

    private static void afficherOrdonnance() {
        System.out.println("  --- Affichage d'une ordonnance ---");
        try {
            String cid = lireChaine("  ID de la consultation : ");
            Ordonnance ord = trouverOrdonnanceParConsultation(cid);

            if (ord == null) {
                System.out.println("  [ERREUR] Aucune ordonnance pour cette consultation.");
                return;
            }
            ord.afficher();

        } catch (OrdonnanceInvalideException e) {
            System.err.println("  [ERREUR] " + e.getMessage());
        } finally {
            System.out.println("  [INFO] Affichage de l'ordonnance termine.");
        }
    }

    private static Ordonnance trouverOrdonnanceParConsultation(String consultationId) {
        return clinique.getOrdonnances().stream()
                .filter(o -> o.getConsultation().getIdConsultation()
                        .equalsIgnoreCase(consultationId))
                .findFirst()
                .orElse(null);
    }

    private static void chargerDonneesDemonstration() {
        try {
            System.out.println("\n  Chargement des donnees de demonstration...");

            Medecin m1 = new Medecin("MED001", "Benali", "Karim",
                    LocalDate.of(1975, 3, 14), "0555-112233",
                    "MAT-001", LocalDate.of(2005, 9, 1),
                    180_000, "Cardiologie", "ORD-12345", 2500.0);

            Medecin m2 = new Medecin("MED002", "Ouali", "Samira",
                    LocalDate.of(1982, 7, 22), "0555-445566",
                    "MAT-002", LocalDate.of(2010, 1, 15),
                    155_000, "Pediatrie", "ORD-67890", 2000.0);

            clinique.ajouterMedecin(m1);
            clinique.ajouterMedecin(m2);

            Infirmier i1 = new Infirmier("INF001", "Meziane", "Nadia",
                    LocalDate.of(1990, 5, 8), "0550-778899",
                    "MAT-101", LocalDate.of(2015, 3, 1),
                    80_000, "Urgences", "Principal");

            clinique.ajouterInfirmier(i1);

            Patient p1 = new Patient("PAT001", "Hadj", "Amine",
                    LocalDate.of(1988, 11, 30), "0661-001122",
                    "188309012345678", "O+");
            p1.ajouterAntecedent("Hypertension arterielle");
            p1.ajouterAntecedent("Allergie penicilline");

            Patient p2 = new Patient("PAT002", "Ferhat", "Leila",
                    LocalDate.of(1995, 4, 16), "0770-334455",
                    "195409098765432", "A-");

            clinique.ajouterPatient(p1);
            clinique.ajouterPatient(p2);

            Consultation c1 = clinique.creerConsultation(
                    "PAT001", "MED001",
                    LocalDateTime.now().minusDays(1).withHour(9).withMinute(0),
                    "Insuffisance cardiaque legere",
                    "Patient sous surveillance. ECG normal.");
            c1.setStatut(Consultation.Statut.TERMINEE);

            Consultation c2 = clinique.creerConsultation(
                    "PAT002", "MED002",
                    LocalDateTime.now().withHour(10).withMinute(30),
                    "Angine bacterienne", "");

            Ordonnance ord1 = clinique.creerOrdonnance(c1.getIdConsultation());
            ord1.ajouterMedicament(new Medicament("Bisoprolol", "5mg - 1cp/matin", 30,
                    "Insuffisance cardiaque decompensee"));
            ord1.ajouterMedicament(new Medicament("Ramipril", "10mg - 1cp/soir", 30,
                    "Grossesse"));

            Ordonnance ord2 = clinique.creerOrdonnance(c2.getIdConsultation());
            ord2.ajouterMedicament(new Medicament("Amoxicilline", "1g - 3x/jour", 7,
                    "Allergie penicillines"));

            System.out.println("  Donnees de demonstration chargees avec succes !\n");

        } catch (Exception e) {
            System.err.println("  [AVERTISSEMENT] Erreur lors du chargement : " + e.getMessage());
        }
    }

    private static String lireChaine(String invite) {
        System.out.print(invite);
        return sc.nextLine().trim();
    }

    private static int lireEntier(String invite) {
        while (true) {
            System.out.print(invite);
            String s = sc.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("  [!] Entrez un nombre entier valide.");
            }
        }
    }

    private static double lireDouble(String invite) {
        while (true) {
            System.out.print(invite);
            String s = sc.nextLine().trim();
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.println("  [!] Entrez un nombre decimal valide (ex: 2500.0).");
            }
        }
    }

    private static LocalDate lireDate(String invite) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (true) {
            System.out.print(invite);
            String s = sc.nextLine().trim();
            try {
                return LocalDate.parse(s, fmt);
            } catch (DateTimeParseException e) {
                System.out.println("  [!] Format invalide. Utilisez JJ/MM/AAAA (ex: 15/03/1990).");
            }
        }
    }

    private static LocalDateTime lireDateHeure(String invite) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        while (true) {
            System.out.print(invite);
            String s = sc.nextLine().trim();
            try {
                return LocalDateTime.parse(s, fmt);
            } catch (DateTimeParseException e) {
                System.out.println("  [!] Format invalide. Utilisez JJ/MM/AAAA HH:mm (ex: 25/05/2026 14:30).");
            }
        }
    }
}
