package modele;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Consultation {

    
    public enum Statut {
        PLANIFIEE,
        EN_COURS,
        TERMINEE,
        ANNULEE
    }

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static int compteur = 1; 

    
    private String idConsultation;
    private Patient patient;
    private Medecin medecin;
    private LocalDateTime dateHeure;
    private String diagnostic;
    private String notesCliniques;
    private Statut statut;

    public Consultation(Patient patient, Medecin medecin,
            LocalDateTime dateHeure, String diagnostic) {
        this(patient, medecin, dateHeure, diagnostic, "");
    }

    public Consultation(Patient patient, Medecin medecin,
            LocalDateTime dateHeure, String diagnostic, String notesCliniques) {
        if (patient == null) {
            throw new IllegalArgumentException("Le patient de la consultation est obligatoire.");
        }
        if (medecin == null) {
            throw new IllegalArgumentException("Le medecin de la consultation est obligatoire.");
        }
        if (dateHeure == null) {
            throw new IllegalArgumentException("La date et l'heure de la consultation sont obligatoires.");
        }
        this.idConsultation = String.format("CONS-%03d", compteur++);
        this.patient = patient;
        this.medecin = medecin;
        this.dateHeure = dateHeure;
        this.diagnostic = (diagnostic == null) ? "" : diagnostic;
        this.notesCliniques = (notesCliniques == null) ? "" : notesCliniques;
        this.statut = Statut.PLANIFIEE;
    }

    

    
    public void afficher() {
        String sep = "-".repeat(52);
        System.out.println(sep);
        System.out.printf("  ID Consultation : %s%n", idConsultation);
        System.out.printf("  Patient         : %s %s%n", patient.getPrenom(), patient.getNom());
        System.out.printf("  Medecin         : Dr. %s %s%n", medecin.getPrenom(), medecin.getNom());
        System.out.printf("  Date / Heure    : %s%n", dateHeure.format(FMT));
        System.out.printf("  Diagnostic      : %s%n", diagnostic.isBlank() ? "(en attente)" : diagnostic);
        System.out.printf("  Notes cliniques : %s%n", notesCliniques.isBlank() ? "(aucune)" : notesCliniques);
        System.out.printf("  Statut          : %s%n", statut);
        System.out.println(sep);
    }

    

    public String getIdConsultation() {
        return idConsultation;
    }

    public Patient getPatient() {
        return patient;
    }

    public Medecin getMedecin() {
        return medecin;
    }

    public LocalDateTime getDateHeure() {
        return dateHeure;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public String getNotesCliniques() {
        return notesCliniques;
    }

    public Statut getStatut() {
        return statut;
    }

    

    public void setStatut(Statut statut) {
        if (statut == null) {
            throw new IllegalArgumentException("Le statut de consultation est obligatoire.");
        }
        this.statut = statut;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = (diagnostic == null) ? "" : diagnostic.trim();
    }

    public void setNotesCliniques(String notes) {
        this.notesCliniques = (notes == null) ? "" : notes.trim();
    }

    

    @Override
    public String toString() {
        return String.format("Consultation{id='%s', patient='%s', medecin='Dr. %s', date='%s', statut=%s}",
                idConsultation, patient.getNom(), medecin.getNom(),
                dateHeure.format(FMT), statut);
    }
}
