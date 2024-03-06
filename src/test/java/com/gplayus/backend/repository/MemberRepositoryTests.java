package com.gplayus.backend.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.gplayus.backend.config.JpaAuditingConfig;
import com.gplayus.backend.domain.Member;
import com.gplayus.backend.enums.MemberRole;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DisplayName("Member CRUD Test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(JpaAuditingConfig.class)
@DataJpaTest
class MemberRepositoryTests {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberRepositoryTests(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @DisplayName("Create Member")
    @Test
    void createMember() {
        // given
        long count = memberRepository.count();

        // when
        Member member = Member.of("dev@gmail.com", "dev", MemberRole.ROLE_USER, "dev", "dev");
        memberRepository.save(member);

        // then
        long readedCount = memberRepository.count();
        assertThat(readedCount).isEqualTo(count + 1);
    }

    @DisplayName("Read Member")
    @Test
    void readMember() {
        // given
        long count = 1000L;

        // when
        List<Member> members = memberRepository.findAll();

        // then
        long readedCount = members.size();
        assertThat(readedCount).isEqualTo(count);
    }

    @DisplayName("Update Member")
    @Test
    void updateMember() {
        // given
        Member member = memberRepository.findById(1L).orElseThrow();
        String updatedName = "prod";

        // when
        member.setName(updatedName);
        Member updatedMember = memberRepository.save(member);

        // then
        assertThat(updatedMember).hasFieldOrPropertyWithValue("name", updatedName);
    }

    @DisplayName("Delete Member")
    @Test
    void deleteMember() {
        // given
        long count = memberRepository.count();

        // when
        memberRepository.deleteById(1L);

        // then
        long readedCount = memberRepository.count();
        assertThat(readedCount).isEqualTo(count - 1);
    }
}