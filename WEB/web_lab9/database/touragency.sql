-- Script was copied from pgAdmin 4, just to show its creating process and structure

DROP TABLE public.clients;
DROP TABLE public.tour_types;
DROP TABLE public.tours;
DROP TABLE public.orders;

CREATE TABLE public.clients
(
    id integer NOT NULL,
    paid_orders_amount integer NOT NULL,
    personal_discount integer NOT NULL,
    full_name character varying(30) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT clients_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.clients
    OWNER to postgres;



CREATE TABLE public.tours
(
    id integer NOT NULL,
    type_id integer NOT NULL,
    name character varying(40) COLLATE pg_catalog."default" NOT NULL,
    cost double precision NOT NULL,
    location character varying(40) COLLATE pg_catalog."default" NOT NULL,
    is_burning boolean NOT NULL,
    CONSTRAINT id PRIMARY KEY (id),
    CONSTRAINT type_id FOREIGN KEY (type_id)
        REFERENCES public.tour_types (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        DEFERRABLE INITIALLY DEFERRED
)

TABLESPACE pg_default;

ALTER TABLE public.tours
    OWNER to postgres;



CREATE TABLE public.tour_types
(
    id integer NOT NULL,
    name character varying(20) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT tour_type_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.tour_types
    OWNER to postgres;



CREATE TABLE public.orders
(
    id integer NOT NULL,
    tour_id integer NOT NULL,
    client_id integer NOT NULL,
    CONSTRAINT orders_pkey PRIMARY KEY (id),
    CONSTRAINT client_id FOREIGN KEY (client_id)
        REFERENCES public.clients (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT tour_id FOREIGN KEY (tour_id)
        REFERENCES public.tours (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        DEFERRABLE INITIALLY DEFERRED
)

TABLESPACE pg_default;

ALTER TABLE public.orders
    OWNER to postgres;


INSERT INTO tour_types (id, name) VALUES (1, 'excursion');
INSERT INTO tour_types (id, name) VALUES (2, 'holiday');
INSERT INTO tour_types (id, name) VALUES (3, 'shopping');


INSERT INTO tours (id, type_id, name, cost, location, is_burning) VALUES (1, 1, 'Tour Open Minsk', 175.5, 'Minsk, Belarus', false);
INSERT INTO tours (id, type_id, name, cost, location, is_burning) VALUES (2, 1, 'Neswizh', 35.0, 'Neswizh, Belarus', false);
INSERT INTO tours (id, type_id, name, cost, location, is_burning) VALUES (3, 3, 'Milan Tour de Magazines', 250.0, 'Milan, Italy', true);
INSERT INTO tours (id, type_id, name, cost, location, is_burning) VALUES (4, 2, 'Puerto Rico San-Huan', 750.0, 'San-Huan, Puerto Rico', true);
INSERT INTO tours (id, type_id, name, cost, location, is_burning) VALUES (5, 2, 'Sunny Beach Holiday', 300.0, 'Sunny Beach, Bulgaria', false);

INSERT INTO clients (id, full_name, paid_orders_amount, personal_discount) VALUES (1, 'Dan McMillan', 5, 0);
INSERT INTO clients (id, full_name, paid_orders_amount, personal_discount) VALUES (2, 'Ann Dicckenson', 2, 5);
INSERT INTO clients (id, full_name, paid_orders_amount, personal_discount) VALUES (3, 'Betty Cooper', 3, 0);
INSERT INTO clients (id, full_name, paid_orders_amount, personal_discount) VALUES (4, 'Pall Dot', 10, 30);


INSERT INTO orders (id, tour_id, client_id) VALUES (1, 1, 3);
INSERT INTO orders (id, tour_id, client_id) VALUES (2, 1, 2);
INSERT INTO orders (id, tour_id, client_id) VALUES (3, 3, 2);
INSERT INTO orders (id, tour_id, client_id) VALUES (4, 1, 2);
INSERT INTO orders (id, tour_id, client_id) VALUES (5, 3, 3);
INSERT INTO orders (id, tour_id, client_id) VALUES (6, 1, 3);
INSERT INTO orders (id, tour_id, client_id) VALUES (7, 2, 1);