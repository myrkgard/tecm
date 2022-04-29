package com.myrkgard.tecm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myrkgard.tecm.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	private final AdminService adminService;
	
	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}
	
	@GetMapping("/resetDatabase")
	public ResponseEntity<String> resetDatabase() {
		this.adminService.resetDatabase();
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}
}
