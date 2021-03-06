-- Generated by Oracle SQL Developer Data Modeler 17.2.0.188.1059
--   at:        2019-10-14 15:28:07 MSK
--   site:      Oracle Database 11g
--   type:      Oracle Database 11g

CREATE SEQUENCE sq_part START WITH 1 INCREMENT BY 1 MINVALUE 0 MAXVALUE 999 CYCLE;


CREATE TABLE institution (
    id     INTEGER NOT NULL,
    type   VARCHAR2(30) NOT NULL,
    name   VARCHAR2(40) NOT NULL,
    city   VARCHAR2(20) NOT NULL
);

ALTER TABLE institution ADD CONSTRAINT institution_pk PRIMARY KEY ( id );

CREATE TABLE participant (
    id                      INTEGER NOT NULL,
    full_name               VARCHAR2(40) NOT NULL,
    avg_sertificate_grade   INTEGER NOT NULL,
    sertificate_num         INTEGER NOT NULL,
    speciality_id           INTEGER,
    UNIQUE(full_name)
);

ALTER TABLE participant ADD CONSTRAINT participant_pk PRIMARY KEY ( id );

CREATE TABLE relation_1 (
    participant_id   INTEGER NOT NULL,
    institution_id   INTEGER NOT NULL
);

ALTER TABLE relation_1 ADD CONSTRAINT relation_1_pk PRIMARY KEY ( participant_id,institution_id );

CREATE TABLE speciality (
    id     INTEGER NOT NULL,
    name   VARCHAR2(30) NOT NULL
);

ALTER TABLE speciality ADD CONSTRAINT speciality_pk PRIMARY KEY ( id );

CREATE TABLE test (
    id             INTEGER NOT NULL,
    "Date"         DATE NOT NULL,
    subject_name   VARCHAR2(30) NOT NULL
);

ALTER TABLE test ADD CONSTRAINT test_pk PRIMARY KEY ( id );

CREATE TABLE test_result (
    id               INTEGER NOT NULL,
    points           INTEGER NOT NULL CHECK((points >= 0) AND (points <= 100)),
    participant_id   INTEGER NOT NULL,
    test_id          INTEGER NOT NULL
);

ALTER TABLE test_result ADD CONSTRAINT test_result_pk PRIMARY KEY ( id );

ALTER TABLE participant
    ADD CONSTRAINT participant_speciality_fk FOREIGN KEY ( speciality_id )
        REFERENCES speciality ( id ) ON DELETE CASCADE;

ALTER TABLE relation_1
    ADD CONSTRAINT relation_1_institution_fk FOREIGN KEY ( institution_id )
        REFERENCES institution ( id );

ALTER TABLE relation_1
    ADD CONSTRAINT relation_1_participant_fk FOREIGN KEY ( participant_id )
        REFERENCES participant ( id );

ALTER TABLE test_result
    ADD CONSTRAINT test_result_participant_fk FOREIGN KEY ( participant_id )
        REFERENCES participant ( id ) ON UPDATE CASCADE;

ALTER TABLE test_result
    ADD CONSTRAINT test_result_test_fk FOREIGN KEY ( test_id )
        REFERENCES test ( id );

CREATE INDEX idx_participant_name ON participant ( full_name );


-- Oracle SQL Developer Data Modeler Summary Report: 
-- 
-- CREATE TABLE                             6
-- CREATE INDEX                             0
-- ALTER TABLE                             11
-- CREATE VIEW                              0
-- ALTER VIEW                               0
-- CREATE PACKAGE                           0
-- CREATE PACKAGE BODY                      0
-- CREATE PROCEDURE                         0
-- CREATE FUNCTION                          0
-- CREATE TRIGGER                           0
-- ALTER TRIGGER                            0
-- CREATE COLLECTION TYPE                   0
-- CREATE STRUCTURED TYPE                   0
-- CREATE STRUCTURED TYPE BODY              0
-- CREATE CLUSTER                           0
-- CREATE CONTEXT                           0
-- CREATE DATABASE                          0
-- CREATE DIMENSION                         0
-- CREATE DIRECTORY                         0
-- CREATE DISK GROUP                        0
-- CREATE ROLE                              0
-- CREATE ROLLBACK SEGMENT                  0
-- CREATE SEQUENCE                          0
-- CREATE MATERIALIZED VIEW                 0
-- CREATE SYNONYM                           0
-- CREATE TABLESPACE                        0
-- CREATE USER                              0
-- 
-- DROP TABLESPACE                          0
-- DROP DATABASE                            0
-- 
-- REDACTION POLICY                         0
-- 
-- ORDS DROP SCHEMA                         0
-- ORDS ENABLE SCHEMA                       0
-- ORDS ENABLE OBJECT                       0
-- 
-- ERRORS                                   0
-- WARNINGS                                 0
