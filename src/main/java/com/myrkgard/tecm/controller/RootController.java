package com.myrkgard.tecm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.myrkgard.tecm.model.Folder;
import com.myrkgard.tecm.service.RootService;

@RestController
@RequestMapping("/root")
public class RootController {
	private final RootService rootService;

	public RootController(RootService rootService) {
		this.rootService = rootService;
	}

	@GetMapping
	public ResponseEntity<Folder> getRootFolder() {
		Folder folder = this.rootService.getRootFolder();
		return new ResponseEntity<>(folder, folder == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}

}