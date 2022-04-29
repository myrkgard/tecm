package com.myrkgard.tecm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myrkgard.tecm.database.Database;
import com.myrkgard.tecm.model.Folder;

@Service
public class FolderService {
	
	public boolean addFolder(Folder folder) {
		return Database.getInstance().addFolder(folder);
	}
	
	public List<Folder> getAllFolders() {
		return Database.getInstance().allFolders();
	}
	
	public Folder getFolderById(String id) {
		return Database.getInstance().getFolderById(id);
	}
	
}
