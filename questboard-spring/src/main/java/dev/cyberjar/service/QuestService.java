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
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestService {

    private final QuestRepository repository;
    private final ApplicationEventPublisher events;

    public QuestService(QuestRepository repository, ApplicationEventPublisher events) {
        this.repository = repository;
        this.events = events;
    }


    public Quest findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new QuestNotFoundException(id));
    }


    @Transactional
    public Quest create(CreateQuestRequest request) {
        Quest quest = new Quest(
                request.title(),
                request.difficulty(),
                request.reward(),
                request.requiredClass()
        );

        return repository.save(quest);
    }

    @Transactional(readOnly = true)
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

        return repository.findAll();
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
        Quest quest = findById(id);
        repository.delete(quest);
    }

    @Transactional
    public AssignQuestResponse assign(Long id, AssignQuestRequest request) {
        Quest quest = findById(id);

        if(!quest.getStatus().equals(QuestStatus.OPEN)) {
            throw new QuestAlreadyAssignedException(quest.getId());
        }

        quest.assignTo(request.heroName());

        events.publishEvent(new QuestAssignedEvent(
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
