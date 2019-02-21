package com.github.sigma.blog;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.client.*;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static java.lang.String.format;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Here we solve a problem to read posts files from JAR archive when
 * code will be executing via java -jar ... as well as in case when
 * main method will be executed right from IDE. See method for details.
 */
@Slf4j
public class TestData {

  private final static JacksonJsonProvider jacksonJsonProvider = new JacksonJaxbJsonProvider()
      .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

  private final static Client restClient = ClientBuilder.newBuilder()
                                                        .register(jacksonJsonProvider)
                                                        .build();

  private static final String BACKEND_HOSTNAME = System.getenv().get("BACKEND_HOSTNAME");
  private static final String defaultBackendHostname = "127.0.0.1";
  private static final String backendHostname = null == BACKEND_HOSTNAME
      ? System.getProperty("backendHostname", defaultBackendHostname)
      : BACKEND_HOSTNAME;

  private final static WebTarget webTarget =
      restClient.target(format("http://%s:8080/blog/api/v1", backendHostname));

  public static void main(String[] args) {
    File byteCodeLocation = getByteCodeLocation();
    File[] files = getPostsFiles(byteCodeLocation);
    for (File file : files) {
      Entity<HashMap<String, String>> request = createPostRequest(file, byteCodeLocation);
      Response response = restClient("/posts/create").post(request);
      String title = request.getEntity().get("title");
      String status = response.getStatusInfo().getReasonPhrase().toLowerCase();
      log.info("{} post: {}", status, title);
    }
  }

  @SneakyThrows
  private static File getByteCodeLocation() {
    return new File(TestData.class.getProtectionDomain()
                                  .getCodeSource()
                                  .getLocation()
                                  .toURI());
  }

  @SneakyThrows
  private static boolean codeExecutedFromIde(File jarFile) {
    // if jarFIle is classes directory, then code was executed right from IDE
    return jarFile.isDirectory() && jarFile.getAbsolutePath().endsWith("classes");
  }

  @SneakyThrows
  private static File[] getPostsFiles(File byteCodeLocation) {
    // if byteCodeLocation is classes directory,
    // then code was executed right from IDE,
    // so we can read files from regular filesystem path
    if (codeExecutedFromIde(byteCodeLocation)) {
      URL posts = TestData.class.getResource("/posts/");
      return Paths.get(posts.toURI()).toFile().listFiles();
    }
    // otherwise it was executed as java -jar path/to/file.jar
    JarFile jar = new JarFile(byteCodeLocation);
    ArrayList<JarEntry> entries = Collections.list(jar.entries());
    ArrayList<File> posts = new ArrayList<>();
    // Filter all **/*.md files from JAR archive
    for (JarEntry entry : entries) {
      if (!entry.getName().toLowerCase().endsWith(".md")) continue;
      // transform: posts/file.md -> /posts/file.md
      posts.add(new File(format("/%s", entry.getName())));
    }
    File[] result = new File[posts.size()];
    posts.toArray(result);
    return result;
  }

  private static Entity<HashMap<String, String>> createPostRequest(File file, File byteCodeLocation) {
    HashMap<String, String> payload = new HashMap<>();
    String fileName = file.getName();
    // replace all underscores with spaces and drop last ".md" part:
    String title = fileName.replaceAll("_", " ").split(".md")[0];
    String body = readFileContent(file.getPath(), byteCodeLocation);
    payload.put("title", title);
    payload.put("body", body);
    return Entity.entity(payload, APPLICATION_JSON);
  }

  private static Invocation.Builder restClient(String path) {
    WebTarget webTarget = TestData.webTarget.path(path);
    return webTarget.request(APPLICATION_JSON);
  }

  @SneakyThrows
  private static String readFileContent(String filePath, File byteCodeLocation) {
    StringBuilder content = new StringBuilder();
    @Cleanup InputStream inputStream = codeExecutedFromIde(byteCodeLocation)
        ? Files.newInputStream(Paths.get(filePath))
        : TestData.class.getResourceAsStream(filePath);
    @Cleanup BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    String line;
    while ((line = bufferedReader.readLine()) != null)
      content.append(line)
             // needed workaround to proper MD -> HTML conversion
             .append("\n");
    return content.toString();
  }
}
