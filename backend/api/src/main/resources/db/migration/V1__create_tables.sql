CREATE TABLE chats (
    chat_id         BIGSERIAL       PRIMARY KEY,
    chat_name       VARCHAR(50),
    chat_pic_addr   VARCHAR(100),
    followship_creation_time TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE users (
    user_id                 BIGSERIAL PRIMARY KEY,
    user_name               VARCHAR(30)                    NOT NULL,
    user_birthday_date      DATE                           NOT NULL,
    user_email              VARCHAR(100)                   UNIQUE,
    user_phone_number       VARCHAR(20)                    UNIQUE,
    user_profile_pic_addr   VARCHAR(100),
    user_password_hash      VARCHAR(60)                    NOT NULL,
    registered_since        TIMESTAMP WITHOUT TIME ZONE    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    role                    VARCHAR(10)                    NOT NULL DEFAULT 'USER'
);

CREATE TABLE messages (
    message_id  BIGSERIAL                   PRIMARY KEY,
    content     TEXT                        NOT NULL,
    sent_time   TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    chat_id     BIGINT                      NOT NULL,
    user_id     BIGINT                      NOT NULL,
    CONSTRAINT fk_message_chat FOREIGN KEY (chat_id)
                               REFERENCES chats(chat_id)
                               ON DELETE CASCADE,
    CONSTRAINT fk_message_user FOREIGN KEY (user_id)
                               REFERENCES users(user_id)
                               ON DELETE CASCADE
);

CREATE TABLE pets (
    pet_id          BIGSERIAL    PRIMARY KEY,
    pet_name        VARCHAR(30)  NOT NULL,
    pet_pic_addr    VARCHAR(100) UNIQUE,
    pet_type        VARCHAR(30)  NOT NULL,
    pet_description VARCHAR(140)
);

CREATE TABLE posts (
    id                  BIGSERIAL                   PRIMARY KEY,
    author_id           BIGINT                      NOT NULL,
    post_pic_addr       VARCHAR(100),
    post_description    VARCHAR(140),
    post_creation_time  TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_post_author FOREIGN KEY (author_id)
                              REFERENCES users(user_id)
                              ON DELETE CASCADE
);

CREATE TABLE pet_owners (
    user_id                         BIGINT      NOT NULL,
    pet_id                          BIGINT      NOT NULL,
    ownership_since                 TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (user_id, pet_id),

    CONSTRAINT fk_petowner_user
                                FOREIGN KEY (user_id)
                                REFERENCES users(user_id)
                                ON DELETE CASCADE,

    CONSTRAINT fk_petowner_pet
                                FOREIGN KEY (pet_id)
                                REFERENCES pets(pet_id)
                                ON DELETE CASCADE
);

CREATE TYPE friendship_status AS ENUM ('PENDING', 'ACCEPTED', 'BLOCKED');

CREATE TABLE friend_list (
    user_id                 BIGINT                      NOT NULL,
    friend_id               BIGINT                      NOT NULL,
    status                  friendship_status           NOT NULL,
    last_status_change_time TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (user_id, friend_id),

    CONSTRAINT fk_friendlist_user
                    FOREIGN KEY (user_id)
                    REFERENCES users(user_id)
                    ON DELETE CASCADE,

    CONSTRAINT fk_friendlist_friend
                    FOREIGN KEY (friend_id)
                    REFERENCES users(user_id)
                    ON DELETE CASCADE,

    CONSTRAINT chk_friend_not_self
        CHECK (user_id <> friend_id)
);

CREATE TYPE member_role AS ENUM ('ADMIN', 'MODERATOR', 'DEFAULT');

CREATE TABLE chat_members (
    chat_id          BIGINT         NOT NULL,
    member_id        BIGINT         NOT NULL,
    member_role      member_role    NOT NULL    DEFAULT 'DEFAULT',
    is_member_active BOOLEAN        NOT NULL    DEFAULT TRUE,

    PRIMARY KEY (chat_id, member_id),

    CONSTRAINT fk_chatmember_chat
        FOREIGN KEY (chat_id)
        REFERENCES chats(chat_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_chatmember_user
        FOREIGN KEY (member_id)
        REFERENCES users(user_id)
        ON DELETE CASCADE,

    CONSTRAINT chk_valid_member
        CHECK (member_id > 0 AND chat_id > 0)
);

CREATE TABLE published_pets (
    pet_id           BIGINT                      NOT NULL,
    post_id          BIGINT                      NOT NULL,
    publication_date TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (pet_id, post_id),

    CONSTRAINT fk_published_pet
                            FOREIGN KEY (pet_id)
                            REFERENCES pets(pet_id)
                            ON DELETE CASCADE,

    CONSTRAINT fk_published_post
                            FOREIGN KEY (post_id)
                            REFERENCES posts(id)
                            ON DELETE CASCADE
);

CREATE TABLE user_follows (
    follower_id              BIGINT                      NOT NULL,
    following_id             BIGINT                      NOT NULL,
    followship_creation_time TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (follower_id, following_id),

    CONSTRAINT fk_userfollow_follower
                                    FOREIGN KEY (follower_id)
                                    REFERENCES users(user_id)
                                    ON DELETE CASCADE,

    CONSTRAINT fk_userfollow_following
                                    FOREIGN KEY (following_id)
                                    REFERENCES users(user_id)
                                    ON DELETE CASCADE,

    CONSTRAINT chk_not_self_follow
        CHECK (follower_id <> following_id)
);

