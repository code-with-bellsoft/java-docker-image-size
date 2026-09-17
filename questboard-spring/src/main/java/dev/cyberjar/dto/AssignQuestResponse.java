package dev.cyberjar.dto;


import dev.cyberjar.domain.QuestStatus;

public record AssignQuestResponse(
        Long questId,
        String heroName,
        QuestStatus status
) {
}
