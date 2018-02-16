package fr.miage.orleans.wsi.demogreetingsm1.modele;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Vote {
    private String votant;
    private String choix;
    private long id;

    private static long IDENTIFIANTS = 0;

    public Vote() {
        this(null,null);
    }
    public Vote(String votant, String choix) {
        this.id = this.IDENTIFIANTS++;
        this.votant = votant;
        this.choix = choix;
    }
    public String getVotant() {
        return votant;
    }
    public void setVotant(String votant) {
        this.votant = votant;
    }
    public String getChoix() {
        return choix;
    }
    public void setChoix(String choix) {
        this.choix = choix;
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vote)) return false;
        Vote vote = (Vote) o;
        return !(votant != null ? !votant.equals(vote.votant) : vote.votant != null);
    }
    @Override
    public int hashCode() {
        return votant != null ? votant.hashCode() : 0;
    }
}