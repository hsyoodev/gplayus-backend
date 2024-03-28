package com.gplayus.backend.domain;

import com.gplayus.backend.enums.AppStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class App extends Base {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(length = 100, nullable = false)
    private String name;

    @Setter
    @Column(columnDefinition = "TEXT")
    private String description;

    @Setter
    @URL
    @Column(length = 100, nullable = false)
    private String androidUrl;

    @Setter
    @URL
    @Column(length = 100, nullable = false)
    private String webUrl;

    @Setter
    @Column
    private LocalDateTime endedDate;

    @Setter
    @Column(length = 100)
    private String endedBy;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppStatus status;

    @ManyToOne
    private Member member;

    @OneToMany(mappedBy = "app")
    private List<Tester> testers;

    public static App of(Member member, String name, String description, String androidUrl, String webUrl) {
        return new App(null, name, description, androidUrl, webUrl, null, null, AppStatus.IN_PROGRESS, member, new ArrayList<>());
    }
}
