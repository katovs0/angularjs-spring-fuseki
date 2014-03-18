package com.angualrspringapp.external;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.jena.web.DatasetAdapter;
import org.apache.jena.web.DatasetGraphAccessorHTTP;
import org.apache.log4j.Logger;

import com.angualrspringapp.beans.DiveEntry;
import com.angualrspringapp.beans.SearchFilter;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.update.UpdateExecutionFactory;
import com.hp.hpl.jena.update.UpdateFactory;
import com.hp.hpl.jena.update.UpdateProcessor;
import com.hp.hpl.jena.update.UpdateRequest;

public class FusekiAdapter {
	private static Logger LOG = Logger.getLogger(FusekiAdapter.class);
	
	private static String EXTERNAL_SOURCE = "http://dbpedia.org";
	private static String RDF_STORE = "http://localhost:3030/ds";
	private static String DIVE_URI = "http://scubadive.networld.to/dive.rdf#";
	private static String HOME_PAGE = "http://stefankatov.com/diveLog/"; 	//http://personalpages.manchester.ac.uk/student/stefan.katov
	private static String RDF_SYNTAX = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
	private static String RDF_SCHEMA = "http://www.w3.org/2000/01/rdf-schema#";
	
	private static DiveEntryResultSetMapper diveEntryMapper = DiveEntryResultSetMapper.getInstance();
	private static LocationsResultSetMapper locationsMapper = LocationsResultSetMapper.getInstance();
	private static DatasetGraphAccessorHTTP updater = new DatasetGraphAccessorHTTP(RDF_STORE + "/data");
	private static DatasetAdapter adapter = new DatasetAdapter (updater);
	
	public static Model writeModelToDefault (Model m) {
		StringWriter out = new StringWriter();
		
		LOG.info("#####\nEXTERNAL_SOURCE: " + EXTERNAL_SOURCE);
		LOG.info("RDF_STORE: " + RDF_STORE + "\n########\n");
		
		LOG.info("Writing model to the default graph");
		
		adapter.add(m);
    	Model defaultModel = adapter.getModel();
    	
    	if (null != defaultModel) {
    		defaultModel.write(out, "RDF/XML");
    		LOG.info("MODEL: \n" + out.toString());
    	} else LOG.info("MODEL: NULL");
		
		return defaultModel;
	}
	
	

	
	public static Resource storeDiveEntry (DiveEntry diveEntry) {
    			
    	System.out.println("### Getting the default MODEL");
    	LOG.info("### Getting the default MODEL");
    	
//    	Model defaultModel = adapter.getModel();
//    	LOG.info("### Got the default MODEL");
    	
    	Model diveM = ModelFactory.createDefaultModel();
    	LOG.info("### Created a default MODEL");

    	
    
    	UUID uniqueId;
    	
    	//Refactoring idea: http://pic.dhe.ibm.com/infocenter/db2luw/v10r1/index.jsp?topic=%2Fcom.ibm.swg.im.dbclient.rdf.doc%2Fdoc%2Fc0060696.html
    	Resource dive1 = diveM.createResource(HOME_PAGE + "Dive/" + (uniqueId=UUID.randomUUID()))
    							.addProperty(diveM.createProperty(RDF_SYNTAX, "type"), diveM.createResource(DIVE_URI + "Dive"))
//    							.addProperty(diveM.createProperty(RDF_SYNTAX, "ID"), uniqueId.toString())
    							.addProperty(diveM.createProperty(DIVE_URI, "id"), uniqueId.toString())
    							.addProperty(diveM.createProperty(DIVE_URI, "diver"), diveEntry.getDiver())
    							.addProperty(diveM.createProperty(DIVE_URI, "name"), diveEntry.getName())
    							.addProperty(diveM.createProperty(DIVE_URI, "maxdeep"), ResourceFactory.createTypedLiteral(diveEntry.getDepth()))
    							.addProperty(diveM.createProperty(DIVE_URI, "role"), diveEntry.getBuddy())
    							
    							//// diveURI = "http://scubadive.networld.to/dive.rdf#"
    							.addProperty(diveM.createProperty(DIVE_URI, "bottomtime"), ResourceFactory.createTypedLiteral(diveEntry.getMinutes()))
    							.addProperty(diveM.createProperty(DIVE_URI, "airTemperature"), ResourceFactory.createTypedLiteral(diveEntry.getAirTemp()))
    							.addProperty(diveM.createProperty(DIVE_URI, "bottomTemperature"), ResourceFactory.createTypedLiteral(diveEntry.getWaterTemp()))
    							////
    							
    							.addProperty(diveM.createProperty(EXTERNAL_SOURCE+"/ontology/location"), diveEntry.getLocation());

//    	diveM.add(diveM.createResource(diveURI + "Dive"), diveM.createProperty(diveURI, "diver"), "Stefan313");
//    	diveM.add(diveM.createResource(diveURI + "Dive"), diveM.createProperty(diveURI, "name"), "First Dive1");
//    	diveM.add(diveM.createResource(diveURI + "Dive"), diveM.createProperty(diveURI, "maxdeep"), ResourceFactory.createTypedLiteral(313));
//    	
    	LOG.info("Adding a resource to the default graph: \n" + dive1.toString());
    	
    	adapter.add(diveM);

    	
    	return dive1;

	}
	
	public static List<DiveEntry> getAllDives() {
		List<DiveEntry> divesList = new ArrayList<DiveEntry>();
		List<String> diveIds = new ArrayList<String>();
		
		String sparqlQueryString1= "PREFIX scuba: <http://scubadive.networld.to/dive.rdf#>"
				+ "select ?s "
				+ "where { "
				+ "?s a scuba:Dive . "
				+ "}";
		
		Query query = QueryFactory.create(sparqlQueryString1);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(RDF_STORE + "/query", query);
		
		ResultSet results = qexec.execSelect();
		
		diveIds =  diveEntryMapper.mapDiveIdsList(results);
		
		//Close after the full set of dives is retrieved
		qexec.close() ;
		
		for(String id : diveIds) {
			divesList.add(getDiveById(id));
//			divesList.add(getEnrichedDiveById(id));
		}
		
		LOG.info("Dives list: " + divesList );
		
		return divesList;
	}

	public static DiveEntry getDiveById(String id) {
		
		String sparqlQueryString1= "SELECT * WHERE {"
				+ "<" + id + ">"
				+ " ?p ?o ."
				+ "}";
		
		
		Query query = QueryFactory.create(sparqlQueryString1);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(RDF_STORE + "/query", query);
		
		ResultSet results = qexec.execSelect();
		qexec.close() ;
		return diveEntryMapper.mapDiveEntry(id, results);

	}
	
	public static DiveEntry getEnrichedDiveById(String id) {
		
		String sparqlQueryString1= "PREFIX scuba: <http://scubadive.networld.to/dive.rdf#>"
					+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
					+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>"
					+ "PREFIX dbpprop: <http://dbpedia.org/property/>"
					+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
			
					+ "select distinct  ?p ?o ?img ?extLink ?lang1 ?photoCollection "
					+ "where { "
					+ "<" + id + "> ?p ?o ; " 
 					+ "dbpedia-owl:location ?location . "

					+ "optional { "
   					+ "SERVICE <http://dbpedia.org/sparql> { " 
    					+ "?s1 ?p1 ?location . "
    					+ "?s1 owl:sameAs ?s2 . "
    					+ "?s2 dbpedia-owl:thumbnail ?img . "
    					
    					+ "?s2 dbpprop:officialLanguages ?lang1 ."
    					+ "?s2 dbpedia-owl:wikiPageExternalLink ?extLink ."
    					+ "?s2 dbpprop:hasPhotoCollection ?photoCollection ."
//    					+ "?lang1 rdfs:label ?lang ."
//    					+ "Filter(lang(?lang) = 'en')"
    					+ "}"
    				+ "}"

    				+ "}";

		LOG.info(sparqlQueryString1);
		
		Query query = QueryFactory.create(sparqlQueryString1);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(RDF_STORE + "/query", query);
		
		ResultSet results = qexec.execSelect();
		
		DiveEntry dive = diveEntryMapper.mapEnrichedDiveEntry(id, results);
		qexec.close() ;
		
		return dive;

	}

	public static List<String> getAllLocations() {
		
		String sparqlQuery = "SELECT DISTINCT ?country_name ?name "
			+ "WHERE {"
			+ " ?s <http://www.w3.org/2001/sw/DataAccess/tests/result-set#variable> ?country_name . "
			+ " ?s <http://www.w3.org/2001/sw/DataAccess/tests/result-set#value> ?name . "
    		+ " }";
		
		Query query = QueryFactory.create(sparqlQuery);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(RDF_STORE + "/query", query);
		
		ResultSet results = qexec.execSelect();

		List<String> locationsList = locationsMapper.mapStringLocationsList(results);
		qexec.close();
		
		return locationsList;
	}
	
	public static boolean removeDiveById(String id) {
		
		String updateRequestString = "DELETE { ?s ?p ?o } "
				+ "WHERE "
				+ " { ?s ?p ?o ; "
				+ " <http://scubadive.networld.to/dive.rdf#id> "
				+ "'" + id + "' "
				+ "} ";
		
		
		try {
			
			UpdateRequest  request = UpdateFactory.create();
			request.add(updateRequestString);
			
			LOG.info("@@@@");
			LOG.info(request.toString());
			
			UpdateProcessor processor = UpdateExecutionFactory.createRemoteForm(request, RDF_STORE + "/update");
			
			LOG.info("COMTEXT:\n" + processor.getContext());
			LOG.info("GRAPH STORE: \n" + processor.getGraphStore());
		
			processor.execute();
			
		} catch (Exception e) {
			LOG.error("ERROR while updating dive entry [" + id + "]\n" + e.getMessage());
			return false;
		}
		
		return true;

	}
	
	public static boolean removeAllDives() {
		
		String updateRequestString = "DELETE { ?s ?p ?o } "
				+ "WHERE "
				+ " { ?s ?p ?o ; "
				+ "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://scubadive.networld.to/dive.rdf#Dive> "
				+ "} ";
		
		
		try {
			
			UpdateRequest  request = UpdateFactory.create();
			request.add(updateRequestString);
			
			LOG.info("@@@@");
			LOG.info(request.toString());
			
			UpdateProcessor processor = UpdateExecutionFactory.createRemoteForm(request, RDF_STORE + "/update");
			
			LOG.info("COMTEXT:\n" + processor.getContext());
			LOG.info("GRAPH STORE: \n" + processor.getGraphStore());
		
			processor.execute();
			
		} catch (Exception e) {
			LOG.error("ERROR while removing all dives: " + e.getMessage());
			return false;
		}
		
		return true;

	}




	public static void storeDiver(String diver) {
		LOG.info("Adding a diver to the RDF store");
		
		Model defaultModel = ModelFactory.createDefaultModel();
		
		Resource diver1 = defaultModel.createResource(HOME_PAGE + "DiverProfile/" + diver)
				.addProperty(defaultModel.createProperty(RDF_SYNTAX, "type"), defaultModel.createResource(DIVE_URI + "DiverProfile"))
				.addProperty(defaultModel.createProperty("http://xmlns.com/foaf/0.1/givenname"), diver);
		
		
		
		adapter.add(defaultModel);
		
		LOG.info("Diver added to the RDF store");
		
	}




	public static List<String> getAllDivers() {
		LOG.info("GETTING ALL DIVERS...");
		List<String> divers = new ArrayList<String>();
		
		String sparqlQueryString1= "SELECT ?p ?o "
				+ "where { "
				+ " ?s ?p ?o ."
				+ "?s a <http://scubadive.networld.to/dive.rdf#DiverProfile> . "
				+ " }";
		
		
		Query query = QueryFactory.create(sparqlQueryString1);
		
		LOG.info("@QUERY:");
		LOG.info(query.toString());
		
		QueryExecution qexec = QueryExecutionFactory.sparqlService(RDF_STORE + "/query", query);
		
		ResultSet results = qexec.execSelect();
		qexec.close() ;
		
		LOG.info(results);
		
		divers =  diveEntryMapper.mapDiversList(results);
		
		return divers;
	}




	public static boolean removeDiver(String diver) {
		LOG.info("Removing diver: " + diver);
		
		String updateRequestString = "DELETE { ?s ?p ?o } "
				+ "WHERE "
				+ " { ?s ?p ?o ; "
				+ " <http://xmlns.com/foaf/0.1/givenname> "
				+ "'" + diver + "' "
				+ "} ";
		
		
		try {
			
			UpdateRequest  request = UpdateFactory.create();
			request.add(updateRequestString);
			
			LOG.info("@@@@");
			LOG.info(request.toString());
			
			UpdateProcessor processor = UpdateExecutionFactory.createRemoteForm(request, RDF_STORE + "/update");
			
			LOG.info("CONTEXT:\n" + processor.getContext());
			LOG.info("GRAPH STORE: \n" + processor.getGraphStore());
		
			processor.execute();
			
		} catch (Exception e) {
			LOG.error("ERROR while deleting diver [" + diver + "]\n" + e.getMessage());
			return false;
		}
		
		return true;
		
	}
	
	
	public static boolean removeAllDivers() {
		LOG.info("Removing all divers... ");
		
		String updateRequestString = "DELETE { ?s ?p ?o } "
				+ "WHERE "
				+ " { ?s ?p ?o ; "
				+ " <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> "
				+ "<http://scubadive.networld.to/dive.rdf#DiverProfile> } ";
		
		
		try {
			
			UpdateRequest  request = UpdateFactory.create();
			request.add(updateRequestString);
			
			LOG.info("@@@@");
			LOG.info(request.toString());
			
			UpdateProcessor processor = UpdateExecutionFactory.createRemoteForm(request, RDF_STORE + "/update");
			
			LOG.info("CONTEXT:\n" + processor.getContext());
			LOG.info("GRAPH STORE: \n" + processor.getGraphStore());
		
			processor.execute();
			
		} catch (Exception e) {
			LOG.error("ERROR while deleting all divers \n" + e.getMessage());
			return false;
		}
		
		return true;
		
	}




	public static List<DiveEntry> getFilteredDives() {
		List<DiveEntry> divesList = new ArrayList<DiveEntry>();
		List<String> diveIds = new ArrayList<String>();

		String sparqlQueryString1= "PREFIX scuba: <http://scubadive.networld.to/dive.rdf#>"
			+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
			+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>"
			+ "PREFIX dbpprop: <http://dbpedia.org/property/>"
			+ "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>"
			+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
	
			+ "select distinct  ?s "
			+ "where { "
				+ "?s ?p ?o . "
				+ "?s a scuba:Dive ."
				+ "?s dbpedia-owl:location ?name " 

					+ "SERVICE <http://dbpedia.org/sparql> { " 
					+ "?s1 ?p1 ?name . "
					+ "?s1 owl:sameAs ?s2 . "
					+ "optional {?s2 dbpedia-owl:language ?lang . }"
					
					+ "} FILTER( ?lang = <http://dbpedia.org/resource/English_language> )"
			+ "}";
		
		Query query = QueryFactory.create(sparqlQueryString1);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(RDF_STORE + "/query", query);
		
		ResultSet results = qexec.execSelect();
		qexec.close() ;
		
		diveIds =  diveEntryMapper.mapDiveIdsList(results);
		
		for(String id : diveIds) {
//			divesList.add(getDiveById(id));
			divesList.add(getEnrichedDiveById(id));
		}
		
		LOG.info("Dives list Filtered: " + divesList );
		
		return divesList;
	}




	public static List<DiveEntry> getSearchFilteredDives(SearchFilter filter) {
		List<DiveEntry> divesList = new ArrayList<DiveEntry>();
		List<String> diveIds = new ArrayList<String>();
		
		StringBuilder sparqlQuery = new StringBuilder();
		sparqlQuery.append("PREFIX scuba: <http://scubadive.networld.to/dive.rdf#>\n");
		sparqlQuery.append("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n");
		sparqlQuery.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n");
		sparqlQuery.append("PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>\n");
		sparqlQuery.append("PREFIX dbpprop: <http://dbpedia.org/property/>\n");
		sparqlQuery.append("PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>\n");
		sparqlQuery.append("PREFIX owl: <http://www.w3.org/2002/07/owl#>\n");	
		sparqlQuery.append("select distinct  ?s ");
		sparqlQuery.append("where { ");
			sparqlQuery.append("?s ?p ?o . ");
			sparqlQuery.append("?s a scuba:Dive .");
			sparqlQuery.append("?s scuba:airTemperature ?airTemp . ");
			sparqlQuery.append("?s scuba:bottomTemperature ?waterTemp . ");
			sparqlQuery.append("?s scuba:maxdeep ?depth . ");
			sparqlQuery.append("?s scuba:bottomtime ?time . ");
			sparqlQuery.append("?s scuba:diver ?diverName . ");
			sparqlQuery.append("?s dbpedia-owl:location ?location . ");
//			sparqlQuery.append("Filter ( ");
				if(filter.getMinAirTemp() != null && filter.getMaxAirTemp() != null) sparqlQuery.append("FILTER ( ?airTemp >= " + filter.getMinAirTemp() + " && ?airTemp <= " + filter.getMaxAirTemp() + " )");
				else if(filter.getMinAirTemp() != null) sparqlQuery.append("FILTER ( ?airTemp >= " + filter.getMinAirTemp() + " )");
				else if (filter.getMaxAirTemp() != null) sparqlQuery.append("FILTER ( ?airTemp <= " + filter.getMaxAirTemp() + " )");
				
				if(filter.getMinWaterTemp() != null && filter.getMaxWaterTemp() != null) sparqlQuery.append("FILTER ( ?waterTemp >= " + filter.getMinWaterTemp() + " && ?waterTemp <= " + filter.getMaxWaterTemp() + " )");
				else if(filter.getMinWaterTemp() != null) sparqlQuery.append("FILTER ( ?waterTemp >= " + filter.getMinWaterTemp() + " )");
				else if (filter.getMaxWaterTemp() != null) sparqlQuery.append("FILTER ( ?waterTemp <= " + filter.getMaxWaterTemp() + " )");
				
				if(filter.getMinDepth() != null && filter.getMaxDepth() != null) sparqlQuery.append("FILTER ( ?depth >= " + filter.getMinDepth() + " && ?depth <= " + filter.getMaxDepth() + " )");
				else if(filter.getMinDepth() != null) sparqlQuery.append("FILTER ( ?depth >= " + filter.getMinDepth() + " )");
				else if(filter.getMaxDepth() != null) sparqlQuery.append("FILTER ( ?depth <= " + filter.getMaxDepth() + " )");
				
				if(filter.getMinTime() != null && filter.getMaxTime() != null) sparqlQuery.append("FILTER ( ?time >= " + filter.getMinTime() + " && ?time <= " + filter.getMaxTime() + " )");
				else if(filter.getMinTime() != null) sparqlQuery.append("FILTER ( ?time >= " + filter.getMinTime() + " )");
				else if(filter.getMaxTime() != null) sparqlQuery.append("FILTER ( ?time <= " + filter.getMaxTime() + " )");
				
				if(filter.getDiverName()!=null && !filter.getDiverName().isEmpty()) sparqlQuery.append(" FILTER (?diverName = \"" + filter.getDiverName() + "\" )");
				if(filter.getLocation()!=null && !filter.getLocation().isEmpty()) sparqlQuery.append(" FILTER (?location = \"" + filter.getLocation() + "\" )");
//			sparqlQuery.append(")");
		sparqlQuery.append("}");
		
		String sparqlQueryString1 = sparqlQuery.toString();

//		String sparqlQueryString1= "PREFIX scuba: <http://scubadive.networld.to/dive.rdf#>\n"
//			+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
//			+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
//			+ "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>\n"
//			+ "PREFIX dbpprop: <http://dbpedia.org/property/>\n"
//			+ "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>\n"
//			+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
//	
//			+ "select distinct  ?s "
//			+ "where { "
//				+ "?s ?p ?o . "
//				+ "?s a scuba:Dive ."
//				+ "?s scuba:airTemperature ?airTemp . "
//				+ "?s scuba:diver ?diverName . "
//				+ "Filter ( "
//					+ "( ?airTemp > " + filter.getMinAirTemp() + " && ?airTemp <= " + filter.getMaxAirTemp() + " )"
//					+ " && (?diverName = \"" + filter.getDiverName() + "\" )"
//				+ ")"
//
//			+ "}";
		
		
		
		LOG.info(sparqlQueryString1);
		
		Query query = QueryFactory.create(sparqlQueryString1);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(RDF_STORE + "/query", query);
		
		ResultSet results = qexec.execSelect();
		qexec.close() ;
		
		diveIds =  diveEntryMapper.mapDiveIdsList(results);
		
		for(String id : diveIds) {
//			divesList.add(getDiveById(id));
			divesList.add(getEnrichedDiveById(id));
		}
		
		LOG.info("Dives list Search Filtered: " + divesList );
		
		return divesList;
	}

	
	
}
