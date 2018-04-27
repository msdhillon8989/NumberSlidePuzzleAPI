package com.game.puzzle.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Response {

	private String status;
	private String message;

	public Response(String status, String message) {
		this.status = status;
		this.message = message;
	}

	public Response() {
		this.status = "SUCCESS";

	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


}
