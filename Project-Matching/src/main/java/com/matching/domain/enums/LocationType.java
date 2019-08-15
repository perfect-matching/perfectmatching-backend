package com.matching.domain.enums;

import lombok.Getter;

import java.util.Random;

@Getter
public enum LocationType {

    SEOUL("서울"),
    BUSAN("부산"),
    DAEGU("대구"),
    GWANGJU("광주"),
    DAEJEON("대전"),
    ULSAN("울산"),
    CHUNGCHEONGBUKDO("충청북도"),
    CHUNGCHEONGNAMDO("충청남도"),
    JEOLLABUKDO("전라북도"),
    JEOLLANAMDO("전라남도"),
    GYENONGSNAGBUKDO("경상북도"),
    GYENONGSANGNAMDO("경상남도"),
    JEJUDO("제주도");

    private String location;

    LocationType(String location) {
        this.location = location;
    }

    public static LocationType getRandomLocationType() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}
