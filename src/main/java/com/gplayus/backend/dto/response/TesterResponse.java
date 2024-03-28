package com.gplayus.backend.dto.response;

import com.gplayus.backend.domain.Tester;
import com.gplayus.backend.enums.TesterStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TesterResponse {
    private final Long id;
    private final TesterStatus status;
    private final MemberResponse memberResponse;
    private final AppResponse appResponse;

    public static List<TesterResponse> fromTesters(List<Tester> testers) {
        return testers.stream()
                .map(tester -> new TesterResponse(
                                tester.getId(),
                                tester.getStatus(),
                                MemberResponse.from(tester.getMember()),
                                null
                        )
                )
                .toList();
    }

    public static TesterResponse fromTester(Tester tester) {
        return new TesterResponse(
                tester.getId(),
                tester.getStatus(),
                MemberResponse.from(tester.getMember()),
                AppResponse.fromApps(tester.getApp())
        );
    }
}
