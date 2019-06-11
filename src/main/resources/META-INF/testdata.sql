USE seed;
INSERT INTO users (user_name, user_pass) VALUES ('MadsJ', 'm1234j');
INSERT INTO users (user_name, user_pass) VALUES ('MadsW', 'm1234w');
INSERT INTO roles (role_name) VALUES ('user');
INSERT INTO roles (role_name) VALUES ('admin');
INSERT INTO user_roles (user_name, role_name) VALUES ('user', 'admin');
INSERT INTO user_roles (user_name, role_name) VALUES ('admin', 'admin');