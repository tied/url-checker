package ru.webdl.jira.urlchecker.marker;

import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
import ru.webdl.jira.urlchecker.marker.url.Url;
import ru.webdl.jira.urlchecker.marker.url.UrlFinder;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ExportAsService({UrlMarkerImpl.class})
@Named("urlMarker")
public class UrlMarkerImpl {
    private final UrlFinder urlFinder;

    public UrlMarkerImpl(UrlFinder urlFinder) {
        this.urlFinder = urlFinder;
    }

    public String mark(String text) {
        List<Url> urls = urlFinder.find(text);
        for (Url url : urls) {
            String marks = getMarks(url).stream().map(Mark::getMark).collect(Collectors.joining());
            String urlAsString = url.getUrlAsString()
                    .replaceAll("\\[", "\\\\[")
                    .replaceAll("\\|", "\\\\|");
            text = text.replaceAll(urlAsString, urlAsString + " " + marks);
        }
        return text;
    }

    public List<Mark> getMarks(Url url) {
        List<Mark> result = new ArrayList<>();

        if (isNonSecureHttpProto(url)) {
            result.add(Mark.NOT_SECURE_PROTO);
        }
        if (isGovZone(url)) {
            result.add(Mark.GOV_ZONE);
        }
        return result;
    }

    public boolean isNonSecureHttpProto(Url url) {
        return !url.getUrl().getProtocol().equals("https");
    }

    public boolean isGovZone(Url url) {
        return url.getUrl().getHost().endsWith(".gov");
    }

    public String removeAllMarks(String text) {
        for (Mark mark : Mark.values()) {
            String markName = mark.getMark().replaceAll("\\[", "\\\\[");
            text = text.replaceAll("\\s?" + markName, "");
        }
        return text;
    }
}
