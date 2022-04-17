INSERT INTO usuario (usuario_id, nombre, apellidos, fecha_nacimiento, dni, pass, direccion, telefono, email, bio, roles, p_relacion_cuidador, c_tipo, e_especialidad, e_centro) VALUES (10001,'John','Doe Cuidador','1999-04-04','Y1234567W','$2a$12$IIYwXMf22f/xdtrORCTq/uEETrsf727zqN6o8.3Z31F0VwR5SE.ky','C/ Direccion Ejemplo Nº1',612345678,'johndoe.cuidador@mail.com',NULL,'CUIDADOR',NULL,'Informal',NULL,NULL);
INSERT INTO usuario (usuario_id, nombre, apellidos, fecha_nacimiento, dni, pass, direccion, telefono, email, bio, roles, p_relacion_cuidador, c_tipo, e_especialidad, e_centro) VALUES (10002,'John','Doe Participante','1969-04-04','Y1234568W','$2a$12$IIYwXMf22f/xdtrORCTq/uEETrsf727zqN6o8.3Z31F0VwR5SE.ky','C/ Direccion Ejemplo Nº2',612345678,'johndoe.participante@mail.com',NULL, 'PARTICIPANTE', 'Hijo',NULL,NULL,NULL);
INSERT INTO usuario (usuario_id, nombre, apellidos, fecha_nacimiento, dni, pass, direccion, telefono, email, bio, roles, p_relacion_cuidador, c_tipo, e_especialidad, e_centro) VALUES (10003,'John','Doe Especialista','1989-04-04','Y1234569W','$2a$12$IIYwXMf22f/xdtrORCTq/uEETrsf727zqN6o8.3Z31F0VwR5SE.ky','C/ Direccion Ejemplo Nº3',612345678,'johndoe.especialista@mail.com',NULL, 'ESPECIALISTA', NULL,NULL,'Neuropsicología','Centro Ejemplo');

INSERT INTO programa (programa_id, usuario_id, titulo, area, descripcion, duracion, tipo_duracion, puntuacion) VALUES (1,10001,'Titulo 1','Area 1','Descripcion 1',3,'semanas',0);
INSERT INTO ejercicio (ejercicio_id, titulo, descripcion, duracion, tipo_duracion) VALUES (1,'Titulo Ejercicio','Descripcion Ejercicio',5,'minutos');

INSERT INTO recordatorio (recordatorio_id, usuario_id, fecha, etiqueta, descripcion) VALUES (1,10002,'2022-04-12 12:30:00','Etiqueta','Descripcion');
INSERT INTO alarma (alarma_id, usuario_id, fecha, etiqueta) VALUES (1,10002,'2022-04-12 12:45:00','Etiqueta Alarma');
INSERT INTO diario (diario_id, usuario_id, fecha, anotacion) VALUES (1,10002,'2022-04-12','Anotaciones de diario.');

INSERT INTO informe (informe_id, usuario_id, fecha, resultados_areas, diagnostico, observaciones) VALUES (1,10002,'2022-04-12','Resultados del participante.','Diagnostico pendiente.','Observaciones pendientes.');

INSERT INTO participante_programa (partpro_id, usuario_id, programa_id) VALUES (1,10002,1);
INSERT INTO programa_ejercicio (pej_id, programa_id, ejercicio_id) VALUES (1,1,1);