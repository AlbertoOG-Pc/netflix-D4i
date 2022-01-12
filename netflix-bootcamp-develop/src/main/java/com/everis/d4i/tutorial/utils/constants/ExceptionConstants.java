package com.everis.d4i.tutorial.utils.constants;

public class ExceptionConstants {

	public static final String ERROR = "ERROR";

	public static final String MESSAGE_INEXISTENT_SEASON = "SEASON INEXISTENT - Season does not exist";
	public static final String MESSAGE_INEXISTENT_CHAPTER = "CHAPTER INEXISTENT - Chapter does not exist";
	public static final String MESSAGE_INEXISTENT_ACTOR = "ACTOR INEXISTENT - Actor does not exist";
	public static final String MESSAGE_INEXISTENT_TVSHOW = "TVSHOW INEXISTENT - Tv show does not exist";
	public static final String MESSAGE_INEXISTENT_TVSHOW_OR_SEASON = "ARGUMENT INEXISTENT - Tv show OR Season does not exit";
	
	
	public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR - An internal server error has ocurred";
	public static final String UNAUTHORIZED_ERROR = "UNAUTHORIZED - Operacion invalid for your role";



	private ExceptionConstants() {
		throw new IllegalStateException("Utility Class");
	}

}
