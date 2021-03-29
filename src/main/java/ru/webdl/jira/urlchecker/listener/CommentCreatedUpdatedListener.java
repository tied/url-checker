package ru.webdl.jira.urlchecker.listener;

import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.jira.event.issue.IssueEvent;
import com.atlassian.jira.event.type.EventType;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.comments.Comment;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.webdl.jira.urlchecker.UrlCheckerService;

@Component
public class CommentCreatedUpdatedListener {
    private static final Logger log = LoggerFactory.getLogger(CommentCreatedUpdatedListener.class);
    private final UrlCheckerService urlCheckerService;

    public CommentCreatedUpdatedListener(@ComponentImport EventPublisher eventPublisher, UrlCheckerService urlCheckerService) {
        eventPublisher.register(this);
        log.info("REDISTERED!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        this.urlCheckerService = urlCheckerService;
    }

    @EventListener
    public void onIssueEvent(IssueEvent issueEvent) {
        Long eventTypeId = issueEvent.getEventTypeId();
        Issue issue = issueEvent.getIssue();
        Comment comment = issueEvent.getComment();
        log.info("eventTypeId : {}", eventTypeId);
        if (comment != null) {
            log.info("Comment is not null");
            urlCheckerService.markUrlsInComment(comment);
        }

        if (eventTypeId.equals(EventType.ISSUE_CREATED_ID)) {
//            log.info("Issue {} has been created at {}.", issue.getKey(), issue.getCreated());
        }
    }

}
