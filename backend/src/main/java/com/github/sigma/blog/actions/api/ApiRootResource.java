package com.github.sigma.blog.actions.api;

import com.github.sigma.blog.actions.BaseRestResource;
import lombok.Getter;
import org.apache.struts2.convention.annotation.*;

import java.util.Map;

import static com.github.sigma.blog.actions.BaseRestResource.Constants.*;
import static com.opensymphony.xwork2.Action.*;

@Results({
    @Result(name = INPUT, type = json),
    @Result(name = ERROR, type = json, params = {
        globalErrorCodeKey, globalErrorCodeValue,
        globalErrorStatusCodeKey, globalErrorStatusCodeValue,
        "ignoreHierarchy", "false",
        "includeProperties", ".*",
    }),
    @Result(type = json, params = { "root", "links" }),
})
@ExceptionMappings({
    @ExceptionMapping(
        result = SUCCESS,
        exception = globalMappingException,
        params = { globalExceptionMappingParamKey, globalExceptionMappingParamValue }
    ),
})
@Namespaces({
    @Namespace("/"),
    @Namespace("/*"),
    @Namespace("/*/*"),
    @Namespace("/*/*/*"),
})
@InterceptorRef(json)
@ParentPackage(jsonDefault)
public class ApiRootResource extends BaseRestResource {

    @Getter Map<String, Object> links = getPredefinedLinks();

    @Override
    @Actions({
        @Action("*"),
        @Action("api")
    })
    public String execute() {
        return SUCCESS;
    }
}
