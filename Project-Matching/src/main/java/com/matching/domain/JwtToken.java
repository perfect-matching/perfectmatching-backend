package com.matching.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@Table
@NoArgsConstructor
@EqualsAndHashCode(of = "idx")
public class JwtToken implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false, length = 500)
    private String token;

    @Builder
    public JwtToken(String token) {
        this.token = token;
    }

}
