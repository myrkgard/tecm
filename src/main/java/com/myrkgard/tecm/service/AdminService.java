package com.myrkgard.tecm.service;

import org.springframework.stereotype.Service;

import com.myrkgard.tecm.database.Database;

@Service
public class AdminService {
	
	public void resetDatabase() {
		Database.getInstance().createDummy();
	}
}
