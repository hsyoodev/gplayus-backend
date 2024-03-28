package com.gplayus.backend.dto.request;

import com.gplayus.backend.domain.App;
import com.gplayus.backend.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppRequest {
    private String name;
    private String description;
    private String androidUrl;
    private String webUrl;

    public static AppRequest of(String name, String description, String androidUrl, String web_url) {
        return new AppRequest(name, description, androidUrl, web_url);
    }

    public App toEntity(Member member) {
        return App.of(member, name, description, androidUrl, webUrl);
    }
}
