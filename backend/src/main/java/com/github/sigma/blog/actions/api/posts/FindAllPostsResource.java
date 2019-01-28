package com.github.sigma.blog.actions.api.posts;

import com.github.sigma.blog.actions.BaseRestResource;
import com.github.sigma.blog.domain.PostResponse;
import com.github.sigma.blog.domain.PostService;
import lombok.Getter;
import org.apache.struts2.convention.annotation.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static com.github.sigma.blog.actions.BaseRestResource.Constants.*;
import static com.opensymphony.xwork2.Action.ERROR;
import static com.opensymphony.xwork2.Action.INPUT;

@Namespaces({
    @Namespace("/api/posts"),
    @Namespace("/api/v1/posts") })
@Results({
    @Result(name = INPUT, type = json),
    @Result(name = ERROR, type = json, params = {
        globalErrorCodeKey, globalErrorCodeValue,
        globalErrorStatusCodeKey, globalErrorStatusCodeValue,
    }),
    @Result(type = json, params = { "root", "posts" }),
})
public class FindAllPostsResource extends BaseRestResource {

    @Inject
    PostService postService;

    @Getter List<PostResponse> posts = new ArrayList<>();

    @Action("find-all")
    public String findAll() {
        posts = postService.findPosts();
        return SUCCESS;
    }
}
