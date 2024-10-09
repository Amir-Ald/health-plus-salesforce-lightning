-- Table: Account
CREATE TABLE Account
(
    Account_Id            INT PRIMARY KEY AUTO_INCREMENT,
    Account_Name          VARCHAR(255),
    Account_Number        VARCHAR(40),
    Account_Owner         INT,
    Account_Site          VARCHAR(80),
    Account_Source        VARCHAR(255),
    Active                BOOLEAN,
    Annual_Revenue        DECIMAL(18, 0),
    Billing_Address       VARCHAR(255),
    Clean_Status          VARCHAR(255),
    Created_By            INT,
    Customer_Priority     VARCHAR(255),
    DnB_Company           VARCHAR(255),
    DataCom_Key           VARCHAR(20),
    Description           TEXT,
    DUNS_Number           VARCHAR(9),
    Einstein_Account_Tier VARCHAR(2),
    Employees             INT,
    Fax                   VARCHAR(255),
    Industry              VARCHAR(255),
    Last_Modified_By      INT,
    NAICS_Code            VARCHAR(20),
    NAICS_Description     VARCHAR(120),
    Number_of_Locations   INT,

    FOREIGN KEY (Account_Owner) REFERENCES User (User_Id),
    FOREIGN KEY (Created_By) REFERENCES User (User_Id),
    FOREIGN KEY (Last_Modified_By) REFERENCES User (User_Id)
);

-- Table: Contact
CREATE TABLE Contact
(
    Contact_Id                      INT PRIMARY KEY AUTO_INCREMENT,
    Account_Id                      INT,
    Assistant                       VARCHAR(40),
    Asst_Phone                      VARCHAR(20),
    Birthdate                       DATE,
    Clean_Status                    VARCHAR(255),
    Contact_Owner                   INT,
    Created_By                      INT,
    Creation_Source                 VARCHAR(255),
    DataCom_Key                     VARCHAR(20),
    Department                      VARCHAR(80),
    Description                     TEXT,
    Do_Not_Call                     BOOLEAN,
    Email                           VARCHAR(255),
    Email_Opt_Out                   BOOLEAN,
    Fax                             VARCHAR(255),
    Fax_Opt_Out                     BOOLEAN,
    Gender_Identity                 VARCHAR(255),
    Home_Phone                      VARCHAR(20),
    Individual_Id                   INT,
    Languages                       VARCHAR(100),
    Last_Modified_By                INT,
    Last_Stay_In_Touch_Request_Date DATE,

    FOREIGN KEY (Account_Id) REFERENCES Account (Account_Id),
    FOREIGN KEY (Contact_Owner) REFERENCES User (User_Id),
    FOREIGN KEY (Created_By) REFERENCES User (User_Id),
    FOREIGN KEY (Last_Modified_By) REFERENCES User (User_Id)
);

-- Table: Family
CREATE TABLE Family
(
    Family_Id           INT PRIMARY KEY AUTO_INCREMENT,
    Account_Number      VARCHAR(50),
    Annual_Fee          DECIMAL(18, 0),
    Branch_Name         VARCHAR(255),
    Client_Family       VARCHAR(255),
    Client_Name         VARCHAR(255),
    Created_By          INT,
    Family_Name         VARCHAR(80),
    Last_Modified_By    INT,
    Monthly_Fee         DECIMAL(18, 0),
    Plan_Category       VARCHAR(255),
    Plan_Type           VARCHAR(255),
    Renewing_Next_Month BOOLEAN,
    Start_Date          DATE,
    Start_Year YEAR,
    Trainer_Id          INT,

    FOREIGN KEY (Client_Name) REFERENCES Contact (Contact_Id),
    FOREIGN KEY (Trainer_Id) REFERENCES Trainer (Trainer_Id),
    FOREIGN KEY (Created_By) REFERENCES User (User_Id),
    FOREIGN KEY (Last_Modified_By) REFERENCES User (User_Id)
);

-- Table: Trainer
CREATE TABLE Trainer
(
    Trainer_Id       INT PRIMARY KEY AUTO_INCREMENT,
    Created_By       INT,
    Last_Modified_By INT,
    Owner            INT,
    Phone_Number     VARCHAR(20),
    Trainer_Name     VARCHAR(80),

    FOREIGN KEY (Created_By) REFERENCES User (User_Id),
    FOREIGN KEY (Last_Modified_By) REFERENCES User (User_Id),
    FOREIGN KEY (Owner) REFERENCES User (User_Id)
);