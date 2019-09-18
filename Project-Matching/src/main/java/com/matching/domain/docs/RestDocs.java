package com.matching.domain.docs;

import org.springframework.http.HttpHeaders;

import java.net.URI;

public class RestDocs {
    private static final String DOCS_LINK = "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"";
    private URI uri;

    public RestDocs(URI uri) {
        this.uri = uri;
    }

    public HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(this.uri);
        headers.add("Link", DOCS_LINK);

        return headers;
    }
}
