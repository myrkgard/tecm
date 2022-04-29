package com.myrkgard.tecm.service;

import org.springframework.stereotype.Service;

import com.myrkgard.tecm.database.Database;
import com.myrkgard.tecm.model.Folder;

@Service
public class RootService {

	public Folder getRootFolder() {
		return Database.getInstance().getRootFolder();
	}

}
