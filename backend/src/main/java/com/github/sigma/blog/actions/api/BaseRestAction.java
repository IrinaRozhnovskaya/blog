package com.github.sigma.blog.actions.api;

import com.github.sigma.blog.actions.Hateoas;
import com.github.sigma.blog.domain.PostNotFoundException;
import com.github.sigma.blog.domain.PostResponse;
import com.github.sigma.blog.domain.PostService;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.*;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static com.opensymphony.xwork2.Action.*;
import static java.lang.String.format;

@Results({
    @Result(type = "json"),
    @Result(name = INPUT, type = "json"),
    @Result(name = ERROR, type = "json", params = {
        "errorCode", "-1",
        "statusCode", "400",
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
public abstract class BaseRestAction extends ActionSupport {

    private static final Logger log = LogManager.getLogManager().getLogger(BaseRestAction.class.getName());

    @Inject
    Hateoas hateoas;

    @PostConstruct
    public void init() {
        add_Links("      _self -    GET", "/api");
        add_Links("     health -    GET", "/api/health");
        add_Links("   find all -    GET", "/api/post/all");
        add_Links(" create new -   POST", "/api/post/new?postText={markdown}");
        add_Links("   find one -    GET", "/api/post/one?id={uuid}");
        add_Links("update post -    PUT", "/api/post/edit?id={uuid}&postText={markdown}");
        add_Links("remove post - DELETE", "/api/post/delete?id={uuid}");
    }

    private Map<String, Object> _links = new HashMap<>();

    protected void add_Links(String key, String value) {
        final HttpServletRequest request = ServletActionContext.getRequest();
        _links.put(key, hateoas.linkTo(request, value));
    }

    protected Map<String, Object> getPredefinedLinks() {
        return _links;
    }

    protected PostResponse saveOrUpdatePost(PostService postService, UUID uuid, String id, String postText) {
        try {
            return null == postText
                ? postService.findOnePost(uuid)
                : postService.editOnePost(uuid, postText);
        }
        catch (PostNotFoundException e) {
            addActionError(format("Post with id '%s' wasn't found: '%s'", id, e.getLocalizedMessage()));
            return null;
        }
        catch (Throwable e) {
            addActionError(format("Unexpected error: '%s'", e.getLocalizedMessage()));
            log.severe(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }
}
