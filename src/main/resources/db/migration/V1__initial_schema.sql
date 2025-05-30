-- Create Role table
CREATE TABLE "Rol" (
    id SERIAL PRIMARY KEY,
    rol VARCHAR(255) NOT NULL
);

-- Create Group table
CREATE TABLE "Administrada" (
    id SERIAL PRIMARY KEY,
    unidad VARCHAR(255) NOT NULL
);

-- Create Branch table
CREATE TABLE "Sucursal" (
    id SERIAL PRIMARY KEY,
    sucursal VARCHAR(255) NOT NULL
);

-- Create Client table
CREATE TABLE "Cliente" (
    id SERIAL PRIMARY KEY,
    cliente VARCHAR(255) NOT NULL
);

-- Create AuditType table
CREATE TABLE "Tipo_Auditoria" (
    id SERIAL PRIMARY KEY,
    tipo_Auditoria VARCHAR(255) NOT NULL
);

-- Create Audited table
CREATE TABLE "Auditado" (
    id SERIAL PRIMARY KEY,
    auditado VARCHAR(255) NOT NULL
);

-- Create Answer table
CREATE TABLE "Respuestas" (
    id SERIAL PRIMARY KEY,
    respuestas VARCHAR(255) NOT NULL
);

-- Create Features table
CREATE TABLE "Caracteristicas" (
    id SERIAL PRIMARY KEY,
    id_tipo_auditoria INTEGER REFERENCES "Tipo_Auditoria"(id),
    id_respuestas INTEGER REFERENCES "Respuestas"(id)
);

-- Create Audit table
CREATE TABLE "Auditoria" (
    id SERIAL PRIMARY KEY,
    fecha_auditoria DATE NOT NULL,
    id_tipo_auditoria INTEGER REFERENCES "Tipo_Auditoria"(id),
    id_auditado INTEGER REFERENCES "Auditado"(id)
);

-- Create User table
CREATE TABLE "Usuario" (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    mail VARCHAR(255) NOT NULL,
    contraseña VARCHAR(255) NOT NULL,
    id_rol INTEGER REFERENCES "Rol"(id)
);

-- Create AfipInput table
CREATE TABLE "Ingreso_Afip" (
    id SERIAL PRIMARY KEY,
    apellido VARCHAR(255) NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    cuil VARCHAR(255) NOT NULL,
    legajo VARCHAR(255) NOT NULL,
    asignacion VARCHAR(255) NOT NULL,
    id_cliente INTEGER REFERENCES "Cliente"(id),
    uoc VARCHAR(255) NOT NULL,
    id_sucursal INTEGER REFERENCES "Sucursal"(id),
    fecha_ingreso DATE NOT NULL,
    id_caracteristicas INTEGER REFERENCES "Caracteristicas"(id),
    id_auditoria INTEGER REFERENCES "Auditoria"(id)
);

-- Create CommonInput table
CREATE TABLE "Ingreso_Comun" (
    id SERIAL PRIMARY KEY,
    apellido VARCHAR(255) NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    cuil VARCHAR(255) NOT NULL,
    legajo VARCHAR(255) NOT NULL,
    asignacion VARCHAR(255) NOT NULL,
    id_cliente INTEGER REFERENCES "Cliente"(id),
    uoc VARCHAR(255) NOT NULL,
    id_sucursal INTEGER REFERENCES "Sucursal"(id),
    fecha_ingreso DATE NOT NULL,
    id_caracteristicas INTEGER REFERENCES "Caracteristicas"(id),
    id_auditoria INTEGER REFERENCES "Auditoria"(id)
);

-- Create indexes
CREATE INDEX idx_usuario_rol ON "Usuario"(id_rol);
CREATE UNIQUE INDEX idx_usuario_mail ON "Usuario"(mail);
CREATE INDEX idx_auditoria_tipo ON "Auditoria"(id_tipo_auditoria);
CREATE INDEX idx_auditoria_auditado ON "Auditoria"(id_auditado);
CREATE INDEX idx_caracteristicas_tipo ON "Caracteristicas"(id_tipo_auditoria);
CREATE INDEX idx_caracteristicas_respuestas ON "Caracteristicas"(id_respuestas);
CREATE INDEX idx_ingreso_afip_cliente ON "Ingreso_Afip"(id_cliente);
CREATE INDEX idx_ingreso_afip_sucursal ON "Ingreso_Afip"(id_sucursal);
CREATE INDEX idx_ingreso_afip_caracteristicas ON "Ingreso_Afip"(id_caracteristicas);
CREATE INDEX idx_ingreso_afip_auditoria ON "Ingreso_Afip"(id_auditoria);
CREATE INDEX idx_ingreso_comun_cliente ON "Ingreso_Comun"(id_cliente);
CREATE INDEX idx_ingreso_comun_sucursal ON "Ingreso_Comun"(id_sucursal);
CREATE INDEX idx_ingreso_comun_caracteristicas ON "Ingreso_Comun"(id_caracteristicas);
CREATE INDEX idx_ingreso_comun_auditoria ON "Ingreso_Comun"(id_auditoria);

-- Insert default roles
INSERT INTO "Rol" (id, rol) VALUES (1, 'Admin');
INSERT INTO "Rol" (id, rol) VALUES (2, 'Auditor');
INSERT INTO "Rol" (id, rol) VALUES (3, 'Administrador de Personal');
INSERT INTO "Rol" (id, rol) VALUES (4, 'Gerente');

-- Insert default audit types (Tipo_Auditoria)
INSERT INTO "Tipo_Auditoria" (id, tipo_Auditoria) VALUES (1, 'SUELDOS');
INSERT INTO "Tipo_Auditoria" (id, tipo_Auditoria) VALUES (2, 'ANTICIPOS SIN DESCONTAR');
INSERT INTO "Tipo_Auditoria" (id, tipo_Auditoria) VALUES (3, 'BAJAS FACTURABLES');
INSERT INTO "Tipo_Auditoria" (id, tipo_Auditoria) VALUES (4, 'CATEGORIAS - GENERALES');
INSERT INTO "Tipo_Auditoria" (id, tipo_Auditoria) VALUES (5, 'FERIADO - POR CANTIDAD');
INSERT INTO "Tipo_Auditoria" (id, tipo_Auditoria) VALUES (6, 'FERIADO - POR CONCEPTO');
INSERT INTO "Tipo_Auditoria" (id, tipo_Auditoria) VALUES (7, 'FERIADO - POR FECHA DE EGRESO');
INSERT INTO "Tipo_Auditoria" (id, tipo_Auditoria) VALUES (8, 'FERIADO - POR FECHA DE INGRESO');
INSERT INTO "Tipo_Auditoria" (id, tipo_Auditoria) VALUES (9, 'CRUCE DE AFIP');
INSERT INTO "Tipo_Auditoria" (id, tipo_Auditoria) VALUES (10, 'SIN RESPUESTA');

-- Insert default answers (Respuestas)
INSERT INTO "Respuestas" (id, respuestas) VALUES (1, 'SE AJUSTA');
INSERT INTO "Respuestas" (id, respuestas) VALUES (2, 'NO SE AJUSTA');
INSERT INTO "Respuestas" (id, respuestas) VALUES (3, 'PENDIENTE HOMOLOGACION');
INSERT INTO "Respuestas" (id, respuestas) VALUES (4, 'AJUSTADO');
INSERT INTO "Respuestas" (id, respuestas) VALUES (5, 'ERROR DE AFIP');
INSERT INTO "Respuestas" (id, respuestas) VALUES (6, 'ERROR DE GRANDES CUENTAS');
INSERT INTO "Respuestas" (id, respuestas) VALUES (7, 'ERROR DE LEGALES');
INSERT INTO "Respuestas" (id, respuestas) VALUES (8, 'LEGAJO DUPLICADO CON CUIL/DNI MAL CARGO');
INSERT INTO "Respuestas" (id, respuestas) VALUES (9, 'MAL CARGADO EL CUIL EN KTNA');
INSERT INTO "Respuestas" (id, respuestas) VALUES (10, 'MAL CARGADO EN KTNA PERTENECE A OTRA UNIDAD');
INSERT INTO "Respuestas" (id, respuestas) VALUES (11, 'MAL REPORTE DE KTNA');
INSERT INTO "Respuestas" (id, respuestas) VALUES (12, 'SE CONFECCIONA EL FORMULARIO 885/A FALTANTE');
INSERT INTO "Respuestas" (id, respuestas) VALUES (13, 'SE DIO DE BAJA EN KTNA');
INSERT INTO "Respuestas" (id, respuestas) VALUES (14, 'SE_DIO_NUEVAMENTE_EL_ALTA_EN_AFIP');
INSERT INTO "Respuestas" (id, respuestas) VALUES (15, 'SE ELIMINÓ LA ASIGNACIÓN PORQUE ES UNA UNIDAD ERRÓNEA');
INSERT INTO "Respuestas" (id, respuestas) VALUES (16, 'SE ELIMINO LA ASIGNACION PORQUE ES UNA NEGATIVA');
INSERT INTO "Respuestas" (id, respuestas) VALUES (17, 'SE MODIFICA EL FORMULARIO 885/A');
INSERT INTO "Respuestas" (id, respuestas) VALUES (18, 'SE REGULARIZA CUANDO SE UNIFICAN LOS DATOS DE LA AFIP');
INSERT INTO "Respuestas" (id, respuestas) VALUES (19, 'SE CAMBIO EL CUIL PROVISORIO POR EL DEFINITIVO');
INSERT INTO "Respuestas" (id, respuestas) VALUES (20, 'SE MODIFICO SIT REVISTA EN AFIP');
INSERT INTO "Respuestas" (id, respuestas) VALUES (21, 'SIN RESPUESTA');

-- Insert default features (Caracteristicas)
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (1, 1, 1);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (2, 1, 2);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (3, 1, 3);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (4, 1, 4);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (5, 2, 1);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (6, 2, 2);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (7, 2, 3);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (8, 2, 4);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (9, 3, 1);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (10, 3, 2);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (11, 3, 3);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (12, 3, 4);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (13, 4, 1);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (14, 4, 2);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (15, 4, 3);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (16, 4, 4);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (17, 5, 1);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (18, 5, 2);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (19, 5, 3);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (20, 5, 4);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (21, 6, 1);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (22, 6, 2);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (23, 6, 3);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (24, 6, 4);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (25, 7, 1);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (26, 7, 2);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (27, 7, 3);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (28, 7, 4);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (29, 8, 1);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (30, 8, 2);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (31, 8, 3);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (32, 8, 4);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (33, 9, 5);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (34, 9, 6);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (35, 9, 7);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (36, 9, 8);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (37, 9, 9);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (38, 9, 10);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (39, 9, 11);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (40, 9, 12);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (41, 9, 13);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (42, 9, 14);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (43, 9, 15);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (44, 9, 16);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (45, 9, 17);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (46, 9, 18);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (47, 9, 19);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (48, 9, 20);
INSERT INTO "Caracteristicas" (id, id_tipo_auditoria, id_respuestas) VALUES (49, 10, 21);

-- Insert default clients (Cliente)
INSERT INTO "Cliente" (id, cliente) VALUES (1, 'AIELLO SUPERMERCADO S.A.');
INSERT INTO "Cliente" (id, cliente) VALUES (2, 'ANTIMA SA');
INSERT INTO "Cliente" (id, cliente) VALUES (3, 'BASLOG SRL');
INSERT INTO "Cliente" (id, cliente) VALUES (4, 'BINKA S.A.');
INSERT INTO "Cliente" (id, cliente) VALUES (5, 'CASINO BUENOS AIRES S.A. COMPAÑIA DE INVERSIONES EN ENTRETENIMIENTO S.A. - UTE');
INSERT INTO "Cliente" (id, cliente) VALUES (6, 'CELULOSA CAMPANA S.A.');
INSERT INTO "Cliente" (id, cliente) VALUES (7, 'CIAGESER SA');
INSERT INTO "Cliente" (id, cliente) VALUES (8, 'CONGREGACION DE MADRES DE DESAMPARADOS Y SAN JOSE DE LA MONTAÑA');
INSERT INTO "Cliente" (id, cliente) VALUES (9, 'DAYS TOUR S.A.');
INSERT INTO "Cliente" (id, cliente) VALUES (10, 'DISAL S.A.');
INSERT INTO "Cliente" (id, cliente) VALUES (11, 'DORINKA S.R.L.');
INSERT INTO "Cliente" (id, cliente) VALUES (12, 'EMAPI S.A.');
INSERT INTO "Cliente" (id, cliente) VALUES (13, 'EMPRESA DISTRIBUIDORA DE ELECTRICIDAD DE SALTA EDESA S.A.');
INSERT INTO "Cliente" (id, cliente) VALUES (14, 'EMPRESA DISTRIBUIDORA DE ENERGIA NORTE SA');
INSERT INTO "Cliente" (id, cliente) VALUES (15, 'ENEX INGENIERIA S A');
INSERT INTO "Cliente" (id, cliente) VALUES (16, 'ESTABLECIMIENTO PILAR S.A.');
INSERT INTO "Cliente" (id, cliente) VALUES (17, 'ESTABLECIMIENTO SANTA ANA S.A.');
INSERT INTO "Cliente" (id, cliente) VALUES (18, 'EXPORT PACK SRL');
INSERT INTO "Cliente" (id, cliente) VALUES (19, 'FRUTANDINA S A');
INSERT INTO "Cliente" (id, cliente) VALUES (20, 'GAMA SONIC ARGENTINA S.R.L.');
INSERT INTO "Cliente" (id, cliente) VALUES (21, 'GEFCO ARGENTINA S.A.');
INSERT INTO "Cliente" (id, cliente) VALUES (22, 'GREIF ARGENTINA SA');
INSERT INTO "Cliente" (id, cliente) VALUES (23, 'LABORATORIOS KÖNIG S.A.');
INSERT INTO "Cliente" (id, cliente) VALUES (24, 'LIBERTAD S.A.');
INSERT INTO "Cliente" (id, cliente) VALUES (25, 'LYT LOGISTICA Y TRANSPORTE S.R.L.');
INSERT INTO "Cliente" (id, cliente) VALUES (26, 'MARCELO T. DE ALVEAR 1368 SOCIEDAD ANONIMA');
INSERT INTO "Cliente" (id, cliente) VALUES (27, 'MILLAN S A');
INSERT INTO "Cliente" (id, cliente) VALUES (28, 'MIX-57 S.A.S.');
INSERT INTO "Cliente" (id, cliente) VALUES (29, 'MUTUAL DE EMPLEADOS Y OBREROS PETROLEROS PRIVADOS');
INSERT INTO "Cliente" (id, cliente) VALUES (30, 'NEOLIN S.A.');
INSERT INTO "Cliente" (id, cliente) VALUES (31, 'PENTOMAT S A I C I F Y M');
INSERT INTO "Cliente" (id, cliente) VALUES (32, 'RECORTERA ARGENTINA S.A.');
INSERT INTO "Cliente" (id, cliente) VALUES (33, 'REFINERIA DEL CENTRO S.A.');
INSERT INTO "Cliente" (id, cliente) VALUES (34, 'REYLAT SOCIEDAD ANONIMA');
INSERT INTO "Cliente" (id, cliente) VALUES (35, 'RICHI S.A.');
INSERT INTO "Cliente" (id, cliente) VALUES (36, 'ROBERTO PASMANTER SOCIEDAD ANONIMA');
INSERT INTO "Cliente" (id, cliente) VALUES (37, 'SOUTH CONVENTION CENTER S.A.');
INSERT INTO "Cliente" (id, cliente) VALUES (38, 'SUELAS LEAL S.A.');
INSERT INTO "Cliente" (id, cliente) VALUES (39, 'TASA LOGISTICA S.A.');
INSERT INTO "Cliente" (id, cliente) VALUES (40, 'TECMAQ S.R.L.');
INSERT INTO "Cliente" (id, cliente) VALUES (41, 'TRANSPORTES 9 DE JULIO S A');

-- Insert default branches (Sucursal)
INSERT INTO "Sucursal" (id, sucursal) VALUES (1, 'Bahia Blanca');
INSERT INTO "Sucursal" (id, sucursal) VALUES (2, 'Cencosud CD EASY');
INSERT INTO "Sucursal" (id, sucursal) VALUES (3, 'Cordoba');
INSERT INTO "Sucursal" (id, sucursal) VALUES (4, 'Grandes Cuentas');
INSERT INTO "Sucursal" (id, sucursal) VALUES (5, 'Las Toscas');
INSERT INTO "Sucursal" (id, sucursal) VALUES (6, 'Mar del Plata');
INSERT INTO "Sucursal" (id, sucursal) VALUES (7, 'Mendoza');
INSERT INTO "Sucursal" (id, sucursal) VALUES (8, 'Puerto (TRP)');
INSERT INTO "Sucursal" (id, sucursal) VALUES (9, 'Rosario');
INSERT INTO "Sucursal" (id, sucursal) VALUES (10, 'San Luis');
INSERT INTO "Sucursal" (id, sucursal) VALUES (11, 'Tucuman');
INSERT INTO "Sucursal" (id, sucursal) VALUES (12, 'ZONA NORTE');
INSERT INTO "Sucursal" (id, sucursal) VALUES (13, 'ZONA OESTE');
INSERT INTO "Sucursal" (id, sucursal) VALUES (14, 'ZONA SUR');

-- Insert default groups (Administrada)
INSERT INTO "Administrada" (id, unidad) VALUES (1, 'GA');
INSERT INTO "Administrada" (id, unidad) VALUES (2, 'GAG');
INSERT INTO "Administrada" (id, unidad) VALUES (3, 'GGB');
INSERT INTO "Administrada" (id, unidad) VALUES (4, 'GI');
INSERT INTO "Administrada" (id, unidad) VALUES (5, 'GL');
INSERT INTO "Administrada" (id, unidad) VALUES (6, 'GLD');
INSERT INTO "Administrada" (id, unidad) VALUES (7, 'GS');

-- Insert default audited (Auditado)
INSERT INTO "Auditado" (id, auditado) VALUES (1, 'Si');
INSERT INTO "Auditado" (id, auditado) VALUES (2, 'No');

-- Common Audits with id_auditado = 2 (10 records)
INSERT INTO "Auditoria" (fecha_auditoria, id_tipo_auditoria, id_auditado) VALUES ('2023-08-30', 4, 2);
INSERT INTO "Auditoria" (fecha_auditoria, id_tipo_auditoria, id_auditado) VALUES ('2023-10-05', 5, 2);
INSERT INTO "Auditoria" (fecha_auditoria, id_tipo_auditoria, id_auditado) VALUES ('2023-12-10', 6, 2);
INSERT INTO "Auditoria" (fecha_auditoria, id_tipo_auditoria, id_auditado) VALUES ('2024-02-15', 7, 2);
INSERT INTO "Auditoria" (fecha_auditoria, id_tipo_auditoria, id_auditado) VALUES ('2024-04-20', 8, 2);
INSERT INTO "Auditoria" (fecha_auditoria, id_tipo_auditoria, id_auditado) VALUES ('2024-06-25', 1, 2);
INSERT INTO "Auditoria" (fecha_auditoria, id_tipo_auditoria, id_auditado) VALUES ('2024-08-30', 2, 2);
INSERT INTO "Auditoria" (fecha_auditoria, id_tipo_auditoria, id_auditado) VALUES ('2024-10-05', 3, 2);
INSERT INTO "Auditoria" (fecha_auditoria, id_tipo_auditoria, id_auditado) VALUES ('2024-12-10', 4, 2);
INSERT INTO "Auditoria" (fecha_auditoria, id_tipo_auditoria, id_auditado) VALUES ('2025-02-15', 5, 2);

-- Common Audits with id_auditado = 1 (10 records)
INSERT INTO "Auditoria" (fecha_auditoria, id_tipo_auditoria, id_auditado) VALUES ('2023-01-25', 5, 1);
INSERT INTO "Auditoria" (fecha_auditoria, id_tipo_auditoria, id_auditado) VALUES ('2023-03-30', 6, 1);
INSERT INTO "Auditoria" (fecha_auditoria, id_tipo_auditoria, id_auditado) VALUES ('2023-05-05', 7, 1);

-- Insert AFIP audits with id_auditado = 2 (10 records)
INSERT INTO "Auditoria" (fecha_auditoria, id_tipo_auditoria, id_auditado) VALUES ('2023-01-15', 9, 2);
INSERT INTO "Auditoria" (fecha_auditoria, id_tipo_auditoria, id_auditado) VALUES ('2023-02-20', 9, 2);
INSERT INTO "Auditoria" (fecha_auditoria, id_tipo_auditoria, id_auditado) VALUES ('2023-03-10', 9, 2);
INSERT INTO "Auditoria" (fecha_auditoria, id_tipo_auditoria, id_auditado) VALUES ('2023-04-05', 9, 2);
INSERT INTO "Auditoria" (fecha_auditoria, id_tipo_auditoria, id_auditado) VALUES ('2023-05-18', 9, 2);
INSERT INTO "Auditoria" (fecha_auditoria, id_tipo_auditoria, id_auditado) VALUES ('2023-06-22', 9, 2);
INSERT INTO "Auditoria" (fecha_auditoria, id_tipo_auditoria, id_auditado) VALUES ('2023-07-30', 9, 2);
INSERT INTO "Auditoria" (fecha_auditoria, id_tipo_auditoria, id_auditado) VALUES ('2023-08-14', 9, 2);
INSERT INTO "Auditoria" (fecha_auditoria, id_tipo_auditoria, id_auditado) VALUES ('2023-09-25', 9, 2);
INSERT INTO "Auditoria" (fecha_auditoria, id_tipo_auditoria, id_auditado) VALUES ('2023-10-08', 9, 2);

-- Insert default users (Usuario)
INSERT INTO "Usuario" (id, nombre, apellido, mail, contraseña, id_rol) VALUES (1, 'Mariano', 'Rojas Matas', 'mrojas@mail.com', 'admin', 1);
INSERT INTO "Usuario" (id, nombre, apellido, mail, contraseña, id_rol) VALUES (2, 'Mariano', 'Rodriguez Lusardi', 'mrodr@mail.com', 'admin', 1);
INSERT INTO "Usuario" (id, nombre, apellido, mail, contraseña, id_rol) VALUES (3, 'Marty', 'McFly', 'auditor@mail.com', 'auditor', 2);
INSERT INTO "Usuario" (id, nombre, apellido, mail, contraseña, id_rol) VALUES (4, 'Morgan', 'Freeman', 'personal@mail.com', 'personal', 3);
INSERT INTO "Usuario" (id, nombre, apellido, mail, contraseña, id_rol) VALUES (5, 'Keanu', 'Reeves', 'gerente@mail.com', 'gerente', 4);

-- Ingresos comunes sin auditar
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('GONZALEZ', 'JUAN PABLO', '20123456789', '315001', '604001', 1, 'AIELLO (Comercio) - GA', 1, '2024-01-15', 49, 1);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('LOPEZ', 'ANA MARIA', '27123456789', '315002', '604002', 2, 'ANTIMA (Comercio) - GAG', 2, '2024-01-16', 49, 1);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('PEREZ', 'ROBERTO CARLOS', '30123456789', '315003', '604003', 3, 'BASLOG (Comercio) - GGB', 3, '2024-01-17', 49, 1);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('SANCHEZ', 'LAURA BEATRIZ', '29123456789', '315004', '604004', 4, 'BINKA (Comercio) - GGB', 4, '2024-01-18', 49, 1);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('TORRES', 'MIGUEL ANGEL', '28123456789', '315005', '604005', 5, 'CASINO (Comercio) - GGB', 5, '2024-01-19', 49, 1);

INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('GONZALEZ', 'JUAN PABLO', '20123456789', '315001', '604001', 1, 'AIELLO (Comercio) - GA', 1, '2024-01-15', 49, 2);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('LOPEZ', 'ANA MARIA', '27123456789', '315002', '604002', 2, 'ANTIMA (Comercio) - GAG', 2, '2024-01-16', 49, 2);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('PEREZ', 'ROBERTO CARLOS', '30123456789', '315003', '604003', 3, 'BASLOG (Comercio) - GGB', 3, '2024-01-17', 49, 2);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('SANCHEZ', 'LAURA BEATRIZ', '29123456789', '315004', '604004', 4, 'BINKA (Comercio) - GGB', 4, '2024-01-18', 49, 2);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('TORRES', 'MIGUEL ANGEL', '28123456789', '315005', '604005', 5, 'CASINO (Comercio) - GGB', 5, '2024-01-19', 49, 2);

INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('GONZALEZ', 'JUAN PABLO', '20123456789', '315001', '604001', 1, 'AIELLO (Comercio) - GA', 1, '2024-01-15', 49, 3);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('LOPEZ', 'ANA MARIA', '27123456789', '315002', '604002', 2, 'ANTIMA (Comercio) - GAG', 2, '2024-01-16', 49, 3);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('PEREZ', 'ROBERTO CARLOS', '30123456789', '315003', '604003', 3, 'BASLOG (Comercio) - GGB', 3, '2024-01-17', 49, 3);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('SANCHEZ', 'LAURA BEATRIZ', '29123456789', '315004', '604004', 4, 'BINKA (Comercio) - GGB', 4, '2024-01-18', 49, 3);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('TORRES', 'MIGUEL ANGEL', '28123456789', '315005', '604005', 5, 'CASINO (Comercio) - GGB', 5, '2024-01-19', 49, 3);

INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('GONZALEZ', 'JUAN PABLO', '20123456789', '315001', '604001', 1, 'AIELLO (Comercio) - GA', 1, '2024-01-15', 49, 4);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('LOPEZ', 'ANA MARIA', '27123456789', '315002', '604002', 2, 'ANTIMA (Comercio) - GAG', 2, '2024-01-16', 49, 4);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('PEREZ', 'ROBERTO CARLOS', '30123456789', '315003', '604003', 3, 'BASLOG (Comercio) - GGB', 3, '2024-01-17', 49, 4);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('SANCHEZ', 'LAURA BEATRIZ', '29123456789', '315004', '604004', 4, 'BINKA (Comercio) - GGB', 4, '2024-01-18', 49, 4);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('TORRES', 'MIGUEL ANGEL', '28123456789', '315005', '604005', 5, 'CASINO (Comercio) - GGB', 5, '2024-01-19', 49, 4);

INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('GONZALEZ', 'JUAN PABLO', '20123456789', '315001', '604001', 1, 'AIELLO (Comercio) - GA', 1, '2024-01-15', 49, 5);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('LOPEZ', 'ANA MARIA', '27123456789', '315002', '604002', 2, 'ANTIMA (Comercio) - GAG', 2, '2024-01-16', 49, 5);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('PEREZ', 'ROBERTO CARLOS', '30123456789', '315003', '604003', 3, 'BASLOG (Comercio) - GGB', 3, '2024-01-17', 49, 5);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('SANCHEZ', 'LAURA BEATRIZ', '29123456789', '315004', '604004', 4, 'BINKA (Comercio) - GGB', 4, '2024-01-18', 49, 5);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('TORRES', 'MIGUEL ANGEL', '28123456789', '315005', '604005', 5, 'CASINO (Comercio) - GGB', 5, '2024-01-19', 49, 5);

INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('GONZALEZ', 'JUAN PABLO', '20123456789', '315001', '604001', 1, 'AIELLO (Comercio) - GA', 1, '2024-01-15', 49, 6);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('LOPEZ', 'ANA MARIA', '27123456789', '315002', '604002', 2, 'ANTIMA (Comercio) - GAG', 2, '2024-01-16', 49, 6);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('PEREZ', 'ROBERTO CARLOS', '30123456789', '315003', '604003', 3, 'BASLOG (Comercio) - GGB', 3, '2024-01-17', 49, 6);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('SANCHEZ', 'LAURA BEATRIZ', '29123456789', '315004', '604004', 4, 'BINKA (Comercio) - GGB', 4, '2024-01-18', 49, 6);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('TORRES', 'MIGUEL ANGEL', '28123456789', '315005', '604005', 5, 'CASINO (Comercio) - GGB', 5, '2024-01-19', 49, 6);

INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('GONZALEZ', 'JUAN PABLO', '20123456789', '315001', '604001', 1, 'AIELLO (Comercio) - GA', 1, '2024-01-15', 49, 7);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('LOPEZ', 'ANA MARIA', '27123456789', '315002', '604002', 2, 'ANTIMA (Comercio) - GAG', 2, '2024-01-16', 49, 7);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('PEREZ', 'ROBERTO CARLOS', '30123456789', '315003', '604003', 3, 'BASLOG (Comercio) - GGB', 3, '2024-01-17', 49, 7);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('SANCHEZ', 'LAURA BEATRIZ', '29123456789', '315004', '604004', 4, 'BINKA (Comercio) - GGB', 4, '2024-01-18', 49, 7);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('TORRES', 'MIGUEL ANGEL', '28123456789', '315005', '604005', 5, 'CASINO (Comercio) - GGB', 5, '2024-01-19', 49, 7);

INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('GONZALEZ', 'JUAN PABLO', '20123456789', '315001', '604001', 1, 'AIELLO (Comercio) - GA', 1, '2024-01-15', 49, 8);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('LOPEZ', 'ANA MARIA', '27123456789', '315002', '604002', 2, 'ANTIMA (Comercio) - GAG', 2, '2024-01-16', 49, 8);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('PEREZ', 'ROBERTO CARLOS', '30123456789', '315003', '604003', 3, 'BASLOG (Comercio) - GGB', 3, '2024-01-17', 49, 8);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('SANCHEZ', 'LAURA BEATRIZ', '29123456789', '315004', '604004', 4, 'BINKA (Comercio) - GGB', 4, '2024-01-18', 49, 8);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('TORRES', 'MIGUEL ANGEL', '28123456789', '315005', '604005', 5, 'CASINO (Comercio) - GGB', 5, '2024-01-19', 49, 8);

INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('GONZALEZ', 'JUAN PABLO', '20123456789', '315001', '604001', 1, 'AIELLO (Comercio) - GA', 1, '2024-01-15', 49, 9);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('LOPEZ', 'ANA MARIA', '27123456789', '315002', '604002', 2, 'ANTIMA (Comercio) - GAG', 2, '2024-01-16', 49, 9);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('PEREZ', 'ROBERTO CARLOS', '30123456789', '315003', '604003', 3, 'BASLOG (Comercio) - GGB', 3, '2024-01-17', 49, 9);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('SANCHEZ', 'LAURA BEATRIZ', '29123456789', '315004', '604004', 4, 'BINKA (Comercio) - GGB', 4, '2024-01-18', 49, 9);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('TORRES', 'MIGUEL ANGEL', '28123456789', '315005', '604005', 5, 'CASINO (Comercio) - GGB', 5, '2024-01-19', 49, 9);

INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('GONZALEZ', 'JUAN PABLO', '20123456789', '315001', '604001', 1, 'AIELLO (Comercio) - GA', 1, '2024-01-15', 49, 10);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('LOPEZ', 'ANA MARIA', '27123456789', '315002', '604002', 2, 'ANTIMA (Comercio) - GAG', 2, '2024-01-16', 49, 10);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('PEREZ', 'ROBERTO CARLOS', '30123456789', '315003', '604003', 3, 'BASLOG (Comercio) - GGB', 3, '2024-01-17', 49, 10);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('SANCHEZ', 'LAURA BEATRIZ', '29123456789', '315004', '604004', 4, 'BINKA (Comercio) - GGB', 4, '2024-01-18', 49, 10);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('TORRES', 'MIGUEL ANGEL', '28123456789', '315005', '604005', 5, 'CASINO (Comercio) - GGB', 5, '2024-01-19', 49, 10);

-- Ingresos comunes auditadas

INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('MARTINEZ', 'MARIA SOLEDAD', '27123456789', '315002', '604002', 2, 'ANTIMA (Comercio) - GAG', 2, '2024-02-20', 2, 11);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('MARTINEZ', 'MARIA SOLEDAD', '27123456789', '315002', '604002', 2, 'ANTIMA (Comercio) - GAG', 2, '2024-02-20', 3, 12);
INSERT INTO "Ingreso_Comun" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('MARTINEZ', 'MARIA SOLEDAD', '27123456789', '315002', '604002', 2, 'ANTIMA (Comercio) - GAG', 2, '2024-02-20', 1, 13);

-- Ingresos AFIP sin auditar

INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('RODRIGUEZ', 'CARLOS ALBERTO', '30123456789', '315003', '604003', 3, 'BASLOG (Comercio) - GGB', 3, '2024-03-10', 49, 14);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('RAMIREZ', 'DIEGO ALEJANDRO', '23123456789', '315006', '604006', 6, 'CELULOSA (Comercio) - GGB', 6, '2024-01-20', 49, 14);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('FERNANDEZ', 'MARIA JOSE', '24123456789', '315007', '604007', 7, 'CIAGESER (Comercio) - GGB', 7, '2024-01-21', 49, 14);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('GOMEZ', 'PABLO MARTIN', '25123456789', '315008', '604008', 8, 'CONGREGACION (Comercio) - GGB', 8, '2024-01-22', 49, 14);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('DIAZ', 'CAROLINA ELIZABETH', '26123456789', '315009', '604009', 9, 'DAYS TOUR (Comercio) - GGB', 9, '2024-01-23', 49, 14);

INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('RODRIGUEZ', 'CARLOS ALBERTO', '30123456789', '315003', '604003', 3, 'BASLOG (Comercio) - GGB', 3, '2024-03-10', 49, 15);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('RAMIREZ', 'DIEGO ALEJANDRO', '23123456789', '315006', '604006', 6, 'CELULOSA (Comercio) - GGB', 6, '2024-01-20', 49, 15);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('FERNANDEZ', 'MARIA JOSE', '24123456789', '315007', '604007', 7, 'CIAGESER (Comercio) - GGB', 7, '2024-01-21', 49, 15);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('GOMEZ', 'PABLO MARTIN', '25123456789', '315008', '604008', 8, 'CONGREGACION (Comercio) - GGB', 8, '2024-01-22', 49, 15);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('DIAZ', 'CAROLINA ELIZABETH', '26123456789', '315009', '604009', 9, 'DAYS TOUR (Comercio) - GGB', 9, '2024-01-23', 49, 15);

INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('RODRIGUEZ', 'CARLOS ALBERTO', '30123456789', '315003', '604003', 3, 'BASLOG (Comercio) - GGB', 3, '2024-03-10', 49, 16);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('RAMIREZ', 'DIEGO ALEJANDRO', '23123456789', '315006', '604006', 6, 'CELULOSA (Comercio) - GGB', 6, '2024-01-20', 49, 16);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('FERNANDEZ', 'MARIA JOSE', '24123456789', '315007', '604007', 7, 'CIAGESER (Comercio) - GGB', 7, '2024-01-21', 49, 16);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('GOMEZ', 'PABLO MARTIN', '25123456789', '315008', '604008', 8, 'CONGREGACION (Comercio) - GGB', 8, '2024-01-22', 49, 16);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('DIAZ', 'CAROLINA ELIZABETH', '26123456789', '315009', '604009', 9, 'DAYS TOUR (Comercio) - GGB', 9, '2024-01-23', 49, 16);

INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('RODRIGUEZ', 'CARLOS ALBERTO', '30123456789', '315003', '604003', 3, 'BASLOG (Comercio) - GGB', 3, '2024-03-10', 49, 17);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('RAMIREZ', 'DIEGO ALEJANDRO', '23123456789', '315006', '604006', 6, 'CELULOSA (Comercio) - GGB', 6, '2024-01-20', 49, 17);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('FERNANDEZ', 'MARIA JOSE', '24123456789', '315007', '604007', 7, 'CIAGESER (Comercio) - GGB', 7, '2024-01-21', 49, 17);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('GOMEZ', 'PABLO MARTIN', '25123456789', '315008', '604008', 8, 'CONGREGACION (Comercio) - GGB', 8, '2024-01-22', 49, 17);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('DIAZ', 'CAROLINA ELIZABETH', '26123456789', '315009', '604009', 9, 'DAYS TOUR (Comercio) - GGB', 9, '2024-01-23', 49, 17);

INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('RODRIGUEZ', 'CARLOS ALBERTO', '30123456789', '315003', '604003', 3, 'BASLOG (Comercio) - GGB', 3, '2024-03-10', 49, 18);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('RAMIREZ', 'DIEGO ALEJANDRO', '23123456789', '315006', '604006', 6, 'CELULOSA (Comercio) - GGB', 6, '2024-01-20', 49, 18);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('FERNANDEZ', 'MARIA JOSE', '24123456789', '315007', '604007', 7, 'CIAGESER (Comercio) - GGB', 7, '2024-01-21', 49, 18);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('GOMEZ', 'PABLO MARTIN', '25123456789', '315008', '604008', 8, 'CONGREGACION (Comercio) - GGB', 8, '2024-01-22', 49, 18);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('DIAZ', 'CAROLINA ELIZABETH', '26123456789', '315009', '604009', 9, 'DAYS TOUR (Comercio) - GGB', 9, '2024-01-23', 49, 18);

INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('RODRIGUEZ', 'CARLOS ALBERTO', '30123456789', '315003', '604003', 3, 'BASLOG (Comercio) - GGB', 3, '2024-03-10', 49, 19);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('RAMIREZ', 'DIEGO ALEJANDRO', '23123456789', '315006', '604006', 6, 'CELULOSA (Comercio) - GGB', 6, '2024-01-20', 49, 19);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('FERNANDEZ', 'MARIA JOSE', '24123456789', '315007', '604007', 7, 'CIAGESER (Comercio) - GGB', 7, '2024-01-21', 49, 19);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('GOMEZ', 'PABLO MARTIN', '25123456789', '315008', '604008', 8, 'CONGREGACION (Comercio) - GGB', 8, '2024-01-22', 49, 19);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('DIAZ', 'CAROLINA ELIZABETH', '26123456789', '315009', '604009', 9, 'DAYS TOUR (Comercio) - GGB', 9, '2024-01-23', 49, 19);

INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('RODRIGUEZ', 'CARLOS ALBERTO', '30123456789', '315003', '604003', 3, 'BASLOG (Comercio) - GGB', 3, '2024-03-10', 49, 20);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('RAMIREZ', 'DIEGO ALEJANDRO', '23123456789', '315006', '604006', 6, 'CELULOSA (Comercio) - GGB', 6, '2024-01-20', 49, 20);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('FERNANDEZ', 'MARIA JOSE', '24123456789', '315007', '604007', 7, 'CIAGESER (Comercio) - GGB', 7, '2024-01-21', 49, 20);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('GOMEZ', 'PABLO MARTIN', '25123456789', '315008', '604008', 8, 'CONGREGACION (Comercio) - GGB', 8, '2024-01-22', 49, 20);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('DIAZ', 'CAROLINA ELIZABETH', '26123456789', '315009', '604009', 9, 'DAYS TOUR (Comercio) - GGB', 9, '2024-01-23', 49, 20);

INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('RODRIGUEZ', 'CARLOS ALBERTO', '30123456789', '315003', '604003', 3, 'BASLOG (Comercio) - GGB', 3, '2024-03-10', 49, 21);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('RAMIREZ', 'DIEGO ALEJANDRO', '23123456789', '315006', '604006', 6, 'CELULOSA (Comercio) - GGB', 6, '2024-01-20', 49, 21);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('FERNANDEZ', 'MARIA JOSE', '24123456789', '315007', '604007', 7, 'CIAGESER (Comercio) - GGB', 7, '2024-01-21', 49, 21);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('GOMEZ', 'PABLO MARTIN', '25123456789', '315008', '604008', 8, 'CONGREGACION (Comercio) - GGB', 8, '2024-01-22', 49, 21);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('DIAZ', 'CAROLINA ELIZABETH', '26123456789', '315009', '604009', 9, 'DAYS TOUR (Comercio) - GGB', 9, '2024-01-23', 49, 21);

INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('RODRIGUEZ', 'CARLOS ALBERTO', '30123456789', '315003', '604003', 3, 'BASLOG (Comercio) - GGB', 3, '2024-03-10', 49, 22);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('RAMIREZ', 'DIEGO ALEJANDRO', '23123456789', '315006', '604006', 6, 'CELULOSA (Comercio) - GGB', 6, '2024-01-20', 49, 22);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('FERNANDEZ', 'MARIA JOSE', '24123456789', '315007', '604007', 7, 'CIAGESER (Comercio) - GGB', 7, '2024-01-21', 49, 22);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('GOMEZ', 'PABLO MARTIN', '25123456789', '315008', '604008', 8, 'CONGREGACION (Comercio) - GGB', 8, '2024-01-22', 49, 22);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('DIAZ', 'CAROLINA ELIZABETH', '26123456789', '315009', '604009', 9, 'DAYS TOUR (Comercio) - GGB', 9, '2024-01-23', 49, 22);

INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('RODRIGUEZ', 'CARLOS ALBERTO', '30123456789', '315003', '604003', 3, 'BASLOG (Comercio) - GGB', 3, '2024-03-10', 49, 23);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('RAMIREZ', 'DIEGO ALEJANDRO', '23123456789', '315006', '604006', 6, 'CELULOSA (Comercio) - GGB', 6, '2024-01-20', 49, 23);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('FERNANDEZ', 'MARIA JOSE', '24123456789', '315007', '604007', 7, 'CIAGESER (Comercio) - GGB', 7, '2024-01-21', 49, 23);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('GOMEZ', 'PABLO MARTIN', '25123456789', '315008', '604008', 8, 'CONGREGACION (Comercio) - GGB', 8, '2024-01-22', 49, 23);
INSERT INTO "Ingreso_Afip" (apellido, nombre, cuil, legajo, asignacion, id_cliente, uoc, id_sucursal, fecha_ingreso, id_caracteristicas, id_auditoria) VALUES ('DIAZ', 'CAROLINA ELIZABETH', '26123456789', '315009', '604009', 9, 'DAYS TOUR (Comercio) - GGB', 9, '2024-01-23', 49, 23);


-- Insert default common inputs (Ingreso_Comun)
