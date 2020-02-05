DROP TABLE IF EXISTS Users;
CREATE TABLE Users (
  Id                    INT AUTO_INCREMENT PRIMARY KEY,
  Username              VARCHAR(100) UNIQUE,
  First_Name             VARCHAR(100),
  Last_Name              VARCHAR(100),
  Email                 VARCHAR(250) UNIQUE NOT NULL,
  Date_Of_Birth           DATETIME,
  Password_Hash          VARCHAR(250) NOT NULL,
  Lockout_End            DATETIME,
  Login_Attempts         INT,
  Password_Create_Date    DATETIME NOT NULL,
  Assigned_Role          VARCHAR(100),
  Registered_By          INT,
  Awaiting_Registration  BOOLEAN
);

INSERT INTO Users (
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
    Awaiting_Registration
) VALUES (
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
    false
);
