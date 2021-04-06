package com.rbiedrawa.oauth.web;

import java.security.Principal;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class HelloController {

	@GetMapping("hello")
	Mono<String> hello(@AuthenticationPrincipal Principal auth) {
		return Mono.just(String.format("Hello %s!", auth.getName()));
	}
}
