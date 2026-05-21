package modele;

import exception.OrdonnanceInvalideException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Ordonnance {

    private static int compteur = 1; 

    
    private String idOrdonnance;
    private Consultation consultation;
    private List<Medicament> medicaments;
    private LocalDate dateEmission;

    

    
    public Ordonnance(Consultation consultation) throws OrdonnanceInvalideException {
        if (consultation == null) {
            throw new OrdonnanceInvalideException(
                    "Impossible de creer une ordonnance : consultation inexistante.");
        }
        
        if (consultation.getDiagnostic() == null || consultation.getDiagnostic().isBlank()) {
            throw new OrdonnanceInvalideException(
                    "Impossible de creer une ordonnance : aucun diagnostic associe a cette consultation.");
        }
        this.idOrdonnance = String.format("ORD-%03d", compteur++);
        this.consultation = consultation;
        this.medicaments = new ArrayList<>();
        this.dateEmission = LocalDate.now();
    }

    

    
    public void ajouterMedicament(Medicament med) {
        if (med == null) {
            throw new IllegalArgumentException("Le medicament a ajouter ne peut pas etre null.");
        }
        medicaments.add(med);
    }

    
    public void afficher() throws OrdonnanceInvalideException {
        if (medicaments.isEmpty()) {
            throw new OrdonnanceInvalideException(
                    "L'ordonnance " + idOrdonnance + " est invalide : aucun medicament prescrit.");
        }

        String sep = "=".repeat(54);
        System.out.println(sep);
        System.out.println("             ORDONNANCE MEDICALE");
        System.out.println(sep);
        System.out.printf("  ID Ordonnance : %s%n", idOrdonnance);
        System.out.printf("  Patient       : %s %s%n", consultation.getPatient().getPrenom(),
                consultation.getPatient().getNom());
        System.out.printf("  Medecin       : Dr. %s %s%n", consultation.getMedecin().getPrenom(),
                consultation.getMedecin().getNom());
        System.out.printf("  Date emission : %s%n", dateEmission);
        System.out.printf("  Diagnostic    : %s%n", consultation.getDiagnostic());
        System.out.println("-".repeat(54));
        System.out.println("  Medicaments prescrits :");

        for (int i = 0; i < medicaments.size(); i++) {
            Medicament m = medicaments.get(i);
            System.out.printf("   %d. %-25s | %s%n", i + 1, m.getNom(), m.getDosage());
            System.out.printf("      Duree du traitement : %d jours%n", m.getDureeTraitement());
            if (!m.getContreIndications().isBlank()) {
                System.out.printf("      Contre-indications  : %s%n", m.getContreIndications());
            }
        }
        System.out.println(sep);
    }

    

    public String getIdOrdonnance() {
        return idOrdonnance;
    }

    public Consultation getConsultation() {
        return consultation;
    }

    public List<Medicament> getMedicaments() {
        return new ArrayList<>(medicaments);
    }

    public LocalDate getDateEmission() {
        return dateEmission;
    }

    

    @Override
    public String toString() {
        return String.format("Ordonnance{id='%s', patient='%s', nb_medicaments=%d}",
                idOrdonnance, consultation.getPatient().getNom(), medicaments.size());
    }
}
