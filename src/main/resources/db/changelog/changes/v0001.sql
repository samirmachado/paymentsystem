CREATE TABLE application."user" (
	id bigserial NOT NULL,
	created_date timestamp NOT NULL,
	modified_date timestamp NULL,
	cpf varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	"password" varchar(255) NOT NULL,
	username varchar(255) NOT NULL,
	CONSTRAINT uk_2qv8vmk5wxu215bevli5derq UNIQUE (cpf),
	CONSTRAINT uk_ob8kqyqqgmefl0aco34akdtpe UNIQUE (email),
	CONSTRAINT uk_sb8bbouer5wak8vyiiy4pf2bx UNIQUE (username),
	CONSTRAINT user_pkey PRIMARY KEY (id)
);


CREATE TABLE application.bill (
	id bigserial NOT NULL,
	created_date timestamp NOT NULL,
	modified_date timestamp NULL,
	corrected_value numeric(19,2) NULL,
	due_date date NOT NULL,
	fine_percent_per_day float4 NULL,
	interest_percent float4 NULL,
	"name" varchar(255) NOT NULL,
	number_of_days_late int8 NULL,
	original_value numeric(19,2) NOT NULL,
	payment_date date NULL,
	user_id int8 NOT NULL,
	CONSTRAINT bill_pkey PRIMARY KEY (id),
	CONSTRAINT fkqhq5aolak9ku5x5mx11cpjad9 FOREIGN KEY (user_id) REFERENCES application."user"(id)
);


CREATE TABLE application.user_roles (
	user_id int8 NOT NULL,
	roles int4 NULL,
	CONSTRAINT fk55itppkw3i07do3h7qoclqd4k FOREIGN KEY (user_id) REFERENCES application."user"(id)
);

INSERT INTO application."user" (created_date,modified_date,cpf,email,"password",username) VALUES
	 ('2021-03-15 00:43:55.967','2021-03-15 00:43:55.967','94935927070','admin@email.com','$2a$12$yWhBrouIICTmRyfDM3fhK.oKZ5ISLlCguswV4A.UhG02X5zfl.66C','admin');
INSERT INTO application.user_roles (user_id,roles) VALUES
	 (1,0);