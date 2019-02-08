package com.github.sigma.blog.domain;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OverProtocol;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Arquillian.class)
public class PostServiceTest {

    @Inject
    PostService postService;

    @Inject
    PostRepository postRepository;

    @Deployment
    @OverProtocol("Servlet 3.0")
    public static WebArchive createDeployment() {
        File[] files = Maven.resolver()
                .loadPomFromFile("pom.xml")
                .importRuntimeDependencies()
                .resolve()
                .withTransitivity().asFile();
        return ShrinkWrap.create(WebArchive.class, "test.war")
                //.addPackages(true, StrutsApplication.class.getPackage())
                .addPackages(true, "com.github.sigma")
                .addAsLibraries(files)
                .addAsResource("arquillian/META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("arquillian/WEB-INF/beans.xml", "beans.xml")
                .addAsWebInfResource("arquillian/WEB-INF/jboss-web.xml", "jboss-web.xml")
                .addAsWebInfResource("arquillian/WEB-INF/web.xml", "web.xml");
    }

    @Test
    public void savePost() {
        // given:
        assertThat(postRepository.getAll()).isEmpty();

        // when:
        postService.save(PostRequest.of("title", "body"));

        // then:
        assertThat(postRepository.getAll()).isNotEmpty();
        // and:
        assertThat(postRepository.getAll().size()).isEqualTo(1);
    }
}
