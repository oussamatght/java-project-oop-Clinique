package modele;

import java.time.LocalDate;


public class Medecin extends PersonnelMedical {

    
    private String specialite;
    private String numeroOrdre;
    private double tarifConsultation;

    

    public Medecin(String identifiant, String nom, String prenom,
            LocalDate dateNaissance, String telephone,
            String matricule, LocalDate dateEmbauche, double salaire,
            String specialite, String numeroOrdre, double tarifConsultation) {
        
        super(identifiant, nom, prenom, dateNaissance, telephone,
                matricule, dateEmbauche, salaire);
        setSpecialite(specialite);
        setNumeroOrdre(numeroOrdre);
        setTarifConsultation(tarifConsultation); 
    }

    

    
    @Override
    public void afficherProfil() {
        String sep = "-".repeat(48);
        System.out.println(sep);
        System.out.println("  [MEDECIN]   Dr. " + getPrenom() + " " + getNom());
        System.out.println("  ID          : " + getIdentifiant());
        System.out.println("  Specialite  : " + specialite);
        System.out.println("  N Ordre     : " + numeroOrdre);
        System.out.printf("  Tarif       : %.2f DA%n", tarifConsultation);
        System.out.println("  Anciennete  : " + calculerAnciennete() + " an(s)");
        System.out.println("  Age         : " + calculerAge() + " ans");
        System.out.println("  Tel         : " + getTelephone());
        System.out.println(sep);
    }

    

    public String getSpecialite() {
        return specialite;
    }

    public String getNumeroOrdre() {
        return numeroOrdre;
    }

    public double getTarifConsultation() {
        return tarifConsultation;
    }

    

    
    public void setTarifConsultation(double tarif) {
        if (tarif <= 0) {
            throw new IllegalArgumentException(
                    "Le tarif de consultation doit etre superieur a 0.");
        }
        this.tarifConsultation = tarif;
    }

    public void setSpecialite(String specialite) {
        if (specialite == null || specialite.isBlank()) {
            throw new IllegalArgumentException("La specialite du medecin est obligatoire.");
        }
        this.specialite = specialite.trim();
    }

    public void setNumeroOrdre(String numeroOrdre) {
        if (numeroOrdre == null || numeroOrdre.isBlank()) {
            throw new IllegalArgumentException("Le numero d'ordre est obligatoire.");
        }
        this.numeroOrdre = numeroOrdre.trim();
    }

    

    @Override
    public String toString() {
        return String.format("Medecin{id='%s', Dr. %s %s, spec='%s', tarif=%.2f DA}",
                getIdentifiant(), getPrenom(), getNom(), specialite, tarifConsultation);
    }
}
