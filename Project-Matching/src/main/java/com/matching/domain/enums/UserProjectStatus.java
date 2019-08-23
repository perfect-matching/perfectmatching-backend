package com.matching.domain.enums;

import lombok.Getter;

import java.util.Random;

@Getter
public enum UserProjectStatus {

    WAIT("대기"),
    MATCHING("승인"),
    FAIL("거절");

    private String status;

    UserProjectStatus(String status) {
        this.status = status;
    }

    public static UserProjectStatus getRandomUserProjectStatus() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}
