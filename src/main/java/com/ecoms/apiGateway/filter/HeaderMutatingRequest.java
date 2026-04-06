package com.ecoms.apiGateway.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.*;

public class HeaderMutatingRequest extends HttpServletRequestWrapper {

    private final Map<String, String> additionalHeaders;

    public HeaderMutatingRequest(HttpServletRequest request, String userEmail, String userRoles) {
        super(request);
        this.additionalHeaders = new HashMap<>();
        this.additionalHeaders.put("X-User-Email", userEmail);
        this.additionalHeaders.put("X-User-Roles", userRoles);
    }

    @Override
    public String getHeader(String name) {
        if (additionalHeaders.containsKey(name)) {
            return additionalHeaders.get(name);
        }
        return super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        if (additionalHeaders.containsKey(name)) {
            return Collections.enumeration(List.of(additionalHeaders.get(name)));
        }
        return super.getHeaders(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        Set<String> names = new HashSet<>(additionalHeaders.keySet());
        Enumeration<String> originalNames = super.getHeaderNames();
        while (originalNames != null && originalNames.hasMoreElements()) {
            names.add(originalNames.nextElement());
        }
        return Collections.enumeration(names);
    }
}

