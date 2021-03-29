package ru.webdl.jira.urlchecker.marker.url;

import java.net.URL;

public interface Url {
    URL getUrl();

    String getName();

    String getUrlAsString();
}
