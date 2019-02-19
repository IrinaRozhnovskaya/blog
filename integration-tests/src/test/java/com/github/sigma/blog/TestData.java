package com.github.sigma.blog;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Test;

import javax.ws.rs.client.*;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.util.Collections.singletonMap;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class TestData {

    // tag::jackson[]
    private static JacksonJsonProvider jacksonJsonProvider = new JacksonJaxbJsonProvider()
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

    final static Client restClient = ClientBuilder.newBuilder()
            .register(jacksonJsonProvider)
            .build();
    // end::jackson[]

    final static WebTarget rootApi = restClient.target("http://127.0.0.1:8080/blog/api/v1");

    int initalSize = 0;
    UUID uuid = null;

    //@Test
    public void test() {
        final Map rootApiResponse = jsonBuilder("/").get(Map.class);
        log.info("rootApiResponse: {}", rootApiResponse.toString());
        assertThat(rootApiResponse).size().isEqualTo(7);

        final Collection findAllResponse = jsonBuilder("/posts/find-all").get(Collection.class);
        log.info("findAllResponse: {}", findAllResponse.toString());
        int initalSize = findAllResponse.size();

        final Response createPostResponse = jsonBuilder("/posts/create").post(createPostEntity());
        log.info("createPostResponse: {}", createPostResponse);

        final HashMap post = HashMap.class.cast(createPostResponse.readEntity(HashMap.class));
        log.info("post: {}", post);
        assertThat(createPostResponse.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());
        assertThat(post.get("lastModifiedAt")).isNotNull();
        assertThat(post.get("body")).isNotNull();
        assertThat(post.get("title")).isNotNull();
        assertThat(post.get("id")).isNotNull();

        final String draftId = post.get("id").toString();
        uuid = UUID.fromString(draftId);
    }

    @After
    public void after() {
        jsonBuilder("/posts/delete").post(deletePostEntity());
    }

    private Entity<Map<String, String>> deletePostEntity() {
        return Entity.entity(singletonMap("id", uuid.toString()), APPLICATION_JSON);
    }

    private Entity<HashMap<String, String>> createPostEntity() {
        final HashMap<String, String> payload = new HashMap<>();
        payload.put("title", "ololo");
        payload.put("body", "# trololo");
        return Entity.entity(payload, APPLICATION_JSON);
    }

    private Invocation.Builder jsonBuilder(String path) {
        final WebTarget webTarget = rootApi.path(path);
        return webTarget.request(APPLICATION_JSON);
    }
}
