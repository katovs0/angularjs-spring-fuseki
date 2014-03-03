package com.angualrspringapp.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.jena.web.DatasetAdapter;
import org.apache.jena.web.DatasetGraphAccessorHTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.angualrspringapp.beans.DiveEntry;
import com.angualrspringapp.external.DiveEntryResultSetMapper;
import com.angualrspringapp.external.FusekiAdapter;
import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;

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
