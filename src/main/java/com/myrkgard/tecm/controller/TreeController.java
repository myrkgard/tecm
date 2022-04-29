package com.myrkgard.tecm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import com.myrkgard.tecm.model.TreeResponse;
import com.myrkgard.tecm.service.TreeService;

@RestController
@RequestMapping("/tree")
public class TreeController {
	private final TreeService treeService;

	public TreeController(TreeService treeService) {
		this.treeService = treeService;
	}

	@GetMapping("/**")
	public ResponseEntity<TreeResponse> getTreeResponse(HttpServletRequest request) {
		String path = new AntPathMatcher().extractPathWithinPattern(
				request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString(),
				request.getRequestURI());
		TreeResponse treeResponse = this.treeService.getTreeResponse(path);
		return new ResponseEntity<>(treeResponse, treeResponse == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}
}