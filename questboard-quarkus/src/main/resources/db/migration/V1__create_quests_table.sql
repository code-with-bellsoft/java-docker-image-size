CREATE TABLE quests (
                        id BIGSERIAL PRIMARY KEY,
                        title VARCHAR(255) NOT NULL,
                        difficulty VARCHAR(32) NOT NULL,
                        reward INTEGER NOT NULL,
                        required_class VARCHAR(255) NOT NULL
);