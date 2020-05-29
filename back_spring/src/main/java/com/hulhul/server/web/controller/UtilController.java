package com.hulhul.server.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class UtilController {

	@RequestMapping("/api")
	public RedirectView swagger() {
		return new RedirectView("/swagger-ui.html");
	}
}
