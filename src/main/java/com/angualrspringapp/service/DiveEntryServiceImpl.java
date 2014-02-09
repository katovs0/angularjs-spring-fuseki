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

//    @Override
//    public DiveEntry getDiveById(Long id) {
//        return findDiveEntryById(id);
//    }

    @Override
    public void addDive(DiveEntry diveEntry) {
    	LOG.info("ADD DIVE: " + diveEntry.toString());

        FusekiAdapter.storeDiveEntry(diveEntry);
    }

    @Override
    public void deleteDiveById(String id) {
    	LOG.info("REMOVE DIVE: " + id);
    	
    	boolean success = FusekiAdapter.removeDiveById(id);

        if(success) System.out.println("SUCCESSFULLY ON REMOVED DIVE: " + id);
        else System.out.println("COULD NOT REMOVE DIVE: " + id);
        
    }
    
//    @Override
//    public void deleteDiveById(Long id) {
//    	System.out.println("REMOVE DIVE" + id);
//    	
//        DiveEntry foundDiveEntry = findDiveEntryById(id);
//        if (foundDiveEntry != null) {
//            divesList.remove(foundDiveEntry);
//            id--;
//        }
//    }

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

//    private DiveEntry findDiveEntryById(Long id) {
//        for (DiveEntry DiveEntry : divesList) {
//            if (DiveEntry.getId() == id) {
//                return DiveEntry;
//            }
//        }
//
//        return null;
//    }
    
    private DiveEntry findDiveEntryById(String id) {
    	
    	return FusekiAdapter.getDiveById(id);

    }
    
//    private void jenaTest() {
//    	Model m = ModelFactory.createDefaultModel();
//    	String NS = "http://example.com/test/";
//    	
//    	Resource r = m.createResource(NS + "r");
//    	Property p = m.createProperty(NS + "p");
//    	
//    	r.addProperty(p, "hello world", XSDDatatype.XSDstring);
//    	
//    	m.write(System.out, "Turtle");
//    	
//    	//////
////    	Query query = QueryFactory.create(OntologyAcces.PREFIXES+"SELECT *{?s ?p ?o} LIMIT 1000"); 
////    	
////    	
////    	QueryExecutionFactory.sparqlService(service, query);
////    	UpdateRemote.execute();
////    	DatasetAccessor a;
//    }
    
//    private void jenaToDBPedia() {
//    	
//        String sparqlQueryString1= "PREFIX dbont: <http://dbpedia.org/ontology/> "+
//        	    "PREFIX dbp: <http://dbpedia.org/property/>"+
//        	        "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>"+
//        	    "   SELECT ?musician  ?place"+
////        	    "   FROM<http://dbpedia.org/resource/Daphne_Oram>"+
//        	    "   WHERE {  "+
//        	    "       ?musician dbont:birthPlace ?place ."+
//        	    "        } LIMIT 10";
//
//
//        	      Query query = QueryFactory.create(sparqlQueryString1);
//        	      QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
//
//        	      ResultSet results = qexec.execSelect();
////        	      ResultSetFormatter.out(System.out, results, query);
//        	      
//        	      Model m = ResultSetFormatter.toModel(results);
//        	      m.write(System.out, "RDF/XML-ABBREV");
//        	      
//        	      
////        	      System.out.println("MODEL\n" + m.toString() + "\n=======================");
////        	      
////        	      System.out.println("--------------------CSV----------------------------`");
////        		  ResultSetFormatter.outputAsCSV(System.out, results);
////        		  System.out.println("----------------------JSON--------------------------`");
////        		  ResultSetFormatter.outputAsJSON(System.out, results);
////        		  System.out.println("------------------XML------------------------------`");
////        		  ResultSetFormatter.outputAsXML(System.out, results);
//        		  
////        		  while(results.hasNext()){
////        			  System.out.println("========================");
////        		      System.out.println(results.next()); 
////        		  }
//        		  
//        		  
////        		  if(results != null) System.out.println("###ROW NUMBER" + results.getRowNumber() +"\n ###");
////        		  if(results != null) System.out.println("###RESULT VARS" + results.getResultVars() +"\n ###");
//        		//        	      if(results != null) System.out.println("###NEXT BINDING" + results.nextBinding() +"\n ###");
//        		//        	      if(results != null) System.out.println("###NEXT SOLUTION" + results.nextSolution() +"\n ###");
//        		  
//        		  if(results.getResourceModel() != null) System.out.println("@@@" + results.getResourceModel()); 
//        		  else System.out.println("£££££££££££");
//
//        	     qexec.close() ;
//    	
//    }
    
    
//    private void jenaToFusekiLocal2 (DiveEntry diveEntry) {
//    	String LOCATION = "http://dbpedia.org/ontology/location";
//    	
//    	String REMOTE_URL = "http://localhost:3030/ds/data";
//    	
//    	DatasetGraphAccessorHTTP updater = new DatasetGraphAccessorHTTP(REMOTE_URL);
//    	DatasetAdapter adapter = new DatasetAdapter (updater);
//    	Graph defaultGraph;
//    	
//    	System.out.println("### Getting the default graph for" + REMOTE_URL);
//    	defaultGraph = updater.httpGet();
//    	System.out.println("#" + defaultGraph.toString());
//    			
//    	System.out.println("### Getting the default MODEL");
//    	Model defaultModel = adapter.getModel();
//    	if (null != defaultModel) defaultModel.write(System.out, "RDF/XML");
//    	
//    	Model diveM = ModelFactory.createDefaultModel();
//    	
////        public static final Resource TZTYPES = m.createResource(uri + "TZTYPES" );
////        public static final Property Street = m.createProperty(uri, "Street" );
//    	
//    	String diveURI = "http://scubadive.networld.to/dive.rdf#";
//    
//    	
//    	Resource dive1 = diveM.createResource(diveURI + "Dive/" + diveEntry.getId())
//    							.addProperty(diveM.createProperty(diveURI, "diver"), diveEntry.getDiver())
//    							.addProperty(diveM.createProperty(diveURI, "name"), diveEntry.getName())
//    							.addProperty(diveM.createProperty(diveURI, "maxdeep"), ResourceFactory.createTypedLiteral(diveEntry.getDepth()))
//    							.addProperty(diveM.createProperty(diveURI, "role"), diveEntry.getBuddy())
//    							.addProperty(diveM.createProperty(LOCATION), diveEntry.getLocation());
//
////    	diveM.add(diveM.createResource(diveURI + "Dive"), diveM.createProperty(diveURI, "diver"), "Stefan33");
////    	diveM.add(diveM.createResource(diveURI + "Dive"), diveM.createProperty(diveURI, "name"), "First Dive");
////    	diveM.add(diveM.createResource(diveURI + "Dive"), diveM.createProperty(diveURI, "maxdeep"), ResourceFactory.createTypedLiteral(33));
//    	
//    	adapter.add(diveM);
//    	
//    	defaultModel = adapter.getModel();
//    	if (null != defaultModel) defaultModel.write(System.out, "RDF/XML"); else System.out.println("++++++");
//    	
//    }
//    
//    
//    private void jenaToFusekiLocal() {
//    	
//	    String sparqlQueryString1= "SELECT * WHERE {?s ?p ?o .}";
//	
//	
//		  Query query = QueryFactory.create(sparqlQueryString1);
//		  QueryExecution qexec = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", query);
//		
//		  ResultSet results = qexec.execSelect();
//		  ResultSetFormatter.out(System.out, results, query);  
//		  
//		  System.out.println("--------------------CSV----------------------------`");
//		  ResultSetFormatter.outputAsCSV(System.out, results);
//		  System.out.println("----------------------JSON--------------------------`");
//		  ResultSetFormatter.outputAsJSON(System.out, results);
//		  System.out.println("------------------XML------------------------------`");
//		  ResultSetFormatter.outputAsXML(System.out, results);
//		  
//		  while(results.hasNext()){
//			  System.out.println("========================");
//		      System.out.println(results.next()); 
//		  }
//		  
//		  
//		  if(results != null) System.out.println("###ROW NUMBER" + results.getRowNumber() +"\n ###");
//		  if(results != null) System.out.println("###RESULT VARS" + results.getResultVars() +"\n ###");
//		//        	      if(results != null) System.out.println("###NEXT BINDING" + results.nextBinding() +"\n ###");
//		//        	      if(results != null) System.out.println("###NEXT SOLUTION" + results.nextSolution() +"\n ###");
//		  
//		  if(results.getResourceModel() != null) System.out.println("@@@" + results.getResourceModel()); 
//		  else System.out.println("£££££££££££");
//		  
//		
//		 qexec.close() ;
//    	
//    }
    
}
