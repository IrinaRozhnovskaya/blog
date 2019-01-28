package com.github.sigma.blog.actions.api.posts;

import com.github.sigma.blog.actions.BaseRestResource;
import com.github.sigma.blog.domain.PostService;
import lombok.Getter;
import lombok.Setter;
import org.apache.struts2.convention.annotation.*;

import javax.inject.Inject;

import static com.github.sigma.blog.actions.BaseRestResource.Constants.*;
import static com.opensymphony.xwork2.Action.ERROR;
import static com.opensymphony.xwork2.Action.INPUT;
import static java.lang.String.format;

/**
 * http delete :8080/blog/api/posts/delete id=88888888-4444-4444-4444-000000000000
 */

@Namespaces({
    @Namespace("/api/posts"),
    @Namespace("/api/v1/posts") })
@Results({
    @Result(type = json),
    @Result(name = INPUT, type = json),
    @Result(name = ERROR, type = json, params = {
        globalErrorCodeKey, globalErrorCodeValue,
        globalErrorStatusCodeKey, globalErrorStatusCodeValue,
    }),
})
public class DeletePostResource extends BaseRestResource {

    @Inject
    PostService postService;

    @Setter String id;
    @Getter String message;

    @Action("delete")
    public String delete() {
        try { postService.delete(id); }
        catch (Throwable e) { message = e.getLocalizedMessage(); return ERROR; }
        message = format("Post with id '%s' was removed.", id);
        return SUCCESS;
    }
}
