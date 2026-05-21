package modele;


public class Medicament {

    
    private String nom;
    private String dosage;
    private int dureeTraitement; 
    private String contreIndications;

    

    public Medicament(String nom, String dosage, int dureeTraitement, String contreIndications) {
        setNom(nom);
        setDosage(dosage);
        setDureeTraitement(dureeTraitement);
        setContreIndications(contreIndications);
    }

    

    public String getNom() {
        return nom;
    }

    public String getDosage() {
        return dosage;
    }

    public int getDureeTraitement() {
        return dureeTraitement;
    }

    public String getContreIndications() {
        return contreIndications;
    }

    

    public void setNom(String nom) {
        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("Le nom du medicament est obligatoire.");
        }
        this.nom = nom.trim();
    }

    public void setDosage(String dosage) {
        if (dosage == null || dosage.isBlank()) {
            throw new IllegalArgumentException("Le dosage/posologie est obligatoire.");
        }
        this.dosage = dosage.trim();
    }

    public void setDureeTraitement(int duree) {
        if (duree <= 0) {
            throw new IllegalArgumentException("La duree du traitement doit etre > 0 jour.");
        }
        this.dureeTraitement = duree;
    }

    public void setContreIndications(String contreInd) {
        this.contreIndications = (contreInd == null) ? "" : contreInd.trim();
    }

    

    @Override
    public String toString() {
        return String.format("Medicament{nom='%s', dosage='%s', duree=%d j, contre-ind='%s'}",
                nom, dosage, dureeTraitement,
                contreIndications.isBlank() ? "aucune" : contreIndications);
    }
}
