create table if not exists act32_client (
    dni int primary key,
    nom text not null,
    premium bool not null
);

create table if not exists act32_comanda (
    dni_client int not null,
    num_comanda int primary key,
    preu_total decimal(8,2) not null,
    data date not null,
    foreign key (dni_client) references act32_client(dni)
);