package service;

import exceptions.NameNotFoundException;
import modele.Contact;
import modele.Personne;

import javax.jws.WebService;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

@WebService
public class Annuaire implements IAnnuaire, Serializable {

    private static HashMap<String,Personne> annuaire;
    static {
        annuaire = new HashMap<String,Personne>();

        Personne fred = new Personne("moal", "frederic", new Date());
        fred.addContact(new Contact("tel", "0238000000", fred));
        fred.addContact(new Contact("mail", "frederic.moal@univ-orleans.fr", fred));
        annuaire.put(fred.getNom(), fred);

        final Personne matthieu = new Personne("exbrayat", "matthieu", new Date());
        matthieu.addContact(new Contact("tel","0202020202", matthieu));
        annuaire.put(matthieu.getNom(), matthieu);

        final Personne yohan = new Personne("yohan", "boichut", new Date());
        yohan.addContact(new Contact("mail","yohan.boichut@univ-orleans.fr", yohan));
        annuaire.put(yohan.getNom(), yohan);
    };

    public Personne getPersonneByName(String name) {
        return annuaire.get(name);
    }

    public String searchTelephone(String nom) throws NameNotFoundException {
        Personne p =  annuaire.get(nom);
        if (p==null) {
            //return "Pas trouve";
            throw new NameNotFoundException();
        }
        for(Contact leContact : p.getContacts()) {
            if(leContact.getType().equals("tel")) {
                return leContact.toString();
            }
        }
        return "Pas de téléphone";
    }

    public void addPersonne(Personne p) {
        annuaire.put(p.getNom(), p);
    }


    public void addContact(String nom, String type, String adresse) {

    }

    public void addPersonneList(String nom, String prenom, String tel, Date date) {
        Personne p = new Personne(nom,prenom,date);
        p.addContact(new Contact("tel",tel,p));
        annuaire.put(p.getNom(), p);
    }

    public Collection<String> getAllNom() {
        return annuaire.keySet();
    }
}