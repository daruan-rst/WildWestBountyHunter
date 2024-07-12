CREATE TABLE IF NOT EXISTS wanted_poster (
    "id" SERIAL PRIMARY KEY,
    "outlaw" BIGINT,
    "reward" DECIMAL,
    "outlaw_description" varchar(100),
    "poster_name" varchar(100),
    "last_place" BIGINT,
    FOREIGN KEY (last_place) REFERENCES town(id)
    FOREIGN KEY (outlaw) REFERENCES person(id)
);
