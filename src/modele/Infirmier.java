package modele;

import java.time.LocalDate;


public class Infirmier extends PersonnelMedical {

    
    private String service;
    private String grade;

    

    public Infirmier(String identifiant, String nom, String prenom,
            LocalDate dateNaissance, String telephone,
            String matricule, LocalDate dateEmbauche, double salaire,
            String service, String grade) {
        
        super(identifiant, nom, prenom, dateNaissance, telephone,
                matricule, dateEmbauche, salaire);
        setService(service);
        setGrade(grade);
    }

    

    
    @Override
    public void afficherProfil() {
        String sep = "-".repeat(48);
        System.out.println(sep);
        System.out.println("  [INFIRMIER] " + getPrenom() + " " + getNom());
        System.out.println("  ID          : " + getIdentifiant());
        System.out.println("  Service     : " + service);
        System.out.println("  Grade       : " + grade);
        System.out.println("  Anciennete  : " + calculerAnciennete() + " an(s)");
        System.out.println("  Age         : " + calculerAge() + " ans");
        System.out.println("  Tel         : " + getTelephone());
        System.out.println(sep);
    }

    

    public String getService() {
        return service;
    }

    public String getGrade() {
        return grade;
    }

    public void setService(String service) {
        if (service == null || service.isBlank()) {
            throw new IllegalArgumentException("Le service de l'infirmier est obligatoire.");
        }
        this.service = service.trim();
    }

    public void setGrade(String grade) {
        if (grade == null || grade.isBlank()) {
            throw new IllegalArgumentException("Le grade de l'infirmier est obligatoire.");
        }
        this.grade = grade.trim();
    }

    

    @Override
    public String toString() {
        return String.format("Infirmier{id='%s', nom='%s %s', service='%s', grade='%s'}",
                getIdentifiant(), getPrenom(), getNom(), service, grade);
    }
}
