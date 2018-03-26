package fr.miage.orleans.applisondages.filters;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

import static services.Constantes.TOKEN;

public class SecurityInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(SecurityInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        RestTemplate restTemplate1 = new RestTemplate();
        String URL_SONDAGE="http://localhost:8000/checkToken";
        ResponseEntity<String> resultat2 =null;

        String token= httpServletRequest.getHeader("Authorization");
        //on verifie que le token existe bien
        if (token!=null){
            // headers
            HttpHeaders httpHeaders2 = new HttpHeaders();
            httpHeaders2.set("Authorization", token);

            // body
            MultiValueMap<String, String> map2= new LinkedMultiValueMap<String, String>();

            // headers + body
            HttpEntity<MultiValueMap<String,String>> httpEntity2 = new HttpEntity<MultiValueMap<String,String>>(map2 , httpHeaders2);

            // REST call for Location
            resultat2 = restTemplate1.exchange(URL_SONDAGE, HttpMethod.GET, httpEntity2, String.class);
            if (resultat2.getStatusCode().value()!= HttpStatus.OK.value()){
                httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
                return false;
            }
            return true;

        }
        return false;

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
