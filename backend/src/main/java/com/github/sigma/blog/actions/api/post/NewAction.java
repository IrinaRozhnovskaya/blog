package com.github.sigma.blog.actions.api.post;

import com.github.sigma.blog.actions.Hateoas;
import com.github.sigma.blog.actions.api.BaseRestAction;
import com.github.sigma.blog.domain.PostService;
import com.github.sigma.blog.domain.PostRequest;
import com.github.sigma.blog.domain.PostResponse;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static com.opensymphony.xwork2.Action.*;
import static javax.ws.rs.core.HttpHeaders.LOCATION;

/**
http post http://127.0.0.1:8080/blog/api/post/new?postText=trololo
 */

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
@Namespace("/api/post")
@ParentPackage("json-default")
public class NewAction extends BaseRestAction {

    @Inject
    Hateoas hateoas;

    @Inject
    PostService postService;

    String postText;

    public void setPostText(final String postText){
        this.postText = postText;
    }

    @Override
    @Action("new")
    public String execute() {
        final Logger log = LogManager.getLogManager().getLogger(NewAction.class.getName());
        log.warning("\n\n postText: " + postText + "\n");
        final PostRequest payload = PostRequest.of(postText);
        log.warning("\n\n payload: " + payload + "\n");
        final PostResponse savedPost = postService.save(payload);
        log.warning("\n\n savedPost: " + savedPost + "\n");
        final HttpServletRequest request = ServletActionContext.getRequest();
        log.warning("\n\n request: " + request + "\n");
        final String location = hateoas.getPostLocation(request, savedPost.getId());
        final HttpServletResponse response = ServletActionContext.getResponse();
        log.warning("\n\n response: " + response + "\n");
        response.setHeader(LOCATION, location);
        response.setStatus(201); // CREATED HTTP response code
        return SUCCESS;
    }
}
