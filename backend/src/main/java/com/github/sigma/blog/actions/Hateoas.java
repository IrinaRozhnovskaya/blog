package com.github.sigma.blog.actions;

import lombok.extern.java.Log;

import javax.enterprise.inject.Default;
import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import static java.lang.String.format;

@Log
@Default
public class Hateoas {

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
     * Gets posts location.
     *
     * @param request not nullable {@link HttpServletRequest} instance.
     * @param id non nullable posts identifier.
     * @return HTTP location to posts.
     */
    public String getPostLocation(HttpServletRequest request, String id) {
        Objects.requireNonNull(request, "request may not be null.");
        Objects.requireNonNull(id, "id may not be null.");
        return linkTo(request, "api", "v1", "posts", "create", format("id=%s", id));
    }
}
