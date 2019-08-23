package com.matching.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table
@NoArgsConstructor
@EqualsAndHashCode(of = "idx")
public class DoneProject implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 100)
    private String summary;

    @Column(nullable = false, length = 5000)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime modifiedDate;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(length = 100)
    private String socialUrl;

    @Column
    private Long projectIdx;

    @OneToMany(mappedBy = "doneProject", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<UsedSkill> usedSkills = new HashSet<>();

    @Builder
    public DoneProject(User user, String title, String summary, String content, LocalDateTime createdDate, LocalDateTime modifiedDate, LocalDateTime startDate, LocalDateTime endDate, String socialUrl, Long projectIdx) {
        this.user = user;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.socialUrl = socialUrl;
        this.projectIdx = projectIdx;
    }

    public void addUsedSkill(UsedSkill usedSkill) {
        usedSkill.setDoneProject(this);
        this.usedSkills.add(usedSkill);
    }
}
