package com.thecaveman.covidtracker.controllers;

import com.thecaveman.covidtracker.models.LocationState;
import com.thecaveman.covidtracker.services.CovidDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller // this > RestController KEKW
public class HomeController {

    @Autowired // wired from CovidDataService.class
    CovidDataService covidDataService;

    @GetMapping("/")  // localhost:8080/
    public String home(Model model){
        List<LocationState> allStates =  covidDataService.getAllStates();
        int totalReportedCases = allStates.stream().mapToInt( state -> state.getLatestCases()).sum();
        int totalNewCases = allStates.stream().mapToInt( state -> state.getDiffFromLastDay()).sum();
        model.addAttribute("locationStates", allStates);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);
        return "home"; // return home.html from template **return string must match the file!!** and we can do it because of Thymeleaf god
    }
}
