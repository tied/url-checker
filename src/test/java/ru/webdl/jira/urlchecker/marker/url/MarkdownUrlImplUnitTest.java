package ru.webdl.jira.urlchecker.marker.url;

import org.junit.Before;
import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MarkdownUrlImplUnitTest {
    private final String exampleName = "Example";
    private final String exampleUrl = "https://example.com";

    private URL url;
    private Url urlWithName;
    private Url urlWithoutName;

    @Before
    public void setup() throws Exception {
        url = new URL(exampleUrl);
        urlWithName = new MarkdownUrlImpl(url, exampleName);
        urlWithoutName = new MarkdownUrlImpl(url);
    }

    @Test
    public void getName() {
        assertEquals(exampleName, urlWithName.getName());
        assertNull(urlWithoutName.getName());
    }

    @Test
    public void getUrl() {
        assertEquals(url, urlWithName.getUrl());
    }

    @Test
    public void getMarkDown() {
        assertEquals(String.format("[%s|%s]", exampleName, exampleUrl), urlWithName.getUrlAsString());
        assertEquals(String.format("[%s]", exampleUrl), urlWithoutName.getUrlAsString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithNullParams() {
        new MarkdownUrlImpl(null);
    }

    @Test
    public void testToString() {
        String expected = "UrlImpl{url=" + urlWithoutName.getUrl() + ", name='null'}";
        assertEquals(expected, urlWithoutName.toString());
    }
}
