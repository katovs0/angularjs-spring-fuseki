package com.angualrspringapp.external;

import org.apache.jena.web.DatasetAdapter;
import org.apache.jena.web.DatasetGraphAccessorHTTP;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;

public class DbPediaAdapter {
	
	public static void importCoutriesList () {
		String LOCATION = "http://dbpedia.org/ontology/location";
    	String REMOTE_URL = "http://localhost:3030/ds/data";
        String sparqlQueryString1= "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
        		+ "PREFIX type: <http://dbpedia.org/class/yago/>"
        		+ "PREFIX prop: <http://dbpedia.org/property/>"
        		+ "SELECT ?country ?country_name "
        		+ "WHERE {"
        		+ "?country a type:LandlockedCountries ."
        		+ "OPTIONAL {?country rdfs:label ?country_name Filter(lang(?country_name) = 'en')} ."
        		+ "OPTIONAL {?country prop:populationEstimate ?population} . }";
        
        String sparqlQueryString2 = "PREFIX dbprop: <http://dbpedia.org/property/> "
					        	  + "PREFIX prop: <http://dbpedia.org/property/>"
					        	  + "PREFIX type: <http://dbpedia.org/class/yago/> "
					        	  + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
					        	  + "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>"
					        	  + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>       "

        		+ "SELECT DISTINCT ?country_name ?country "
        		+ "where {"
        		+ "?country rdf:type dbpedia-owl:Country;"
        			+ "dbprop:commonName ?country_name ."
        		+ "OPTIONAL {?country dbprop:yearEnd ?yearEnd}"
        		+ "FILTER (!bound(?yearEnd))"
        		+ "MINUS { ?country a type:LandlockedCountries } ."
        		+ "OPTIONAL {?country rdfs:label ?country_name Filter(lang(?country_name) = 'en')} ."
        		+ "OPTIONAL {?country prop:populationEstimate ?population} . "
        		+ "}";
        
    	DatasetGraphAccessorHTTP updater = new DatasetGraphAccessorHTTP(REMOTE_URL);
    	DatasetAdapter adapter = new DatasetAdapter (updater);
//    	Graph defaultGraph;
    	
    	System.out.println("### Getting the default graph for" + REMOTE_URL);
//    	defaultGraph = updater.httpGet();
//    	System.out.println("#" + defaultGraph.toString());
		
		



	      Query query = QueryFactory.create(sparqlQueryString2);
	      QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
	
	      ResultSet results = qexec.execSelect();
//	        	      ResultSetFormatter.out(System.out, results, query);
	      
	      Model m = ResultSetFormatter.toModel(results);
	     
	     adapter.add(m);
	     
	     qexec.close() ;
	}
	
	
  private void jenaToDBPedia() {
	
    String sparqlQueryString1= "PREFIX dbont: <http://dbpedia.org/ontology/> "+
    	    "PREFIX dbp: <http://dbpedia.org/property/>"+
    	        "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>"+
    	    "   SELECT ?musician  ?place"+
//    	    "   FROM<http://dbpedia.org/resource/Daphne_Oram>"+
    	    "   WHERE {  "+
    	    "       ?musician dbont:birthPlace ?place ."+
    	    "        } LIMIT 10";


    	      Query query = QueryFactory.create(sparqlQueryString1);
    	      QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);

    	      ResultSet results = qexec.execSelect();
//    	      ResultSetFormatter.out(System.out, results, query);
    	      
    	      Model m = ResultSetFormatter.toModel(results);
    	      m.write(System.out, "RDF/XML-ABBREV");
    	      
    	      
//    	      System.out.println("MODEL\n" + m.toString() + "\n=======================");
//    	      
//    	      System.out.println("--------------------CSV----------------------------`");
//    		  ResultSetFormatter.outputAsCSV(System.out, results);
//    		  System.out.println("----------------------JSON--------------------------`");
//    		  ResultSetFormatter.outputAsJSON(System.out, results);
//    		  System.out.println("------------------XML------------------------------`");
//    		  ResultSetFormatter.outputAsXML(System.out, results);
    		  
//    		  while(results.hasNext()){
//    			  System.out.println("========================");
//    		      System.out.println(results.next()); 
//    		  }
    		  
    		  
//    		  if(results != null) System.out.println("###ROW NUMBER" + results.getRowNumber() +"\n ###");
//    		  if(results != null) System.out.println("###RESULT VARS" + results.getResultVars() +"\n ###");
    		//        	      if(results != null) System.out.println("###NEXT BINDING" + results.nextBinding() +"\n ###");
    		//        	      if(results != null) System.out.println("###NEXT SOLUTION" + results.nextSolution() +"\n ###");
    		  
    		  if(results.getResourceModel() != null) System.out.println("@@@" + results.getResourceModel()); 
    		  else System.out.println("£££££££££££");

    	     qexec.close() ;
	
}

}
