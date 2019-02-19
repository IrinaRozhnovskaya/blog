package com.github.sigma.blog;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import static java.lang.String.format;

@Slf4j
public class TestData {

  final static JacksonJsonProvider jacksonJsonProvider = new JacksonJaxbJsonProvider()
      .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

  final static Client restClient = ClientBuilder.newBuilder()
                                                .register(jacksonJsonProvider)
                                                .build();

  final static WebTarget rootApi = restClient.target("http://127.0.0.1:8080/blog/api/v1");

  public static void main(String[] args) {
    createPosts();
  }

  @SneakyThrows
  static void createPosts() {
    File[] files = getPostsDir().listFiles();
    for (File file : files) {
      Entity<HashMap<String, String>> request = createPostEntity(file);
      Response response = jsonBuilder("/posts/create").post(request);
      String title = request.getEntity().get("title");
      String status = response.getStatusInfo().getReasonPhrase().toLowerCase();
      log.info("post {} {}", title, status);
    }
  }

  static File getPostsDir() throws URISyntaxException {
    return Paths.get(TestData.class.getResource("/posts/").toURI()).toFile();
  }

  static Entity<HashMap<String, String>> createPostEntity(File file) {
    HashMap<String, String> payload = new HashMap<>();
    String fileName = file.getName();
    String title = fileName.replaceAll("_", " ").split(".md")[0];
    String body = readFile(file.getPath(), Charset.defaultCharset());
    payload.put("title", title);
    payload.put("body", body);
    return Entity.entity(payload, MediaType.APPLICATION_JSON);
  }

  static Invocation.Builder jsonBuilder(String path) {
    WebTarget webTarget = rootApi.path(path);
    return webTarget.request(MediaType.APPLICATION_JSON);
  }

  @SneakyThrows
  static String readFile(String filePath, Charset encoding) {
    byte[] encoded = Files.readAllBytes(Paths.get(filePath));
    return new String(encoded, encoding);
  }
}
