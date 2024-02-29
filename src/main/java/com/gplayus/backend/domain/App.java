package com.gplayus.backend.domain;

import com.gplayus.backend.enums.AppStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;

@Getter
@ToString(callSuper = true)
@Entity
public class App extends Base {

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
    private List<Tester> testers = new ArrayList<>();

    protected App() {
    }

    private App(Member member, String name, String description, String androidUrl, String webUrl,
            LocalDateTime endedDate, String endedBy, AppStatus status) {
        this.name = name;
        this.description = description;
        this.androidUrl = androidUrl;
        this.webUrl = webUrl;
        this.endedDate = endedDate;
        this.endedBy = endedBy;
        this.status = status;
        this.member = member;
    }

    public static App of(Member member, String name, String description, String androidUrl,
            String webUrl,
            LocalDateTime endedDate, String endedBy) {
        return new App(member, name, description, androidUrl, webUrl, endedDate, endedBy,
                AppStatus.IN_PROGRESS);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof App app)) {
            return false;
        }
        return Objects.equals(id, app.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
