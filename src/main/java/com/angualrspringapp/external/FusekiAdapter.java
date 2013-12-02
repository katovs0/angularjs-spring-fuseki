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
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;

public class FusekiAdapter {
	private static Logger LOG = Logger.getLogger(FusekiAdapter.class);
	
	private static String EXTERNAL_SOURCE = "http://dbpedia.org";
	private static String RDF_STORE = "http://localhost:3030/ds";
	private static String diveURI = "http://scubadive.networld.to/dive.rdf#";
	
	private static DiveEntryResultSetMapper diveEntryMapper = DiveEntryResultSetMapper.getInstance();
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
    	Model defaultModel = adapter.getModel();
    	
    	Model diveM = ModelFactory.createDefaultModel();

    	
    
    	
    	Resource dive1 = diveM.createResource(diveURI + "Dive/" + UUID.randomUUID() + ":" + diveEntry.getId())
    							.addProperty(diveM.createProperty(diveURI, "diver"), diveEntry.getDiver())
    							.addProperty(diveM.createProperty(diveURI, "name"), diveEntry.getName())
    							.addProperty(diveM.createProperty(diveURI, "maxdeep"), ResourceFactory.createTypedLiteral(diveEntry.getDepth()))
    							.addProperty(diveM.createProperty(diveURI, "role"), diveEntry.getBuddy())
    							.addProperty(diveM.createProperty(EXTERNAL_SOURCE+"/ontology/location"), diveEntry.getLocation());

//    	diveM.add(diveM.createResource(diveURI + "Dive"), diveM.createProperty(diveURI, "diver"), "Stefan33");
//    	diveM.add(diveM.createResource(diveURI + "Dive"), diveM.createProperty(diveURI, "name"), "First Dive");
//    	diveM.add(diveM.createResource(diveURI + "Dive"), diveM.createProperty(diveURI, "maxdeep"), ResourceFactory.createTypedLiteral(33));
    	
    	LOG.info("Adding a resource to the default graph: \n" + dive1.toString());
    	
    	adapter.add(diveM);
    	
    	return dive1;

	}




	public static List<DiveEntry> getAllDives() {
		List<DiveEntry> divesList = new ArrayList<DiveEntry>();
//		StringWriter out = new StringWriter();
		
		String sparqlQueryString1= "SELECT ?s ?p ?o2"
				+ "where {"
				+ "?s <http://scubadive.networld.to/dive.rdf#name> ?divename ."
				+ "?s ?p ?o2 ."
				+ "}";
		
		
		Query query = QueryFactory.create(sparqlQueryString1);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(RDF_STORE + "/query", query);
		
		ResultSet results = qexec.execSelect();
		qexec.close() ;
		return diveEntryMapper.mapDiveEntryList(results);
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

	
	
}
