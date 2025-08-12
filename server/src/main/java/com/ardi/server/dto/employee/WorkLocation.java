package com.ardi.server.dto.employee;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum WorkLocation {
    UNKNOWN("미정"),
    DOMESTIC("국내"),
    KOREA("한국법인"),
    JAPAN("일본법인"),
    CHINA("중국법인"),
    SINGAPORE("싱가포르법인"),
    HONG_KONG("홍콩법인"),
    VIETNAM("베트남법인"),
    THAILAND("태국법인"),
    INDIA("인도법인"),
    EUROPE("유럽법인"),
    USA("미국법인"),
    CANADA("캐나다법인"),
    AUSTRALIA("호주법인"),
    BRAZIL("브라질법인");

    private final String displayName;

    public static List<String> getAllWorkLocations() {
        return Arrays.stream(WorkLocation.values())
                .map(WorkLocation::getDisplayName)
                .toList();
    }

    public static WorkLocation fromDisplayName(String displayName) {
        if (displayName == null || displayName.trim().isEmpty()) {
            return null;
        }

        return Arrays.stream(WorkLocation.values())
                .filter(location -> location.getDisplayName().equals(displayName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid work location: " + displayName));

    }
}
