package com.github.vladbaton.interceptor;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;

public class ConstraintViolationInterceptor implements ContainerRequestFilter, ContainerResponseFilter {
    @Override
    public void filter(final ContainerRequestContext requestContext) {
        if (requestContext.getMethod().equalsIgnoreCase("GET")) {
            //чтоооооо
        }
    }

    @Override
    public void filter(final ContainerRequestContext requestContext, final ContainerResponseContext responseContext) {

    }
}
