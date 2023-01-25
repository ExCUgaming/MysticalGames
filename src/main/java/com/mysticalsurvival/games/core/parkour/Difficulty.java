package com.mysticalsurvival.games.core.parkour;

public enum Difficulty {

    EASY("EASY") {

    },
    NORMAL("NORMAL") {

    },
    HARD("HARD") {

    };

    private final String id;
    private Difficulty (String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
