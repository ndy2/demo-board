package com.example.demoboard.domain;

public enum VoteType {
    UP("추천"),DOWN("비추천");

    private String desc;

    VoteType(String desc) {
        this.desc = desc;
    }
}
