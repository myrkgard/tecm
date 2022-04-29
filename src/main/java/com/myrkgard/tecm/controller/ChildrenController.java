package com.myrkgard.tecm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.myrkgard.tecm.model.ChildrenResponse;
import com.myrkgard.tecm.service.ChildrenService;

@RestController
@RequestMapping("/children")
public class ChildrenController {
	private final ChildrenService childrenService;

	public ChildrenController(ChildrenService childrenService) {
		this.childrenService = childrenService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<ChildrenResponse> getChildrenResponse(@PathVariable String id) {
		ChildrenResponse childrenResponse = childrenService.getChildrenResponse(id);
		System.out.println(Integer.toString(childrenResponse.folders.size()));
		System.out.println(Integer.toString(childrenResponse.documents.size()));
		return new ResponseEntity<>(childrenResponse, childrenResponse == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}
}