package com.rbiedrawa.oauth;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;

import org.junit.jupiter.api.Tag;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Tag("integration")
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureWireMock(port = 8081)
// TODO: for now hardcoded in order to fix create 'issuer-uri' response dynamically based on injected port.
public @interface IntegrationTest {

}
