package com.github.sigma.blog.actions.api.health;

import com.github.sigma.blog.actions.BaseRestResource;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.json.annotations.JSON;

import javax.enterprise.context.RequestScoped;

@RequestScoped
@Namespace("/api")
public class HealthResource extends BaseRestResource {

    String status = "DOWN";

    @JSON(name = "status")
    public String getStatus() {
        return status;
    }

    @Override
    @Action("health")
    public String execute() throws Exception {
        status = "UP";
        return SUCCESS;
    }

    @Action(
            value = "fail",
            exceptionMappings = {
                    @ExceptionMapping(exception = "java.lang.Throwable", result = ERROR)
            }
    )
    public String failImmediately() {
        if (true) throw new RuntimeException("should fail immediately");
        return ERROR;
    }
}
