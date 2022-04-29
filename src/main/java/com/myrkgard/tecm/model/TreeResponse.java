package com.myrkgard.tecm.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TreeResponse {
	public Folder folder;
	public Document document;
	
	public TreeResponse(Folder folder, Document document) {
		this.folder = folder;
		this.document = document;
	}
}
