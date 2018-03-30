package modele;

import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

public class Contact implements Serializable {

    private String type;
    private String adresse;
    private Personne personne;

    public Contact() {

    }

    public Contact(String type, String adresse, Personne personne) {
        this.type = type;
        this.adresse = adresse;
        this.personne = personne;
    }

    public String getType() {
        return this.type;
    }

    public String getAdresse() {
        return this.adresse;
    }

    @XmlTransient
    public Personne getPersonne() {
        return this.personne;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

}
