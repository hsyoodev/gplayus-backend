package com.gplayus.backend.dto.response;

import com.gplayus.backend.domain.App;
import com.gplayus.backend.enums.AppStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AppResponse {
    private final Long id;
    private final String name;
    private final String description;
    private final String androidUrl;
    private final String webUrl;
    private final LocalDateTime createdDate;
    private final LocalDateTime endedDate;
    private final AppStatus status;
    private final MemberResponse memberResponse;
    private final List<TesterResponse> testerResponse;

    public static AppResponse fromApps(App app) {
        return new AppResponse(
                app.getId(),
                app.getName(),
                app.getDescription(),
                app.getAndroidUrl(),
                app.getWebUrl(),
                app.getCreatedDate(),
                app.getEndedDate(),
                app.getStatus(),
                MemberResponse.from(app.getMember()),
                null
        );
    }

    public static AppResponse fromApp(App app) {
        return new AppResponse(
                app.getId(),
                app.getName(),
                app.getDescription(),
                app.getAndroidUrl(),
                app.getWebUrl(),
                app.getCreatedDate(),
                app.getEndedDate(),
                app.getStatus(),
                MemberResponse.from(app.getMember()),
                TesterResponse.fromTesters(app.getTesters())
        );
    }
}
