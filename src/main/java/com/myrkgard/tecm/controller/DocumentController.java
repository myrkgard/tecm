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

import com.myrkgard.tecm.model.Document;
import com.myrkgard.tecm.service.DocumentService;

@RestController
@RequestMapping("/documents")
public class DocumentController {
	private final DocumentService documentService;

	public DocumentController(DocumentService documentService) {
		this.documentService = documentService;
	}

	@GetMapping
	public ResponseEntity<List<Document>> getAllDocuments() {
		return new ResponseEntity<>(this.documentService.getAllDocuments(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<String> addFolder(@RequestBody Document document) {
		boolean ok = this.documentService.addDocument(document);
		return new ResponseEntity<>(ok ? "OK" : "Error", ok ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Document> getDocumentById(@PathVariable String id) {
		Document d = this.documentService.getDocumentById(id);
		return new ResponseEntity<>(d, d == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);

	}
}