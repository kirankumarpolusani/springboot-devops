package com.sapient.feecalc.contoller;

import com.sapient.feecalc.dto.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class RestEndPoints {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.dummyurl}")
    private String dummyUrl;

    @RequestMapping("/test")
    public String testRestTemplate() {
        ResponseEntity<String> res = restTemplate.exchange(dummyUrl, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class);
        return res.getBody();
    }

    @RequestMapping("/emps")
    public List<Emp> test1Method() {
        ResponseEntity<List<Emp>> res = restTemplate.exchange("http://localhost:3000/employees", HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), new ParameterizedTypeReference<List<Emp>>() {
        });
        return res.getBody();
    }
}
