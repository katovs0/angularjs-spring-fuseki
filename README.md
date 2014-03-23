angular-spring-fuseki
====================

A simple 'Dive Logging' web application that integrates between AngularJS, Spring MVC, and Fuseki Server

The application supports creating and manipulating of divers lists, creating and editing of dive entries, and filtering on the dive entries' properties. 
The filtered entries then are displayed with enrichment information retrieved from external Linked data sources such as DBPedia.


Then CHECKOUT the Fuseki Server:

git clone https://github.com/katovs0/jena-fuseki.git

RUN Fuseki Server:

fuseki-server --update --desc tdb.ttl /ds

CHECKOUT the project:

git clone https://github.com/katovs0/angularjs-spring-fuseki.git

RUN: 

mvn tomcat:run

Then open the URL: http://localhost:8080/SimpleDiveLog
