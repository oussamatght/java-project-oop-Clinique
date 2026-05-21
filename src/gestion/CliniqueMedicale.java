package gestion;

import exception.DossierMedicalException;
import exception.MedecinIndisponibleException;
import exception.MedecinInexistantException;
import exception.OrdonnanceInvalideException;
import exception.PatientInexistantException;
import modele.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CliniqueMedicale {

    private static final int DUREE_CONSULTATION_MINUTES = 30;
    private static final int MAX_CONSULTATIONS_PAR_MEDECIN_PAR_JOUR = 12;

    private String nom;
    private String adresse;

    private List<Patient> patients;
    private List<Medecin> medecins;
    private List<Infirmier> infirmiers;
    private List<Consultation> consultations;
    private List<Ordonnance> ordonnances;

    public CliniqueMedicale(String nom, String adresse) {
        this.nom = nom;
        this.adresse = adresse;
        this.patients = new ArrayList<>();
        this.medecins = new ArrayList<>();
        this.infirmiers = new ArrayList<>();
        this.consultations = new ArrayList<>();
        this.ordonnances = new ArrayList<>();
    }

    public void ajouterPatient(Patient patient) {
        patients.add(patient);
        System.out.println("  [OK] Patient enregistre : " + patient.getPrenom() + " " + patient.getNom());
    }

    public Patient rechercherPatient(String id) throws PatientInexistantException {
        return patients.stream()
                .filter(p -> p.getIdentifiant().equalsIgnoreCase(id))
                .findFirst()
                .orElseThrow(() -> new PatientInexistantException(
                        "Aucun patient trouve avec l'ID : " + id));
    }

    public void accederDossierPatient(String patientId, String roleAccedant)
            throws PatientInexistantException, DossierMedicalException {

        if (roleAccedant == null || roleAccedant.isBlank()) {
            throw new DossierMedicalException(
                    "Acces refuse au dossier : identite de l'accedant non specifiee.");
        }
        Patient p = rechercherPatient(patientId);
        System.out.println("  Acces autorise par : " + roleAccedant);
        p.afficherDossier();
    }

    public void ajouterMedecin(Medecin medecin) {
        medecins.add(medecin);
        System.out.println("  [OK] Medecin enregistre : Dr. "
                + medecin.getPrenom() + " " + medecin.getNom());
    }

    public Medecin rechercherMedecin(String id) throws MedecinInexistantException {
        return medecins.stream()
                .filter(m -> m.getIdentifiant().equalsIgnoreCase(id))
                .findFirst()
                .orElseThrow(() -> new MedecinInexistantException(
                        "Aucun medecin trouve avec l'ID : " + id));
    }

    public void ajouterInfirmier(Infirmier infirmier) {
        infirmiers.add(infirmier);
        System.out.println("  [OK] Infirmier enregistre : "
                + infirmier.getPrenom() + " " + infirmier.getNom());
    }

    public Consultation creerConsultation(String patientId, String medecinId,
            LocalDateTime dateHeure, String diagnostic)
            throws PatientInexistantException, MedecinInexistantException, MedecinIndisponibleException {
        return creerConsultation(patientId, medecinId, dateHeure, diagnostic, "");
    }

    public Consultation creerConsultation(String patientId, String medecinId,
            LocalDateTime dateHeure, String diagnostic,
            String notes)
            throws PatientInexistantException, MedecinInexistantException, MedecinIndisponibleException {

        if (dateHeure == null) {
            throw new IllegalArgumentException("Date/heure de consultation obligatoire.");
        }

        Patient patient = rechercherPatient(patientId);
        Medecin medecin = rechercherMedecin(medecinId);

        verifierDisponibiliteMedecin(medecin, dateHeure);

        Consultation c = new Consultation(patient, medecin, dateHeure, diagnostic, notes);
        consultations.add(c);
        System.out.println("  [OK] Consultation creee : " + c.getIdConsultation());
        return c;
    }

    private void verifierDisponibiliteMedecin(Medecin medecin, LocalDateTime dateHeure)
            throws MedecinIndisponibleException {

        LocalDate dateDemandee = dateHeure.toLocalDate();

        long nbConsultationsJour = consultations.stream()
                .filter(c -> c.getMedecin().getIdentifiant().equals(medecin.getIdentifiant()))
                .filter(c -> c.getDateHeure().toLocalDate().equals(dateDemandee))
                .filter(this::estConsultationActivePourPlanning)
                .count();

        if (nbConsultationsJour >= MAX_CONSULTATIONS_PAR_MEDECIN_PAR_JOUR) {
            throw new MedecinIndisponibleException(
                    "Dr. " + medecin.getNom() + " a atteint la limite quotidienne de "
                            + MAX_CONSULTATIONS_PAR_MEDECIN_PAR_JOUR + " consultations.");
        }

        boolean occupe = consultations.stream()
                .filter(c -> c.getMedecin().getIdentifiant().equals(medecin.getIdentifiant()))
                .filter(this::estConsultationActivePourPlanning)
                .anyMatch(c -> consultationsSeChevauchent(c.getDateHeure(), dateHeure));

        if (occupe) {
            throw new MedecinIndisponibleException(
                    "Dr. " + medecin.getNom()
                            + " est indisponible : chevauchement avec un autre rendez-vous.");
        }
    }

    private boolean estConsultationActivePourPlanning(Consultation consultation) {
        return consultation.getStatut() != Consultation.Statut.ANNULEE;
    }

    private boolean consultationsSeChevauchent(LocalDateTime debutExistante, LocalDateTime debutNouvelle) {
        LocalDateTime finExistante = debutExistante.plusMinutes(DUREE_CONSULTATION_MINUTES);
        LocalDateTime finNouvelle = debutNouvelle.plusMinutes(DUREE_CONSULTATION_MINUTES);
        return debutExistante.isBefore(finNouvelle) && debutNouvelle.isBefore(finExistante);
    }

    public Consultation rechercherConsultation(String id) throws OrdonnanceInvalideException {
        return consultations.stream()
                .filter(c -> c.getIdConsultation().equalsIgnoreCase(id))
                .findFirst()
                .orElseThrow(() -> new OrdonnanceInvalideException(
                        "Consultation introuvable avec l'ID : " + id));
    }

    public Ordonnance creerOrdonnance(String consultationId) throws OrdonnanceInvalideException {
        Consultation consultation = rechercherConsultation(consultationId);
        Ordonnance ordonnance = new Ordonnance(consultation);
        ordonnances.add(ordonnance);
        System.out.println("  [OK] Ordonnance creee : " + ordonnance.getIdOrdonnance());
        return ordonnance;
    }

    public List<Consultation> rechercherConsultationsParPatient(String patientId) {
        return consultations.stream()
                .filter(c -> c.getPatient().getIdentifiant().equalsIgnoreCase(patientId))
                .collect(Collectors.toList());
    }

    public List<Consultation> rechercherConsultationsParMedecin(String medecinId) {
        return consultations.stream()
                .filter(c -> c.getMedecin().getIdentifiant().equalsIgnoreCase(medecinId))
                .collect(Collectors.toList());
    }

    public int compterConsultationsParMedecin(String medecinId) throws MedecinInexistantException {
        Medecin medecin = rechercherMedecin(medecinId);
        return (int) consultations.stream()
                .filter(c -> c.getMedecin().getIdentifiant().equalsIgnoreCase(medecin.getIdentifiant()))
                .filter(c -> c.getStatut() != Consultation.Statut.ANNULEE)
                .count();
    }

    public void afficherHistoriquePatient(String patientId) throws PatientInexistantException {
        Patient patient = rechercherPatient(patientId);
        List<Consultation> historique = rechercherConsultationsParPatient(patientId);

        System.out.println("\n=== HISTORIQUE PATIENT : " + patient.getPrenom() + " " + patient.getNom() + " ===");
        if (historique.isEmpty()) {
            System.out.println("  Aucune consultation enregistree pour ce patient.");
            return;
        }

        historique.stream()
                .sorted((c1, c2) -> c2.getDateHeure().compareTo(c1.getDateHeure()))
                .forEach(Consultation::afficher);
    }

    public void afficherTableauDeBord() {
        LocalDate aujourd_hui = LocalDate.now();

        long nbConsAujourdhui = consultations.stream()
                .filter(c -> c.getDateHeure().toLocalDate().equals(aujourd_hui))
                .filter(c -> c.getStatut() != Consultation.Statut.ANNULEE)
                .count();

        double revenusJour = calculerRevenusDuJour();

        String sep = "=".repeat(52);
        System.out.println(sep);
        System.out.println("        TABLEAU DE BORD - " + nom.toUpperCase());
        System.out.println(sep);
        System.out.printf("  Adresse            : %s%n", adresse);
        System.out.printf("  Patients inscrits  : %d%n", patients.size());
        System.out.printf("  Medecins           : %d%n", medecins.size());
        System.out.printf("  Infirmiers         : %d%n", infirmiers.size());
        System.out.printf("  Consultations tot. : %d%n", consultations.size());
        System.out.printf("  Consultations auj. : %d%n", nbConsAujourdhui);
        System.out.printf("  Ordonnances        : %d%n", ordonnances.size());
        System.out.printf("  Revenus du jour    : %.2f DA%n", revenusJour);

        Map<String, Long> chargeParMedecin = consultations.stream()
                .filter(c -> c.getDateHeure().toLocalDate().equals(aujourd_hui))
                .filter(c -> c.getStatut() != Consultation.Statut.ANNULEE)
                .collect(Collectors.groupingBy(
                        c -> "Dr. " + c.getMedecin().getPrenom() + " " + c.getMedecin().getNom(),
                        HashMap::new,
                        Collectors.counting()));

        if (!chargeParMedecin.isEmpty()) {
            System.out.println("  Charge medecins (auj.) :");
            chargeParMedecin.forEach((nomMedecin, total) -> System.out
                    .println("    - " + nomMedecin + " : " + total + " consultation(s)"));
        }
        System.out.println(sep);
    }

    public double calculerRevenusDuJour() {
        LocalDate aujourd_hui = LocalDate.now();
        return consultations.stream()
                .filter(c -> c.getDateHeure().toLocalDate().equals(aujourd_hui))
                .filter(c -> c.getStatut() == Consultation.Statut.TERMINEE)
                .mapToDouble(c -> c.getMedecin().getTarifConsultation())
                .sum();
    }

    public void afficherToutLePersonnel() {

        List<Personne> personnel = new ArrayList<>();
        personnel.addAll(medecins);
        personnel.addAll(infirmiers);

        System.out.println("\n=== PERSONNEL MEDICAL (" + personnel.size() + " membre(s)) ===\n");
        for (Personne p : personnel) {
            p.afficherProfil();
            System.out.println();
        }
    }

    public void listerPatients() {
        System.out.println("\n--- Liste des Patients (" + patients.size() + ") ---");
        if (patients.isEmpty()) {
            System.out.println("  (aucun patient enregistre)");
        } else {
            patients.forEach(p -> System.out.println("  " + p));
        }
    }

    public void listerMedecins() {
        System.out.println("\n--- Liste des Medecins (" + medecins.size() + ") ---");
        if (medecins.isEmpty()) {
            System.out.println("  (aucun medecin enregistre)");
        } else {
            medecins.forEach(m -> System.out.println("  " + m));
        }
    }

    public void listerInfirmiers() {
        System.out.println("\n--- Liste des Infirmiers (" + infirmiers.size() + ") ---");
        if (infirmiers.isEmpty()) {
            System.out.println("  (aucun infirmier enregistre)");
        } else {
            infirmiers.forEach(i -> System.out.println("  " + i));
        }
    }

    public void listerConsultations() {
        System.out.println("\n--- Liste des Consultations (" + consultations.size() + ") ---");
        if (consultations.isEmpty()) {
            System.out.println("  (aucune consultation enregistree)");
        } else {
            consultations.forEach(c -> {
                c.afficher();
                System.out.println();
            });
        }
    }

    public String getNom() {
        return nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public List<Patient> getPatients() {
        return new ArrayList<>(patients);
    }

    public List<Medecin> getMedecins() {
        return new ArrayList<>(medecins);
    }

    public List<Infirmier> getInfirmiers() {
        return new ArrayList<>(infirmiers);
    }

    public List<Consultation> getConsultations() {
        return new ArrayList<>(consultations);
    }

    public List<Ordonnance> getOrdonnances() {
        return new ArrayList<>(ordonnances);
    }
}
