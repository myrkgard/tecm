package com.myrkgard.tecm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myrkgard.tecm.database.Database;
import com.myrkgard.tecm.model.Document;
import com.myrkgard.tecm.model.Folder;
import com.myrkgard.tecm.model.TreeResponse;

@Service
public class TreeService {

	// Helper
	private static Folder findFolderByName(List<Folder> folders, String name) {
		for (Folder folder : folders) {
			if (folder.name.equals(name)) {
				return folder;
			}
		}
		return null;
	}

	// Helper
	private static Document findDocumentByName(List<Document> documents, String name) {
		for (Document document : documents) {
			if (document.name.equals(name)) {
				return document;
			}
		}
		return null;
	}

	public TreeResponse getTreeResponse(String path) {

		String[] segments = path.split("/");

		if (segments.length == 0) {
			return null;
		}

		TreeResponse previousElement = new TreeResponse(Database.getInstance().getRootFolder(), null);
		if (previousElement.folder == null) {
			return null;
		}
		if (!previousElement.folder.name.equals(segments[0])) {
			return null;
		}
		
		for (int i = 1; i < segments.length; ++i) {
			
			// see if segment is a folder
			List<Folder> childFolders = Database.getInstance().getFoldersByParentId(previousElement.folder.id);
			Folder currentFolder = findFolderByName(childFolders, segments[i]);
			if (currentFolder != null) {
				// segment is a folder
				previousElement = new TreeResponse(currentFolder, null);
				continue;
			}
			// see if segment is a document
			List<Document> childDocuments = Database.getInstance().getDocumentsByParentId(previousElement.folder.id);
			Document currentDocument = findDocumentByName(childDocuments, segments[i]);
			if (currentDocument == null) {
				// neither a folder nor a document, meaning the path is invalid
				return null;
			} else {
				// segment is a document
				if (i == segments.length -1) {
					// this is the last segment
					previousElement = new TreeResponse(null, currentDocument);
				} else {
					// this a document but there a more segments, which means the path is invalid
					return null;
				}
			}
			
		}

		return previousElement;
	}

}
