--Daniel Artz

-- Drop tables reverse order and sequence
DROP TABLE payment CASCADE CONSTRAINTS;
DROP TABLE expenses CASCADE CONSTRAINTS;
DROP TABLE principal CASCADE CONSTRAINTS;
DROP TABLE opponent CASCADE CONSTRAINTS;
DROP TABLE matter CASCADE CONSTRAINTS;
DROP TABLE bill CASCADE CONSTRAINTS;
DROP TABLE attorney CASCADE CONSTRAINTS;
DROP SEQUENCE principal_id_seq;
DROP SEQUENCE opponent_id_seq;
DROP SEQUENCE client_id_seq;
DROP TABLE client CASCADE CONSTRAINTS;

-- Create tables
CREATE TABLE client (
client_id NUMBER(10) NOT NULL CONSTRAINT client_id_pk PRIMARY KEY,
client_first_name VARCHAR2(30),
client_last_name VARCHAR(30),
client_address VARCHAR2(30),
client_cty VARCHAR(30),
client_state CHAR(2),
client_zip NUMBER(5),
client_phone NUMBER(10),
client_referred VARCHAR(65)

);

-- Create Sequences

CREATE SEQUENCE client_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE opponent_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE principal_id_seq START WITH 1 INCREMENT BY 1;

---

CREATE TABLE attorney (
att_id NUMBER(10) NOT NULL CONSTRAINT attorney_att_id_pk PRIMARY KEY,
att_first_name VARCHAR2(30),
att_last_name VARCHAR2(30),
att_address VARCHAR2(100),
att_city VARCHAR2(30),
att_state CHAR(2),
att_zip NUMBER(5),
att_phone NUMBER(10) UNIQUE,
att_speciality VARCHAR2(60),
att_ann_salary NUMBER(11, 2),
att_percentage NUMBER(2, 2),
att_year_joined_firm DATE

);

CREATE TABLE bill(
invoice_no NUMBER(10) NOT NULL CONSTRAINT bill_invoice_no_pk PRIMARY KEY,
billed_date DATE,
amount_bill_acct NUMBER(11, 2)
);

CREATE TABLE matter(
matter_id NUMBER(10) NOT NULL CONSTRAINT matter_matter_id_pk PRIMARY KEY,
matter_type VARCHAR2(60),
matter_date_opened DATE,
matter_outcome VARCHAR2(30) CONSTRAINT matter_matter_outcome_cc CHECK(matter_outcome IN('won','lost', 'open')),
matter_date_closed DATE,
att_id NUMBER(10) CONSTRAINT matter_att_id_fk REFERENCES attorney(att_id),
client_id NUMBER(10) CONSTRAINT matter_client_id_fk REFERENCES client(client_id)
);

CREATE TABLE opponent(
opponent_id NUMBER(10) CONSTRAINT opponent_opponent_id_pk PRIMARY KEY,
opponent_first_name VARCHAR2(30),
opponent_last_name VARCHAR2(30),
matter_id NUMBER(10) CONSTRAINT opponent_matter_id_fk REFERENCES matter(matter_id)
);

CREATE TABLE principal(
principal_id NUMBER(10) CONSTRAINT principal_principal_id_pk PRIMARY KEY,
principal_first_name VARCHAR2(30),
principal_last_name VARCHAR2(30),
matter_id NUMBER(10) CONSTRAINT principal_matter_id_fk REFERENCES matter(matter_id)
);

CREATE TABLE expenses(
expense_id NUMBER(10) CONSTRAINT expenses_expense_id_pk PRIMARY KEY,
expense_date DATE,
exp_description VARCHAR2(150),
exp_amount NUMBER(11, 2),
matter_id NUMBER(10) CONSTRAINT expenses_matter_id_fk REFERENCES matter(matter_id),
invoice_no NUMBER(10) CONSTRAINT expenses_invoice_no_fk REFERENCES bill(invoice_no)
);

CREATE TABLE payment(
payment_id NUMBER(10) CONSTRAINT payment_payment_id_pk PRIMARY KEY,
payment_date DATE,
amount_paid NUMBER(11, 2),
client_id NUMBER(10) CONSTRAINT payment_client_id_fk REFERENCES client(client_id),
invoice_no NUMBER(10) CONSTRAINT payment_invoice_no_fk REFERENCES bill(invoice_no)
);



--Insert records into client
INSERT INTO client VALUES
(client_id_seq.NEXTVAL, 'Neal', 'Graham', '9815 Circle Dr.', 'Tallahassee', 'FL', '32308', '9045551897','Sam Peters');
INSERT INTO client VALUES
(client_id_seq.NEXTVAL, 'Myra', 'Sanchez', '172 Alto Park', 'Seattle', 'WA', '42180', '4185551791','Dan Cummings');
INSERT INTO client VALUES
(client_id_seq.NEXTVAL, 'Lisa', 'Smith', '850 East Main', 'Santa Ana', 'CA', '51875', '3075557841','David Bowing');
INSERT INTO client VALUES
(client_id_seq.NEXTVAL, 'Paul', 'Phelp', '994 Kirkman Rd.', 'Northpoint', 'NY', '11795', '4825554788','Dan Brown');
INSERT INTO client VALUES
(client_id_seq.NEXTVAL, 'Sheila', 'Lewis', '195 College Blvd.', 'Newton', 'GA', '37812', '3525554972','Derrek Holding');

--Insert records into attorney fix dates
INSERT INTO attorney VALUES
(1, 'Sam', 'Harris', '62 Mayflower St.', 'Sanford', 'FL', '32771', '4078279555','Divorce Law', 250000.00, .05, to_date('1995', 'YYYY'));
INSERT INTO attorney VALUES
(105, 'David', 'Parrin', '62 Deptford Ave', 'Centerville', 'OH', '45458', '9373713862','Divorce Law', 150000.00, .02, to_date('2000', 'YYYY'));
INSERT INTO attorney VALUES
(3, 'Jesseca', 'Berry', '457 David Rd.', 'Orlando', 'FL', '32808', '4079277654','Custody Law', 150000.00, .02, to_date('2000', 'YYYY'));
INSERT INTO attorney VALUES
(42, 'Jane', 'Flemming', '900 Croftshire Rd.', 'Orlando', 'FL', '32804', '4076788456','Custody Law', 120000.00, .06, to_date('2000', 'YYYY'));
INSERT INTO attorney VALUES
(55, 'Dan', 'Montey', '4500 Circle Ct.', 'New York City', 'NY', '10004', '2128750987','Custody Law', 240000.00, .05, to_date('2000', 'YYYY'));

--Insert records into bill
INSERT INTO bill VALUES
(451, TO_DATE('01/15/1990', 'MM/DD/YYYY'), 450.45);
INSERT INTO bill VALUES
(120, TO_DATE('01/15/1985', 'MM/DD/YYYY'), 900.00);
INSERT INTO bill VALUES
(716, TO_DATE('05/14/2015', 'MM/DD/YYYY'), 2300.50);
INSERT INTO bill VALUES
(315, TO_DATE('06/20/2012', 'MM/DD/YYYY'), 6400.00);
INSERT INTO bill VALUES
(2, TO_DATE('08/15/1980', 'MM/DD/YYYY'), 800.00);

--Insert records into matter
insert into matter values
(120, 'Divorce', to_date('12/15/1989', 'MM/DD/YYYY'), 'won', to_date('12/20/1989', 'MM/DD/YYYY'), 105, 1);
insert into matter values
(126, 'Divorce', to_date('5/20/2000', 'MM/DD/YYYY'), 'won', to_date('11/25/2000', 'MM/DD/YYYY'), 105, 2);
insert into matter values
(180, 'Custody', to_date('7/15/2005', 'MM/DD/YYYY'), 'lost', to_date('12/20/2005', 'MM/DD/YYYY'), 3, 3);
insert into matter values
(250, 'Divorce', to_date('9/12/2016', 'MM/DD/YYYY'), 'open', to_date('12/20/2016', 'MM/DD/YYYY'), 1, 4);
insert into matter values
(350, 'Custody', to_date('12/15/2016', 'MM/DD/YYYY'), 'won', to_date('1/20/2018', 'MM/DD/YYYY'), 55, 5);

--Insert records into opponent
insert into opponent values
(opponent_id_seq.NEXTVAL, 'David', 'Herrigan', 120);
insert into opponent values
(opponent_id_seq.NEXTVAL, 'Chester', 'Barry', 126);
insert into opponent values
(opponent_id_seq.NEXTVAL, 'Mark', 'Luther', 180);
insert into opponent values
(opponent_id_seq.NEXTVAL, 'Heather', 'Price', 250);
insert into opponent values
(opponent_id_seq.NEXTVAL, 'Karry', 'Underhill', 350);

--Insert records into principal
insert into principal values
(principal_id_seq.NEXTVAL, 'Marry', 'Harry', 120);
insert into principal values
(principal_id_seq.NEXTVAL, 'Barry', 'Fairy', 126);
insert into principal values
(principal_id_seq.NEXTVAL, 'Dan', 'Kent', 180);
insert into principal values
(principal_id_seq.NEXTVAL, 'Marb', 'Lab', 250);
insert into principal values
(principal_id_seq.NEXTVAL, 'Jesseca', 'Warrington', 350);

--Insert records into Expenses
insert into expenses values
(456,to_date('12/17/1989', 'MM/DD/YYYY'), 'Consultation', 70.00, 120, 451);
insert into expenses values
(500,to_date('5/25/2000', 'MM/DD/YYYY'), 'Court Appearance', 750.00, 126, 120);
insert into expenses values
(600,to_date('7/25/2005', 'MM/DD/YYYY'), 'Court Appearance', 2000.40, 180, 716);
insert into expenses values
(625,to_date('10/25/2016', 'MM/DD/YYYY'), 'Contract Writing', 400.00, 250, 315);
insert into expenses values
(626,to_date('1/15/2018', 'MM/DD/YYYY'), 'Contract Writing', 400.00, 350, 2);

--Insert records into Payment
insert into payment values
(15, to_date('2/17/1990', 'MM/DD/YYYY'), 70.00, 1, 451);
insert into payment values
(16, to_date('3/17/1991', 'MM/DD/YYYY'), 300.00, 1, 451);
insert into payment values
(17, to_date('5/15/2016', 'MM/DD/YYYY'), 320.00, 2, 120);
insert into payment values
(18, to_date('5/20/2016', 'MM/DD/YYYY'), 320.00, 2, 120);
insert into payment values
(390, to_date('3/20/2013', 'MM/DD/YYYY'), 1500.00, 3, 716);

COMMIT;




