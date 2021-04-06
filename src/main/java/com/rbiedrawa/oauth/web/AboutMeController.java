package com.rbiedrawa.oauth.web;

import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/me")
public class AboutMeController {

	@GetMapping
	Mono<Map<String, Object>> claims(@AuthenticationPrincipal JwtAuthenticationToken auth) {
		return Mono.just(auth.getTokenAttributes());
	}

	@GetMapping("/token")
	Mono<String> token(@AuthenticationPrincipal JwtAuthenticationToken auth) {
		return Mono.just(auth.getToken().getTokenValue());
	}

	@GetMapping("/role_admin")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	Mono<String> role_admin() {
		return Mono.just("ROLE_ADMIN");
	}

	@GetMapping("/scope_messages_read")
	@PreAuthorize("hasAuthority('SCOPE_MESSAGES:READ')")
	Mono<String> scope_api_me_read() {
		return Mono.just("You have 'MESSAGES:READ' scope");
	}
}
