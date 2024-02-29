package com.gplayus.backend.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.gplayus.backend.config.JpaAuditingConfig;
import com.gplayus.backend.domain.App;
import com.gplayus.backend.domain.Member;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DisplayName("App CRUD Test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(JpaAuditingConfig.class)
@DataJpaTest
class AppRepositoryTests {

    private final AppRepository appRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public AppRepositoryTests(MemberRepository memberRepository, AppRepository appRepository) {
        this.appRepository = appRepository;
        this.memberRepository = memberRepository;
    }

    @DisplayName("Create App")
    @Test
    void createApp() {
        // given
        long count = appRepository.count();

        // when
        Member member = memberRepository.findById(1L).orElseThrow();
        App app = App.of(member, "신조어 퀴즈", "신조어 퀴즈를 학습할 수 있는 앱 서비스", "https://play.google.com/",
                "https://play.google.com/",
                null, null);
        appRepository.save(app);

        // then
        long readedCount = appRepository.count();
        assertThat(readedCount).isEqualTo(count + 1);
    }

    @DisplayName("Read App")
    @Test
    void readApp() {
        // given
        long count = 500L;

        // when
        List<App> apps = appRepository.findAll();

        // then
        long readedCount = apps.size();
        assertThat(readedCount).isEqualTo(count);
    }

    @DisplayName("Update App")
    @Test
    void updateApp() {
        // given
        App app = appRepository.findById(1L).orElseThrow();
        String updatedName = "신조어 퀴즈 2";

        // when
        app.setName(updatedName);
        App updatedApp = appRepository.save(app);

        // then
        assertThat(updatedApp).hasFieldOrPropertyWithValue("name", updatedName);
    }

    @DisplayName("Delete App")
    @Test
    void deleteApp() {
        // given
        long count = appRepository.count();

        // when
        appRepository.deleteById(1L);

        // then
        long readedCount = appRepository.count();
        assertThat(readedCount).isEqualTo(count - 1);
    }
}