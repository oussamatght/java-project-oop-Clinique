package modele;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Patient extends Personne {

    
    private String numeroSecuriteSociale;
    private String groupeSanguin;
    private List<String> antecedentsMedicaux;

    
    private static final List<String> GROUPES_VALIDES = Arrays.asList("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-");

    

    public Patient(String identifiant, String nom, String prenom,
            LocalDate dateNaissance, String telephone,
            String numeroSecuriteSociale, String groupeSanguin) {
        super(identifiant, nom, prenom, dateNaissance, telephone); 
        setNumeroSecuriteSociale(numeroSecuriteSociale);
        setGroupeSanguin(groupeSanguin); 
        this.antecedentsMedicaux = new ArrayList<>();
    }

    

    
    public void ajouterAntecedent(String antecedent) {
        if (antecedent != null && !antecedent.isBlank()) {
            antecedentsMedicaux.add(antecedent.trim());
        }
    }

    
    public void afficherDossier() {
        String sep = "=".repeat(52);
        System.out.println(sep);
        System.out.println("          DOSSIER MEDICAL DU PATIENT");
        System.out.println(sep);
        System.out.printf("  ID            : %s%n", getIdentifiant());
        System.out.printf("  Nom complet   : %s %s%n", getPrenom(), getNom());
        System.out.printf("  Age           : %d ans%n", calculerAge());
        System.out.printf("  Telephone     : %s%n", getTelephone());
        System.out.printf("  N Securite    : %s%n", numeroSecuriteSociale);
        System.out.printf("  Groupe sanguin: %s%n", groupeSanguin);
        System.out.println("  Antecedents medicaux :");
        if (antecedentsMedicaux.isEmpty()) {
            System.out.println("    (aucun antecedent enregistre)");
        } else {
            antecedentsMedicaux.forEach(a -> System.out.println("    - " + a));
        }
        System.out.println(sep);
    }

    
    @Override
    public void afficherProfil() {
        String sep = "-".repeat(45);
        System.out.println(sep);
        System.out.println("  [PATIENT]    " + getPrenom() + " " + getNom());
        System.out.println("  ID           : " + getIdentifiant());
        System.out.println("  Age          : " + calculerAge() + " ans");
        System.out.println("  Groupe sg.   : " + groupeSanguin);
        System.out.println("  Tel          : " + getTelephone());
        System.out.println(sep);
    }

    

    public String getNumeroSecuriteSociale() {
        return numeroSecuriteSociale;
    }

    public String getGroupeSanguin() {
        return groupeSanguin;
    }

    public List<String> getAntecedentsMedicaux() {
        return new ArrayList<>(antecedentsMedicaux);
    }

    

    public void setGroupeSanguin(String gs) {
        if (gs == null) {
            throw new IllegalArgumentException("Le groupe sanguin est obligatoire.");
        }
        if (!GROUPES_VALIDES.contains(gs)) {
            throw new IllegalArgumentException(
                    "Groupe sanguin invalide : '" + gs + "'. Valeurs acceptees : " + GROUPES_VALIDES);
        }
        this.groupeSanguin = gs;
    }

    public void setNumeroSecuriteSociale(String nss) {
        if (nss == null || nss.isBlank()) {
            throw new IllegalArgumentException("Le numero de securite sociale est obligatoire.");
        }
        this.numeroSecuriteSociale = nss.trim();
    }

    

    @Override
    public String toString() {
        return String.format("Patient{id='%s', nom='%s %s', groupeSanguin='%s'}",
                getIdentifiant(), getPrenom(), getNom(), groupeSanguin);
    }
}
