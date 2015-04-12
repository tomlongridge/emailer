USE emailer;

INSERT INTO User (firstName, surname, emailAddress, password, enabled) VALUE ('Tom', 'Longridge', 'tomlongridge@gmail.com', 'tom', true);
SET @userId = LAST_INSERT_ID();

INSERT INTO UserRole (userId, role) VALUE (@userId, 'USER');

INSERT INTO Country (name) VALUE ('England');
SET @countryId = LAST_INSERT_ID();

INSERT INTO County (name, country) VALUE ('Somerset', @countryId);
SET @countyId = LAST_INSERT_ID();

INSERT INTO Tower (doveId, dedication, area, town, county, numBells, tenorWeightCwt, tenorWeightQtrs, tenorWeightLbs) VALUE ('BATH+BW', 'St Mary the Virgin', 'Bathwick', 'Bath', @countyId, 10, 18, 3, 20);
SET @towerId = LAST_INSERT_ID();

INSERT INTO Notice (heading, content, tower, createdBy, creationDate, lastModifiedBy, modificationDate) VALUE ('Test Notice', 'This is a longer version of the notice text', @towerId, @userId, current_timestamp(), @userId, current_timestamp());