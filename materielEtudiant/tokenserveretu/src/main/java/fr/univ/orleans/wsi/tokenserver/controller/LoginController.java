package fr.univ.orleans.wsi.tokenserver.controller;

import io.jsonwebtoken.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.http.HttpHeaders.*;
import static services.Constantes.AUTHORIZATION;

@RestController
public class LoginController {


    private final static String SECRET_KEY = " God is real. Except when declared integer";
    private final static String TOKEN_PREFIX = "Eeeeeeeeeeeeeeeeh MACARENA ... AHA ! o/ ";


    private static Map<String, String> loginPassword = new HashMap<>();
    private static Map<String, List<String>> loginRoles = new HashMap<>();
    static {
        loginPassword.put("fred","abcd");
        loginRoles.put("fred", Arrays.asList("user","admin","supersuperadmin"));
        loginPassword.put("yo","yo");
        loginRoles.put("yo", Arrays.asList("user", "scrummaster"));
        loginPassword.put("math","math");
        loginRoles.put("math", Arrays.asList("admin","ceinturenoire"));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String login, @RequestParam String password) {

        /* On check si le login est bien dans la base */
        if(loginPassword.containsKey(login)) {

            if(loginPassword.get(login).equals(password)) {

                JwtBuilder builder = Jwts.builder()
                        .signWith(SignatureAlgorithm.HS256,TOKEN_PREFIX)
                        .setSubject(login);

                String token = builder.compact();
                HttpHeaders header = new HttpHeaders();
                header.set("token",token);
                return new ResponseEntity<>("TOKEN généré...",header,HttpStatus.OK);

            } else { /* Le mot de passe est incorrect */

                return new ResponseEntity<>("Mot de passe incorrect",HttpStatus.FORBIDDEN);

            }
        } else { /* L'utilisateur n'existe pas */

            return new ResponseEntity<>("L'utilisateur n'existe pas",HttpStatus.FORBIDDEN);

        }

    }

    @GetMapping("/checkToken")
    public ResponseEntity<String> checkToken(@RequestHeader(AUTHORIZATION)String token) {

        String login;

        /* Decodage du login a partir du login */
        try {
            Jws<Claims> jwsClaims = Jwts.parser()
                    .setSigningKey(SECRET_KEY.getBytes())
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""));
            login = jwsClaims.getBody().getSubject();
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.FORBIDDEN);
        }

        if(loginPassword.containsKey(login)) {
            return new ResponseEntity<>("Token VALIDE",HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Token NON VALIDE",HttpStatus.OK);
        }

    }
}