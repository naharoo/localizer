create table if not exists l_locale
(
  id      varchar(36) primary key not null unique,
  key     varchar(256)            not null,
  name    varchar(1024)           not null,
  created timestamp               not null,
  updated timestamp               not null,
  deleted timestamp,

  constraint l_locale_id_deleted_uk unique (id, deleted),
  constraint l_locale_key_deleted_uk unique (key, deleted)
);

create unique index l_locale_id_deleted_index on l_locale (id, deleted);
create unique index l_locale_key_deleted_index on l_locale (key, deleted);