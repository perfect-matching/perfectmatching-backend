package com.matching.domain.enums;

import lombok.Getter;

import java.util.Random;

@Getter
public enum PositionType {

    DEVELOPER("개발자"),
    DESIGNER("디자이너"),
    MARKETER("마케터"),
    PLANNER("기획자"),
    ETC("기타"),
    LEADER("리더");

    private String position;

    PositionType(String position) {
        this.position = position;
    }

    public static PositionType getRandomPositionType() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}
