package com.matching.domain.enums;

import lombok.Getter;

import java.util.Random;

@Getter
public enum UserProjectStatus {

    WAIT("대기중"),
    MATCHING("매칭완료"),
    FAIL("매칭실패");

    private String status;

    UserProjectStatus(String status) {
        this.status = status;
    }

    public static UserProjectStatus getRandomUserProjectStatus() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}
