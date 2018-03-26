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


    private final static String SECRET_KEY = "secret";
    private final static String TOKEN_PREFIX = "tokePre";


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

                Claims claims = Jwts.claims().setSubject(login);

                JwtBuilder builder = Jwts.builder()
                        .setClaims(claims)
                        .setExpiration(new Date(System.currentTimeMillis() + 600000))
                        .signWith(SignatureAlgorithm.HS256, SECRET_KEY);

                String token = builder.compact();
                HttpHeaders header = new HttpHeaders();
                header.set("Authorization",token);
                return new ResponseEntity<>("TOKEN généré...",header,HttpStatus.OK);

            } else { /* Le mot de passe est incorrect */

                return new ResponseEntity<>("Mot de passe incorrect",HttpStatus.UNAUTHORIZED);

            }
        } else { /* L'utilisateur n'existe pas */

            return new ResponseEntity<>("L'utilisateur n'existe pas",HttpStatus.UNAUTHORIZED);

        }

    }

    @GetMapping("/checkToken")
    public ResponseEntity<String> checkToken(@RequestHeader(AUTHORIZATION)String token) {
        //TODO
        if(!token.equals(null)){
            try{
                Jws<Claims> jwsClaims = Jwts.parser()
                        .setSigningKey(SECRET_KEY)
                        .parseClaimsJws(token.replace(TOKEN_PREFIX,""));
                String login = jwsClaims.getBody().getSubject();
                if(loginPassword.containsKey(login)){
                    return new ResponseEntity<>("le token est valide",HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<>("Token associé à aucun compte",HttpStatus.UNAUTHORIZED);
                }
            }catch(ExpiredJwtException e){
                return new ResponseEntity<>("le token a expiré",HttpStatus.UNAUTHORIZED);
            }
        }
        else{
            return new ResponseEntity<>("Un token ??? où ça ?",HttpStatus.UNAUTHORIZED);
        }
    }
}