package com.gplayus.backend.service;

import com.gplayus.backend.dto.response.AppResponse;
import com.gplayus.backend.dto.response.TesterResponse;
import com.gplayus.backend.repository.AppRepository;
import com.gplayus.backend.repository.TesterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {
    private final AppRepository appRepository;
    private final TesterRepository testerRepository;

    public Page<AppResponse> findAppsByMemberId(Long memberId, Pageable pageable) {
        return appRepository.findByMemberId(memberId, pageable)
                .map(AppResponse::fromApps);
    }

    public Page<TesterResponse> findAppsByTesterId(Long testerId, Pageable pageable) {
        return testerRepository.findByMemberId(testerId, pageable)
                .map(TesterResponse::fromTester);
    }
}
