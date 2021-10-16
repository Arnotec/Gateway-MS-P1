package org.arnotec.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CircuitBreakerController {

    @GetMapping(path = "/defaultCovidCountries")
    public Map<String, String> defaultCovidCountries() {

        Map<String, String> data = new HashMap<>();
        data.put("message", "Default Covid-19 countries");
        data.put("Covid Countries", "USA, France, Cameroon, Canada, ...");
        return data;
    }

}
