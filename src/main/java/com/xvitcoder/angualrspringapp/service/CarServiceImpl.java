package com.xvitcoder.angualrspringapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.query.DatasetAccessor;
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

/**
 * Created with IntelliJ IDEA.
 * User: xvitcoder
 * Date: 12/20/12
 * Time: 11:14 PM
 */
@Service("carService")
public class CarServiceImpl implements CarService {
    private static List<String> carList = new ArrayList<String>();

    @Override
    public List<String> getAllCars() {
        return carList;
    }

    @Override
    public void addCar(String car) {
        carList.add(car);
//        jenaToFusekiLocal();
    }

    @Override
    public void deleteCar(String car) {
        if (carList.contains(car)) {
            carList.remove(car);
        }
    }

    @Override
    public void deleteAll() {
        carList.clear();
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

        	     qexec.close() ;
    	
    }
    
private void jenaToFusekiLocal() {
    	
        String sparqlQueryString1= "SELECT * WHERE {?s ?p ?o .}";


        	      Query query = QueryFactory.create(sparqlQueryString1);
        	      QueryExecution qexec = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", query);

        	      ResultSet results = qexec.execSelect();
        	      ResultSetFormatter.out(System.out, results, query);       

        	     qexec.close() ;
    	
    }
}
