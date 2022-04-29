package com.myrkgard.tecm.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Document {
	public String id;
	public String parentId;
	public String name;
	public int size;
	
	public Document(String id, String parentId, String name, int size) {
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.size = size;
	}
}
