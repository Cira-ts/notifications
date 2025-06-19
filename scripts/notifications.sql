create sequence seq_app_user start with 1000;
grant select, usage on seq_app_user to postgres;
create table app_user
(
    id                          bigint         not null primary key,
    first_name                  varchar(255)   not null,
    last_name                   varchar(255)   not null,
    email                       varchar(255)   not null,
    password                    varchar(255)   not null,
    status                      varchar(255)   not null,
    registration_date           timestamp(255) not null,
    user_type                   varchar(255)   not null ,
    role                        varchar(255)   not null,
    constraint unique_email unique (email)
);

create sequence seq_app_user_token start with 1000;
grant select, usage on seq_app_user_token to postgres;
create table app_user_token
(
    id                          bigint       not null primary key,
    token                       varchar(255) not null,
    token_type                  varchar(255) not null,
    revoked                     boolean      not null,
    expired                     boolean      not null,
    app_user_id                 bigint       not null,
    foreign key (app_user_id) references app_user (id),
    constraint unique_token unique (token)
);

create sequence seq_customer start with 1000;
grant select, usage on seq_customer to postgres;
create table customer
(
    id                    bigint         not null primary key,
    name                  varchar(255)   not null,
    email                 varchar(255)   not null,
    phone_number          varchar(255)   not null,
    constraint unique_customer_email unique (email)
);

create sequence seq_address start with 1000;
grant select, usage on seq_address to postgres;
create table address
(
    id             bigint         not null primary key,
    type           varchar(255)   not null,
    value          varchar(255)   not null,
    valid_till_date  date           not null,
    customer_id    bigint         not null,
    foreign key (customer_id) references customer (id)
);

create sequence seq_notification_preference start with 1000;
grant select, usage on seq_notification_preference to postgres;
create table notification_preference
(
    id          bigint      not null primary key,
    type        varchar(50) not null,
    opted_in    boolean     not null,
    customer_id bigint      not null,
    foreign key (customer_id) references customer (id)
);

create sequence seq_notification_log start with 1000;
grant select, usage on seq_notification_log to postgres;
create table notification_log
(
    id              bigint          not null primary key,
    customer_id     bigint          not null,
    type            varchar(50)     not null,
    status          varchar(50)     not null,
    sent_time       timestamp       not null,
    error           varchar(150),
    message_uuid    varchar(150)    not null,
    foreign key (customer_id) references customer (id)
);
-- indexes
create index idx_notification_log_type on notification_log(type);
create index idx_notification_log_status on notification_log(status);
create index idx_customer_email on customer(email);
create index idx_notification_preference_type on notification_preference(type);

