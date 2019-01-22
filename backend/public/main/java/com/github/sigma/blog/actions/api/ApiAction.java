package com.github.sigma.blog.actions.api;

import org.apache.struts2.convention.annotation.*;

import java.util.Map;

@Namespace("/")
@Result(type = "json")
@ParentPackage("json-default")
public class ApiAction extends BaseRestAction {

    public Map<String, Object> getLinks() {
        return getPredefinedLinks();
    }

    @Actions({
            @Action("/api"),
            @Action("/api*"),
            @Action("/api/*")
    })
    public String execute() {
        return SUCCESS;
    }
}
