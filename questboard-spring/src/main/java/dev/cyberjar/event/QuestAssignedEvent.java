package dev.cyberjar.event;

public record QuestAssignedEvent(
        Long questId,
        String heroName,
        String heroClass
) {
}
