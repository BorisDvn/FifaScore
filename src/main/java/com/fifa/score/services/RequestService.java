package com.fifa.score.services;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@Service
public class RequestService {
    public ResponseEntity<Map> connectToFootApi(String url) {
        // create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // create headers
        HttpHeaders headers = new HttpHeaders();
        // with add or with headers.set();
        headers.add("X-Auth-Token", "1856b1a8efff4d118603a3a889e93e66");

        // build the request
        HttpEntity<?> request = new HttpEntity<>(headers);

        // make an HTTP GET request with headers
        try {
            Objects.requireNonNull(url);
            ResponseEntity<Map> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    Map.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.hasBody()) {
                //System.out.println("Request Successful.");
                return response;
            } else {
                System.out.println("Request Failed");
                return null;
            }
        } catch (Exception e) {
            System.out.println("RequestService: "+e.toString());
            return null;
        }
    }
}
