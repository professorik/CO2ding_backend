insert into dataType
(name, unit)
values
('CO2', 'pcs'),
('Trees', 'lbs'),
('Energy', 'kWh');

COPY region(name)
FROM 'D:\IdeaProjects\back\src\main\resources\db.migrations\regions.csv'
DELIMITER ','
CSV HEADER;

COPY distribution(dateStart,regionId,value,trees,energy)
FROM 'D:\IdeaProjects\back\src\main\resources\db.migrations\regions.csv'
DELIMITER ','
CSV HEADER;
