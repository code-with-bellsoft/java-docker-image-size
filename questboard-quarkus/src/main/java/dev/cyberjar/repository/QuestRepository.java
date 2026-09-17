package dev.cyberjar.repository;

import dev.cyberjar.domain.Difficulty;
import dev.cyberjar.domain.Quest;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class QuestRepository implements PanacheRepository<Quest> {


    public List<Quest> findByDifficulty(Difficulty difficulty) {
        return list("difficulty", difficulty);
    }

    public List<Quest> findByRequiredClassIgnoreCase(String requiredClass) {
        return list("lower(requiredClass) = lower(?1)", requiredClass);
    }

    public List<Quest> findByDifficultyAndRequiredClassIgnoreCase(
            Difficulty difficulty,
            String requiredClass
    ) {
        return list(
                "difficulty = ?1 and lower(requiredClass) = lower(?2)",
                difficulty,
                requiredClass
        );
    }

}
