package com.github.sigma.blog.actions;

import com.github.sigma.blog.domain.PostNotFoundException;
import com.github.sigma.blog.domain.PostResponse;
import com.github.sigma.blog.domain.PostService;
import com.opensymphony.xwork2.ActionSupport;
import lombok.Getter;
import lombok.extern.java.Log;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.*;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.github.sigma.blog.actions.BaseRestResource.Constants.*;
import static com.opensymphony.xwork2.Action.*;
import static java.lang.String.format;

@Log
@Results({
    @Result(type = json),
    @Result(name = INPUT, type = json),
    @Result(name = ERROR, type = json, params = {
        globalErrorCodeKey, globalErrorCodeValue,
        globalErrorStatusCodeKey, globalErrorStatusCodeValue,
    })
})
@ExceptionMappings({
    @ExceptionMapping(
        result = SUCCESS,
        exception = globalMappingException,
        params = { globalExceptionMappingParamKey, globalExceptionMappingParamValue }
    ),
})
@InterceptorRef(json)
@ParentPackage(jsonDefault)
public abstract class BaseRestResource extends ActionSupport {

    public static class Constants {
        public static final String json = "json";
        public static final String jsonDefault = "json-default";
        public static final String globalErrorCodeKey = "errorCode";
        public static final String globalErrorCodeValue = "-1";
        public static final String globalErrorStatusCodeKey = "statusCode";
        public static final String globalErrorStatusCodeValue = "400";
        public static final String globalMappingException = "java.lang.Exception";
        public static final String globalExceptionMappingParamKey = "param1";
        public static final String globalExceptionMappingParamValue = "val1";
        private Constants() {}
    }

    @Inject
    Hateoas hateoas;

    @PostConstruct
    public void init() {
        add_Links(" _self           ", "/api");
        add_Links(" health status   ", "/api/health");
        add_Links(" find all posts  ", "/api/v1/posts/find-all");
        add_Links(" create post     ", "/api/v1/posts/create     data={markdown}");
        add_Links(" find post by id ", "/api/v1/posts/find-by-id id={uuid}");
        add_Links(" update posts    ", "/api/v1/posts/update     id={uuid} data={markdown}");
        add_Links(" remove posts    ", "/api/v1/posts/delete     id={uuid}");
    }

    @Getter
    protected Map<String, Object> predefinedLinks = new HashMap<>();

    protected void add_Links(String key, String value) {
        final HttpServletRequest request = ServletActionContext.getRequest();
        predefinedLinks.put(key, hateoas.linkTo(request, value));
    }

    protected PostResponse saveOrUpdatePost(PostService postService, UUID uuid, String id, String data) {
        try {
            return null == data
                ? postService.findOnePost(uuid)
                : postService.editOnePost(uuid, data);
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
