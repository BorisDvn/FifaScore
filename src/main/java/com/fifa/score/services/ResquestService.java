package com.fifa.score.services;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class ResquestService {
    public connectToFootApi(String url){
       //String url = "https://api.football-data.org/v2/competitions/" + "2001" + "/teams?season=2020";

        // create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // create headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", "1856b1a8efff4d118603a3a889e93e66");
        //headers.set();

        // build the request
        HttpEntity request = new HttpEntity(headers);

        // make an HTTP GET request with headers
        ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                Map.class
        );

        // check response
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Request Successful.");
            // System.out.println(response.getBody());
        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }
    }
}
