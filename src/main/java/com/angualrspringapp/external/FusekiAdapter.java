package com.angualrspringapp.external;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.jena.web.DatasetAdapter;
import org.apache.jena.web.DatasetGraphAccessorHTTP;
import org.apache.log4j.Logger;

import com.angualrspringapp.beans.DiveEntry;
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
	private static String diveURI = "http://scubadive.networld.to/dive.rdf#";
	private static String RDF_SYNTAX = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
	
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
    	Resource dive1 = diveM.createResource(diveURI + "Dive/" + (uniqueId=UUID.randomUUID()) + ":" + diveEntry.getId())
    							.addProperty(diveM.createProperty(RDF_SYNTAX, "type"), diveM.createResource(diveURI + "Dive"))
//    							.addProperty(diveM.createProperty(RDF_SYNTAX, "ID"), uniqueId.toString())
    							.addProperty(diveM.createProperty(diveURI, "id"), uniqueId.toString())
    							.addProperty(diveM.createProperty(diveURI, "diver"), diveEntry.getDiver())
    							.addProperty(diveM.createProperty(diveURI, "name"), diveEntry.getName())
    							.addProperty(diveM.createProperty(diveURI, "maxdeep"), ResourceFactory.createTypedLiteral(diveEntry.getDepth()))
    							.addProperty(diveM.createProperty(diveURI, "role"), diveEntry.getBuddy())
    							
    							//// diveURI = "http://scubadive.networld.to/dive.rdf#"
    							.addProperty(diveM.createProperty(diveURI, "bottomtime"), ResourceFactory.createTypedLiteral(diveEntry.getMinutes()))
    							.addProperty(diveM.createProperty(diveURI, "airTemperature"), ResourceFactory.createTypedLiteral(diveEntry.getAirTemp()))
    							.addProperty(diveM.createProperty(diveURI, "bottomTemperature"), ResourceFactory.createTypedLiteral(diveEntry.getWaterTemp()))
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
	
//	public static Resource updateDiveEntry (DiveEntry diveEntry) {
//		
//    	System.out.println("### Getting the default MODEL");
//    	LOG.info("### Getting the default MODEL");
//    	Model defaultModel = adapter.getModel();
//    	
//    	Model diveM = ModelFactory.createDefaultModel();
//    
//    	
//    	Resource dive1 = diveM.createResource(diveURI + "Dive/" + UUID.randomUUID() + ":" + diveEntry.getId())
//    							.addProperty(diveM.createProperty(diveURI, "diver"), diveEntry.getDiver())
//    							.addProperty(diveM.createProperty(diveURI, "name"), diveEntry.getName())
//    							.addProperty(diveM.createProperty(diveURI, "maxdeep"), ResourceFactory.createTypedLiteral(diveEntry.getDepth()))
//    							.addProperty(diveM.createProperty(diveURI, "role"), diveEntry.getBuddy())
//    							.addProperty(diveM.createProperty(EXTERNAL_SOURCE+"/ontology/location"), diveEntry.getLocation());
//
////    	diveM.add(diveM.createResource(diveURI + "Dive"), diveM.createProperty(diveURI, "diver"), "Stefan33");
////    	diveM.add(diveM.createResource(diveURI + "Dive"), diveM.createProperty(diveURI, "name"), "First Dive");
////    	diveM.add(diveM.createResource(diveURI + "Dive"), diveM.createProperty(diveURI, "maxdeep"), ResourceFactory.createTypedLiteral(33));
//    	
//    	LOG.info("Adding a resource to the default graph: \n" + dive1.toString());
//    	
//    	adapter.add(diveM);
//    	
//    	return dive1;
//
//	}




	public static List<DiveEntry> getAllDives() {
		List<DiveEntry> divesList = new ArrayList<DiveEntry>();
		List<String> diveIds = new ArrayList<String>();
		
		String sparqlQueryString1= "SELECT ?s "
				+ "where { "
				+ " ?s <http://scubadive.networld.to/dive.rdf#name> ?divename . "
				+ " }";
		
		
		Query query = QueryFactory.create(sparqlQueryString1);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(RDF_STORE + "/query", query);
		
		ResultSet results = qexec.execSelect();
		qexec.close() ;
		
		diveIds =  diveEntryMapper.mapDiveIdsList(results);
		
		for(String id : diveIds) {
			divesList.add(getDiveById(id));
		}
		
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




	public static List<String> getAllLocations() {
		
		String sparqlQuery = "SELECT DISTINCT ?country_name ?name "
			+ "WHERE {"
			+ " ?s <http://www.w3.org/2001/sw/DataAccess/tests/result-set#variable> ?country_name . "
			+ " ?s <http://www.w3.org/2001/sw/DataAccess/tests/result-set#value> ?name . "
    		+ " }";
		
		Query query = QueryFactory.create(sparqlQuery);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(RDF_STORE + "/query", query);
		
		ResultSet results = qexec.execSelect();
		qexec.close() ;


		return locationsMapper.mapStringLocationsList(results);
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

	
	
}
