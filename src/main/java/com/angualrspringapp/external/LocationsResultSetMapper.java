package com.angualrspringapp.external;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.angualrspringapp.beans.DiveEntry;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

public class LocationsResultSetMapper {
	
	private static Logger LOG = LoggerFactory.getLogger(LocationsResultSetMapper.class);
//	
	private static final LocationsResultSetMapper INSTANCE = new LocationsResultSetMapper();
	private LocationsResultSetMapper() {}
	
	public static LocationsResultSetMapper getInstance() {
        return INSTANCE;
    }
//	public DiveEntry mapDiveEntry(String id, ResultSet rs) {
//		LOG.info("Mapping a DiveEntry...");
//		DiveEntry dive = new DiveEntry();
//		
//		dive.setIdString(id);
//		
//		while(rs.hasNext()){
//
//			 QuerySolution binding = rs.nextSolution();                     
////			 System.out.println(binding.get("p"));
////			 System.out.println(binding.get("?p"));
////			 System.out.println(binding.toString());
////			 
//			 if(binding.get("p").toString().contains("diver"))
//				 dive.setDiver(binding.get("o").asLiteral().getString());
//			 if(binding.get("p").toString().contains("maxdeep"))
//				 dive.setDepth(binding.get("o").asLiteral().getInt());
//			 if(binding.get("p").toString().contains("name"))
//				 dive.setName(binding.get("o").asLiteral().getString());
//			 if(binding.get("p").toString().contains("role"))
//				 dive.setBuddy(binding.get("o").asLiteral().getString());
//			 if(binding.get("p").toString().contains("location"))
//				 dive.setLocation(binding.get("o").asLiteral().getString());
//		}
//		
//		LOG.info(dive.toString());
//		return dive;
//		
//	}


	public List<String> mapStringLocationsList(ResultSet rs) {
		LOG.info("Mapping Locations...");
		List<String> locations = new ArrayList<String>();
		
		try{
		while(rs.hasNext()){

			QuerySolution binding = rs.nextSolution();                     
			
			if(binding.get("country_name").toString().contains("country_name"))
				locations.add(binding.get("name").asLiteral().getString());
			
//			System.out.println("##### Locations");
//			System.out.println(locations.toString());

		}
		} catch (Exception e){
			LOG.warn(e.getMessage());
		}
		
		LOG.info("Locations: " + locations.toString());		
		
		return locations;
	}
	
}
