CREATE TABLE IF NOT EXISTS equipment (
    id SERIAL PRIMARY KEY,
    person_id BIGINT,
    value DECIMAL,
    "equipment_name" VARCHAR(80) NOT NULL,
    FOREIGN KEY (person_id) REFERENCES person(id)
);
