package com.angualrspringapp.service;

import com.angualrspringapp.external.DbPediaUtil;
import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
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

public class SparqlTest {
	
	public static void main (String[] args) {
		
//		jenaTest();
		
//		jenaToDBPedia();
		
		DbPediaUtil.importCoutriesList();
		
	}

    
    private static void jenaTest() {
    	Model m = ModelFactory.createDefaultModel();
    	String NS = "http://example.com/test/";
    	
    	Resource r = m.createResource(NS + "r");
    	Property p = m.createProperty(NS + "p");
    	
    	r.addProperty(p, "hello world", XSDDatatype.XSDstring);
    	
    	m.write(System.out, "Turtle");
    	
    	//////
//    	Query query = QueryFactory.create(OntologyAcces.PREFIXES+"SELECT *{?s ?p ?o} LIMIT 1000"); 
//    	
//    	
//    	QueryExecutionFactory.sparqlService(service, query);
//    	UpdateRemote.execute();
//    	DatasetAccessor a;
    }
    
    private static void jenaToDBPedia() {
    	
        String sparqlQueryString1= "PREFIX dbont: <http://dbpedia.org/ontology/> "+
        	    "PREFIX dbp: <http://dbpedia.org/property/>"+
        	        "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>"+
        	    "   SELECT ?musician  ?place"+
//        	    "   FROM<http://dbpedia.org/resource/Daphne_Oram>"+
        	    "   WHERE {  "+
        	    "       ?musician dbont:birthPlace ?place ."+
        	    "        } LIMIT 10";


        	      Query query = QueryFactory.create(sparqlQueryString1);
        	      QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);

        	      ResultSet results = qexec.execSelect();
        	      ResultSetFormatter.out(System.out, results, query);
        	      
//        	      Model m = ResultSetFormatter.toModel(results);
//        	      m.write(System.out, "RDF/XML-ABBREV");
//        	      
        	      
//        	      System.out.println("MODEL\n" + m.toString() + "\n=======================");
//        	      
//        	      System.out.println("--------------------CSV----------------------------`");
//        		  ResultSetFormatter.outputAsCSV(System.out, results);
//        		  System.out.println("----------------------JSON--------------------------`");
//        		  ResultSetFormatter.outputAsJSON(System.out, results);
//        		  System.out.println("------------------XML------------------------------`");
//        		  ResultSetFormatter.outputAsXML(System.out, results);
        		  
//        		  while(results.hasNext()){
//        			  System.out.println("========================");
//        		      System.out.println(results.next()); 
//        		  }
        		  
        		  
//        		  if(results != null) System.out.println("###ROW NUMBER" + results.getRowNumber() +"\n ###");
//        		  if(results != null) System.out.println("###RESULT VARS" + results.getResultVars() +"\n ###");
        		//        	      if(results != null) System.out.println("###NEXT BINDING" + results.nextBinding() +"\n ###");
        		//        	      if(results != null) System.out.println("###NEXT SOLUTION" + results.nextSolution() +"\n ###");
        		  
//        		  if(results.getResourceModel() != null) System.out.println("@@@" + results.getResourceModel()); 
//        		  else System.out.println("£££££££££££");

        	     qexec.close() ;
    	
    }
    
    
//    private static void jenaToFusekiLocal2 (DiveEntry diveEntry) {
//    	String REMOTE_URL = "http://localhost:3030/ds/data";
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
//    							.addProperty(diveM.createProperty(diveURI, "role"), diveEntry.getBuddy());
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
    
    
    private static void jenaToFusekiLocal() {
    	
	    String sparqlQueryString1= "SELECT * WHERE {?s ?p ?o .}";
	
	
		  Query query = QueryFactory.create(sparqlQueryString1);
		  QueryExecution qexec = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", query);
		
		  ResultSet results = qexec.execSelect();
		  ResultSetFormatter.out(System.out, results, query);  
		  
		  System.out.println("--------------------CSV----------------------------`");
		  ResultSetFormatter.outputAsCSV(System.out, results);
		  System.out.println("----------------------JSON--------------------------`");
		  ResultSetFormatter.outputAsJSON(System.out, results);
		  System.out.println("------------------XML------------------------------`");
		  ResultSetFormatter.outputAsXML(System.out, results);
		  
		  while(results.hasNext()){
			  System.out.println("========================");
		      System.out.println(results.next()); 
		  }
		  
		  
		  if(results != null) System.out.println("###ROW NUMBER" + results.getRowNumber() +"\n ###");
		  if(results != null) System.out.println("###RESULT VARS" + results.getResultVars() +"\n ###");
		//        	      if(results != null) System.out.println("###NEXT BINDING" + results.nextBinding() +"\n ###");
		//        	      if(results != null) System.out.println("###NEXT SOLUTION" + results.nextSolution() +"\n ###");
		  
		  if(results.getResourceModel() != null) System.out.println("@@@" + results.getResourceModel()); 
		  else System.out.println("£££££££££££");
		  
		
		 qexec.close() ;
    	
    }
    
}

