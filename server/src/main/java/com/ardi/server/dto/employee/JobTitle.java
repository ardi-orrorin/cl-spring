package com.ardi.server.dto.employee;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum JobTitle {

    ASSOCIATE("사원"),
    SENIOR_ASSOCIATE("주임"),
    ASSISTANT_MANAGER("대리"),
    MANAGER("과장"),
    DEPUTY_GENERAL_MANAGER("차장"),
    GENERAL_MANAGER("부장"),
    DIRECTOR("이사"),
    MANAGING_DIRECTOR("상무"),
    EXECUTIVE_DIRECTOR("전무"),
    VICE_PRESIDENT("부사장"),
    PRESIDENT("사장"),
    CEO("대표이사");

    private final String displayName;

    public static List<String> getAllJobTitles() {
        return Arrays.stream(JobTitle.values())
                .map(JobTitle::getDisplayName)
                .toList();
    }

    public static JobTitle fromDisplayName(String displayName) {
        return Arrays.stream(JobTitle.values())
                .filter(jobTitle -> jobTitle.getDisplayName().equals(displayName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid job title: " + displayName));
    }

}
