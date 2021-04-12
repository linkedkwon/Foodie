package kr.foodie.config.web;

import kr.foodie.config.web.handler.AuthenticatedHandler;
import kr.foodie.config.web.handler.LoginModelHandler;
import kr.foodie.config.web.handler.RedirectHandler;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.ajp.AbstractAjpProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.*;

import java.time.Duration;


@Configuration
public class ContainerConfig {
    @Value("${tomcat.ajp.protocol}")
    String ajpProtocol;

    @Value("${tomcat.ajp.port}")
    int ajpPort;

    @Bean
    public ServletWebServerFactory servletContainer() {

        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(createAjpConnector());

        return tomcat;
    }

    private Connector createAjpConnector() {
        Connector ajpConnector = new Connector(ajpProtocol);
        ajpConnector.setPort(ajpPort);
        ajpConnector.setSecure(false);
        ajpConnector.setAllowTrace(false);
        ajpConnector.setScheme("http");
        ((AbstractAjpProtocol<?>)ajpConnector.getProtocolHandler()).setSecretRequired(false);
        return ajpConnector;
    }
}
