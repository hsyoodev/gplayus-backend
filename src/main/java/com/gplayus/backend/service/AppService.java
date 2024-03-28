package com.gplayus.backend.service;

import com.gplayus.backend.domain.App;
import com.gplayus.backend.domain.Member;
import com.gplayus.backend.domain.Tester;
import com.gplayus.backend.dto.request.AppRequest;
import com.gplayus.backend.dto.response.AppResponse;
import com.gplayus.backend.dto.response.TesterResponse;
import com.gplayus.backend.enums.TesterStatus;
import com.gplayus.backend.exception.AppException;
import com.gplayus.backend.exception.MemberException;
import com.gplayus.backend.exception.TesterException;
import com.gplayus.backend.repository.AppRepository;
import com.gplayus.backend.repository.MemberRepository;
import com.gplayus.backend.repository.TesterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.gplayus.backend.exception.AppExceptionCode.APP_NOT_FOUND;
import static com.gplayus.backend.exception.MemberExceptionCode.MEMBER_NOT_FOUND;
import static com.gplayus.backend.exception.TesterExceptionCode.TESTER_NOT_FOUND;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AppService {
    private final AppRepository appRepository;
    private final MemberRepository memberRepository;
    private final TesterRepository testerRepository;

    public Page<AppResponse> findApps(Pageable pageable) {
        return appRepository.findAll(pageable)
                .map(AppResponse::fromApps);
    }

    public AppResponse findApp(Long appId) {
        return appRepository.findById(appId)
                .stream()
                .map(AppResponse::fromApp)
                .findFirst()
                .orElseThrow(() -> new AppException(APP_NOT_FOUND, appId));
    }

    @Transactional
    public AppResponse createApp(Long memberId, AppRequest appRequest) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MEMBER_NOT_FOUND, memberId));

        App savedApp = appRepository.save(appRequest.toEntity(member));

        return AppResponse.fromApps(savedApp);
    }

    @Transactional
    public AppResponse updateApp(Long appId, AppRequest appRequest) {
        App readedApp = appRepository.findById(appId)
                .orElseThrow(() -> new AppException(APP_NOT_FOUND, appId));
        readedApp.setName(appRequest.getName());
        readedApp.setDescription(appRequest.getDescription());
        readedApp.setAndroidUrl(appRequest.getAndroidUrl());
        readedApp.setWebUrl(appRequest.getWebUrl());

        App updatedApp = appRepository.save(readedApp);

        return AppResponse.fromApps(updatedApp);
    }

    @Transactional
    public AppResponse deleteApp(Long appId) {
        App readedApp = appRepository.findById(appId)
                .orElseThrow(() -> new AppException(APP_NOT_FOUND, appId));

        appRepository.delete(readedApp);

        return AppResponse.fromApps(readedApp);
    }

    @Transactional
    public TesterResponse applyTester(Long memberId, Long appId) {
        App app = appRepository.findById(appId)
                .orElseThrow(() -> new AppException(APP_NOT_FOUND, appId));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MEMBER_NOT_FOUND, memberId));
        Tester savedTester = testerRepository.save(Tester.of(member, app));

        return TesterResponse.fromTester(savedTester);
    }

    @Transactional
    public TesterResponse createTester(Long testerId) {
        Tester readedTester = testerRepository.findById(testerId)
                .orElseThrow(() -> new TesterException(TESTER_NOT_FOUND, testerId));
        readedTester.setStatus(TesterStatus.IN_PROGRESS);

        Tester savedTester = testerRepository.save(readedTester);

        return TesterResponse.fromTester(savedTester);
    }
}
