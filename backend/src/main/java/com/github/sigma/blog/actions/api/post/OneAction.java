package com.github.sigma.blog.actions.api.post;

import com.github.sigma.blog.actions.api.BaseRestAction;
import com.github.sigma.blog.post.PostService;
import com.github.sigma.blog.post.dto.PostResponse;
import org.apache.struts2.convention.annotation.*;

import javax.inject.Inject;

import java.util.UUID;

import static com.opensymphony.xwork2.Action.*;

/**
 * http post http://127.0.0.1:8080/blog/api/post/one id=88888888-4444-4444-4444-000000000000
 */

@Results({
        @Result(type = "json"),
        @Result(name = INPUT, type = "json"),
        @Result(name = ERROR, type = "json", params = {
                "statusCode", "404",
                "errorCode", "404123",
                "ignoreHierarchy", "false",
                "includeProperties", ".*",
        })
})
@ExceptionMappings({
        @ExceptionMapping(
                exception = "java.lang.Exception",
                params = { "param1", "val1" },
                result = SUCCESS
        ),
})
@ParentPackage("json-default")
@InterceptorRef(value = "json")
@Namespace("/api/post") @Action("one")
public class OneAction extends BaseRestAction {

    @Inject
    PostService postService;

    /** 88888888-4444-4444-4444-000000000000 */
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    private PostResponse response;

    public PostResponse getPostResponse() {
        return response;
    }

    @Override
    public String execute() throws Exception {
        final UUID uuid = UUID.fromString(id);
        response = postService.findOnePost(uuid);
        return SUCCESS;
    }
}
