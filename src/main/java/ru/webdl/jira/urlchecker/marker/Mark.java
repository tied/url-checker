package ru.webdl.jira.urlchecker.marker;

public enum Mark {
    NOT_SECURE_PROTO("[небезопасный протокол]"),
    GOV_ZONE("[госорганизация]");

    public final String mark;

    private Mark(String mark) {
        this.mark = mark;
    }

    public String getMark() {
        return mark;
    }
}
