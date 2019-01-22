package com.github.sigma.blog.actions.api;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.opensymphony.xwork2.Action.ERROR;
import static com.opensymphony.xwork2.Action.INPUT;

@Results({
        @Result(type = "json"),
        @Result(name = INPUT, type = "json"),
        @Result(name = ERROR, type = "json")
})
@ParentPackage("json-default")
public abstract class BaseRestAction extends ActionSupport {

    @Inject
    HATEOAS hateoas;

    @PostConstruct
    public void init() {
        //add_Links("fail         GET", "api/fail");
        add_Links("_self        GET", "api");
        add_Links("health       GET", "api/health");
        add_Links("find all     GET", "api/post/all");
        add_Links("find one     GET", "api/post/one");
        add_Links("create new: POST", "api/post/new");
        add_Links("update new:  PUT", "api/post/edit");
    }

    private Map<String, Object> _links = new HashMap<>();

    protected void add_Links(String key, String value) {
        final HttpServletRequest request = ServletActionContext.getRequest();
        _links.put(key, hateoas.linkTo(request, value));
    }

    protected Map<String, Object> getPredefinedLinks() {
        return _links;
    }
}
