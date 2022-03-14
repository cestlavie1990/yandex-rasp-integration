DROP TABLE IF EXISTS "station";
CREATE TABLE "station" (
    "id"             VARCHAR          NOT NULL,
    "settlement_id"  VARCHAR          NOT NULL,
    "esr_code"       VARCHAR,
    "yandex_code"    VARCHAR,
    "title"          VARCHAR          NOT NULL,
    "station_type"   VARCHAR          NOT NULL,
    "transport_type" VARCHAR          NOT NULL,
    "latitude"       DOUBLE PRECISION NOT NULL,
    "longitude"      DOUBLE PRECISION NOT NULL
);

DROP TABLE IF EXISTS "settlement";
CREATE TABLE "settlement" (
    "id"          VARCHAR NOT NULL,
    "region_id"   VARCHAR NOT NULL,
    "esr_code"    VARCHAR,
    "yandex_code" VARCHAR,
    "title"       VARCHAR NOT NULL
);

DROP TABLE IF EXISTS "region";
CREATE TABLE "region" (
    "id"          VARCHAR NOT NULL,
    "country_id"  VARCHAR NOT NULL,
    "esr_code"    VARCHAR,
    "yandex_code" VARCHAR,
    "title"       VARCHAR NOT NULL
);

DROP TABLE IF EXISTS "country";
CREATE TABLE "country" (
    "id"          VARCHAR NOT NULL,
    "esr_code"    VARCHAR,
    "yandex_code" VARCHAR,
    "title"       VARCHAR NOT NULL
);


-- PRIMARY KEY
ALTER TABLE "station"
    DROP CONSTRAINT IF EXISTS "pk__station";
ALTER TABLE "station"
    ADD CONSTRAINT "pk__station" PRIMARY KEY ("id");

ALTER TABLE "settlement"
    DROP CONSTRAINT IF EXISTS "pk__settlement";
ALTER TABLE "settlement"
    ADD CONSTRAINT "pk__settlement" PRIMARY KEY ("id");

ALTER TABLE "region"
    DROP CONSTRAINT IF EXISTS "pk__region";
ALTER TABLE "region"
    ADD CONSTRAINT "pk__region" PRIMARY KEY ("id");

ALTER TABLE "country"
    DROP CONSTRAINT IF EXISTS "pk__country";
ALTER TABLE "country"
    ADD CONSTRAINT "pk__country" PRIMARY KEY ("id");


-- FOREIGN KEY
ALTER TABLE "station"
    DROP CONSTRAINT IF EXISTS "fk__station__settlement";
ALTER TABLE "station"
    ADD CONSTRAINT "fk__station__settlement" FOREIGN KEY ("settlement_id")
        REFERENCES "settlement" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "settlement"
    DROP CONSTRAINT IF EXISTS "fk__settlement__region";
ALTER TABLE "settlement"
    ADD CONSTRAINT "fk__settlement__region" FOREIGN KEY ("region_id")
        REFERENCES "region" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "region"
    DROP CONSTRAINT IF EXISTS "fk__region__country";
ALTER TABLE "region"
    ADD CONSTRAINT "fk__region__country" FOREIGN KEY ("country_id")
        REFERENCES "country" ("id") ON DELETE CASCADE ON UPDATE CASCADE;


-- INDEX
DROP INDEX IF EXISTS "idx__station__latitude";
CREATE INDEX "idx__station__latitude" ON "station" ("latitude");

DROP INDEX IF EXISTS "idx__station__longitude";
CREATE INDEX "idx__station__longitude" ON "station" ("longitude");