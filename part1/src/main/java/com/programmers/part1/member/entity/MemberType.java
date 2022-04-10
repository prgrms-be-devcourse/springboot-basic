package com.programmers.part1.member.entity;

public enum MemberType {

    NORMAL {
        public MemberType convertType() {
            return BLACK;
        }
    },

    BLACK{
        public MemberType convertType() {
            return NORMAL;
        }
    };

    public MemberType convertType(){
        return NORMAL;
    }
}