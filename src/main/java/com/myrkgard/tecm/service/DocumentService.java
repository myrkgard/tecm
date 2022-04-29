package com.myrkgard.tecm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myrkgard.tecm.database.Database;
import com.myrkgard.tecm.model.Document;

@Service
public class DocumentService {
	
	public boolean addDocument(Document document) {
		return Database.getInstance().addDocument(document);
	}
	
	public List<Document> getAllDocuments() {
		return Database.getInstance().allDocuments();
	}
	
	public Document getDocumentById(String id) {
		return Database.getInstance().getDocumentById(id);
	}
	
}
