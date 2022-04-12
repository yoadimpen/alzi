DROP TABLE cuidador IF EXISTS;
DROP TABLE participante IF EXISTS;
DROP TABLE especialista IF EXISTS;
DROP TABLE programa IF EXISTS;
DROP TABLE ejercicio IF EXISTS;
DROP TABLE recordatorio IF EXISTS;
DROP TABLE alarma IF EXISTS;
DROP TABLE diario IF EXISTS;
DROP TABLE informe IF EXISTS;
DROP TABLE participante_programa IF EXISTS;
DROP TABLE programa_ejercicio IF EXISTS;

CREATE TABLE cuidador (
    cuidador_id          INTEGER PRIMARY KEY,
    nombre              VARCHAR(50) NOT NULL,
    apellidos           VARCHAR(50) NOT NULL,
    fecha_nacimiento    DATETIME NOT NULL,
    dni                 VARCHAR(9) NOT NULL,
    direccion           VARCHAR(50) NOT NULL,
    telefono            INTEGER NOT NULL,
    email               VARCHAR(50) NOT NULL,
    bio                 VARCHAR(250),
    tipo                VARCHAR(50) NOT NULL
);

CREATE TABLE participante (
    participante_id          INTEGER PRIMARY KEY,
    nombre              VARCHAR(50) NOT NULL,
    apellidos           VARCHAR(50) NOT NULL,
    fecha_nacimiento    DATETIME NOT NULL,
    dni                 VARCHAR(9) NOT NULL,
    direccion           VARCHAR(50) NOT NULL,
    telefono            INTEGER NOT NULL,
    email               VARCHAR(50) NOT NULL,
    bio                 VARCHAR(250),
    relacion_cuidador   VARCHAR(50),
    cuidador_id         INTEGER,
    FOREIGN KEY (cuidador_id) REFERENCES cuidador
);

CREATE TABLE especialista (
    especialista_id          INTEGER PRIMARY KEY,
    nombre              VARCHAR(50) NOT NULL,
    apellidos           VARCHAR(50) NOT NULL,
    fecha_nacimiento    DATETIME NOT NULL,
    dni                 VARCHAR(9) NOT NULL,
    direccion           VARCHAR(50) NOT NULL,
    telefono            INTEGER NOT NULL,
    email               VARCHAR(50) NOT NULL,
    bio                 VARCHAR(250),
    especialidad        VARCHAR(50) NOT NULL,
    centro              VARCHAR(50) NOT NULL
);

CREATE TABLE programa (
    programa_id         INTEGER PRIMARY KEY,
    especialista_id     INTEGER NOT NULL,
    titulo              VARCHAR(50) NOT NULL,
    area                VARCHAR(50) NOT NULL,
    descrcipcion        VARCHAR(200) NOT NULL,
    duracion            INTEGER NOT NULL,
    tipo_duracion       VARCHAR(50) NOT NULL,
    puntuacion          INTEGER NOT NULL,
    FOREIGN KEY (especialista_id) REFERENCES especialista
);

CREATE TABLE ejercicio (
    ejercicio_id        INTEGER PRIMARY KEY,
    titulo              VARCHAR(50) NOT NULL,
    descripcion         VARCHAR(50) NOT NULL,
    duracion            INTEGER NOT NULL,
    tipo_duracion       VARCHAR(50) NOT NULL
);

CREATE TABLE recordatorio (
    recordatorio_id     INTEGER PRIMARY KEY,
    participante_id     INTEGER NOT NULL,
    fecha               DATETIME NOT NULL,
    etiqueta            VARCHAR(50),
    descripcion         VARCHAR(50),
    FOREIGN KEY (participante_id) REFERENCES participante
);

CREATE TABLE alarma (
    alarma_id           INTEGER PRIMARY KEY,
    participante_id     INTEGER NOT NULL,
    fecha               DATETIME NOT NULL,
    etiqueta            VARCHAR(50), 
    FOREIGN KEY (participante_id) REFERENCES participante
);

CREATE TABLE diario (
    diario_id           INTEGER PRIMARY KEY,
    participante_id     INTEGER NOT NULL,
    fecha               DATE NOT NULL,
    anotacion           VARCHAR(50),
    FOREIGN KEY (participante_id) REFERENCES participante
);

CREATE TABLE informe (
    informe_id          INTEGER PRIMARY KEY,
    participante_id     INTEGER NOT NULL,
    fecha               DATE NOT NULL,
    resultados_areas    VARCHAR(50) NOT NULL,
    diagnostico         VARCHAR(50) NOT NULL,
    observaciones       VARCHAR(50) NOT NULL,
    FOREIGN KEY (participante_id) REFERENCES participante
);

CREATE TABLE participante_programa (
    partpro_id          INTEGER PRIMARY KEY,
    participante_id     INTEGER NOT NULL,
    programa_id         INTEGER NOT NULL,
    FOREIGN KEY (participante_id) REFERENCES participante,
    FOREIGN KEY (programa_id) REFERENCES programa
);

CREATE TABLE programa_ejercicio (
    pej_id              INTEGER PRIMARY KEY,
    programa_id         INTEGER NOT NULL,
    ejercicio_id        INTEGER NOT NULL,
    FOREIGN KEY (programa_id) REFERENCES programa,
    FOREIGN KEY (ejercicio_id) REFERENCES ejercicio
);