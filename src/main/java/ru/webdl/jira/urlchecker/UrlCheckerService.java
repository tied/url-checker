package ru.webdl.jira.urlchecker;

import com.atlassian.jira.issue.comments.Comment;

public interface UrlCheckerService {

    Comment markUrlsInComment(Comment comment);
}
