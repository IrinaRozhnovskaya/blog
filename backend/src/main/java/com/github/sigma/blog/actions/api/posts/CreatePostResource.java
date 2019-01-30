package com.github.sigma.blog.actions.api.posts;

import com.github.sigma.blog.actions.BaseRestResource;
import com.github.sigma.blog.actions.Hateoas;
import com.github.sigma.blog.domain.PostRequest;
import com.github.sigma.blog.domain.PostResponse;
import com.github.sigma.blog.domain.PostService;
import lombok.Getter;
import lombok.Setter;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.github.sigma.blog.actions.BaseRestResource.Constants.*;
import static com.opensymphony.xwork2.Action.ERROR;
import static com.opensymphony.xwork2.Action.INPUT;
import static javax.ws.rs.core.HttpHeaders.LOCATION;


/**
 * ```markdown
 * # Hello World
 * A blog
 *
 * _Let's get started!_
 * ```
 *
 * http post :8080/blog/api/v1/posts/create title=title body="%23%20Hello%20World%21%0AA%20blog%0A%0A_Let%27s%20get%20started%21_"
 */

@Namespaces({
    @Namespace("/api/posts"),
    @Namespace("/api/v1/posts") })
@Results({
    @Result(name = INPUT, type = json),
    @Result(name = ERROR, type = json, params = {
        globalErrorCodeKey, globalErrorCodeValue,
        globalErrorStatusCodeKey, globalErrorStatusCodeValue,
    }),
    @Result(type = json, params = { "root", "created" }),
})
public class CreatePostResource extends BaseRestResource {

    @Inject
    Hateoas hateoas;

    @Inject
    PostService postService;

    @Setter String title;
    @Setter String body;
    @Getter PostResponse created;

    @Action("create")
    public String create() {
        final PostRequest payload = PostRequest.of(title, body);
        created = postService.save(payload);
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String location = hateoas.getPostLocation(request, created.getId());
        final HttpServletResponse resp = ServletActionContext.getResponse();
        resp.setHeader(LOCATION, location);
        resp.setStatus(201); // CREATED HTTP created code
        return SUCCESS;
    }
}
