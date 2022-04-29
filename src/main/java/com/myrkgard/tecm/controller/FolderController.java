package com.myrkgard.tecm.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myrkgard.tecm.model.Folder;
import com.myrkgard.tecm.service.FolderService;

@RestController
@RequestMapping("/folders")
public class FolderController {
	private final FolderService folderService;

	public FolderController(FolderService folderService) {
		this.folderService = folderService;
	}

	@GetMapping
	public ResponseEntity<List<Folder>> getAllFolders() {
		return new ResponseEntity<>(this.folderService.getAllFolders(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<String> addFolder(@RequestBody Folder folder) {
		boolean ok = this.folderService.addFolder(folder);
		return new ResponseEntity<>(ok ? "OK" : "Error", ok ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Folder> getFolderById(@PathVariable String id) {
		Folder f = this.folderService.getFolderById(id);
		return new ResponseEntity<>(f, f == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);

	}
}
