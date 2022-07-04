/*
 * Class : Message
 *
 * Description   : this class generates a set of information about a Resource.
 * 
 * Version       : 1.0
 *
 * Date          : 23/11/2021
 * 
 * Copyright     : Boulares Ouchenne
 */
package metier;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import org.openrdf.query.BindingSet;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.http.HTTPRepository;

public class Resource {
	String orphaCode;
	String label;
	 

	public Resource()
	{
		
	}
	public Vector<Resource> ListToken(String input,int p) throws RepositoryException {
		String SparqlEndPointUrl="http://155.133.131.171:8080/blazegraph/namespace/TSTGN/sparql";	
		Vector<Resource> resourceResponses=new Vector<Resource>();
		 Repository BlazeGraphServer = new HTTPRepository(SparqlEndPointUrl);  
		 BlazeGraphServer.initialize();		
		 //On ouvre une connexion au repository pour envoyer toute les requêtes
		 RepositoryConnection connection = BlazeGraphServer.getConnection();
		 //Création de la Query SELECT
		 //On initialise la query.	 
		 String requeteSPARQL="prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
			 +"prefix ordo: <http://www.orpha.net/ORDO/>"+ 
				 "select DISTINCT ?dis ?label ?type where {?gene ordo:symbol \""+input+"\"^^xsd:string. ?dis ordo:isRelatedTo ?gene. ?dis rdfs:label ?label. ?dis ordo:type ?type}";
		if (p==1)
		{
			requeteSPARQL="prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
					 +"prefix ordo: <http://www.orpha.net/ORDO/>"+ 
						 "select DISTINCT ?dis ?label ?type where {?gene ordo:hgnc_code \""+input+"\"^^xsd:string. ?dis ordo:isRelatedTo ?gene. ?dis rdfs:label ?label. ?dis ordo:type ?type}";
			
		}	
		Set<String> ToBeQuest= new HashSet<String>();
 
		 
		 //System.out.println(requeteSPARQL);	
		 // Création de la Requête 	         
		 org.openrdf.query.TupleQuery selectQuery = null;
		try {
			selectQuery = connection.prepareTupleQuery(QueryLanguage.SPARQL,requeteSPARQL);
		} catch (MalformedQueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		 // on l'exécute			
		 TupleQueryResult selectQueryResult = null;
		try {
			selectQueryResult = selectQuery.evaluate();
		} catch (QueryEvaluationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		 // Récupération Affichage des résultats
		     
		 try {
			
			 while(selectQueryResult.hasNext()) 
				{  
				   // chaque ligne du résultat est un BindingSet  
						
				BindingSet aBinding = selectQueryResult.next();				    
				
					
				Resource T=new Resource();
				T.setOrphaCode(aBinding.getBinding("dis").getValue().stringValue().substring(aBinding.getBinding("dis").getValue().stringValue().lastIndexOf("_")+1));
				String type=aBinding.getBinding("type").getValue().stringValue();
				T.setLabel(aBinding.getBinding("label").getValue().stringValue());
				
				resourceResponses.add(resourceResponses.size(),T);
			    //String type=aBinding.getBinding("type").getValue().stringValue();
				if (type.contains("ST_O_D"))ToBeQuest.add(T.getOrphaCode());
			    
				}
		} catch (QueryEvaluationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 Resource B=new Resource();B.setLabel("-------");B.setOrphaCode("-------");resourceResponses.add(B);
		 
		 if(!ToBeQuest.isEmpty())
		 {//ToBeQuest.add("558");System.out.println("Yess PERPPP!!!!="+ToBeQuest.size());
		   Vector<Resource> Upper=getParents(ToBeQuest); 
		   for(int i=0;i<Upper.size();i++)
		   resourceResponses.add(resourceResponses.size(),Upper.get(i));
		 }
		 
		 return resourceResponses;	
	
	}
	private Vector<Resource> getParents(Set<String> toBeQuest) throws RepositoryException {
		Vector<Resource> Parents=new Vector<Resource>();
		String SparqlEndPointUrl="http://155.133.131.171:8080/blazegraph/namespace/CLSGENES/sparql";	
		
		 Repository BlazeGraphServer = new HTTPRepository(SparqlEndPointUrl);  
		 BlazeGraphServer.initialize();		
		 //On ouvre une connexion au repository pour envoyer toute les requêtes
		 RepositoryConnection connection = BlazeGraphServer.getConnection();
		 //Création de la Query SELECT
		 //On initialise la query.	 
		 String requeteSPARQL="prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
			 +"\n prefix ordo: <http://www.orpha.net/ORDO/>"+ 
				 "\n select DISTINCT ?child ?label where {";
				 
		 for(String codes:toBeQuest)
				 requeteSPARQL=requeteSPARQL+"{?child  ordo:childOf  ordo:Orphanet_"+codes+"} UNION ";
				 
				 
				 requeteSPARQL=requeteSPARQL.substring(0,requeteSPARQL.length()-6);
				 requeteSPARQL=requeteSPARQL+". ?child rdfs:label ?label}";	
		//System.out.println(requeteSPARQL);
	
				 
				// Création de la Requête 	         
				 org.openrdf.query.TupleQuery selectQuery = null;
				try {
					selectQuery = connection.prepareTupleQuery(QueryLanguage.SPARQL,requeteSPARQL);
				} catch (MalformedQueryException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				 // on l'exécute			
				 TupleQueryResult selectQueryResult = null;
				try {
					selectQueryResult = selectQuery.evaluate();
				} catch (QueryEvaluationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	 
				 // Récupération Affichage des résultats
				     
				 try {
					
					 while(selectQueryResult.hasNext()) 
						{  
						   // chaque ligne du résultat est un BindingSet  
								
						BindingSet aBinding = selectQueryResult.next();				    
						
							
						Resource T=new Resource();
						T.setOrphaCode(aBinding.getBinding("child").getValue().stringValue().substring(aBinding.getBinding("child").getValue().stringValue().lastIndexOf("_")+1));
						
						T.setLabel(aBinding.getBinding("label").getValue().stringValue());
						
						Parents.add(Parents.size(),T);
					    
						}
				} catch (QueryEvaluationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
				 
				 
				 
				 
				 
		return Parents;
	}
	public String getOrphaCode() {
		return orphaCode;
	}
	public void setOrphaCode(String orphaCode) {
		this.orphaCode = orphaCode;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	
}
