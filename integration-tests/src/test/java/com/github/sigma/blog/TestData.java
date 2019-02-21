package com.github.sigma.blog;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.ws.rs.client.*;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;

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

//        @Test
        public void test () throws IOException {

//        final Response createPostResponse = jsonBuilder("/posts/create").post(createPostEntity());
//        log.info("createPostResponse: {}", createPostResponse);
//
//        final HashMap post = HashMap.class.cast(createPostResponse.readEntity(HashMap.class));
//        log.info("post: {}", post);
//        assertThat(createPostResponse.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());
//        assertThat(post.get("lastModifiedAt")).isNotNull();
//        assertThat(post.get("body")).isNotNull();
//        assertThat(post.get("title")).isNotNull();
//        assertThat(post.get("id")).isNotNull();

    }
        public static void main(String[] args) throws IOException {
            File posts = new File("\\resources\\posts");
            if (posts.isDirectory() && posts.listFiles() != null) {
                for (File post : posts.listFiles()) {
                    if (post.exists()) {
                        final Response createPostResponse = jsonBuilder("/posts/create").post(createPostEntity(post));
                        log.info("createPostResponse: {}", createPostResponse);
                    }
                }
            }
            final Collection findAllResponse = jsonBuilder("/posts/find-all").get(Collection.class);
            log.info("findAllResponse: {}", findAllResponse.toString());
            System.out.println(findAllResponse.size());
        }

        private static Entity<HashMap<String, String>> createPostEntity(File file) throws IOException {
            final HashMap<String, String> payload = new HashMap<>();
            String title = file.getName();
            String body = readFile(file.getPath(), Charset.defaultCharset());
            payload.put("title", title);
            payload.put("body", body);
            return Entity.entity(payload, APPLICATION_JSON);
        }

        private static Invocation.Builder jsonBuilder (String path){
        final WebTarget webTarget = rootApi.path(path);
        return webTarget.request(APPLICATION_JSON);
        }
    public static String readFile(String filePath, Charset encoding) throws IOException {

            byte[] encoded = Files.readAllBytes(Paths.get(filePath));
            return new String(encoded, encoding);

    }
}

