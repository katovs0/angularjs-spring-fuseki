package com.xvitcoder.angualrspringapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

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
import com.xvitcoder.angualrspringapp.beans.DiveEntry;

@Service("diveEntryService")
public class DiveEntryServiceImpl implements DiveEntryService {
    private static List<DiveEntry> divesList = new ArrayList<DiveEntry>();
    private static Long id = 0L;

    @Override
    public List<DiveEntry> getAllDives() {
        return divesList;
    }

    @Override
    public DiveEntry getDiveById(Long id) {
        return findDiveEntryById(id);
    }

    @Override
    public void addDive(DiveEntry diveEntry) {
    	System.out.println(diveEntry.toString());
        diveEntry.setId(++id);
        divesList.add(diveEntry);
    }

    @Override
    public void deleteDiveById(Long id) {
        DiveEntry foundDiveEntry = findDiveEntryById(id);
        if (foundDiveEntry != null) {
            divesList.remove(foundDiveEntry);
            id--;
        }
    }

    @Override
    public void deleteAll() {
        divesList.clear();
        id = 0L;
    }

    @Override
    public void updateDive(DiveEntry diveEntry) {
        DiveEntry foundDiveEntry = findDiveEntryById(diveEntry.getId());
        if (foundDiveEntry != null) {
            divesList.remove(foundDiveEntry);
            divesList.add(diveEntry);
        }
    }

    private DiveEntry findDiveEntryById(Long id) {
        for (DiveEntry DiveEntry : divesList) {
            if (DiveEntry.getId() == id) {
                return DiveEntry;
            }
        }

        return null;
    }
    
    private void jenaTest() {
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
    
    private void jenaToDBPedia() {
    	
        String sparqlQueryString1= "PREFIX dbont: <http://dbpedia.org/ontology/> "+
        	    "PREFIX dbp: <http://dbpedia.org/property/>"+
        	        "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>"+
        	    "   SELECT ?musician  ?place"+
//        	    "   FROM<http://dbpedia.org/resource/Daphne_Oram>"+
        	    "   WHERE {  "+
        	    "       ?musician dbont:birthPlace ?place ."+
        	    "        }";


        	      Query query = QueryFactory.create(sparqlQueryString1);
        	      QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);

        	      ResultSet results = qexec.execSelect();
        	      ResultSetFormatter.out(System.out, results, query);
        	      
        	      if(results != null) System.out.println("###" + results);
        	      if(results.getResourceModel() != null) System.out.println("@@@" + results.getResourceModel());

        	     qexec.close() ;
    	
    }
    
    private void jenaToFusekiLocal() {
    	
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
