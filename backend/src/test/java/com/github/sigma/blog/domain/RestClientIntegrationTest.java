package com.github.sigma.blog.domain;

import com.github.sigma.blog.StrutsApplication;
import lombok.extern.java.Log;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OverProtocol;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Log
@RunWith(Arquillian.class)
public class RestClientIntegrationTest {

    @Deployment
    @OverProtocol("Servlet 3.0")
    public static WebArchive createDeployment() {

        File[] libs = Maven.resolver()
                .loadPomFromFile("pom.xml")
                .importRuntimeDependencies()
                .resolve()
                .withTransitivity().asFile();

        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackages(true, StrutsApplication.class.getPackage())
                .addAsResource("arquillian/META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsLibraries(libs);
    }

    final static Client restClient = ClientBuilder.newClient();
    final static WebTarget rootApi = restClient.target("http://127.0.0.1:8080/blog/api/v1");

    @Test
    public void test() {
        final Map map = jsonBuilder("/").get(Map.class);
        log.info("map: " + map.toString());
        assertThat(map).isNotEmpty();
    }

    private Builder jsonBuilder(String path) {
        final WebTarget webTarget = rootApi.path(path);
        return webTarget.request(MediaType.APPLICATION_JSON);
    }
}
