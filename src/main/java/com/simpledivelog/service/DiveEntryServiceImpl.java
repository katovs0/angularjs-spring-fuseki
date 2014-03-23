package com.simpledivelog.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.simpledivelog.beans.DiveEntry;
import com.simpledivelog.beans.SearchFilter;
import com.simpledivelog.external.FusekiAdapter;

@Service("diveEntryService")
public class DiveEntryServiceImpl implements DiveEntryService {
//    private static List<DiveEntry> divesList = new ArrayList<DiveEntry>();
//    private static Long id = 0L;
	
	private static Logger LOG = LoggerFactory.getLogger(DiveEntryServiceImpl.class);

    @Override
    public List<String> getAllLocations() {
    	
    	return FusekiAdapter.getAllLocations();
    	
    }
    
    @Override
    public List<DiveEntry> getAllDives() {
    	List<DiveEntry> divesList = new ArrayList<DiveEntry>();    	
    	LOG.info("GETTING ALL DIVES");

    	divesList = FusekiAdapter.getAllDives();
        return divesList;
    }

    @Override
    public void addDive(DiveEntry diveEntry) {
    	LOG.info("ADD DIVE: " + diveEntry.toString());

        FusekiAdapter.storeDiveEntry(diveEntry);
    }

    @Override
    public void deleteDiveById(String id) {
    	LOG.info("REMOVE DIVE: " + id);
    	
    	boolean success = FusekiAdapter.removeDiveById(id);

        if(success) LOG.info("SUCCESSFULLY ON REMOVED DIVE: " + id);
        else LOG.info("COULD NOT REMOVE DIVE: " + id);
        
    }
    
    @Override
    public void deleteAllDives() {
    	LOG.info("REMOVE ALL DIVES");
    	
    	boolean success = FusekiAdapter.removeAllDives();

        if(success) LOG.info("SUCCESSFULLY ON REMOVING ALL DIVES");
        else LOG.info("COULD NOT REMOVE ALL DIVES");
    }

    @Override
    public void updateDive(DiveEntry diveEntry) {
    	LOG.info("UPDATING DIVE [" + diveEntry.getIdString() + "]");
    	
        DiveEntry foundDiveEntry = findDiveEntryById(diveEntry.getIdString());
        if (foundDiveEntry != null) {
        	deleteDiveById(diveEntry.getIdString());
        	addDive(diveEntry);
        }
    }
    
    private DiveEntry findDiveEntryById(String id) {
    	
    	return FusekiAdapter.getDiveById(id);

    }
    
    //Queries both local rdf store and external data source to filter out the data
    public List<DiveEntry> getFilteredDives() {
    	List<DiveEntry> divesList = new ArrayList<DiveEntry>();    	
    	LOG.info("GETTING ALL FILTERED DIVES");

    	divesList = FusekiAdapter.getFilteredDives();
        return divesList;
    }
    
    public List<DiveEntry> getSearchFilteredDives(SearchFilter filter) {
    	List<DiveEntry> divesList = new ArrayList<DiveEntry>();    	
    	LOG.info("GETTING FILTERED DIVES");

    	divesList = FusekiAdapter.getSearchFilteredDives(filter);
        return divesList;
    }




//  @Override
//  public DiveEntry getDiveById(Long id) {
//      return findDiveEntryById(id);
//  }
    
//  private DiveEntry findDiveEntryById(Long id) {
//      for (DiveEntry DiveEntry : divesList) {
//          if (DiveEntry.getId() == id) {
//              return DiveEntry;
//          }
//      }
//
//      return null;
//  }  
    
//  @Override
//  public void deleteDiveById(Long id) {
//  	System.out.println("REMOVE DIVE" + id);
//  	
//      DiveEntry foundDiveEntry = findDiveEntryById(id);
//      if (foundDiveEntry != null) {
//          divesList.remove(foundDiveEntry);
//          id--;
//      }
//  }
    
}
