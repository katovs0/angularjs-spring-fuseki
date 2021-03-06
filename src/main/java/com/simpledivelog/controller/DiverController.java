package com.simpledivelog.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.simpledivelog.service.DiverService;

@Controller
@RequestMapping("/divers")
public class DiverController {

    @Autowired
    private DiverService diverService;

    @RequestMapping("/diverlist.json")
    public @ResponseBody List<String> getDiverList() {
    	System.out.println("GETTING DIVERLIST");
        return diverService.getAllDivers();
    }

    @RequestMapping(value = "/addDiver/{diver}", method = RequestMethod.POST)
    public @ResponseBody void addDiver(@PathVariable("diver") String diver) {
        diverService.addDiver(diver);
    }

    @RequestMapping(value = "/removeDiver/{diver}", method = RequestMethod.DELETE)
    public @ResponseBody void removeDiver(@PathVariable("diver") String diver) {
        diverService.deleteDiver(diver);
    }

    @RequestMapping(value = "/removeAllDivers", method = RequestMethod.DELETE)
    public @ResponseBody void removeAllDivers() {
        diverService.deleteAll();
    }

    @RequestMapping("/layout")
    public String getDiverPartialPage() {
        return "divers/layout";
    }
    
    @RequestMapping("/locationsList.json")
    public @ResponseBody List<String> getLocsList() {
    	
    	List<String> locations = new ArrayList();
    	
//    	locations = diverService.getAllLocations();
    	
        return locations;
    }
    
    
//    @RequestMapping(value = "/autoCompleteCity", method = RequestMethod.POST)
//    public @ResponseBody void autoCompleteCity() {
//        diverService.autoCompleteCity();
//    }
}
