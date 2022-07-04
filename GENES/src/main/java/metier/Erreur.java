/*
 * Class : Erreur
 *
 * Description   : this class generate an error message ex: mismissing parameter 
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

public class Erreur {
Set<Message> errors;

public Erreur() {
	errors= new HashSet<Message>();
}
public void Add(Message M)
{
errors.add(M);
}
public Set<Message> getErrors() {
	return errors;
}

public void setErrors(Set<Message> errors) {
	this.errors = errors;
}

}
