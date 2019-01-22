package com.github.sigma.blog.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class PostResponse {

    static final SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm z");

    final String body;
    final Date when;
    final UUID id;

    private PostResponse(String body, Date when, UUID id) {
        this.body = body;
        this.when = when;
        this.id = id;
    }

    public static PostResponse of(Post post) {
        return new PostResponse(post.getBody(), post.getUpdatedAt(), post.getId());
    }

    public String getId() {
        return id.toString();
    }

    public String getBody() {
        return body;
    }

    public String getWhen() {
        return format.format(when);
    }


}
