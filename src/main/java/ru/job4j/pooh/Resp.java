package ru.job4j.pooh;

public class Resp {
    private final String text;
    private final String status;

    public Resp(String text, Status status) {
        this.text = text;
        this.status = status.getCode();
    }

    public String text() {
        return text;
    }

    public String status() {
        return status;
    }

    public enum Status {
        OK("200"),
        NOT_FOUND("203"),
        NOT_IMPLEMENTED("501");

        private final String code;

        Status(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }
}