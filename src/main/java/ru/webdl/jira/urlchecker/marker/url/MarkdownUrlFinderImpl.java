package ru.webdl.jira.urlchecker.marker.url;

import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;

import javax.inject.Named;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ExportAsService({UrlFinder.class})
@Named("markdownUrlFinder")
public class MarkdownUrlFinderImpl implements UrlFinder {
    private static final String urlRegex = "\\[([\\w\\s].*?)??[|]?(https?:\\/\\/[-a-zA-Z0-9+&@#\\/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#\\/%=~_|])\\]";

    @Override
    public List<Url> find(String text) {
        List<Url> results = new ArrayList<>();
        Matcher m = Pattern.compile(urlRegex).matcher(text);
        while (m.find()) {
            String name = m.group(1);
            String urlString = m.group(2);
            URL url;
            try {
                url = new URL(urlString);
            } catch (MalformedURLException e) {
                continue;
            }
            results.add(new MarkdownUrlImpl(url, name));
        }
        return results;
    }
}
