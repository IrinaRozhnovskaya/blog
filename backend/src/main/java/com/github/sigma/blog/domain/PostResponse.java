package com.github.sigma.blog.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostResponse {

    static final SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm z");

    final UUID id;

    @Getter
    final  String title, body;

    final Date lastModifiedAt;

    public static PostResponse of(Post post) {
        return new PostResponse(post.getId(), post.getTitle(), post.getBody(), post.getLastModifiedAt());
    }

    public String getId() {
        return id.toString();
    }

    public String getLastModifiedAt() {
        return format.format(lastModifiedAt);
    }

}
