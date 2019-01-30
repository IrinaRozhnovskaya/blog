package com.github.sigma.blog.actions.api.posts;

import com.github.sigma.blog.actions.BaseRestResource;
import com.github.sigma.blog.domain.PostNotFoundException;
import com.github.sigma.blog.domain.PostResponse;
import com.github.sigma.blog.domain.PostService;
import lombok.Getter;
import lombok.Setter;
import org.apache.struts2.convention.annotation.*;

import javax.inject.Inject;
import java.util.UUID;

import static com.github.sigma.blog.actions.BaseRestResource.Constants.*;
import static com.opensymphony.xwork2.Action.ERROR;
import static com.opensymphony.xwork2.Action.INPUT;
import static java.lang.String.format;

/**
 * http put http://127.0.0.1:8080/blog/api/v1/posts/update id=88888888-4444-4444-4444-000000000000 title=title body=ololo
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
    @Result(type = json, params = { "root", "updated" }),
})
public class UpdatePostResource extends BaseRestResource {

    @Inject
    PostService postService;

    @Setter String id;
    @Setter String title;
    @Setter String body;

    @Getter PostResponse updated;

    @Action("update")
    public String update() {
        final UUID uuid = UUID.fromString(id);
        try {updated = postService.editOnePost(uuid, title, body);}
        catch (PostNotFoundException e) {
            addActionError(format("Post with id '%s' wasn't found: '%s'", id, e.getLocalizedMessage()));
            return ERROR;
        }
        catch (Throwable e) {
            addActionError(format("Unexpected error: '%s'", e.getLocalizedMessage()));
            throw new RuntimeException(e);
        }
        return SUCCESS;
    }
}
