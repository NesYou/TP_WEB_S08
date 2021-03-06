package service;

import exceptions.NameNotFoundException;
import modele.Personne;

import java.util.Collection;
import java.util.Date;

public interface IAnnuaire {

    Personne getPersonneByName(String name);

    String searchTelephone(String nom) throws NameNotFoundException;

    void addPersonne(Personne p);

    void addPersonneList(String nom, String prenom, String tel, Date date);

    void addContact(String nom, String type, String adresse);

    Collection<String> getAllNom();

}
