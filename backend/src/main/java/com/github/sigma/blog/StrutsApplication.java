package com.github.sigma.blog;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

import javax.servlet.annotation.WebFilter;

// TODO: @WebFilter(urlPatterns = "/*")
@WebFilter(urlPatterns = "/api/*")
public class StrutsApplication extends StrutsPrepareAndExecuteFilter { }
