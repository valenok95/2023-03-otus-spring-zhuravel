

insert into AUTHORS (ID, `name`) values (1, 'Lev Tolstoy');
insert into AUTHORS (ID, `name`) values (2, 'Pushkin');
insert into AUTHORS (ID, `name`) values (4, 'Tvardovsky');
insert into GENRES (ID, `name`) values (1, 'roman');
insert into GENRES (ID, `name`) values (2, 'novella');
insert into BOOKS (ID, `name`, `authorID`, `genreID` ) values (1, 'Voyna i Mir',1,1);
insert into BOOKS (ID, `name`, `authorID`, `genreID` ) values (2, 'Eugene Onegin',2,1);