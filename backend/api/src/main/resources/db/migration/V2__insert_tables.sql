INSERT INTO users (user_id, user_name, user_birthday_date, user_email, user_phone_number, user_password_hash, role)
VALUES
(1, 'Иван Петров', '1990-05-15', 'ivan@example.com', '+79161234567', '$2a$10$xJwL5v5Jz5TZfJQ3YVjXe.BD7xgk9cLm6W7kSJdZJt8dKv1YbH9X2', 'USER'),
(2, 'Мария Сидорова', '1985-08-22', 'maria@example.com', '+79167654321', '$2a$10$yHpK9m8n7L6J5G4F3D2S1.rQwO9iU8Y7T6R5E4W3Q2Z1X0V9B8N7', 'USER'),
(3, 'Админ Админов', '1980-01-10', 'admin@example.com', '+79160000001', '$2a$10$zKqL4n3M2J1H0G9F8E7D6.C5B4V3N2M1L0K9J8I7H6G5F4E3D2S1', 'ADMIN'),
(4, 'Ололошка Няшевна', '2007-01-10', 'pedovochka@example.com', '+79160044444', '$2a$10$zKqL4n3M2J1H0G9F8E7D6.C5B4V3N2M1L0K9J8I7H6G5F4E3D2S4', 'USER'),
(5, 'Ололошич Няшевич', '2007-01-10', 'dr4gP3dovochki@example.com', '+79160042341', '$2a$10$zKqL4n3M2J1H0G9F8E7D6.C5B4V3N2M1L0K9J8I7H4443235BSD3', 'USER');

-- 2. Заполнение pets (питомцы)
INSERT INTO pets (pet_id, pet_name, pet_type, pet_description)
VALUES
(1, 'Барсик', 'CAT',  'Ласковый мейн-кун'),
(2, 'Шарик',  'DOG',  'Добродушный лабрадор'),
(3, 'Кеша',   'BIRD', 'Говорящий попугай'),
(4, 'Тоторо', 'BIRD', 'Говорящий попугай');

-- 3. Заполнение posts (публикации)
INSERT INTO posts (id, author_id, post_description)
VALUES
(1, 1,  'Мой любимый Барсик'),
(2, 2,  'Шарик на прогулке'),
(3, 1,  'Кеша учит новые слова');

-- 4. Заполнение chats (чаты)
INSERT INTO chats (chat_id, chat_name)
VALUES
(1, 'Общий чат'),
(2, 'Приют "Добро"');

-- 5. Заполнение published_pets (связь питомцев и публикаций)
INSERT INTO published_pets (pet_id, post_id)
VALUES
(1, 1),
(2, 2),
(3, 3);

-- 6. Заполнение pet_owners (владельцы питомцев)
INSERT INTO pet_owners (user_id, pet_id)
VALUES
(1, 1),
(2, 2),
(1, 3);

-- 7. Заполнение chat_members (участники чатов)
INSERT INTO chat_members (chat_id, member_id, member_role)
VALUES
(1, 1, 'DEFAULT'),
(1, 2, 'MODERATOR'),
(1, 3, 'ADMIN'),
(2, 1, 'DEFAULT'),
(2, 3, 'ADMIN');

-- 8. Заполнение user_follows (подписки пользователей)
INSERT INTO user_follows (follower_id, following_id)
VALUES
(1, 2),
(2, 1),
(3, 1);

-- 9. Заполнение friend_list (друзья)
INSERT INTO friend_list (user_id, friend_id, status)
VALUES
(1, 2, 'ACCEPTED'),
(1, 3, 'PENDING'),
(2, 3, 'ACCEPTED');

-- 10. Заполнение messages (сообщения в чатах)
INSERT INTO messages (message_id, content, chat_id, user_id)
VALUES
(1, 'Привет всем!', 1, 1),
(2, 'Как дела?', 1, 2),
(3, 'Кто хочет помочь приюту?', 2, 3);
