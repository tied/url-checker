package ru.webdl.jira.urlchecker.marker.url;

import java.net.URL;

public class MarkdownUrlImpl implements Url {
    private final URL url;
    private final String name;

    public MarkdownUrlImpl(URL url) {
        this(url, null);
    }

    public MarkdownUrlImpl(URL url, String name) {
        checkParams(url);
        this.url = url;
        this.name = name;
    }

    private void checkParams(URL url) {
        if (url == null) {
            throw new IllegalArgumentException("URL object can't be null");
        }
    }

    @Override
    public URL getUrl() {
        return url;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUrlAsString() {
        return name != null && !name.isEmpty() ? String.format("[%s|%s]", name, url) : String.format("[%s]", url);
    }

    @Override
    public String toString() {
        return "UrlImpl{" +
                "url=" + url +
                ", name='" + name + '\'' +
                '}';
    }
}
