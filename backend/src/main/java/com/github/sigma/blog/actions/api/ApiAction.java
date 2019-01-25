package com.github.sigma.blog.actions.api;

import org.apache.struts2.convention.annotation.*;

import java.util.Map;

import static com.opensymphony.xwork2.Action.*;

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
@Namespace("/")
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
