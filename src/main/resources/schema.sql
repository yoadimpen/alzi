DROP TABLE usuario IF EXISTS;
DROP TABLE programa IF EXISTS;
DROP TABLE ejercicio IF EXISTS;
DROP TABLE recordatorio IF EXISTS;
DROP TABLE alarma IF EXISTS;
DROP TABLE diario IF EXISTS;
DROP TABLE informe IF EXISTS;
DROP TABLE participante_programa IF EXISTS;
DROP TABLE programa_ejercicio IF EXISTS;

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
    tipo_duracion       VARCHAR(50) NOT NULL,
    puntuacion          INTEGER NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuario
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
    usuario_id          INTEGER NOT NULL,
    fecha               DATETIME NOT NULL,
    etiqueta            VARCHAR(50),
    descripcion         VARCHAR(50),
    FOREIGN KEY (usuario_id) REFERENCES usuario
);

CREATE TABLE alarma (
    alarma_id           INTEGER PRIMARY KEY,
    usuario_id          INTEGER NOT NULL,
    fecha               DATETIME NOT NULL,
    etiqueta            VARCHAR(50), 
    FOREIGN KEY (usuario_id) REFERENCES usuario
);

CREATE TABLE diario (
    diario_id           INTEGER PRIMARY KEY,
    usuario_id          INTEGER NOT NULL,
    fecha               DATE NOT NULL,
    anotacion           VARCHAR(50),
    FOREIGN KEY (usuario_id) REFERENCES usuario
);

CREATE TABLE informe (
    informe_id          INTEGER PRIMARY KEY,
    usuario_id          INTEGER NOT NULL,
    fecha               DATE NOT NULL,
    resultados_areas    VARCHAR(50) NOT NULL,
    diagnostico         VARCHAR(50) NOT NULL,
    observaciones       VARCHAR(50) NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuario
);

CREATE TABLE participante_programa (
    partpro_id          INTEGER PRIMARY KEY,
    usuario_id          INTEGER NOT NULL,
    programa_id         INTEGER NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuario,
    FOREIGN KEY (programa_id) REFERENCES programa
);

CREATE TABLE programa_ejercicio (
    pej_id              INTEGER PRIMARY KEY,
    programa_id         INTEGER NOT NULL,
    ejercicio_id        INTEGER NOT NULL,
    FOREIGN KEY (programa_id) REFERENCES programa,
    FOREIGN KEY (ejercicio_id) REFERENCES ejercicio
);