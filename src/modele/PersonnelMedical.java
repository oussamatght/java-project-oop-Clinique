package modele;

import java.time.LocalDate;
import java.time.Period;


public abstract class PersonnelMedical extends Personne {

    
    private String matricule;
    private LocalDate dateEmbauche;
    private double salaire;

    

    public PersonnelMedical(String identifiant, String nom, String prenom,
            LocalDate dateNaissance, String telephone,
            String matricule, LocalDate dateEmbauche, double salaire) {
        super(identifiant, nom, prenom, dateNaissance, telephone); 
        this.matricule = validerTexteObligatoire(matricule, "matricule");
        this.dateEmbauche = validerDateEmbauche(dateEmbauche);
        setSalaire(salaire); 
    }

    

    
    public int calculerAnciennete() {
        return Period.between(dateEmbauche, LocalDate.now()).getYears();
    }

    

    public String getMatricule() {
        return matricule;
    }

    public LocalDate getDateEmbauche() {
        return dateEmbauche;
    }

    public double getSalaire() {
        return salaire;
    }

    

    
    public void setSalaire(double salaire) {
        if (salaire < 0) {
            throw new IllegalArgumentException("Le salaire ne peut pas etre negatif.");
        }
        this.salaire = salaire;
    }

    public void setMatricule(String matricule) {
        this.matricule = validerTexteObligatoire(matricule, "matricule");
    }

    private String validerTexteObligatoire(String valeur, String champ) {
        if (valeur == null || valeur.isBlank()) {
            throw new IllegalArgumentException("Le champ '" + champ + "' est obligatoire.");
        }
        return valeur.trim();
    }

    private LocalDate validerDateEmbauche(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("La date d'embauche est obligatoire.");
        }
        if (date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La date d'embauche ne peut pas etre dans le futur.");
        }
        return date;
    }

    

    @Override
    public String toString() {
        return String.format("PersonnelMedical{mat='%s', nom='%s %s', salaire=%.2f DA}",
                matricule, getPrenom(), getNom(), salaire);
    }
}
