INSERT INTO RELATION (id,name,description) VALUES (1,'Friend','Casual Friend');
INSERT INTO RELATION (id,name,description) VALUES (2,'Client','Business Contact');
INSERT INTO RELATION (id,name,description) VALUES (3,'Self','Myself');

INSERT INTO EXPENSE_CATEGORY(id,name,description) VALUES (1,'Food','Dining out');
INSERT INTO EXPENSE_CATEGORY(id,name,description) VALUES (2,'Drinks','Dining out');
INSERT INTO EXPENSE_CATEGORY(id,name,description) VALUES (3,'Movie Ticket','Movie tickets');
INSERT INTO EXPENSE_CATEGORY(id,name,description) VALUES (4,'Cab','Taxi charges');

INSERT INTO CUSTOM_GROUP(id,name) VALUES (1,'Dokkudus');
INSERT INTO CUSTOM_GROUP(id,name) VALUES (2,'Fitbit Sportsmen');
INSERT INTO CUSTOM_GROUP(id,name) VALUES (3,'Wild Walkers');

INSERT INTO PERSON(id,first_name,last_name,relation_id) VALUES (1,'Aju','George',1);
INSERT INTO PERSON(id,first_name,last_name,relation_id) VALUES (2,'Jayakrishnan','Balakrishnan',1);
INSERT INTO PERSON(id,first_name,last_name,relation_id) VALUES (3,'Sujith','Nadh',1);
INSERT INTO PERSON(id,first_name,last_name,relation_id) VALUES (4,'Sajan','Raj',3);
INSERT INTO PERSON(id,first_name,last_name,relation_id) VALUES (5,'Abraham','Joseph',1);
INSERT INTO PERSON(id,first_name,last_name,relation_id) VALUES (6,'Babin','Baby',1);
INSERT INTO PERSON(id,first_name,last_name,relation_id) VALUES (7,'Anoop','Paul',1);
INSERT INTO PERSON(id,first_name,last_name,relation_id) VALUES (8,'Retheesh','John',1);

INSERT INTO PERSON_GROUP(person_id,group_id) VALUES (4,1);
INSERT INTO PERSON_GROUP(person_id,group_id) VALUES (4,2);
INSERT INTO PERSON_GROUP(person_id,group_id) VALUES (4,3);

INSERT INTO PERSON_GROUP(person_id,group_id) VALUES (1,1);
INSERT INTO PERSON_GROUP(person_id,group_id) VALUES (1,2);
INSERT INTO PERSON_GROUP(person_id,group_id) VALUES (1,3);

INSERT INTO PERSON_GROUP(person_id,group_id) VALUES (2,1);

INSERT INTO PERSON_GROUP(person_id,group_id) VALUES (3,1);
INSERT INTO PERSON_GROUP(person_id,group_id) VALUES (3,2);
INSERT INTO PERSON_GROUP(person_id,group_id) VALUES (3,3);

INSERT INTO PERSON_GROUP(person_id,group_id) VALUES (5,2);

INSERT INTO PERSON_GROUP(person_id,group_id) VALUES (6,2);

INSERT INTO PERSON_GROUP(person_id,group_id) VALUES (7,2);

INSERT INTO PERSON_GROUP(person_id,group_id) VALUES (8,2);

