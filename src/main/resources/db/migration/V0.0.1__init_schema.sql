create table if not exists l_locale
(
    id      varchar(36) primary key not null unique,
    key     varchar(256)            not null,
    name    varchar(1024)           not null,
    created timestamp               not null,
    updated timestamp               not null,
    deleted timestamp,

    constraint l_locale_key_deleted_uk unique (key, deleted)
);

create table if not exists l_resource
(
    id        varchar(36) primary key not null unique,
    key       varchar(256)            not null,
    locale_id varchar(36)             not null,
    value     text                    not null,
    created   timestamp               not null,
    updated   timestamp               not null,
    deleted   timestamp,

    constraint l_resource_locale_id_locale_fk foreign key (locale_id) references l_locale (id),
    constraint l_resource_key_locale_deleted_uk unique (key, locale_id, deleted)
);

create index l_resource_key_deleted_index on l_resource (key, deleted);