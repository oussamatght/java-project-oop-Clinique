package modele;

import java.time.LocalDate;
import java.time.Period;


public abstract class Personne {

    
    private String identifiant;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String telephone;

    private static final int LONGUEUR_MIN_TEL = 8;

    

    public Personne(String identifiant, String nom, String prenom,
            LocalDate dateNaissance, String telephone) {
        this.identifiant = validerTexteObligatoire(identifiant, "identifiant");
        this.nom = validerTexteObligatoire(nom, "nom");
        this.prenom = validerTexteObligatoire(prenom, "prenom");
        this.dateNaissance = validerDateNaissance(dateNaissance);
        this.telephone = validerTelephone(telephone);
    }

    

    
    public int calculerAge() {
        return Period.between(dateNaissance, LocalDate.now()).getYears();
    }

    
    public abstract void afficherProfil();

    

    public String getIdentifiant() {
        return identifiant;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public String getTelephone() {
        return telephone;
    }

    

    public void setNom(String nom) {
        this.nom = validerTexteObligatoire(nom, "nom");
    }

    public void setPrenom(String prenom) {
        this.prenom = validerTexteObligatoire(prenom, "prenom");
    }

    public void setTelephone(String telephone) {
        this.telephone = validerTelephone(telephone);
    }

    private String validerTexteObligatoire(String valeur, String champ) {
        if (valeur == null || valeur.isBlank()) {
            throw new IllegalArgumentException("Le champ '" + champ + "' est obligatoire.");
        }
        return valeur.trim();
    }

    private LocalDate validerDateNaissance(LocalDate dateNaissance) {
        if (dateNaissance == null) {
            throw new IllegalArgumentException("La date de naissance est obligatoire.");
        }
        if (dateNaissance.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La date de naissance ne peut pas etre dans le futur.");
        }
        return dateNaissance;
    }

    private String validerTelephone(String telephone) {
        String tel = validerTexteObligatoire(telephone, "telephone").replaceAll("\\s+", "");
        if (tel.length() < LONGUEUR_MIN_TEL) {
            throw new IllegalArgumentException("Le numero de telephone est trop court.");
        }
        return tel;
    }

    

    @Override
    public String toString() {
        return String.format("Personne[id=%s | %s %s | age=%d]",
                identifiant, prenom, nom, calculerAge());
    }
}
