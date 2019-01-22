package com.github.sigma.blog.actions.api.post;

import com.github.sigma.blog.actions.api.BaseRestAction;
import com.github.sigma.blog.domain.PostService;
import com.github.sigma.blog.domain.PostResponse;
import org.apache.struts2.convention.annotation.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static com.opensymphony.xwork2.Action.ERROR;
import static com.opensymphony.xwork2.Action.INPUT;

@Results({
        @Result(type = "json"),
        @Result(name = INPUT, type = "json"),
        @Result(name = ERROR, type = "json")
})
@ApplicationScoped
@Namespace("/api/post")
@ParentPackage("json-default")
public class AllAction extends BaseRestAction {

    @Inject
    PostService postService;

    List<PostResponse> response = new ArrayList<>();

    public List<PostResponse> getPost() {
        return response;
    }

    @Override
    @Action("all")
    public String execute() throws Exception {
        response = postService.findPosts();
        return SUCCESS;
    }
}
