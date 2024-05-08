CREATE TABLE IF NOT EXISTS "person" (
  "id" SERIAL PRIMARY KEY,
  "_object_type" varchar(80) NOT NULL,
  "name" varchar(80) NOT NULL,
  "town_id" SERIAL NOT NULL,
  "money" varchar(100) NOT NULL,
  "reputation" varchar(100),
  "bounty_value" varchar(100),
  "alive" boolean
);
