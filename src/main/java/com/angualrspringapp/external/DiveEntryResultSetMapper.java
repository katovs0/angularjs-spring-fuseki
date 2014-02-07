package com.angualrspringapp.external;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.angualrspringapp.beans.DiveEntry;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.sparql.engine.binding.BindingMap;

public class DiveEntryResultSetMapper {
	
	private static Logger LOG = LoggerFactory.getLogger(DiveEntryResultSetMapper.class);
//	
	private static final DiveEntryResultSetMapper INSTANCE = new DiveEntryResultSetMapper();
	private DiveEntryResultSetMapper() {}
	
	public static DiveEntryResultSetMapper getInstance() {
        return INSTANCE;
    }
	public DiveEntry mapDiveEntry(String id, ResultSet rs) {
		LOG.info("Mapping a DiveEntry...");
		DiveEntry dive = new DiveEntry();
		
		dive.setIdString(id);
		
		while(rs.hasNext()){

			 QuerySolution binding = rs.nextSolution();                     
//			 System.out.println(binding.get("p"));
//			 System.out.println(binding.get("?p"));
//			 System.out.println(binding.toString());
//			 
			 if(binding.get("p").toString().contains("diver"))
				 dive.setDiver(binding.get("o").asLiteral().getString());
			 if(binding.get("p").toString().contains("maxdeep"))
				 dive.setDepth(binding.get("o").asLiteral().getInt());
			 if(binding.get("p").toString().contains("name"))
				 dive.setName(binding.get("o").asLiteral().getString());
			 if(binding.get("p").toString().contains("role"))
				 dive.setBuddy(binding.get("o").asLiteral().getString());
			 if(binding.get("p").toString().contains("location"))
				 dive.setLocation(binding.get("o").asLiteral().getString());
			 if(binding.get("p").toString().contains("bottomtime"))
				 dive.setMinutes(binding.get("o").asLiteral().getInt());
			 if(binding.get("p").toString().contains("airTemperature"))
				 dive.setAirTemp(binding.get("o").asLiteral().getDouble());
			 if(binding.get("p").toString().contains("bottomTemperature"))
				 dive.setWaterTemp(binding.get("o").asLiteral().getDouble());
//			 if(binding.get("p").toString().contains("id"))
//				 dive.setId(binding.get("o").asLiteral().getLong());
			 if(binding.get("p").toString().contains("id"))
				 dive.setIdString(binding.get("o").asLiteral().getString());
//			 if(binding.get("p").toString().contains("rdf-syntax-ns#ID"))
//				 dive.setIdString(binding.get("o").asLiteral().getString());
		}
		
		LOG.info(dive.toString());
		return dive;
		
	}

//	public List<DiveEntry> mapDiveEntryList(ResultSet rs) {
//		LOG.info("Mapping a DiveEntryList...");
//		List<DiveEntry> divesList = new ArrayList();
//		DiveEntry dive;
//		
//		System.out.println(rs.toString());
//		
//		while(rs.hasNext()){
//			 dive = new DiveEntry();
//
//			 QuerySolution binding = rs.nextSolution();
//			 System.out.println("###" + binding);
//			 if(binding.get("s").toString().contains("#Dive"))
//				 dive.setIdString(binding.get("s").toString());
//			 
//			 while((binding = rs.nextSolution()).get("s").toString().equals(dive.getIdString()) ) {
//				 System.out.println(binding.toString());
//				 
//				 if(binding.get("p").toString().contains("diver"))
//					 dive.setDiver(binding.get("o2")!=null ? binding.get("o2").asLiteral().getString() : "MISSING_DIVER");
//				 if(binding.get("p").toString().contains("maxdeep"))
//					 dive.setDepth(binding.get("o2")!=null ? binding.get("o2").asLiteral().getInt() : 0);
//				 if(binding.get("p").toString().contains("name"))
//					 dive.setName(binding.get("o2")!=null ? binding.get("o2").asLiteral().getString() : "MISSING_NAME");
//				 if(binding.get("p").toString().contains("role"))
//					 dive.setBuddy(binding.get("o2")!=null ? binding.get("o2").asLiteral().getString() : "MISSING_ROLE");
//				 if(binding.get("p").toString().contains("location"))
//					 dive.setLocation(binding.get("o2")!=null ? binding.get("o2").asLiteral().getString() : "MISSING_LOCATION");
//			 }
//			 System.out.println("## Dive\n" + dive);
//			 
//			 divesList.add(dive);
//
//		}
//		
//		LOG.info("DIVES_LIST:\n" + divesList.toString());
//		return divesList;
//	}
	

	public List<String> mapDiveIdsList(ResultSet rs) {
		LOG.info("Mapping a DiveIdsList...");
		List<String> diveIds = new ArrayList<String>();
		
		while(rs.hasNext()){

			 QuerySolution binding = rs.nextSolution();
			 LOG.debug("Mapping a binding...");
			 if(binding.get("s").toString().contains("#Dive"))
				 diveIds.add(binding.get("s").toString());
		}

		LOG.info("DIVES_LIST:\n" + diveIds.toString());
		return diveIds;
	}
	
}
