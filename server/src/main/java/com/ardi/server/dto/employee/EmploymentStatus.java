package com.ardi.server.dto.employee;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum EmploymentStatus {

    WORKING("근무"),
    ON_LEAVE("휴직"),
    RESIGNED("퇴사");

    private final String displayName;

    public static List<String> getAllEmploymentStatuses() {
        return Arrays.stream(EmploymentStatus.values())
                .map(EmploymentStatus::getDisplayName)
                .toList();
    }

    public static EmploymentStatus fromDisplayName(String displayName) {
        if (displayName == null || displayName.trim().isEmpty()) {
            return null;
        }
        
        return Arrays.stream(EmploymentStatus.values())
                .filter(status -> status.getDisplayName().equals(displayName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid employment status: " + displayName));
    }
}
