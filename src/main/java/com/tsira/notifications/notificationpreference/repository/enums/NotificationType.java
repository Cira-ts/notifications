package com.tsira.notifications.notificationpreference.repository.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum NotificationType {
    PROMOTIONAL("სარეკლამო"),
    SYSTEM_ALERT("სისტემური შეტყობინებები"),
    ACCOUNT_ACTIVITY("ანგარიშთან დაკავშირებული აქტივობები")
    ;

    private final String nameKa;

    public String getCode() {
        return this.name();
    }
}
