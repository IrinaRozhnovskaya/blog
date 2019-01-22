package com.github.sigma.blog.post;

import java.util.UUID;

import static java.lang.String.format;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(UUID id) {
        super(format("Post wasn't found.", id));
    }
}
