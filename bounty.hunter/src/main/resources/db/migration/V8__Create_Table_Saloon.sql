CREATE TABLE IF NOT EXISTS saloon (
    id SERIAL PRIMARY KEY,
    saloon_name VARCHAR(80) NOT NULL,
    town_id BIGINT NOT NULL,
    FOREIGN KEY (town_id) REFERENCES town(id)
);
