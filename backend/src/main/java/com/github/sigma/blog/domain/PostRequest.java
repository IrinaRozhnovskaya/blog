package com.github.sigma.blog.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(staticName = "of")
public class PostRequest {

    @Getter
    final String title, body;
}
