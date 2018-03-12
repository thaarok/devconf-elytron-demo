package cz.devconf.demo.webfrontend.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.wildfly.security.auth.client.AuthenticationConfiguration;
import org.wildfly.security.auth.client.AuthenticationContext;
import org.wildfly.security.auth.client.MatchRule;
import org.wildfly.security.auth.util.ElytronAuthenticator;
import org.wildfly.security.sasl.SaslMechanismSelector;

@Path("/proxyRestService")
public class ProxyRestService {

    @GET
    @Produces("text/plain")
    @PermitAll
    public String getPrincipal() throws Exception {

        Authenticator.setDefault(new ElytronAuthenticator());

        return AuthenticationContext.empty().with(MatchRule.ALL, AuthenticationConfiguration.empty()
                .setSaslMechanismSelector(SaslMechanismSelector.ALL)
                .useName("admin@wildfly.org")
                .usePassword("admin")
        ).runCallable(() -> {
                    URL url = new URL("http://localhost:8080/web-frontend/rest/securedRestService");
                    URLConnection conn = url.openConnection();
                    conn.connect();

                    String lines = "";
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                         lines += br.readLine();
                    }
                    return lines;
                });
    }

}
