CREATE TABLE tool_type (
    name VARCHAR(255) PRIMARY KEY,
    daily_charge DECIMAL(10, 2) NOT NULL,
    weekday_charge BOOLEAN NOT NULL,
    weekend_charge BOOLEAN NOT NULL,
    holiday_charge BOOLEAN NOT NULL
);

CREATE TABLE tool (
    tool_code VARCHAR(255) PRIMARY KEY,
    tool_type VARCHAR(255) NOT NULL,
    brand VARCHAR(255) NOT NULL,
    CONSTRAINT fk_tool_type FOREIGN KEY (tool_type) REFERENCES tool_type (name)
);

INSERT INTO tool_type (name, daily_charge, weekday_charge, weekend_charge, holiday_charge) VALUES
    ('Ladder', 1.99, true, true, false),
    ('Chainsaw', 1.49, true, false, true),
    ('Jackhammer', 2.99, true, false, false);

INSERT INTO tool (tool_code, tool_type, brand) VALUES
    ('CHNS', 'Chainsaw', 'Stihl'),
    ('LADW', 'Ladder', 'Werner'),
    ('JAKD', 'Jackhammer', 'DeWalt'),
    ('JAKR', 'Jackhammer', 'Ridgid');