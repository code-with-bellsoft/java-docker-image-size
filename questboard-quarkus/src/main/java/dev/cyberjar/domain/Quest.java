package dev.cyberjar.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "quests")
public class Quest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty", nullable = false)
    private Difficulty difficulty;

    @Column(name = "reward", nullable = false)
    private int reward;

    @Column(name = "required_class", nullable = false)
    private String requiredClass;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private QuestStatus status = QuestStatus.OPEN;

    @Column(name = "assigned_hero")
    private String assignedHero;

    protected Quest() {
    }

    public Quest(String title, Difficulty difficulty, int reward, String requiredClass) {
        this.title = title;
        this.difficulty = difficulty;
        this.reward = reward;
        this.requiredClass = requiredClass;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int getReward() {
        return reward;
    }

    public String getRequiredClass() {
        return requiredClass;
    }

    public QuestStatus getStatus() {
        return status;
    }

    public void update(String title, Difficulty difficulty, int reward, String requiredClass) {
        this.title = title;
        this.difficulty = difficulty;
        this.reward = reward;
        this.requiredClass = requiredClass;
    }

    public void assignTo(String heroName) {
        this.status = QuestStatus.ASSIGNED;
        this.assignedHero = heroName;
    }

}
