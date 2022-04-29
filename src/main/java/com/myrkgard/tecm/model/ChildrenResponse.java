package com.myrkgard.tecm.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ChildrenResponse {
	public List<Folder> folders;
	public List<Document> documents;

	public ChildrenResponse(List<Folder> folders, List<Document> documents) {
		this.folders = folders;
		this.documents = documents;
	}
}
