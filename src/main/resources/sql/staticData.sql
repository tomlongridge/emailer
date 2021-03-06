USE emailer;

INSERT INTO User (firstName, surname, emailAddress, password, enabled, creationDate, modificationDate) VALUE ('Tom', 'Longridge', 'tomlongridge@gmail.com', '$2a$10$LDDswWfVbKAODvMIS3HlhuHt87Z79kVN8A7nZ01NkN/UMQh0M1hp.', true, current_timestamp(), current_timestamp());
SET @userId = LAST_INSERT_ID();

INSERT INTO UserRole (userId, role) VALUE (@userId, 'USER');

INSERT INTO User (firstName, surname, emailAddress, password, enabled, creationDate, modificationDate) VALUE ('Another', 'User', 'email@gmail.com', '$2a$10$LDDswWfVbKAODvMIS3HlhuHt87Z79kVN8A7nZ01NkN/UMQh0M1hp.', true, current_timestamp(), current_timestamp());
SET @userId2 = LAST_INSERT_ID();

INSERT INTO Country (name) VALUE ('Scotland');

INSERT INTO Country (name) VALUE ('Wales');
SET @countryId = LAST_INSERT_ID();
INSERT INTO County (name, country) VALUE ('Powys', @countryId);

INSERT INTO Country (name) VALUE ('England');
SET @countryId = LAST_INSERT_ID();

INSERT INTO County (name, country) VALUE ('Cornwall', @countryId);
INSERT INTO County (name, country) VALUE ('Devon', @countryId);
INSERT INTO County (name, country) VALUE ('Somerset', @countryId);
SET @countyId = LAST_INSERT_ID();

INSERT INTO Board (identifier) VALUE ('BATH+BW');
SET @towerId = LAST_INSERT_ID();

INSERT INTO Tower (boardId, dedication, area, town, county, numBells, tenorWeightCwt, tenorWeightQtrs, tenorWeightLbs) VALUE (@towerId, 'St Mary the Virgin', 'Bathwick', 'Bath', @countyId, 10, 18, 3, 20);

INSERT INTO Membership (boardId, userId, role, joined, approvedBy) VALUE (@towerId, @userId, 'Tower Master', current_timestamp(), @userId);
INSERT INTO Membership (boardId, userId, role, joined, approvedBy) VALUE (@towerId, @userId2, NULL, current_timestamp(), @userId);

INSERT INTO Board (identifier) VALUE ('BathBranch');
SET @groupId = LAST_INSERT_ID();
INSERT INTO `Group` (boardId, name) VALUE (@groupId, 'Bath Branch');
INSERT INTO Affiliates (affiliate, towerGroup) VALUE (@towerId, @groupId)

INSERT INTO Board (identifier) VALUE ('BathWells');
SET @groupId2 = LAST_INSERT_ID();
INSERT INTO `Group` (boardId, name) VALUE (@groupId2, 'Bath and Wells Association');
INSERT INTO Affiliates (affiliate, towerGroup) VALUE (@groupId, @groupId2)

INSERT INTO Notice (heading, content, board, createdBy, creationDate, lastModifiedBy, modificationDate) VALUE ('Test Notice', 'This is a longer version of the notice text', @towerId, @userId, current_timestamp(), @userId, current_timestamp());
INSERT INTO Notice (heading, content, board, createdBy, creationDate, lastModifiedBy, modificationDate) VALUE ('Group Test Notice', 'Group This is a longer version of the notice text', @groupId, @userId, current_timestamp(), @userId, current_timestamp());

INSERT INTO Notice (heading, content, board, createdBy, creationDate, lastModifiedBy, modificationDate) VALUE ('Test Event', 'This is a longer version of the event text', @towerId, @userId, current_timestamp(), @userId, current_timestamp());
SET @eventId = LAST_INSERT_ID();
INSERT INTO Event (noticeId, startDate, endDate) VALUES (@eventId, current_timestamp(), current_timestamp());

INSERT INTO Notice (heading, content, board, createdBy, creationDate, lastModifiedBy, modificationDate) VALUE ('Test Event 2', 'This is a longer version of the event text', @towerId, @userId, current_timestamp(), @userId, current_timestamp());
SET @eventId = LAST_INSERT_ID();
INSERT INTO Event (noticeId, startDate, endDate) VALUES (@eventId, current_timestamp(), DATE_ADD(current_timestamp(), INTERVAL 1 DAY));
