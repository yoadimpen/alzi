DROP TABLE usuario IF EXISTS;
DROP TABLE programa IF EXISTS;
DROP TABLE ejercicio IF EXISTS;
DROP TABLE recordatorio IF EXISTS;
DROP TABLE informe IF EXISTS;
DROP TABLE participante_programa IF EXISTS;
DROP TABLE programa_ejercicio IF EXISTS;
DROP TABLE ejercicio_pregunta IF EXISTS;

CREATE TABLE usuario (
    usuario_id          INTEGER PRIMARY KEY,
    nombre              VARCHAR(50) NOT NULL,
    apellidos           VARCHAR(50) NOT NULL,
    fecha_nacimiento    DATE NOT NULL,
    dni                 VARCHAR(9) NOT NULL,
    pass                VARCHAR(250) NOT NULL,
    direccion           VARCHAR(50) NOT NULL,
    telefono            INTEGER NOT NULL,
    email               VARCHAR(50) NOT NULL,
    bio                 VARCHAR(250),
    roles               VARCHAR(50) NOT NULL,
    p_relacion_cuidador VARCHAR(50),
    c_tipo              VARCHAR(50),
    e_especialidad      VARCHAR(50),
    e_centro            VARCHAR(50)
);

CREATE TABLE programa (
    programa_id         INTEGER PRIMARY KEY,
    usuario_id          INTEGER NOT NULL,
    titulo              VARCHAR(50) NOT NULL,
    area                VARCHAR(50) NOT NULL,
    descripcion         VARCHAR(200) NOT NULL,
    duracion            INTEGER NOT NULL,
    puntuacion          INTEGER NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuario
    ON DELETE CASCADE
);

CREATE TABLE ejercicio (
    ejercicio_id        INTEGER PRIMARY KEY,
    titulo              VARCHAR(50) NOT NULL,
    descripcion         VARCHAR NOT NULL,
    duracion            INTEGER NOT NULL
);

CREATE TABLE recordatorio (
    recordatorio_id     INTEGER PRIMARY KEY,
    usuario_id          INTEGER NOT NULL,
    fecha               DATETIME NOT NULL,
    etiqueta            NVARCHAR(50),
    descripcion         VARCHAR(50),
    FOREIGN KEY (usuario_id) REFERENCES usuario
    ON DELETE CASCADE
);

CREATE TABLE informe (
    informe_id          INTEGER PRIMARY KEY,
    usuario_id          INTEGER NOT NULL,
    fecha               DATE NOT NULL,
    link       VARCHAR(50) NOT NULL,
    observaciones       VARCHAR(50) NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuario
    ON DELETE CASCADE
);

CREATE TABLE participante_programa (
    partpro_id          INTEGER PRIMARY KEY,
    usuario_id          INTEGER NOT NULL,
    programa_id         INTEGER NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuario
    ON DELETE CASCADE,
    FOREIGN KEY (programa_id) REFERENCES programa
    ON DELETE CASCADE
);

CREATE TABLE programa_ejercicio (
    pej_id              INTEGER PRIMARY KEY,
    programa_id         INTEGER NOT NULL,
    ejercicio_id        INTEGER NOT NULL,
    FOREIGN KEY (programa_id) REFERENCES programa
    ON DELETE CASCADE,
    FOREIGN KEY (ejercicio_id) REFERENCES ejercicio
    ON DELETE CASCADE
);

CREATE TABLE pregunta (
    pregunta_id         INTEGER PRIMARY KEY,
    multi               BOOLEAN NOT NULL,
    descripcion         VARCHAR,
    question            VARCHAR NOT NULL,
    link_question       VARCHAR,
    answer              VARCHAR,
    option_1            VARCHAR,
    link_option_1       VARCHAR,
    is_correct_1        BOOLEAN,
    option_2            VARCHAR,
    link_option_2       VARCHAR,
    is_correct_2        BOOLEAN,
    option_3            VARCHAR,
    link_option_3       VARCHAR,
    is_correct_3        BOOLEAN,
    option_4            VARCHAR,
    link_option_4       VARCHAR,
    is_correct_4        BOOLEAN
);

CREATE TABLE ejercicio_pregunta (
    ejpre_id            INTEGER PRIMARY KEY,
    ejercicio_id        INTEGER NOT NULL,
    pregunta_id         INTEGER NOT NULL,
    FOREIGN KEY (ejercicio_id) REFERENCES ejercicio
    ON DELETE CASCADE,
    FOREIGN KEY (pregunta_id) REFERENCES pregunta
    ON DELETE CASCADE
);