DROP SCHEMA IF EXISTS westerosDB;
CREATE SCHEMA IF NOT EXISTS westerosDB CHARACTER SET utf8 COLLATE utf8_general_ci;
USE westerosDB;


CREATE TABLE House(
name varchar(50) NOT NULL PRIMARY KEY,
region enum ('The North', 'The Vale' , 'Iron Islands' ,'Crownlands' , 'Westerlands','The Reach' , 'Stormlands' ,'Dorne', 'Riverlands', 'The Gift'),
seat varchar(50),
motto varchar(50),
sigil varchar(150)
);

CREATE TABLE Member(
id int AUTO_INCREMENT PRIMARY KEY,
memberName varchar(50) NOT NULL,
houseName varchar(50) NOT NULL
);


CREATE TABLE Color(
color enum ('RED', 'BLUE', 'GREEN', 'WHITE', 'BLACK','BROWN','PURPLE', 
			'BEIGE','AQUA','LIME','GRAY', 'ORANGE','GOLD', 'CRIMSON', 'SILVER', 
            'DARKRED', 'DARKGREEN', 'DARKGRAY', 'DARKBLUE', 'DARKORANGE'),
houseName varchar(50),
PRIMARY KEY (color, houseName)
);

#This table only for new houses, to locate them on the map
CREATE TABLE Coordinates(
x double,
y double,
houseName varchar(50),
PRIMARY KEY (x,y)
);

ALTER TABLE Member 
	ADD CONSTRAINT fk_memberHouse FOREIGN KEY (houseName) REFERENCES House(name);

ALTER TABLE Color 
	ADD CONSTRAINT fk_colorHouse FOREIGN KEY (houseName) REFERENCES House(name);
         
ALTER TABLE Coordinates 
	ADD CONSTRAINT fk_coordinatesHouse FOREIGN KEY (houseName) REFERENCES House(name);
         		 
#House Stark
INSERT INTO House values ("House Stark", "The North", "Winterfell", "Winter is Coming" , "/Images/StarkSigil.png");
INSERT INTO Member (memberName, houseName) values ("Rickard Stark", "House Stark");
INSERT INTO Member (memberName, houseName)  values ("Brandon Stark", "House Stark");
INSERT INTO Member (memberName, houseName)  values ("Eddard Stark", "House Stark");
INSERT INTO Member (memberName, houseName)  values ("Lady Catelyn", "House Stark");
INSERT INTO Member (memberName, houseName)  values ("Robb Stark", "House Stark");
INSERT INTO Member (memberName, houseName)  values ("Sansa Stark", "House Stark");
INSERT INTO Member (memberName, houseName)  values ("Arya Stark", "House Stark");
INSERT INTO Member (memberName, houseName)  values ("Lyanna Stark", "House Stark");
INSERT INTO Member (memberName, houseName)  values ("Benjen Stark", "House Stark");
INSERT INTO Member (memberName, houseName)  values ("Jon Snow", "House Stark");
INSERT INTO Color values ("GRAY", "House Stark");
INSERT INTO Color values ("WHITE", "House Stark");


#House Lannister
INSERT INTO House values ("House Lannister", "Westerlands", "Casterly Rock", "A Lannister Always Pays His Debts", "/Images/LannisterSigil.png");               
INSERT INTO Member (memberName, houseName)  values ("Tywin Lannister", "House Lannister");
INSERT INTO Member (memberName, houseName)  values ("Joanna Lannister", "House Lannister");
INSERT INTO Member (memberName, houseName)  values ("Joffrey Baratheon", "House Lannister");
INSERT INTO Member (memberName, houseName) values ("Myrcella Baratheon", "House Lannister");
INSERT INTO Member (memberName, houseName) values ("Tommen Baratheon", "House Lannister");
INSERT INTO Member (memberName, houseName) values ("Jaime Lannister", "House Lannister");
INSERT INTO Member (memberName, houseName) values ("Tyrion Lannister", "House Lannister");
INSERT INTO Member (memberName, houseName) values ("Kevan Lannister", "House Lannister");
INSERT INTO Member (memberName, houseName) values ("Lady Dorna", "House Lannister");
INSERT INTO Member (memberName, houseName) values ("Lancel Lannister", "House Lannister");
INSERT INTO Member (memberName, houseName)  values ("Martyn Lannister", "House Lannister");
INSERT INTO Member (memberName, houseName)  values ("Willem Lannister", "House Lannister");
INSERT INTO Member (memberName, houseName)  values ("Stafford Lannister", "House Lannister");
INSERT INTO Color values ("GOLD", "House Lannister");
INSERT INTO Color values ("CRIMSON", "House Lannister");


#House Durrandon
INSERT INTO House values ("House Durrandon", "Stormlands", "Storm's End", "Ours is the Fury" , "/Images/DurrandonSigil.png");               
INSERT INTO Member (memberName, houseName)  values ("Durran Durrandon", "House Durrandon");
INSERT INTO Member (memberName, houseName)  values ("Argilac Durrandon", "House Durrandon");
INSERT INTO Member (memberName, houseName)  values ("Argalia Durrandon", "House Durrandon");
INSERT INTO Color values ("GOLD", "House Durrandon");
INSERT INTO Color values ("BLACK", "House Durrandon");


#House Tyrell
INSERT INTO House values ("House Tyrell", "The Reach", "Highgarden", "Growing Strong" , "/Images/TyrellSigil.png");               
INSERT INTO Member (memberName, houseName)  values ("Luthor Tyrell", "House Tyrell");
INSERT INTO Member (memberName, houseName)  values ("Lady Olenna", "House Tyrell");
INSERT INTO Member (memberName, houseName)  values ("Mace Tyrell", "House Tyrell");
INSERT INTO Member (memberName, houseName)  values ("Lady Alerie", "House Tyrell");
INSERT INTO Member (memberName, houseName)  values ("Margaery Tyrell", "House Tyrell");
INSERT INTO Member (memberName, houseName)  values ("Loras Tyrell", "House Tyrell");
INSERT INTO Color values ("GREEN", "House Tyrell");
INSERT INTO Color values ("GOLD", "House Tyrell");


#House Arryn
INSERT INTO House values ("House Arryn", "The Vale", "The Eyrie", "As High as Honor" , "/Images/ArrynSigil.png");               
INSERT INTO Member (memberName,houseName) values ("Jasper Arryn", "House Arryn");
INSERT INTO Member (memberName,houseName) values ("Jon Arryn", "House Arryn");
INSERT INTO Member (memberName,houseName) values ("Lady Lysa", "House Arryn");
INSERT INTO Member (memberName,houseName) values ("Robin Arryn", "House Arryn");
INSERT INTO Member (memberName,houseName) values ("Alys Arryn", "House Arryn");
INSERT INTO Member (memberName,houseName) values ("Ronnel Arryn", "House Arryn");
INSERT INTO Color values ("BLUE", "House Arryn");
INSERT INTO Color values ("WHITE", "House Arryn");


#House Targaryen
INSERT INTO House values ("House Targaryen", "Crownlands", "Dragonstone", "Fire and Blood" , "/Images/TargaryenSigil.png");               
INSERT INTO Member (memberName,houseName) values ("Aerys II Targaryen", "House Targaryen");
INSERT INTO Member (memberName,houseName) values ("Rhaegar Targaryen", "House Targaryen");
INSERT INTO Member (memberName,houseName) values ("Aemon Targaryen", "House Targaryen");
INSERT INTO Member (memberName,houseName) values ("Viserys Targaryen", "House Targaryen");
INSERT INTO Member (memberName,houseName) values ("Daenerys Targaryen", "House Targaryen");
INSERT INTO Color values ("RED", "House Targaryen");
INSERT INTO Color values ("BLACK", "House Targaryen");


#House Martell
INSERT INTO House values ("House Martell", "Dorne", "Sunspear", "Unbowed, Unbent, Unbroken" , "/Images/MartellSigil.png");               
INSERT INTO Member (memberName,houseName) values ("Doran Martell", "House Martell");
INSERT INTO Member (memberName,houseName) values ("Trystane Martell", "House Martell");
INSERT INTO Member (memberName,houseName) values ("Oberyn Martell", "House Martell");
INSERT INTO Member (memberName,houseName) values ("Ellaria Sand", "House Martell");
INSERT INTO Member (memberName,houseName) values ("Elia Martell", "House Martell");
INSERT INTO Member (memberName,houseName) values ("Lewyn Martell", "House Martell");
INSERT INTO Color values ("RED", "House Martell");
INSERT INTO Color values ("GOLD", "House Martell");
INSERT INTO Color values ("ORANGE", "House Martell");


#House Hoare
INSERT INTO House values ("House Hoare", "Riverlands", "Harrenhal", "Unknown" , "/Images/HoareSigil.png");               
INSERT INTO Member (memberName,houseName) values ("Harrag Hoare", "House Hoare");
INSERT INTO Member (memberName,houseName) values ("Qhored Hoare", "House Hoare");
INSERT INTO Member (memberName,houseName) values ("Harren Hoare", "House Hoare");
INSERT INTO Member (memberName,houseName) values ("​Unnamed Hoare", "House Hoare");
INSERT INTO Color values ("SILVER", "House Hoare");
INSERT INTO Color values ("GOLD", "House Hoare");
INSERT INTO Color values ("DARKGREEN", "House Hoare");
INSERT INTO Color values ("WHITE", "House Hoare");
INSERT INTO Color values ("BLACK", "House Hoare");
INSERT INTO Color values ("RED", "House Hoare");
INSERT INTO Color values ("BLUE", "House Hoare");


#House Frey
INSERT INTO House values ("House Frey", "Riverlands", "The Twins", "We Stand Together" , "/Images/FreySigil.png");               
INSERT INTO Member (memberName,houseName) values ("Walder Frey", "House Frey");
INSERT INTO Member (memberName,houseName) values ("Kitty Frey", "House Frey");
INSERT INTO Member (memberName,houseName) values ("Stevron Frey", "House Frey");
INSERT INTO Member (memberName,houseName) values ("Lothar Frey", "House Frey");
INSERT INTO Color values ("GRAY", "House Frey");
INSERT INTO Color values ("DARKGRAY", "House Frey");
INSERT INTO Color values ("BLUE", "House Frey");

#House Greyjoy
INSERT INTO House values ("House Greyjoy", "Iron Islands", "Pyke", "We Do Not Sow" , "/Images/GreyjoySigil.png");               
INSERT INTO Member (memberName,houseName) values ("Balon Greyjoy", "House Greyjoy");
INSERT INTO Member (memberName,houseName) values ("Rodrik  Greyjoy", "House Greyjoy");
INSERT INTO Member (memberName,houseName) values ("Maron Greyjoy", "House Greyjoy");
INSERT INTO Member (memberName,houseName) values ("​Yara Greyjoy", "House Greyjoy");
INSERT INTO Member (memberName,houseName) values ("​Theon Greyjoy", "House Greyjoy");
INSERT INTO Member (memberName,houseName) values ("​Euron Greyjoy", "House Greyjoy");
INSERT INTO Color values ("SILVER", "House Greyjoy");
INSERT INTO Color values ("GOLD", "House Greyjoy");
INSERT INTO Color values ("BLACK", "House Greyjoy");


#The Nights Watch
INSERT INTO House values ("The Night's Watch", "The Gift", "Castle Black", "Night gathers, and now my watch begins", "/Images/NightsWatchSigil.png");               
INSERT INTO Member (memberName,houseName) values ("Eddison Tollett", "The Night's Watch");
INSERT INTO Member (memberName,houseName) values ("Jon Snow", "The Night's Watch");
INSERT INTO Member (memberName,houseName) values ("Alliser Thorne", "The Night's Watch");
INSERT INTO Member (memberName,houseName) values ("​Jeor Mormon", "The Night's Watch");
INSERT INTO Member (memberName,houseName) values ("Denys Mallister", "The Night's Watch");
INSERT INTO Member (memberName,houseName) values ("Cotter Pyke", "The Night's Watch");
INSERT INTO Member (memberName,houseName) values ("Endrew Tarth", "The Night's Watch");
INSERT INTO Member (memberName,houseName) values ("Bowen Marsh", "The Night's Watch");
INSERT INTO Member (memberName,houseName) values ("Othell Yarwyck", "The Night's Watch");
INSERT INTO Color values ("BLACK", "The Night's Watch");
INSERT INTO Color values ("GRAY", "The Night's Watch");
INSERT INTO Color values ("DARKGRAY", "The Night's Watch");
