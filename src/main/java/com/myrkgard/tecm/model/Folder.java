package com.myrkgard.tecm.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Folder {
	public String id;
	public String parentId;
	public String name;
	public int numberOfChildren;
	
	public Folder(String id, String parentId, String name, int numberOfChildren) {
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.numberOfChildren = numberOfChildren;
	}
}
