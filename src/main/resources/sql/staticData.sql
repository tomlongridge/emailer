USE emailer;

INSERT INTO Country (name) VALUE ('England');
SET @countryId = LAST_INSERT_ID();

INSERT INTO County (name, countryId) VALUE ('Somerset', @countryId);
SET @countyId = LAST_INSERT_ID();

INSERT INTO Tower (dedication, area, town, countyId) VALUE ('St Mary the Virgin', 'Bathwick', 'Bath', @countyId);