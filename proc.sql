create table if not exists Client (
	dni int auto_increment,
	nom varchar(255) not null,
	premium boolean default false,
	primary key (dni)
);

create table if not exists Comanda (
	num_comanda int,
	preu_total decimal(8, 2) not null,
	data date not null,
	dni_client int not null,
	primary key(num_comanda),
	foreign key(dni_client) references Client(dni)
);

create table if not exists ResumFacturacio (
	mes int not null,
	any int not null,
	dni_client int not null,
	quantitat decimal(8, 2),
	primary key(mes, any, dni_client),
	foreign key(dni_client) references Client(dni)
);

drop procedure if exists crea_resum_facturacio;

delimiter //
create procedure crea_resum_facturacio
(in p_mes int, p_any int)
begin
	declare acaba boolean default false;
	declare v_dni int;
	declare v_suma int;
	declare c_info cursor for 
		select c.dni, sum(co.preu_total)
		from Client c 
		left join Comanda co on co.dni_client = c.dni 
		where month(co.data) = p_mes and year(co.data) = p_any
		group by c.dni;
	declare continue handler for not found set acaba = true;
	open c_info;
	read_loop: loop
		fetch c_info into v_dni, v_suma;
		if acaba then
			leave read_loop;
		end if;
		insert into ResumFacturacio (mes, any, dni_client, quantitat) values (p_mes, p_any, v_dni, v_suma);
	end loop;
	close c_info;

end //
delimiter ;
