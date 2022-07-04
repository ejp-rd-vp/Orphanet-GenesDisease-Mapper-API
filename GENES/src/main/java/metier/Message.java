/*
 * Class : Message
 *
 * Description   : this class generates a set of information about a Message.
 * 
 * Version       : 1.0
 *
 * Date          : 23/11/2021
 * 
 * Copyright     : Boulares Ouchenne
 */
package metier;

public class Message {
String message;
String code="typeMismatch";


public Message(String message) {
	this.message = message;
}

public String getMessage() {
	return message;
}

public String getCode() {
	return code;
}

public void setCode(String code) {
	this.code = code;
}

public void setMessage(String message) {
	this.message = message;
}

}
