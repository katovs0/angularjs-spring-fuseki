package com.angualrspringapp.external;

import java.util.List;

import com.angualrspringapp.beans.DiveEntry;
import com.angualrspringapp.beans.SearchFilter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

public interface ExternalSourceAdapter {
	
	public Model writeModelToDefault (Model m);
	
	public Resource storeDiveEntry (DiveEntry diveEntry);
	
	public List<DiveEntry> getAllDives();
		
	public DiveEntry getDiveById(String id);
	
	public DiveEntry getEnrichedDiveById(String id);
	
	public List<String> getAllLocations();	
	
	public boolean removeDiveById(String id);	
	
	public boolean removeAllDives();
	
	public void storeDiver(String diver);
			
	public List<String> getAllDivers();

	public boolean removeDiver(String diver);
	
	public boolean removeAllDivers();

	public List<DiveEntry> getFilteredDives();

	public List<DiveEntry> getSearchFilteredDives(SearchFilter filter);
}
