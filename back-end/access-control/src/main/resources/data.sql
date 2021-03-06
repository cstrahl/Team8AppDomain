CREATE TABLE IF NOT EXISTS Users
(
    Id                    INT AUTO_INCREMENT PRIMARY KEY,
    Username              VARCHAR(100) UNIQUE,
    First_Name            VARCHAR(100),
    Last_Name             VARCHAR(100),
    Email                 VARCHAR(250) UNIQUE NOT NULL,
    Date_Of_Birth         DATETIME,
    Password_Hash         VARCHAR(250)        NOT NULL,
    Lockout_End           DATETIME,
    Login_Attempts        INT,
    Password_Create_Date  DATETIME            NOT NULL,
    Assigned_Role         VARCHAR(100),
    Registered_By         INT,
    Awaiting_Registration BOOLEAN,
    Temp_Password         BOOLEAN
);

MERGE INTO Users (Id,
                  Username,
                  First_Name,
                  Last_Name,
                  Email,
                  Date_Of_Birth,
                  Password_Hash,
                  Lockout_End,
                  Login_Attempts,
                  Password_Create_Date,
                  Assigned_Role,
                  Registered_By,
                  Awaiting_Registration,
                  Temp_Password)
    VALUES (1,
            'TAdmin0000',
            'Test',
            'Admin',
            'TestAdmin@testing.com',
            now(),
            '$2a$10$7xCQwLaBoTDmk/E4lksT4OcBWFlKZroC806TgLJVrKmo56GuXbhze',
            now(),
            0,
            now(),
            'ROLE_ADMIN',
            1,
            false,
            false),
           (2,
            'TManager0000',
            'Test',
            'Manager',
            'TestManager@testing.com',
            now(),
            '$2a$10$7xCQwLaBoTDmk/E4lksT4OcBWFlKZroC806TgLJVrKmo56GuXbhze',
            now(),
            0,
            now(),
            'ROLE_MANAGER',
            1,
            false,
            false),
           (3,
            'TAccountant0000',
            'Test',
            'Accountant',
            'TestAccountant@testing.com',
            now(),
            '$2a$10$7xCQwLaBoTDmk/E4lksT4OcBWFlKZroC806TgLJVrKmo56GuXbhze',
            now(),
            0,
            now(),
            'ROLE_USER',
            1,
            false,
            false);

CREATE TABLE IF NOT EXISTS Accounts
(
    Id              INT AUTO_INCREMENT PRIMARY KEY,
    Name            VARCHAR(100) UNIQUE,
    Enabled         BOOLEAN,
    Description     VARCHAR(250),
    Side            VARCHAR(50) NOT NULL,
    Category        VARCHAR(50),
    Subcategory     VARCHAR(50),
    Initial_Balance DOUBLE      NOT NULL,
    Debit           DOUBLE      NOT NULL,
    Credit          DOUBLE      NOT NULL,
    Balance         DOUBLE      NOT NULL,
    Account_Added   DATETIME    NOT NULL,
    Created_By      VARCHAR(50) NOT NULL,
    Sort_Order      INT         NOT NULL,
    Statement       VARCHAR(50),
    Comments        VARCHAR(250)
);

CREATE TABLE IF NOT EXISTS Journals
(
    Id         INT AUTO_INCREMENT PRIMARY KEY,
    Start_Date DATETIME NOT NULL,
    End_Date   DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS Journal_Entry
(
    Id            INT AUTO_INCREMENT PRIMARY KEY,
    Journal_Id    INT          NOT NULL,
    Creator_Id    INT          NOT NULL,
    Create_Date   DATETIME     NOT NULL,
    Reviewer_Id   INT          NULL,
    Review_Date   DATETIME     NULL,
    Document_Id   INT          NULL,
    Status        VARCHAR(50)  NULL,
    Denied_Reason VARCHAR(250) NULL
);

CREATE TABLE IF NOT EXISTS Ledger_Entry
(
    Id               INT AUTO_INCREMENT PRIMARY KEY,
    Account_Id       INT      NOT NULL,
    Journal_Entry_Id INT      NOT NULL,
    Create_Date      DATETIME NOT NULL,
    Credit           DOUBLE,
    Debit            DOUBLE,
    Description      VARCHAR(250),
    Pending          BIT      NOT NULL
);
