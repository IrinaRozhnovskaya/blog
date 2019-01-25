package com.github.sigma.blog.actions;

import javax.enterprise.inject.Default;
import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static java.lang.String.format;

@Default
public class Hateoas {

    private static final Logger log = LogManager.getLogManager().getLogger(Hateoas.class.getName());

    public String linkTo(HttpServletRequest request, String... pathParts)  {
        URL url = parseUrl(request);
        StringBuilder urlBuilder = new StringBuilder(
            format("%s://%s%s", url.getProtocol(), url.getAuthority(), request.getServletContext().getContextPath()));
        for (String pathPart : pathParts)
            urlBuilder.append(pathPart);
        return urlBuilder.toString();
    }

    private URL parseUrl(HttpServletRequest request) {
        try { return new URL(request.getRequestURL().toString()); }
        catch (MalformedURLException e) {
            log.fine(format("cannot parse URL: %s", e.getLocalizedMessage()));
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets post location.
     *
     * @param request not nullable {@link HttpServletRequest} instance.
     * @param id non nullable post identifier.
     * @return HTTP location to post.
     */
    public String getPostLocation(HttpServletRequest request, String id) {
        Objects.requireNonNull(request, "request may not be null.");
        Objects.requireNonNull(id, "id may not be null.");
        return linkTo(request, "api", "post", "new", format("?id=%s", id));
    }
}
