package fr.miage.orleans.wsi.demogreetingsm1.controller;

import fr.miage.orleans.wsi.demogreetingsm1.modele.Sondage;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping(value = "/rest")
public class SondageController {

    static Map<Long,Sondage> bd;

    static {
        bd = new HashMap<>();
        Sondage sondage1 = new Sondage("xxx?", Arrays.asList("x1","x2","x3"));
        Sondage sondage2 = new Sondage("yyy?", Arrays.asList("y1","y2","y3","y4"));
        bd.put(sondage1.getId(),sondage1);
        bd.put(sondage2.getId(),sondage2);
    }

    @RequestMapping(value = "/sondages",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Sondage> getAllSondages() {
        return bd.values();
    }

    @RequestMapping(value = "/sondages/{id}",method = RequestMethod.GET)
    public Sondage getSondageById(@PathVariable long id) {
        return bd.get(id);
    }

    @RequestMapping(value = "/sondage", method = RequestMethod.POST)
    public ResponseEntity<String> creerSondage(@RequestParam String question, @RequestParam List<String> propositions) {
        Sondage nouveauSondage = new Sondage(question,propositions);
        bd.put(nouveauSondage.getId(),nouveauSondage);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/{id}")
                .buildAndExpand(nouveauSondage.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

}