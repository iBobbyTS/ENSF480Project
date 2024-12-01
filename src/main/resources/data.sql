-- Theater Table
INSERT INTO Theater (theaterNumber, capacity, theaterType) VALUES
('Theater 1', 100, 'IMAX'),
('Theater 2', 100, 'Dolby'),
('Theater 3', 100, '3D'),
('Theater 4', 100, 'VIP'),
('Theater 5', 100, 'Standard');
#
# Movie Table
INSERT INTO Movie (title, description, duration, coverUrl, trailerUrl) VALUES
('The Wild Robot', 'After a shipwreck, an intelligent robot called Roz is stranded on an uninhabited island. To survive the harsh environment, Roz bonds with the island''s animals and cares for an orphaned baby goose.', 90, 'https://m.media-amazon.com/images/M/MV5BZjM2M2E3YzAtZDJjYy00MDhkLThiYmItOGZhNzQ3NTgyZmI0XkEyXkFqcGc@._V1_QL75_UX380_CR0,20,380,562_.jpg', 'https://www.imdb.com/title/tt29623480'),
('Maharaja', 'A barber seeks vengeance after his home is burglarized, cryptically telling police his "lakshmi" has been taken, leaving them uncertain if it''s a person or object. His quest to recover the elusive "lakshmi" unfolds.', 175, 'https://m.media-amazon.com/images/M/MV5BOTFlMTIxOGItZTk0Zi00MTk2LWJiM2UtMzlhZWYzNjQ4N2Y3XkEyXkFqcGc@._V1_FMjpg_UY2048_.jpg', 'https://www.imdb.com/title/tt26548265'),
('Dune: Part Two', 'Paul Atreides unites with the Fremen while on a warpath of revenge against the conspirators who destroyed his family. Facing a choice between the love of his life and the fate of the universe, he endeavors to prevent a terrible future.', 120, 'https://m.media-amazon.com/images/M/MV5BNTc0YmQxMjEtODI5MC00NjFiLTlkMWUtOGQ5NjFmYWUyZGJhXkEyXkFqcGc@._V1_QL75_UX380_CR0,0,380,562_.jpg', 'https://www.imdb.com/title/tt15239678'),
('12th Fail', 'The real-life story of IPS Officer Manoj Kumar Sharma and IRS Officer Shraddha Joshi.', 120, 'https://m.media-amazon.com/images/M/MV5BNTE3OTIxZDYtNjA0NC00N2YxLTg1NGQtOTYxNmZkMDkwOWNjXkEyXkFqcGc@._V1_QL75_UY562_CR24,0,380,562_.jpg', 'https://www.imdb.com/title/tt23849204'),
('Oppenheimer', 'A dramatization of the life story of J. Robert Oppenheimer, the physicist who had a large hand in the development of the atomic bombs that brought an end to World War II.', 120, 'https://m.media-amazon.com/images/M/MV5BN2JkMDc5MGQtZjg3YS00NmFiLWIyZmQtZTJmNTM5MjVmYTQ4XkEyXkFqcGc@._V1_SL200_QL1.jpg', 'https://www.imdb.com/title/tt15398776'),
('Spider-Man: Across the Spider-Verse', 'Miles Morales catapults across the multiverse, where he encounters a team of Spider-People charged with protecting its very existence. When the heroes clash on how to handle a new threat, Miles must redefine what it means to be a hero.', 111, 'https://m.media-amazon.com/images/M/MV5BNThiZjA3MjItZGY5Ni00ZmJhLWEwN2EtOTBlYTA4Y2E0M2ZmXkEyXkFqcGc@._V1_QL75_UX380_CR0,1,380,562_.jpg', 'https://www.imdb.com/title/tt9362722')
;
#
# -- ShowTime Table
# INSERT INTO ShowTime (movieId, theaterId, showTime) VALUES
# (1, 1, '2024-12-01 14:30:00'),
# (2, 2, '2024-12-01 19:00:00'),
# (3, 3, '2024-12-02 13:00:00'),
# (4, 4, '2024-12-02 16:00:00'),
# (5, 5, '2024-12-03 18:30:00');

-- Seat Table
# INSERT INTO Seat (seatRow, seatColumn, isReserved, showTimeId, seatPrice) VALUES
# (1, 1, FALSE, 1, 15),
# (1, 2, TRUE, 1, 15),
# (2, 1, FALSE, 2, 15),
# (2, 2, TRUE, 2, 15),
# (3, 1, FALSE, 3, 15),
# (3, 2, TRUE, 3, 15),
# (4, 1, FALSE, 4, 15),
# (4, 2, TRUE, 4, 15),
# (5, 1, FALSE, 5, 15),
# (5, 2, TRUE, 5, 15);


-- RegisteredUser Table
INSERT INTO RegisteredUser (usrEmail, password) VALUES
 ('burnice.white@calydon.com', '39442418ce0d9c990de8c8650aac5e713663d815'),
 ('anby.demara@cunning.com', 'e1f8ddc30960fabee1178bd38fa52ad0421ea655'),
 ('grace.howard@belobog.com', '771c4f3744af6dc5c0ab1354fe2271f3fc7ff3e7'),
 ('nicole.demara@cunning.com', 'c6d89a2bdc02a8a0cbe3c5f4849eb850a565b7a9'),
 ('luciana.montefio@calydon.com', '9f5eaaecf4d20a3c93d1f34f7997efd2f1217704');
-- burnice0523, anby0220, grace0414, nicole1111, luciana0814

INSERT INTO RegisteredUser (usrEmail, password, admin) VALUES ('admin@admin.com', '0a07822c91525a83b2beb03690418cc2886d3c63', TRUE);
-- 'firefly26710'
-- Card Table
INSERT INTO Card (cardNum, expiry, cvc, usrEmail) VALUES
    ('1234567812355678', '2025-12-01', 123, 'burnice.white@calydon.com'),
    ('8765432187654321', '2026-06-01', 456, 'anby.demara@cunning.com'),
    ('5555555555555555', '2024-07-15', 789, 'grace.howard@belobog.com'),
    ('4444111122223333', '2027-10-20', 345, 'nicole.demara@cunning.com'),
    ('3333222211114444', '2028-01-01', 678, 'luciana.montefio@calydon.com');
# -- Ticket Table
# INSERT INTO Ticket (userId, seatId, isPurchased, ticketPrice) VALUES
# (1, 2, TRUE, 15),
# (2, 4, TRUE, 15),
# (3, 6, TRUE, 15),
# (4, 8, TRUE, 15),
# (5, 10, TRUE, 15);
#
# -- Payment Table
# INSERT INTO Payment (ticketId, isPaid, cardNum) VALUES
# (1, TRUE, '1234567812355678'),
# (2, TRUE, '8765432187654321'),
# (3, TRUE, '5555555555555555'),
# (4, TRUE, '4444111122223333');
