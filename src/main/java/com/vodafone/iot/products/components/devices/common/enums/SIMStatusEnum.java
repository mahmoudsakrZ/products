package com.vodafone.iot.products.components.devices.common.enums;

import lombok.Getter;

public enum SIMStatusEnum {

    ACTIVE("ACTIVE"),
    WAITING_FOR_ACTIVATION("WAITING"),
    BLOCKED("BLOCKED"),
    DEACTIVATED("DEACTIVATED");

    @Getter
    private final String status;

    public static final String VALUE_LIST = "[ACTIVE, WAITING_FOR_ACTIVATION, BLOCKED, DEACTIVATED]";

    SIMStatusEnum(final String status) {
        this.status = status;
    }

    public static boolean contains(String value) {
        try {
            valueOf(value);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

}
