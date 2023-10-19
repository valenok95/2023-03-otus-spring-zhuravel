insert into AUTHORS (AUTHOR_ID, `name`) values (1, 'Lev Tolstoy');
insert into AUTHORS (AUTHOR_ID, `name`) values (2, 'Pushkin');
insert into AUTHORS (AUTHOR_ID, `name`) values (4, 'Tvardovsky');
insert into GENRES (GENRE_ID, `name`) values (1, 'roman');
insert into GENRES (GENRE_ID, `name`) values (2, 'novella');
insert into BOOKS (BOOK_ID, `name`, `AUTHOR_ID`, `GENRE_ID` ) values (1, 'Voyna i Mir',1,1);
insert into BOOKS (BOOK_ID, `name`, `AUTHOR_ID`, `GENRE_ID` ) values (2, 'Eugene Onegin',2,1);