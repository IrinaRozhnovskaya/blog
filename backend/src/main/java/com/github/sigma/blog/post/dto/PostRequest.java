package com.github.sigma.blog.post.dto;

public class PostRequest {

    final String postText;

    public static PostRequest of(String postText) {
        return new PostRequest(postText);
    }

    private PostRequest(String postText) {
        this.postText = postText;
    }

    public String getPostText() {
        return postText;
    }
}
