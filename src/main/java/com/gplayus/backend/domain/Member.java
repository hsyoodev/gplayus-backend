package com.gplayus.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class Member extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(length = 100, nullable = false)
    private String email;

    @Setter
    @Column(length = 100, nullable = false)
    private String name;

    @OneToMany(mappedBy = "member")
    private List<App> apps = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Tester> testers = new ArrayList<>();

    protected Member() {
    }

    private Member(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public static Member of(String email, String name) {
        return new Member(email, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Member member)) {
            return false;
        }
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}