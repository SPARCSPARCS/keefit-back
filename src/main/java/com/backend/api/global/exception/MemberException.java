package com.backend.api.global.exception;

public class MemberException extends RuntimeException {
    // 기본 생성자 추가
    public MemberException() {
        super();
    }

    // 메시지를 받는 생성자
    public MemberException(String message) {
        super(message);
    }

    public static class MemberNotFoundException extends RuntimeException {
        public MemberNotFoundException(String message) { super(message); }
    }

    public static class MemberDuplicatedException extends MemberException {
        public MemberDuplicatedException(String message) {
            super(message);
        }
    }
}