package com.gplayus.backend.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.gplayus.backend.config.JpaAuditingConfig;
import com.gplayus.backend.domain.App;
import com.gplayus.backend.domain.Member;
import com.gplayus.backend.domain.Tester;
import com.gplayus.backend.enums.TesterStatus;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DisplayName("Tester CRUD Test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(JpaAuditingConfig.class)
@DataJpaTest
class TesterRepositoryTests {

    private final TesterRepository testerRepository;
    private final MemberRepository memberRepository;
    private final AppRepository appRepository;

    @Autowired
    public TesterRepositoryTests(MemberRepository memberRepository, AppRepository appRepository,
            TesterRepository testerRepository) {
        this.testerRepository = testerRepository;
        this.memberRepository = memberRepository;
        this.appRepository = appRepository;
    }

    @DisplayName("Create Tester")
    @Test
    void createTester() {
        // given
        long count = testerRepository.count();

        // when
        Member member = memberRepository.findById(1L).orElseThrow();
        App app = appRepository.findById(1L).orElseThrow();
        Tester tester = Tester.of(member, app);
        testerRepository.save(tester);

        // then
        long readedCount = testerRepository.count();
        assertThat(readedCount).isEqualTo(count + 1);
    }

    @DisplayName("Read Tester")
    @Test
    void readTester() {
        // given
        long count = 500L;

        // when
        List<Tester> testers = testerRepository.findAll();

        // then
        long readedCount = testers.size();
        assertThat(readedCount).isEqualTo(count);
    }

    @DisplayName("Update App")
    @Test
    void updateTester() {
        // given
        Tester tester = testerRepository.findById(1L).orElseThrow();
        TesterStatus updatedTesterStatus = TesterStatus.IN_PROGRESS;

        // when
        tester.setStatus(updatedTesterStatus);
        Tester updatedTester = testerRepository.save(tester);

        // then
        assertThat(updatedTester).hasFieldOrPropertyWithValue("status",
                updatedTesterStatus);
    }

    @DisplayName("Delete App")
    @Test
    void deleteTester() {
        // given
        long count = testerRepository.count();

        // when
        testerRepository.deleteById(1L);

        // then
        long readedCount = testerRepository.count();
        assertThat(readedCount).isEqualTo(count - 1);
    }
}