package ru.webdl.jira.urlchecker.marker.url;

import java.util.List;

public interface UrlFinder {

    List<Url> find(String text);
}
