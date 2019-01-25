package com.github.sigma.blog.domain;

import java.util.UUID;

import static java.lang.String.format;

public class PostNotFoundException extends Exception {
    public PostNotFoundException(UUID id) {
        super(format("Post wasn't found.", id));
    }
}
