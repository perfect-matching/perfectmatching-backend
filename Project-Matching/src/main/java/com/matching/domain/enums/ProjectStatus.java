package com.matching.domain.enums;

import lombok.Getter;

import java.util.Random;

@Getter
public enum ProjectStatus {

    RECRUIT("모집중"),
    PROGRESS("진행중"),
    COMPLETE("완료"),
    END("종료");

    private String status;

    ProjectStatus(String status) {
        this.status = status;
    }

    public static ProjectStatus getRandomProjectStatus() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}
