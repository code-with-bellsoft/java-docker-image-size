package dev.cyberjar.repository;

import dev.cyberjar.domain.Difficulty;
import dev.cyberjar.domain.Quest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestRepository extends JpaRepository<Quest, Long> {

    List<Quest> findByDifficulty(Difficulty difficulty);

    List<Quest> findByRequiredClassIgnoreCase(String requiredClass);

    List<Quest> findByDifficultyAndRequiredClassIgnoreCase(
            Difficulty difficulty,
            String requiredClass
    );

}