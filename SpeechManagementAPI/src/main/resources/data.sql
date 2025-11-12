DELETE FROM speeches;
-- Insert init data into speeches table
INSERT INTO speeches (author, subject, body, speech_date) VALUES
('Martin Luther King Jr.', 'Civil Rights', 'I have a dream that one day this nation will rise up...', '1963-08-28'),
('Winston Churchill', 'World War II', 'We shall fight on the beaches, we shall fight on the landing grounds...', '1940-06-04'),
('Barack Obama', 'Healthcare Reform', 'Today we mark a victory for the American people as we pass historic healthcare reform...', '2010-03-23'),
('Malala Yousafzai', 'Education', 'One child, one teacher, one book, one pen can change the world...', '2013-07-12'),
('Steve Jobs', 'Innovation', 'Innovation distinguishes between a leader and a follower...', '2005-06-12');
