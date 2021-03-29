package ru.webdl.jira.urlchecker.marker;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ru.webdl.jira.urlchecker.marker.url.MarkdownUrlImpl;
import ru.webdl.jira.urlchecker.marker.url.Url;
import ru.webdl.jira.urlchecker.marker.url.UrlFinder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UrlMarkerImplTest {

    private UrlFinder urlFinder;
    private UrlMarkerImpl urlMarker;

    @Before
    public void setUp() throws Exception {
        urlFinder = Mockito.mock(UrlFinder.class);
        urlMarker = new UrlMarkerImpl(urlFinder);
    }

    @Test
    public void getMarks() {
        Url url = createUrl("https://example.com", null);

        List<Mark> marks = urlMarker.getMarks(url);

        assertTrue(marks.isEmpty());
    }

    @Test
    public void getMarks_notGovZone() {
        Url url = createUrl("https://example.gov/some/path", null);

        List<Mark> marks = urlMarker.getMarks(url);

        assertEquals(1, marks.size());
        assertEquals(Mark.GOV_ZONE, marks.get(0));
    }

    @Test
    public void getMarks_notSecureProto() {
        Url url = createUrl("http://example.com/some-path", null);

        List<Mark> marks = urlMarker.getMarks(url);

        assertEquals(1, marks.size());
        assertEquals(Mark.NOT_SECURE_PROTO, marks.get(0));
    }

    @Test
    public void mark() {
        String exampleText = "Some example [http://example.com] text [Site|https://some.gov]";
        String expectedText = "Some example [http://example.com] " + Mark.NOT_SECURE_PROTO.getMark() +
                " text [Site|https://some.gov] " + Mark.GOV_ZONE.getMark();
        List<Url> urls = new ArrayList<>();
        urls.add(createUrl("http://example.com", null));
        urls.add(createUrl("https://some.gov", "Site"));
        Mockito.when(urlFinder.find(exampleText)).thenReturn(urls);

        String result = urlMarker.mark(exampleText);

        assertEquals(expectedText, result);
    }

    @Test
    public void removeAllMarks() {
        String excepted = "Some example [http://example.com] text [Site|https://some.gov]";
        String text = urlMarker.mark(excepted);

        String result = urlMarker.removeAllMarks(text);

        assertEquals(text, result);
    }

    private Url createUrl(String url, String name) {
        MarkdownUrlImpl markdownUrl;
        try {
            if (name == null || name.isEmpty()) {
                markdownUrl = new MarkdownUrlImpl(new URL(url));
            } else {
                markdownUrl = new MarkdownUrlImpl(new URL(url), name);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        return markdownUrl;
    }
}
