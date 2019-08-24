package com.sapient.feecalc.contoller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Controller
public class WebAppContoller {

    private String appMode;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public WebAppContoller(Environment environment){
        appMode = environment.getProperty("app-mode");
    }

    @RequestMapping("/login")
    public String index(Model model){
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info(username);
        model.addAttribute("datetime", new Date());
        model.addAttribute("username", "Kiran Kumar");
        model.addAttribute("projectname", "Emotion Recognition");

        model.addAttribute("mode", appMode);

        return "index";
    }


}
