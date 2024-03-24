CREATE TABLE IF NOT EXISTS equipment (
    id SERIAL PRIMARY KEY,
    person_id BIGINT,
    value DECIMAL,
    FOREIGN KEY (person_id) REFERENCES person(id)
);
