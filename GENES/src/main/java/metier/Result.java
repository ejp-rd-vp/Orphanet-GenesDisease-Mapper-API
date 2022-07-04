/*
 * Class : Message
 *
 * Description   : this class generates a set of information about a Result.
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
import org.openrdf.repository.RepositoryException;
public class Result {
	public Vector<Resource> directLinkedResourceResponses;
	public Vector<Resource> inferredResourceResponses;

	//public Vector<Resource> all;
	
	public String apiVersion= "v0.1";
	public String orphanetEndpoint="http://155.133.131.171:8080/Orphanet/resource/search?";
	public Result(String input, int p) throws RepositoryException {
		Resource V= new Resource();
		this.directLinkedResourceResponses=new Vector<Resource>();
		this.inferredResourceResponses=new Vector<Resource>();
		//this.all=V.ListToken(input, p);
	    Vector<Resource> resourceResponses=V.ListToken(input, p);
		boolean next=false;
	    
	    for (int i=0;i<resourceResponses.size();i++)
		{
			if(!resourceResponses.get(i).getOrphaCode().contains("----"))
			orphanetEndpoint=orphanetEndpoint+"code="+resourceResponses.get(i).getOrphaCode()+"&";
			else next=true;
			
			Resource Temp=resourceResponses.get(i);	
			Temp.setOrphaCode("http://www.orpha.net/ORDO/Orphanet_"+resourceResponses.get(i).getOrphaCode());
			
			if(!next)
			this.directLinkedResourceResponses.add(this.directLinkedResourceResponses.size(),
					Temp
					); 
			else this.inferredResourceResponses.add(this.inferredResourceResponses.size(),
Temp);
		}	
	    this.inferredResourceResponses.remove(0);
		orphanetEndpoint=orphanetEndpoint.substring(0,orphanetEndpoint.length()-1 );	
	}
	
	public Result() throws RepositoryException {
		this.directLinkedResourceResponses=new Vector<Resource>();
		this.inferredResourceResponses=new Vector<Resource>();

	}
	
		
}
