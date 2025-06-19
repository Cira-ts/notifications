package com.tsira.notifications.address.repository.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AddressType {
    EMAIL("ელ-ფოსტა"),
    SMS("სმს"),
    POSTAL("საფოსტო")
    ;

    private final String nameKa;

    public String getCode() {
        return this.name();
    }
}
