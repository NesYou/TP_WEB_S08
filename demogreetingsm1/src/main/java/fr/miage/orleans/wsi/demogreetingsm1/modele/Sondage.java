package fr.miage.orleans.wsi.demogreetingsm1.modele;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Sondage {
    private static long IDENTIFIANTS = 0;
    private long id;
    private String question;
    private List<String> reponses;
    private List<Vote> votes;

    public Sondage() {
        this("");
    }

    public Sondage(String question) {
        this(question,new ArrayList<>());
    }

    public Sondage(String question, List<String> reponses) {
        this.id = IDENTIFIANTS++;
        this.question = question;
        this.reponses = reponses;
        this.votes = new ArrayList<>();
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<String> getReponses() {
        return reponses;
    }

    public void setReponses(List<String> reponses) {
        this.reponses = reponses;
    }

    public void addReponses(String... rep) {
        for(String r: rep) {
            reponses.add(r);
        }
    }

    public void voter(Vote vote) {
        if (reponses.contains(vote.getChoix())) {
            votes.add(vote);
        }
    }
}