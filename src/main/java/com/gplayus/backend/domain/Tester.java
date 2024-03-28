package com.gplayus.backend.domain;

import com.gplayus.backend.enums.TesterStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Tester extends Base {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TesterStatus status;

    @ManyToOne
    private Member member;

    @ManyToOne
    private App app;

    public static Tester of(Member member, App app) {
        return new Tester(null, TesterStatus.APPLIED, member, app);
    }
}
