package com.github.sigma.blog.domain;

import com.github.sigma.blog.StrutsApplication;
import org.assertj.core.api.AbstractCharSequenceAssert;
import org.assertj.core.api.Assertions;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OverProtocol;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@RunWith(Arquillian.class)
public class PostServiceTest {

    @Inject
    PostService postService;

    @Inject
    PostRepository postRepository;

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

    @Before
    public void testData() {
        // given:
        assertThat(postRepository.getAll()).isEmpty();
        // when:
        postService.save(PostRequest.of("title", "body"));
    }

    @Test
    public void savePost() {
        // then:
        assertThat(postRepository.getAll()).isNotEmpty();
        // and:
        assertThat(postRepository.getAll().size()).isEqualTo(1);
    }
}
