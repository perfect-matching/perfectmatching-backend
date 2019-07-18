package com.matching.repository;

import lombok.Getter;

@Getter
public class ProjectNotFoundException extends RuntimeException {

    private final Long id;

    public ProjectNotFoundException(final Long id) {
        super("Project could not be found with id: " + id);
        this.id = id;
    }
}
