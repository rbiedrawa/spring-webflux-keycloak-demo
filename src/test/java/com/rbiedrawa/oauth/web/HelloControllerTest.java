package com.rbiedrawa.oauth.web;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOidcLogin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.rbiedrawa.oauth.IntegrationTest;

import org.junit.jupiter.api.Test;

@IntegrationTest
@AutoConfigureWebTestClient
class HelloControllerTest {

	@Autowired
	private WebTestClient client;

	@Test
	void should_load_hello_resource() {
		// given
		var subject = "test_user";
		var userAuthority = new SimpleGrantedAuthority("ROLE_USER");

		// when
		client.mutateWith(mockOidcLogin().idToken(builder -> builder.subject(subject)).authorities(userAuthority))
			  .get()
			  .uri("/hello").exchange()
			  // then
			  .expectStatus().is2xxSuccessful()
			  .expectBody(String.class).value(containsString("Hello test_user!"));
	}

	@Test
	void should_return_forbidden_when_user_dont_have_required_role() {
		// given
		var subject = "test_user";
		var userAuthority = new SimpleGrantedAuthority("ROLE_TEST");

		// when
		client.mutateWith(mockOidcLogin().idToken(builder -> builder.subject(subject)).authorities(userAuthority))
			  .get()
			  .uri("/hello").exchange()
			  // then
			  .expectStatus().isEqualTo(HttpStatus.FORBIDDEN);
	}
}