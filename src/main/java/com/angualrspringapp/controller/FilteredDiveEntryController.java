package com.angualrspringapp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.angualrspringapp.beans.DiveEntry;
import com.angualrspringapp.beans.SearchFilter;
import com.angualrspringapp.service.DiveEntryService;

@Controller
@RequestMapping("/divesfilter")
public class FilteredDiveEntryController {
	
	@Autowired
	private DiveEntryService diveEntryService;
	
	@RequestMapping("divesfilterlist.json")
    public @ResponseBody List<DiveEntry> getDiveList() {
        return diveEntryService.getFilteredDives();
    }
	
	//http://examples.javacodegeeks.com/enterprise-java/servlet/get-all-request-parameters-in-servlet/
	@RequestMapping("searchDivesList.json" )
	public @ResponseBody List<DiveEntry> getFilteredDiveList(@RequestParam Map<String, String> p) {
		SearchFilter filter = new SearchFilter();
		if(p.get("minAirTemp") != null) filter.setMinAirTemp(Double.parseDouble(p.get("minAirTemp"))); 
		if(p.get("maxAirTemp") != null) filter.setMaxAirTemp(Double.parseDouble(p.get("maxAirTemp"))); 
		if(p.get("minWaterTemp") != null) filter.setMinWaterTemp(Double.parseDouble(p.get("minWaterTemp"))); 
		if(p.get("maxWaterTemp") != null) filter.setMaxWaterTemp(Double.parseDouble(p.get("maxWaterTemp"))); 
		if(p.get("minDepth") != null) filter.setMinDepth(Double.parseDouble(p.get("minDepth"))); 
		if(p.get("maxDepth") != null) filter.setMaxDepth(Double.parseDouble(p.get("maxDepth"))); 
		if(p.get("minTime") != null) filter.setMinTime(Double.parseDouble(p.get("minTime"))); 
		if(p.get("maxTime") != null) filter.setMaxTime(Double.parseDouble(p.get("maxTime"))); 
		filter.setDiverName(p.get("diverName"));
		filter.setLocation(p.get("location"));
		
        return diveEntryService.getSearchFilteredDives(filter);
    }

    @RequestMapping("/layout")
    public String getDivePartialPage(ModelMap modelMap) {
        return "divesfilter/layout";
    }
	

}
