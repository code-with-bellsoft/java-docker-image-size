package dev.cyberjar.event;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.TransactionPhase;
import org.jboss.logging.Logger;


@ApplicationScoped
public class QuestAssignedEventListener {

    private static final Logger log =
            Logger.getLogger(QuestAssignedEventListener.class);

    public void onQuestAssigned(
            @Observes(during = TransactionPhase.AFTER_SUCCESS)
            QuestAssignedEvent event
    ) {
        log.infof(
                "Quest %d assigned to %s (%s)",
                event.questId(),
                event.heroName(),
                event.heroClass()
        );
    }
}
