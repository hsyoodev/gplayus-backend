package com.gplayus.backend.domain;

import com.gplayus.backend.enums.TesterStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Entity
public class Tester extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private TesterStatus testerStatus;

    protected Tester() {
    }

    private Tester(TesterStatus testerStatus) {
        this.testerStatus = testerStatus;
    }

    public static Tester of() {
        return new Tester(TesterStatus.APPLIED);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tester tester)) {
            return false;
        }
        return Objects.equals(id, tester.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
