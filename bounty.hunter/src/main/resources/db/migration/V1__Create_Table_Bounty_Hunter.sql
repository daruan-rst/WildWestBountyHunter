CREATE TABLE IF NOT EXISTS "bounty_hunter" (
  "id" SERIAL PRIMARY KEY,
  "cowboy_name" varchar(80) NOT NULL,
  "reputation" varchar(80) NOT NULL,
  "town_name" varchar(100) NOT NULL
);
