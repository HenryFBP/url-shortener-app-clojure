CREATE TABLE redirects (
    slug varchar(10) primary key not null,
    url varchar(1000) not null,
);

INSERT INTO redirects VALUES("test", "test2");