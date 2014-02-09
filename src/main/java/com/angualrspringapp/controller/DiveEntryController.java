package com.angualrspringapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.angualrspringapp.beans.DiveEntry;
import com.angualrspringapp.service.DiveEntryService;

@Controller
@RequestMapping("/dives")
public class DiveEntryController {
	
	@Autowired
	private DiveEntryService diveEntryService;
	
	@RequestMapping("diveslist.json")
    public @ResponseBody List<DiveEntry> getDiveList() {
        return diveEntryService.getAllDives();
    }

    @RequestMapping(value = "/addDive", method = RequestMethod.POST)
    public @ResponseBody void addDive(@RequestBody DiveEntry dive) {
    	System.out.println(dive.toString());
        diveEntryService.addDive(dive);
    }

    @RequestMapping(value = "/updateDive", method = RequestMethod.PUT)
    public @ResponseBody void updateDive(@RequestBody DiveEntry dive) {
        diveEntryService.updateDive(dive);
    }

//    @RequestMapping(value = "/removeDive/{id}", method = RequestMethod.DELETE)
//    public @ResponseBody void removeDive(@PathVariable("id") Long id) {
//        diveEntryService.deleteDiveById(id);
//    }
    
    @RequestMapping(value = "/removeDive/{id}", method = RequestMethod.DELETE)
    public @ResponseBody void removeDive(@PathVariable("id") String id) {
        diveEntryService.deleteDiveById(id);
    }

    @RequestMapping(value = "/removeAllDives", method = RequestMethod.DELETE)
    public @ResponseBody void removeAllDives() {
        diveEntryService.deleteAllDives();
    }

    @RequestMapping("/layout")
    public String getDivePartialPage(ModelMap modelMap) {
        return "dives/layout";
    }
    
    @RequestMapping("/locationsList.json")
    public @ResponseBody List<String> getLocsList() {
    	
    	List<String> locations;
    	
    	locations = diveEntryService.getAllLocations();
    	
        return locations;
    }
	

}
