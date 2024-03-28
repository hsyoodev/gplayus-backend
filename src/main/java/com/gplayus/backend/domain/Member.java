package com.gplayus.backend.domain;

import com.gplayus.backend.enums.MemberRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends Base {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(length = 100, nullable = false)
    private String email;

    @Setter
    @Column(length = 100, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole role;

    @OneToMany(mappedBy = "member")
    private List<App> apps;

    @OneToMany(mappedBy = "member")
    private List<Tester> testers;

    private Member(Long id, String email, String name, MemberRole role, List<App> apps, List<Tester> testers, String createdBy, String modifiedBy) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
        this.apps = apps;
        this.testers = testers;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
    }

    public static Member of(String email, String name, MemberRole role, String createdBy,
                            String modifiedBy) {
        return new Member(null, email, name, role, new ArrayList<>(), new ArrayList<>(), createdBy, modifiedBy);
    }
}