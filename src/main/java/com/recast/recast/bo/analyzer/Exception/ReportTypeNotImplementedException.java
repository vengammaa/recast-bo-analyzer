package com.recast.recast.bo.analyzer.Exception;

public class ReportTypeNotImplementedException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5758076237975552231L;
	
	public ReportTypeNotImplementedException() {
		super();
	}

	public ReportTypeNotImplementedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ReportTypeNotImplementedException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReportTypeNotImplementedException(String message) {
		super(message);
	}

	public ReportTypeNotImplementedException(Throwable cause) {
		super(cause);
	}

}
