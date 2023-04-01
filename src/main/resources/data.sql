insert into application_users (dtype, version, id, username,name,hashed_password, email, phone)
values ('customers', 1, 1,'user','Todo1 Test','$2a$10$xdbKoM48VySZqVSU/cSlVeJn0Z04XCZ7KZBjUBC00eKo5uLswyOpe', 'todo1test@gmail.com', '123456789');

insert into shopping_carts(id, active, version, customer_id)
values (1, true, 1, 1);

insert into application_users_roles (application_users_id, roles)
values ('1', 'USER');

insert into application_users (dtype, version, id, username,name,hashed_password, email, phone)
values ('customers', 1, 2,'admin','Diego Gonzalez','$2a$10$jpLNVNeA7Ar/ZQ2DKbKCm.MuT2ESe.Qop96jipKMq7RaUgCoQedV.', 'diego23gonza@hotmail.com', '123456789');

insert into shopping_carts(id, active, version, customer_id)
values (2, true, 1, 2);

insert into application_users_roles (application_users_id, roles)
values ('2', 'USER');

insert into application_users_roles (application_users_id, roles)
values ('2', 'ADMIN');

update application_users
SET shopping_cart_id = 1 where username = 'user';

update application_users
SET shopping_cart_id = 2 where username = 'admin';
