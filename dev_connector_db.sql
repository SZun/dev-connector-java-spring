DROP DATABASE IF EXISTS DevConnectorDB;

CREATE DATABASE DevConnectorDB;

USE DevConnectorDB;

CREATE TABLE Users(
	Id BINARY(16) PRIMARY KEY,
    Username VARCHAR(100) UNIQUE NOT NULL,
    `Password` VARCHAR(255) NOT NULL
);

CREATE TABLE Roles(
	Id BINARY(16) PRIMARY KEY,
	Authority VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE Posts(
	Id BINARY(16) PRIMARY KEY,
    User_Id BINARY(16) NOT NULL,
    `Text` TEXT NOT NULL,
    Title VARCHAR(155) NOT NULL,
    Profile_Pic_URL VARCHAR(255) NOT NULL,
    Post_Date DATE NOT NULL,
    FOREIGN KEY(User_Id) REFERENCES Users(Id)
);

CREATE TABLE Comments(
	Id BINARY(16) PRIMARY KEY,
    User_Id BINARY(16) NOT NULL,
    `Text` VARCHAR(255) NOT NULL,
    Comment_Date DATE NOT NULL,
    FOREIGN KEY(User_Id) REFERENCES Users(Id)
);

CREATE TABLE `Profiles`(
	Id BINARY(16) PRIMARY KEY,
    User_Id BINARY(16) NOT NULL,
    Handle VARCHAR(255) NOT NULL,
    Company VARCHAR(150) NULL,
    Website VARCHAR(255) NULL,
    Location VARCHAR(255) NULL,
    `Description` VARCHAR(255) NULL,
    Github_Username VARCHAR(155) NULL,
    FOREIGN KEY(User_Id) REFERENCES Users(Id)
);

CREATE TABLE Experiences(
	Id BINARY(16) PRIMARY KEY,
	Title VARCHAR(155) NOT NULL,
    Company VARCHAR(155) NOT NULL,
    Location VARCHAR(255) NOT NULL,
    From_Date DATE NOT NULL,
    To_Date DATE NULL,
    `Current` BOOLEAN NOT NULL DEFAULT 1,
    `Description` VARCHAR(255) NULL
);

CREATE TABLE Educations(
	Id BINARY(16) PRIMARY KEY,
    School VARCHAR(255) NOT NULL,
    Degree VARCHAR(200) NOT NULL,
    Field_Of_Study VARCHAR(155) NULL,
    From_Date DATE NOT NULL,
    To_Date DATE NULL,
    `Current` BOOLEAN NOT NULL DEFAULT 1,
    `Description` VARCHAR(255) NULL
);

CREATE TABLE Social_Medias(
	Id BINARY(16) PRIMARY KEY,
    Link VARCHAR(255) NOT NULL
);

CREATE TABLE Skills(
	Id BINARY(16) PRIMARY KEY,
    `Name` VARCHAR(50) NOT NULL
);

CREATE TABLE Profiles_Skills(
	Profile_Id BINARY(16) NOT NULL,
    Skill_Id BINARY(16) NOT NULL,
    PRIMARY KEY(Profile_Id,Skill_Id),
	FOREIGN KEY(Profile_Id) REFERENCES `Profiles`(Id),
	FOREIGN KEY(Skill_Id) REFERENCES Skills(Id)
);

CREATE TABLE Profiles_Educations(
	Profile_Id BINARY(16) NOT NULL,
	Education_Id BINARY(16) UNIQUE NOT NULL,
	PRIMARY KEY(Profile_Id,Education_Id),
	FOREIGN KEY(Profile_Id) REFERENCES `Profiles`(Id),
	FOREIGN KEY(Education_Id) REFERENCES Educations(Id)
);

CREATE TABLE Profiles_Experiences(
	Profile_Id BINARY(16) NOT NULL,
	Experience_Id BINARY(16) UNIQUE NOT NULL,
	PRIMARY KEY(Profile_Id,Experience_Id),
	FOREIGN KEY(Profile_Id) REFERENCES `Profiles`(Id),
	FOREIGN KEY(Experience_Id) REFERENCES Experiences(Id)
);

CREATE TABLE Profiles_Social_Medias(
	Profile_Id BINARY(16) NOT NULL,
	Social_Media_Id BINARY(16) UNIQUE NOT NULL,
	PRIMARY KEY(Profile_Id,Social_Media_Id),
	FOREIGN KEY(Profile_Id) REFERENCES `Profiles`(Id),
	FOREIGN KEY(Social_Media_Id) REFERENCES Social_Medias(Id)
);

CREATE TABLE Posts_Likes(
	User_Id BINARY(16) NOT NULL,
    Post_Id BINARY(16) NOT NULL,
    PRIMARY KEY(User_Id,Post_Id),
	FOREIGN KEY(User_Id) REFERENCES Users(Id),
	FOREIGN KEY(Post_Id) REFERENCES Posts(Id)
);

CREATE TABLE Posts_Comments(
	Post_Id BINARY(16) NOT NULL,
    Comment_Id BINARY(16) NOT NULL,
    PRIMARY KEY(Comment_Id,Post_Id),
	FOREIGN KEY(Post_Id) REFERENCES Posts(Id),
	FOREIGN KEY(Comment_Id) REFERENCES `Comments`(Id)
);

CREATE TABLE Users_Roles(
	User_Id BINARY(16)  NOT NULL,
    Role_Id BINARY(16)  NOT NULL,
    PRIMARY KEY(User_Id,Role_Id),
	FOREIGN KEY(User_Id) REFERENCES Users(Id),
	FOREIGN KEY(Role_Id) REFERENCES Roles(Id)
);