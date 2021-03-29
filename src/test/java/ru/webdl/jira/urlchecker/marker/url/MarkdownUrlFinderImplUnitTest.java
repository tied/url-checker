package ru.webdl.jira.urlchecker.marker.url;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class MarkdownUrlFinderImplUnitTest {

    private UrlFinder urlFinder;

    @Before
    public void setup() throws Exception {
        urlFinder = new MarkdownUrlFinderImpl();
    }

    @Test
    public void find() {
        String text = "Some text with [Example|https://example.com] url and another named example " +
                "[Some another example|https://another.example.com/page.html]";

        List<Url> urlsWithName = urlFinder.find(text);

        assertEquals(2, urlsWithName.size());
        assertEquals("Example", urlsWithName.get(0).getName());
        assertEquals("https://example.com", urlsWithName.get(0).getUrl().toString());
        assertEquals("Some another example", urlsWithName.get(1).getName());
        assertEquals("https://another.example.com/page.html", urlsWithName.get(1).getUrl().toString());
    }

    @Test
    public void find_withoutNamedUrls() {
        String text = "Some text with [https://example.com] url and another named example" +
                "[https://another.example.com/page.html]";

        List<Url> urls = urlFinder.find(text);

        assertEquals(2, urls.size());
        assertNull(urls.get(1).getName());
        assertEquals("https://another.example.com/page.html", urls.get(1).getUrl().toString());
    }
}
