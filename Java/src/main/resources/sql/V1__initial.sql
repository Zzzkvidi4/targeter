create table targeter_user (
	id BIGSERIAL PRIMARY KEY,
	username VARCHAR(100) NOT NULL UNIQUE,
	password VARCHAR NOT NULL UNIQUE,
	name VARCHAR(15),
	surname VARCHAR(15)
);

create table target_category (
	id BIGSERIAL PRIMARY KEY,
	user_id BIGINT REFERENCES targeter_user(id) ON DELETE CASCADE,
	name VARCHAR(15) NOT NULL
);

CREATE TYPE status AS ENUM ('ToDo', 'InProgress', 'Done');
							  
CREATE TYPE week_day AS ENUM ('Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday');

-- можно задать интервал(например, 3 дня) и/или дни недели с временем для каждого дня
CREATE TABLE schedule (
	id BIGSERIAL PRIMARY KEY,
	begin_time TIMESTAMP NOT NULL DEFAULT current_timestamp,
	interval INTERVAL,
	week_days WEEK_DAY[],
	week_days_time TIME[]
);

create table target (
	id BIGSERIAL PRIMARY KEY,
	user_id BIGINT REFERENCES targeter_user(id) ON DELETE CASCADE NOT NULL,
	text varchar(250) NOT NULL,
	target_category_id BIGINT REFERENCES target_category(id) NOT NULL,
	status STATUS NOT NULL DEFAULT 'ToDo',
	schedule BIGINT REFERENCES schedule(id),
	photo_report BYTEA
);

create table motivation (
	id BIGSERIAL PRIMARY KEY,
    text varchar(350) NOT NULL,
	target_category_id BIGINT REFERENCES target_category(id)
);