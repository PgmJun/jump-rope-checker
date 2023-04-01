package com.jul.jumpropetornamentchecker.domain.attend;

import com.jul.jumpropetornamentchecker.domain.Competition;
import com.jul.jumpropetornamentchecker.domain.Organization;
import com.jul.jumpropetornamentchecker.domain.department.Department;
import com.jul.jumpropetornamentchecker.domain.event.Event;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionAttend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cmptAttendId;

    @ManyToOne
    @JoinColumn(name = "competitionId")
    private Competition competition;

    @ManyToOne
    @JoinColumn(name = "orgId")
    private Organization organization;

    @OneToOne
    @JoinColumn(name = "departId")
    private Department department;

    @OneToOne
    @JoinColumn(name = "eventId")
    private Event event;

    @Column
    @NotNull
    private String playerName;

    @Column
    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender playerGender;

    @Column
    @NotNull
    private String playerBirth;

    @Column
    @NotNull
    private String playerTel;
}
