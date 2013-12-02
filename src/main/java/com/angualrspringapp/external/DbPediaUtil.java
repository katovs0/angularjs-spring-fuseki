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

public class DbPediaUtil {
	
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
        
    	
    	DatasetGraphAccessorHTTP updater = new DatasetGraphAccessorHTTP(REMOTE_URL);
    	DatasetAdapter adapter = new DatasetAdapter (updater);
//    	Graph defaultGraph;
    	
    	System.out.println("### Getting the default graph for" + REMOTE_URL);
//    	defaultGraph = updater.httpGet();
//    	System.out.println("#" + defaultGraph.toString());
		
		



	      Query query = QueryFactory.create(sparqlQueryString1);
	      QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
	
	      ResultSet results = qexec.execSelect();
//	        	      ResultSetFormatter.out(System.out, results, query);
	      
	      Model m = ResultSetFormatter.toModel(results);

	     qexec.close() ;
	     
	     adapter.add(m);
	}

}
