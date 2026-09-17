package dev.cyberjar.exception;

public class QuestNotFoundException extends RuntimeException {

    public QuestNotFoundException(Long id) {
        super("Quest not found: " + id);
    }
}
