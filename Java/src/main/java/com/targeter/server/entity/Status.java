package com.targeter.server.entity;

import lombok.Getter;

@Getter
public enum Status {
    ToDo("Запланированная"),
    InProgress("В работе"),
    Done("Завершенная");

    private String displayName;

    Status(String displayName) {
        this.displayName = displayName;
    }
}
