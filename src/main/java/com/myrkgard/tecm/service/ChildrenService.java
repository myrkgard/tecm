package com.myrkgard.tecm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myrkgard.tecm.database.Database;
import com.myrkgard.tecm.model.ChildrenResponse;
import com.myrkgard.tecm.model.Document;
import com.myrkgard.tecm.model.Folder;

@Service
public class ChildrenService {

	public ChildrenResponse getChildrenResponse(String folderId) {
		Database db = Database.getInstance();
		
		List<Folder> folders = db.getFoldersByParentId(folderId);
		List<Document> documents = db.getDocumentsByParentId(folderId);
		
		return new ChildrenResponse(folders, documents);
	}

}
