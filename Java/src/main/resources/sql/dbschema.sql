create table tbuser (
                      user_id bigserial not null primary key,
                      username varchar(50) not null unique,
                      password varchar not null
);

create table tbtarget (
                        target_id bigserial not null primary key,
                        user_id bigint not null,
                        target_name varchar(100) not null,
                        target_description varchar,
                        constraint target_user_fk foreign key (user_id) references tbuser (user_id)
);