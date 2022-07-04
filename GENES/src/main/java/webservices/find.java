/*
 * Servlet : search
 *
 * Description   : this servlet generates an html web page that contains a set of informations according to a query.
 * 
 * Version       : 1.0
 *
 * Date          : 23/11/2021
 * 
 * Copyright     : Boulares Ouchenne
 */

package webservices;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;


import org.openrdf.query.BindingSet;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.http.HTTPRepository;
import org.openrdf.rio.RDFParseException;

import com.google.gson.Gson;

import metier.Erreur;

import metier.Message;
import metier.Result;
/**
 * Servlet implementation class search
 */
public class find extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletConfig cfg;
    private Gson gson = new Gson();

	  public void init(ServletConfig config) throws ServletException {
	     cfg = config;
	  }

	  public ServletConfig getServletConfig() {
	    return cfg;
	  }

	  

	  public void destroy() {
	  }

	  public void service (ServletRequest req,  ServletResponse res ) 
	  throws ServletException, IOException  {
		  PrintWriter out = res.getWriter();
		  res.setContentType("application/json");
		  res.setCharacterEncoding("UTF-8");
		
		  Set<String> inputs = new HashSet<String>();
		  inputs.add("hgnc");
		  inputs.add("symbol");
		  

      	//recuperate and normalize the set of parameters from

		  String By = req.getParameter("by");
		  if((By==null))
		  {
			  Erreur R= new Erreur();
			  Message M=new Message ("Parameter 'by' is mandatory.");
              M.setCode("required Parameters.");
			  R.Add(M);
              String infos;
	          infos= this.gson.toJson(R);
       	      out.print( infos);
	          out.flush();
              return;
		  }
		  By=By.toLowerCase();
          if(!(inputs.contains(By)))
		  {
			  	  Erreur R= new Erreur();
		              R.Add(new Message ("The value of Parameter 'by' must be one of :{hgnc,symbol}."));
		              String infos;
			          infos= this.gson.toJson(R);
		       	      out.print( infos);
			          out.flush();
		              return;
		  }
		  
         	 String Input = req.getParameter("input");
   		  if((Input==null))
   		  {
   			  Erreur R= new Erreur();
   			  Message M=new Message ("Parameter 'input' is mandatory.");
                 M.setCode("required Parameters.");
   			  R.Add(M);
                 String infos;
   	          infos= this.gson.toJson(R);
          	      out.print( infos);
   	          out.flush();
                 return;
   		  }
   		  Input=Input.toUpperCase();
   		//get ressources linked to Orpha codes
   		Result R = null;
		
   		if (By.equals("symbol"))
   		{
   		try {
			R = new Result(Input,0);
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   		}else
   		{
   	   		try {
   				R = new Result(Input,1);
   			} catch (RepositoryException e) {
   				// TODO Auto-generated catch block
   				e.printStackTrace();
   			}
   	   		}
   			
          String infos;
	      	infos= this.gson.toJson(R);
	      	out.print( infos);
	        out.flush();
        
          
   		  
   		  
             
         
         
		  
		  
	  
	  
	  } 
       
   

	 
}
