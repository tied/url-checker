package ru.webdl.jira.urlchecker;

import com.atlassian.jira.issue.comments.Comment;
import com.atlassian.jira.issue.comments.CommentManager;
import com.atlassian.jira.issue.comments.MutableComment;
import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import ru.webdl.jira.urlchecker.marker.UrlMarkerImpl;

import javax.inject.Named;

@ExportAsService({UrlCheckerService.class})
@Named("urlCheckerService")
public class UrlCheckerServiceImpl implements UrlCheckerService {
    @ComponentImport
    private final CommentManager commentManager;

    private final UrlMarkerImpl urlMarker;

    public UrlCheckerServiceImpl(CommentManager commentManager, UrlMarkerImpl urlMarker) {
        this.commentManager = commentManager;
        this.urlMarker = urlMarker;
    }

    @Override
    public Comment markUrlsInComment(Comment comment) {
        checkParams(comment);
        String body = comment.getBody();
        String updatedBody = urlMarker.removeAllMarks(body);
        updatedBody = urlMarker.mark(updatedBody);
        if (body.equals(updatedBody)) return comment;

        MutableComment mutableComment = commentManager.getMutableComment(comment.getId());
        mutableComment.setBody(updatedBody);
        commentManager.update(mutableComment, false);
        return commentManager.getCommentById(mutableComment.getId());
    }

    private void checkParams(Comment comment) {
        if (comment == null) {
            throw new IllegalArgumentException("Объект Comment не может быть пустым");
        }
    }
}
