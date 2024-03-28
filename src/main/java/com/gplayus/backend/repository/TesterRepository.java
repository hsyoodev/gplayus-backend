package com.gplayus.backend.repository;

import com.gplayus.backend.domain.Tester;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TesterRepository extends JpaRepository<Tester, Long> {
    Page<Tester> findByMemberId(Long memberId, Pageable pageable);
}
