package com.jul.jumpropetornamentchecker.domain.attend;

import com.jul.jumpropetornamentchecker.domain.CompetitionEvent;
import com.jul.jumpropetornamentchecker.domain.department.Department;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventAttend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventAttendId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cmptEventId")
    private CompetitionEvent competitionEvent;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departId")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "cmptAttendId")
    private CompetitionAttend competitionAttend;

    @Column
    private int grade;

    @Column
    private int cnt;
}
