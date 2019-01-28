package com.github.sigma.blog.actions.api.posts;

import com.github.sigma.blog.actions.BaseRestResource;
import com.github.sigma.blog.domain.PostResponse;
import com.github.sigma.blog.domain.PostService;
import lombok.Getter;
import lombok.Setter;
import org.apache.struts2.convention.annotation.*;

import javax.inject.Inject;
import java.util.UUID;

import static com.github.sigma.blog.actions.BaseRestResource.Constants.*;
import static com.opensymphony.xwork2.Action.*;

/**
 * http posts http://127.0.0.1:8080/blog/api/post/one id=88888888-4444-4444-4444-000000000000
 */

@Namespaces({
    @Namespace("/api/posts"),
    @Namespace("/api/v1/posts") })
@Results({
    @Result(name = INPUT, type = json),
    @Result(name = ERROR, type = json, params = {
        globalErrorCodeKey, globalErrorCodeValue,
        globalErrorStatusCodeKey, globalErrorStatusCodeValue,
        "ignoreHierarchy", "false",
        "includeProperties", ".*",
    }),
    @Result(type = json, params = { "root", "post" }),
})
@ExceptionMappings({
    @ExceptionMapping(
        result = SUCCESS,
        exception = globalMappingException,
        params = { globalExceptionMappingParamKey, globalExceptionMappingParamValue }
    ),
})
public class FindPostByIdResource extends BaseRestResource {

    @Inject
    PostService postService;

    @Setter String id;
    @Getter PostResponse post;

    @Action("find-by-id")
    public String execute() {
        final UUID uuid = UUID.fromString(id);
        post = saveOrUpdatePost(postService, uuid, id, null);
        return null == post ? ERROR : SUCCESS;
    }
}
