package com.angualrspringapp.external;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.angualrspringapp.beans.DiveEntry;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

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
		}
		
		LOG.info(dive.toString());
		return dive;
		
	}

	public List<DiveEntry> mapDiveEntryList(ResultSet rs) {
		LOG.info("Mapping a DiveEntryList...");
		List<DiveEntry> divesList = new ArrayList();
		DiveEntry dive;
		
		while(rs.hasNext()){
			dive = new DiveEntry();

			 QuerySolution binding = rs.nextSolution();
			 if(binding.get("s").toString().contains("#Dive"))
				 dive.setIdString(binding.get("s").toString());
			 
			 while((binding = rs.nextSolution()).get("s").toString().equals(dive.getIdString()) ) {
				 if(binding.get("p").toString().contains("diver"))
					 dive.setDiver(binding.get("o2").asLiteral().getString());
				 if(binding.get("p").toString().contains("maxdeep"))
					 dive.setDepth(binding.get("o2")!=null ? binding.get("o2").asLiteral().getInt() : 0);
				 if(binding.get("p").toString().contains("name"))
					 dive.setName(binding.get("o2")!=null ? binding.get("o2").asLiteral().getString() : "MISSING_NAME");
				 if(binding.get("p").toString().contains("role"))
					 dive.setBuddy(binding.get("o2")!=null ? binding.get("o2").asLiteral().getString() : "MISSING_ROLE");
				 if(binding.get("p").toString().contains("location"))
					 dive.setLocation(binding.get("o2")!=null ? binding.get("o2").asLiteral().getString() : "MISSING_LOCATION");
			 }
			 divesList.add(dive);

		}
		
		LOG.info("DIVES_LIST:\n" + divesList.toString());
		return divesList;
	}
	
}
