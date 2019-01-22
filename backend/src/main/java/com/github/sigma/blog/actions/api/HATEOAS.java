package com.github.sigma.blog.actions.api;

import javax.enterprise.context.Dependent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.UUID;

import static java.lang.String.format;

@Dependent
public class HATEOAS {

    public String linkTo(HttpServletRequest request, String... pathParts)  {
        URL url = null;
        try { url = new URL(request.getRequestURL().toString()); }
        catch (MalformedURLException e) { }
        final StringBuilder urlBuilder = null != url
                ? new StringBuilder(url.getProtocol()).append("://").append(url.getAuthority())
                : new StringBuilder("");
        for (String pathPart : pathParts)
            urlBuilder.append("/").append(pathPart);
        return urlBuilder.toString();
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
        return linkTo(request, "api", "post", "new", format("?id=%s", id.toString()));
    }
}
