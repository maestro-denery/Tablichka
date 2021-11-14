create table if not exists `waystones`
(
    id     bigint      not null auto_increment,
    x      integer     not null,
    y   integer     not null,
    z      integer     not null,
    world  binary(16)  not null,
    name   varchar(50) not null,
    constraint waystone_pk primary key (id),
    -- Indexing
    constraint waystone_uq1 unique (x, y, z, world),
    constraint waystone_uq2 unique (y, z, world, x),
    constraint waystone_uq3 unique (z, world, x, y),
    constraint waystone_uq4 unique (world, x, y, z)
);
create table if not exists `player's waystones`
(
    player      binary(16) not null,
    waystone_id bigint     not null,
    constraint player_waystone_pk primary key (player, waystone_id),
    constraint waystone_fk foreign key (waystone_id) references `waystones` (id) on delete cascade on update cascade
);