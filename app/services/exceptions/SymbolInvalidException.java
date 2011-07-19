package services.exceptions;

public class SymbolInvalidException extends Exception {

	SymbolInvalidException(String msg){
		super(msg);
	}

	public SymbolInvalidException() {
		super();
	}
}
