insert into users (version, id, username,name,hashed_password, email)
values (1, 1,'user','Todo1 Test','$2a$10$xdbKoM48VySZqVSU/cSlVeJn0Z04XCZ7KZBjUBC00eKo5uLswyOpe', 'todo1test@gmail.com');

insert into users_roles (users_id, roles)
values ('1', 'USER');

insert into users (id, active, version, username, name,hashed_password, email)
values (2, 1, 1,'admin','Diego Gonzalez','$2a$10$jpLNVNeA7Ar/ZQ2DKbKCm.MuT2ESe.Qop96jipKMq7RaUgCoQedV.', 'diego23gonza@hotmail.com');

insert into users_roles (users_id, roles)
values ('2', 'USER');

insert into users_roles (users_id, roles)
values ('2', 'ADMIN');