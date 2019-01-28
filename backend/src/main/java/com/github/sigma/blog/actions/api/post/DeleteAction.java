package com.github.sigma.blog.actions.api.post;

import com.github.sigma.blog.actions.api.BaseRestAction;
import com.github.sigma.blog.domain.PostService;
import org.apache.struts2.convention.annotation.*;

import javax.inject.Inject;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static com.opensymphony.xwork2.Action.*;
import static java.util.logging.Level.INFO;

/**
 * http delete :8080/blog/api/post/new^?postText=%23%20Hello%20World%21%0AA%20blog%0A%0A_Let%27s%20get%20started%21_
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
public class DeleteAction extends BaseRestAction {

    static final Logger log = LogManager.getLogManager().getLogger(DeleteAction.class.getName());

    @Inject
    PostService postService;

    String id;

    public void setId(final String id) {
        if (log.isLoggable(INFO)) log.info("deletion post with id: " + id);
        this.id = id;
    }

    @Override
    @Action("delete")
    public String execute() {
        try { postService.delete(id); }
        catch (Throwable e) { return ERROR; }
        return SUCCESS;
    }
}
