package dev.cyberjar.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class QuestAssignedEventListener {

    private static final Logger log = LoggerFactory.getLogger(QuestAssignedEventListener.class);

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onQuestAssigned(QuestAssignedEvent event) {
        log.atInfo()
                .addKeyValue("questId", event.questId())
                .addKeyValue("heroName", event.heroName())
                .addKeyValue("heroClass", event.heroClass())
                .log("Quest assigned");
    }
}
