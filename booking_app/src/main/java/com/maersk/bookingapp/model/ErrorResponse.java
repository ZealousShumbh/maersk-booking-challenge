package com.maersk.bookingapp.model;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse {

	/**
	 * The date & time of the error occurence
	 */
	private LocalDateTime timestamp = LocalDateTime.now();

	/**
	 * The http status code
	 */
	private int status;

	/**
	 * The error message about about the nature of error
	 */
	private String error;

	/**
	 * The details of errors encountered during request processing
	 */
	private List<String> details;

	/**
	 * The related url that was accessed when the error was encountered.
	 */
	private String path;

	public ErrorResponse() {
		super();
	}

	public ErrorResponse(LocalDateTime timestamp, int status, String error, List<String> details, String path) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.details = details;
		this.path = path;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public List<String> getDetails() {
		return details;
	}

	public void setDetails(List<String> details) {
		this.details = details;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "ErrorResponse [timestamp=" + timestamp + ", status=" + status + ", error=" + error + ", details="
				+ details + ", path=" + path + "]";
	}

}
