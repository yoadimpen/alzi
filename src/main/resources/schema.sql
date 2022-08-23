DROP TABLE usuario IF EXISTS;
DROP TABLE programa IF EXISTS;
DROP TABLE ejercicio IF EXISTS;
DROP TABLE recordatorio IF EXISTS;
DROP TABLE participante_programa IF EXISTS;
DROP TABLE programa_ejercicio IF EXISTS;
DROP TABLE pregunta IF EXISTS;
DROP TABLE ejercicio_pregunta IF EXISTS;
DROP TABLE informe_programa IF EXISTS;
DROP TABLE informe_ejercicio IF EXISTS;
DROP TABLE informe_pregunta IF EXISTS;
DROP TABLE usuario_cuidador IF EXISTS;
DROP TABLE usuario_especialista IF EXISTS;

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
    tipo                VARCHAR NOT NULL,
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

CREATE TABLE informe_programa (
    informe_programa_id     INTEGER PRIMARY KEY,
    programa_id             INTEGER NOT NULL,
    usuario_id              INTEGER NOT NULL,
    progreso                INTEGER NOT NULL,
    aciertos                INTEGER NOT NULL,
    fallos                  INTEGER NOT NULL,
    observaciones           VARCHAR,
    FOREIGN KEY (programa_id) REFERENCES programa,
    FOREIGN KEY (usuario_id) REFERENCES usuario
);

CREATE TABLE informe_ejercicio (
    informe_ejercicio_id    INTEGER PRIMARY KEY,
    programa_id             INTEGER NOT NULL,
    ejercicio_id            INTEGER NOT NULL,
    usuario_id              INTEGER NOT NULL,
    aciertos                INTEGER NOT NULL,
    fallos                  INTEGER NOT NULL,
    observaciones           VARCHAR,
    finalizado              BOOLEAN,
    FOREIGN KEY (programa_id) REFERENCES programa,
    FOREIGN KEY (ejercicio_id) REFERENCES ejercicio,
    FOREIGN KEY (usuario_id) REFERENCES usuario
);

CREATE TABLE informe_pregunta (
    informe_pregunta_id    INTEGER PRIMARY KEY,
    programa_id             INTEGER NOT NULL,
    ejercicio_id            INTEGER NOT NULL,
    pregunta_id             INTEGER NOT NULL,
    usuario_id              INTEGER NOT NULL,
    respuesta               VARCHAR,
    resultado               BOOLEAN,
    FOREIGN KEY (programa_id) REFERENCES programa,
    FOREIGN KEY (ejercicio_id) REFERENCES ejercicio,
    FOREIGN KEY (pregunta_id) REFERENCES pregunta,
    FOREIGN KEY (usuario_id) REFERENCES usuario
);

CREATE TABLE usuario_cuidador (
    usuario_cuidador_id     INTEGER PRIMARY KEY,
    usuario_id              INTEGER NOT NULL,
    cuidador_id             INTEGER NOT NULL
);

CREATE TABLE usuario_especialista (
    usuario_especialista_id     INTEGER PRIMARY KEY,
    usuario_id              INTEGER NOT NULL,
    especialista_id             INTEGER NOT NULL
);