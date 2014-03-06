package com.angualrspringapp.external;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.angualrspringapp.beans.DiveEntry;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;
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

	public List<String> mapDiveIdsList(ResultSet rs) {
		LOG.info("Mapping a DiveIdsList...");
		List<String> diveIds = new ArrayList<String>();
		
		while(rs.hasNext()){

			 QuerySolution binding = rs.nextSolution();
			 LOG.debug("Mapping a binding...");
			 if(binding.get("s").toString().contains("Dive"))
				 diveIds.add(binding.get("s").toString());
		}

		LOG.info("DIVES_LIST:\n" + diveIds.toString());
		return diveIds;
	}

	public List<String> mapDiversList(ResultSet results) {
		LOG.info("Mapping Divers list...");
		List<String> diversList = new ArrayList<String>();
		
		while(results.hasNext()) {
			LOG.debug("Mapping a binding...");
			QuerySolution binding = results.nextSolution();
			 LOG.debug("Mapping a binding...");
			 if(binding.get("p").toString().contains("givenname"))
				 diversList.add(binding.get("o").toString());
		}
		
		return diversList;
	}

	public DiveEntry mapEnrichedDiveEntry(String id, ResultSet rs) {
		LOG.info("Mapping a Enriched DiveEntry...");
		DiveEntry dive = new DiveEntry();
		
		dive.setIdString(id);
		
		while(rs.hasNext()){

			 QuerySolution binding = rs.nextSolution();                     
		 
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
			 if(binding.get("img") != null)
				 dive.setLocationImage(binding.get("img").toString());
			 else 
				 dive.setLocationImage("http://collider.com/wp-content/uploads/friends-tv-show.jpg"); 
			 
			//?lang ?extLink ?photoCollection
			 RDFNode lang;
			 if((lang = binding.get("lang1")) != null)
				 dive.setLocationLang(lang.isLiteral() ? lang.asLiteral().toString() : lang.toString());
			 else 
				 dive.setLocationLang("UNKNOWN"); 
			 if(binding.get("extLink") != null)
				 dive.setLocationExtLink(binding.get("extLink").toString());
			 else if(dive.getLocationExtLink() == null)
				 dive.setLocationExtLink("NONE"); 
			 if(binding.get("photoCollection") != null)
				 dive.setLocationPhotoCollection(binding.get("photoCollection").toString());
			 else if (dive.getLocationPhotoCollection() == null)
				 dive.setLocationPhotoCollection("NONE"); 
		}
		
		LOG.info(dive.toString());
		return dive;
		
	}
	
}
