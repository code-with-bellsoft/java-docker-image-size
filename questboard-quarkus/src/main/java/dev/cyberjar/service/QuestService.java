package dev.cyberjar.service;

import dev.cyberjar.domain.Difficulty;
import dev.cyberjar.domain.Quest;
import dev.cyberjar.domain.QuestStatus;
import dev.cyberjar.dto.AssignQuestRequest;
import dev.cyberjar.dto.AssignQuestResponse;
import dev.cyberjar.dto.CreateQuestRequest;
import dev.cyberjar.event.QuestAssignedEvent;
import dev.cyberjar.exception.QuestAlreadyAssignedException;
import dev.cyberjar.exception.QuestNotFoundException;
import dev.cyberjar.repository.QuestRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;


@ApplicationScoped
public class QuestService {

    private final QuestRepository repository;
    private final Event<QuestAssignedEvent> events;

    @Inject
    public QuestService(
            QuestRepository repository, Event<QuestAssignedEvent> events

    ) {
        this.repository = repository;
        this.events = events;
    }

    public Quest findById(Long id) {
        Quest quest = repository.findById(id);
        if (quest == null) {
            throw new QuestNotFoundException(id);
        }
        return quest;
    }

    @Transactional
    public Quest create(CreateQuestRequest request) {
        Quest quest = new Quest(
                request.title(),
                request.difficulty(),
                request.reward(),
                request.requiredClass()
        );
        repository.persist(quest);
        return quest;
    }

    public List<Quest> search(Difficulty difficulty, String requiredClass) {
        if (difficulty != null && requiredClass != null) {
            return repository.findByDifficultyAndRequiredClassIgnoreCase(difficulty, requiredClass);
        }
        if (difficulty != null) {
            return repository.findByDifficulty(difficulty);
        }
        if (requiredClass != null) {
            return repository.findByRequiredClassIgnoreCase(requiredClass);
        }
        return repository.listAll();
    }

    @Transactional
    public Quest update(Long id, CreateQuestRequest request) {
        Quest quest = findById(id);
        quest.update(
                request.title(),
                request.difficulty(),
                request.reward(),
                request.requiredClass()
        );
        return quest;
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(findById(id));
    }

    @Transactional
    public AssignQuestResponse assign(Long id, AssignQuestRequest request) {
        Quest quest = repository.findById(id);

        if (!QuestStatus.OPEN.equals(quest.getStatus())) {
            throw new QuestAlreadyAssignedException(quest.getId());
        }

        quest.assignTo(request.heroName());

        events.fire(new QuestAssignedEvent(
                quest.getId(),
                request.heroName(),
                request.heroClass()
        ));

        return new AssignQuestResponse(
                quest.getId(),
                request.heroName(),
                quest.getStatus()
        );
    }

}
