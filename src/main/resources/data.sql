INSERT INTO cuidador (cuidador_id, nombre, apellidos, fecha_nacimiento, dni, direccion, telefono, email, bio, tipo) VALUES (1,'John','Doe Cuidador','1999-04-04 22:33:44','Y1234567W','C/ Direccion Ejemplo Nº1',612345678,'johndoe.cuidador@mail.com',NULL,'Informal');
INSERT INTO participante (participante_id, nombre, apellidos, fecha_nacimiento, dni, direccion, telefono, email, bio, relacion_cuidador,cuidador_id) VALUES (1,'John','Doe Participante','1969-04-04 22:33:44','Y1234568W','C/ Direccion Ejemplo Nº2',612345679,'johndoe.participante@mail.com',NULL,'Hijo',1);
INSERT INTO especialista (especialista_id, nombre, apellidos, fecha_nacimiento, dni, direccion, telefono, email, bio, especialidad, centro) VALUES (1,'John','Doe Especialista','1989-04-04 22:33:44','Y1234569W','C/ Direccion Ejemplo Nº3',612345670,'johndoe.especialista@mail.com',NULL,'Neuropsicología','Centro Ejemplo');

INSERT INTO programa (programa_id, especialista_id, titulo, area, descripcion, duracion, tipo_duracion, puntuacion) VALUES (1,1,'Titulo 1','Area 1','Descripcion 1',3,'semanas',0);
INSERT INTO ejercicio (ejercicio_id, titulo, descripcion, duracion, tipo_duracion) VALUES (1,'Titulo Ejercicio','Descripcion Ejercicio',5,'minutos');

INSERT INTO recordatorio (recordatorio_id, participante_id, fecha, etiqueta, descripcion) VALUES (1,1,'2022-04-12 12:30:00','Etiqueta','Descripcion');
INSERT INTO alarma (alarma_id, participante_id, fecha, etiqueta) VALUES (1,1,'2022-04-12 12:45:00','Etiqueta Alarma');
INSERT INTO diario (diario_id, participante_id, fecha, anotacion) VALUES (1,1,'2022-04-12','Anotaciones de diario.');

INSERT INTO informe (informe_id, participante_id, fecha, resultados_areas, diagnostico, observaciones) VALUES (1,1,'2022-04-12','Resultados del participante.','Diagnostico pendiente.','Observaciones pendientes.');

INSERT INTO participante_programa (partpro_id, participante_id, programa_id) VALUES (1,1,1);
INSERT INTO programa_ejercicio (pej_id, programa_id, ejercicio_id) VALUES (1,1,1);