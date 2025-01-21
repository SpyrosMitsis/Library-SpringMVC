-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.40 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;


-- Dumping database structure for library
CREATE DATABASE IF NOT EXISTS `library` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION = 'N' */;
USE `library`;

-- Dumping structure for table library.app_user
CREATE TABLE IF NOT EXISTS `app_user`
(
    `username`   varchar(255) NOT NULL,
    `first_name` varchar(255) DEFAULT NULL,
    `last_name`  varchar(255) DEFAULT NULL,
    `password`   varchar(255) DEFAULT NULL,
    PRIMARY KEY (`username`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- Dumping data for table library.app_user: ~4 rows (approximately)
INSERT INTO `app_user` (`username`, `first_name`, `last_name`, `password`)
VALUES ('admin', 'Admin', 'Admin', '$2a$10$55BEfsq40EE.lR5jGGiZ7ubcpdq5ws1ETUO8w0d7mIULPzC8.WJ4q'),
       ('kri', 'kri', 'kri', '$2a$10$eketMKXIxUPCMgvwCOGDguz/YF9/fI.Mku.4iFJtzoBQGhUAbZcp6'),
       ('sakis', 'sakis', 'sakis', '$2a$10$aGPebJNOrAZcVdCH2.BAcua/kxNsrUr.cq/1KsqjMDdaMC2u/hek6'),
       ('spyros', 'Spyros', 'Mitsis', '$2a$10$1NBe9F7ILtCEATizlH8bb.L3oLotCPzkAXkmnOXyZtRB6qFzLgIt6'),
       ('xaris', 'xaris', 'xaris', '$2a$10$5/X7RWETOfpJNhIDH2D8/eckwhFCYAc6yFCUiqpLWnWA4YZ3UuOIy');

-- Dumping structure for table library.author
CREATE TABLE IF NOT EXISTS `author`
(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `first_name` varchar(255) DEFAULT NULL,
    `last_name`  varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 11
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- Dumping data for table library.author: ~8 rows (approximately)
INSERT INTO `author` (`id`, `first_name`, `last_name`)
VALUES (1, 'Mark', 'Morford'),
       (2, 'Sheila', 'Heti'),
       (3, 'Michael ', 'Crichton'),
       (4, 'Jane', 'Austen'),
       (5, 'John', 'Grisham'),
       (6, 'Michel', 'Tournier'),
       (7, 'Anne', 'Tyler'),
       (8, 'Robert', 'Heinlein'),
       (9, 'Stel', 'Pavlou'),
       (10, 'Jennifer`', 'Cruise');

-- Dumping structure for table library.book
CREATE TABLE IF NOT EXISTS `book`
(
    `isbn`         varchar(255) NOT NULL,
    `cover_url`    varchar(255)  DEFAULT NULL,
    `description`  varchar(1024) DEFAULT NULL,
    `is_available` bit(1)        DEFAULT NULL,
    `release_date` datetime(6)   DEFAULT NULL,
    `title`        varchar(255)  DEFAULT NULL,
    `created_by`   varchar(255) NOT NULL,
    PRIMARY KEY (`isbn`),
    KEY `FKi7ni5icwanhrnce8cxa1l8j1j` (`created_by`),
    CONSTRAINT `FKi7ni5icwanhrnce8cxa1l8j1j` FOREIGN KEY (`created_by`) REFERENCES `app_user` (`username`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- Dumping data for table library.book: ~8 rows (approximately)
INSERT INTO `book` (`isbn`, `cover_url`, `description`, `is_available`, `release_date`, `title`, `created_by`)
VALUES ('0195153448', 'http://images.amazon.com/images/P/0195153448.01.LZZZZZZZ.jpg',
        'Et reiciendis nihil ut natus laborum sed voluptatem. Nihil distinctio ipsam in harum maxime. Ea harum voluptate et.\r\n\r\nIllo expedita ut enim rerum inventore dolores. Inventore vel voluptatem quam blanditiis. Repellendus voluptatibus qui aspernatur qui ullam itaque dolores deserunt.\r\n\r\nOdit numquam modi doloribus eveniet iusto maiores quisquam quos. Commodi cum rerum quo. Ipsam sit dolores temporibus facere velit. Sunt illo laboriosam quia officia. Ipsam quis dolore aperiam repellendus suscipit.',
        b'1', '2002-01-01 00:00:00.000000', 'Classical Mythology', 'spyros'),
       ('0312252617', 'http://images.amazon.com/images/P/0312252617.01.LZZZZZZZ.jpg',
        'Dolore non est ipsam magni adipisci enim debitis. Ut sed est qui iusto optio. Id maxime eos illum odio officiis et fugit. Magni in dolor exercitationem amet rerum sed qui neque. Aut omnis quae consectetur veritatis.\r\n\r\nNatus ex perspicDolore non est ipsam magni adipisci enim debitis. Ut sed est qui iusto optio. Id maxime eos illum odio officiis et fugit. Magni in dolor exercitationem amet rerum sed qui neque. Aut omnis quae consectetur veritatis.\r\n\r\nNatus ex perspiciatis doloribus a eum. Hic non odio in architecto omnis eligendi. Molestiae minus qui aliquid a sapiente eum iusto. Ex vitae illum sit ut minima voluptatem in quia. Non unde et ut quam nihil omnis.iatis doloribus a eum. Hic non odio in architecto omnis eligendi. Molestiae minus qui aliquid a sapiente eum iusto. Ex vitae illum sit ut minima voluptatem in quia. Non unde et ut quam nihil omnis.',
        b'1', '2001-01-01 00:00:00.000000', 'Fast Women', 'spyros'),
       ('0345402871', 'http://images.amazon.com/images/P/0345402871.01.LZZZZZZZ.jpg',
        'Et reiciendis nihil ut natus laborum sed voluptatem. Nihil distinctio ipsam in harum maxime. Ea harum voluptate et.\r\n\r\nIllo expedita ut enim rerum inventore dolores. Inventore vel voluptatem quam blanditiis. Repellendus voluptatibus qui aspernatur qui ullam itaque dolores deserunt.\r\n\r\nOdit numquam modi doloribus eveniet iusto maiores quisquam quos. Commodi cum rerum quo. Ipsam sit dolores temporibus facere velit. Sunt illo laboriosam quia officia. Ipsam quis dolore aperiam repellendus suscipit.',
        b'1', '1997-01-01 00:00:00.000000', 'Airframe', 'spyros'),
       ('042511774X', 'http://images.amazon.com/images/P/042511774X.01.LZZZZZZZ.jpg',
        'Dolore non est ipsam magni adipisci enim debitis. Ut sed est qui iusto optio. Id maxime eos illum odio officiis et fugit. Magni in dolor exercitationem amet rerum sed qui neque. Aut omnis quae consectetur veritatis.\r\n\r\nNatus ex perspiciatis doloribus a eum. Hic non odio in architecto omnis eligendi. Molestiae minus qui aliquid a sapiente eum iusto. Ex vitae illum sit ut minima voluptatem in quia. Non unde et ut quam nihil omnis.',
        b'1', '1994-01-01 00:00:00.000000', 'Breathing Lessons', 'spyros'),
       ('0440225701', 'http://images.amazon.com/images/P/0440225701.01.LZZZZZZZ.jpg',
        'Dolore non est ipsam magni adipisci enim debitis. Ut sed est qui iusto optio. Id maxime eos illum odio officiis et fugit. Magni in dolor exercitationem amet rerum sed qui neque. Aut omnis quae consectetur veritatis.\r\n\r\nNatus ex perspiciatis doloribus a eum. Hic non odio in architecto omnis eligendi. Molestiae minus qui aliquid a sapiente eum iusto. Ex vitae illum sit ut minima voluptatem in quia. Non unde et ut quam nihil omnis.',
        b'1', '1988-01-01 00:00:00.000000', 'The Street Lawyer', 'spyros'),
       ('0441783589', 'http://images.amazon.com/images/P/0441783589.01.LZZZZZZZ.jpg',
        'Dolore non est ipsam magni adipisci enim debitis. Ut sed est qui iusto optio. Id maxime eos illum odio officiis et fugit. Magni in dolor exercitationem amet rerum sed qui neque. Aut omnis quae consectetur veritatis.\r\n\r\nNatus ex perspiciatis doloribus a eum. Hic non odio in architecto omnis eligendi. Molestiae minus qui aliquid a sapiente eum iusto. Ex vitae illum sit ut minima voluptatem in quia. Non unde et ut quam nihil omnis.',
        b'1', '1987-01-01 00:00:00.000000', 'Starship Troopers', 'spyros'),
       ('055321215X',
        'https://images.squarespace-cdn.com/content/v1/58c180edff7c50dd0e51a2ad/1596041976270-ED06SNZ2GU7QIS7L25WU/9780241374887.jpg',
        'Dolore non est ipsam magni adipisci enim debitis. Ut sed est qui iusto optio. Id maxime eos illum odio officiis et fugit. Magni in dolor exercitationem amet rerum sed qui neque. Aut omnis quae consectetur veritatis.\r\n\r\nNatus ex perspiciatis doloribus a eum. Hic non odio in architecto omnis eligendi. Molestiae minus qui aliquid a sapiente eum iusto. Ex vitae illum sit ut minima voluptatem in quia. Non unde et ut quam nihil omnis.',
        b'1', '1983-01-01 00:00:00.000000', 'Pride and Prejudice', 'spyros'),
       ('0743403843', 'http://images.amazon.com/images/P/0743403843.01.LZZZZZZZ.jpg',
        'Dolore non est ipsam magni adipisci enim debitis. Ut sed est qui iusto optio. Id maxime eos illum odio officiis et fugit. Magni in dolor exercitationem amet rerum sed qui neque. Aut omnis quae consectetur veritatis.\r\n\r\nNatus ex perspiciatis doloribus a eum. Hic non odio in architecto omnis eligendi. Molestiae minus qui aliquid a sapiente eum iusto. Ex vitae illum sit ut minima voluptatem in quia. Non unde et ut quam nihil omnis.',
        b'1', '2002-01-01 00:00:00.000000', 'Decipher', 'spyros'),
       ('0887841740', 'http://images.amazon.com/images/P/0887841740.01.LZZZZZZZ.jpg',
        'Et reiciendis nihil ut natus laborum sed voluptatem. Nihil distinctio ipsam in harum maxime. Ea harum voluptate et.\r\n\r\nIllo expedita ut enim rerum inventore dolores. Inventore vel voluptatem quam blanditiis. Repellendus voluptatibus qui aspernatur qui ullam itaque dolores deserunt.\r\n\r\nOdit numquam modi doloribus eveniet iusto maiores quisquam quos. Commodi cum rerum quo. Ipsam sit dolores temporibus facere velit. Sunt illo laboriosam quia officia. Ipsam quis dolore aperiam repellendus suscipit.',
        b'1', '2004-01-01 00:00:00.000000', 'The Middle Stories', 'spyros'),
       ('2070423204', 'http://images.amazon.com/images/P/2070423204.01.LZZZZZZZ.jpg',
        'Dolore non est ipsam magni adipisci enim debitis. Ut sed est qui iusto optio. Id maxime eos illum odio officiis et fugit. Magni in dolor exercitationem amet rerum sed qui neque. Aut omnis quae consectetur veritatis.\r\n\r\nNatus ex perspiciatis doloribus a eum. Hic non odio in architecto omnis eligendi. Molestiae minus qui aliquid a sapiente eum iusto. Ex vitae illum sit ut minima voluptatem in quia. Non unde et ut quam nihil omnis.',
        b'1', '2002-01-01 00:00:00.000000', 'Lieux dits', 'spyros');

-- Dumping structure for table library.book_author
CREATE TABLE IF NOT EXISTS `book_author`
(
    `book_isbn` varchar(255) NOT NULL,
    `author_id` bigint       NOT NULL,
    PRIMARY KEY (`book_isbn`, `author_id`),
    KEY `FKbjqhp85wjv8vpr0beygh6jsgo` (`author_id`),
    CONSTRAINT `FKbjqhp85wjv8vpr0beygh6jsgo` FOREIGN KEY (`author_id`) REFERENCES `author` (`id`),
    CONSTRAINT `FKlxhwaiinp1xunnqsvnoirba4j` FOREIGN KEY (`book_isbn`) REFERENCES `book` (`isbn`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- Dumping data for table library.book_author: ~8 rows (approximately)
INSERT INTO `book_author` (`book_isbn`, `author_id`)
VALUES ('0195153448', 1),
       ('0887841740', 2),
       ('0345402871', 3),
       ('055321215X', 4),
       ('0440225701', 5),
       ('2070423204', 6),
       ('042511774X', 7),
       ('0441783589', 8),
       ('0743403843', 9),
       ('0312252617', 10);

-- Dumping structure for table library.book_category
CREATE TABLE IF NOT EXISTS `book_category`
(
    `book_isbn`   varchar(255) NOT NULL,
    `category_id` bigint       NOT NULL,
    PRIMARY KEY (`book_isbn`, `category_id`),
    KEY `FKam8llderp40mvbbwceqpu6l2s` (`category_id`),
    CONSTRAINT `FKam8llderp40mvbbwceqpu6l2s` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
    CONSTRAINT `FKnvto1163v9y4o126tf8wgksyj` FOREIGN KEY (`book_isbn`) REFERENCES `book` (`isbn`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- Dumping data for table library.book_category: ~19 rows (approximately)
INSERT INTO `book_category` (`book_isbn`, `category_id`)
VALUES ('0195153448', 1),
       ('0887841740', 1),
       ('0195153448', 2),
       ('0312252617', 3),
       ('055321215X', 3),
       ('0887841740', 3),
       ('0312252617', 4),
       ('0440225701', 4),
       ('0441783589', 4),
       ('055321215X', 4),
       ('0743403843', 4),
       ('0345402871', 5),
       ('042511774X', 5),
       ('2070423204', 5),
       ('0440225701', 6),
       ('0441783589', 6),
       ('0743403843', 6),
       ('2070423204', 7),
       ('042511774X', 8),
       ('0441783589', 9),
       ('0743403843', 9);

-- Dumping structure for table library.book_inventory
CREATE TABLE IF NOT EXISTS `book_inventory`
(
    `id`                 bigint NOT NULL AUTO_INCREMENT,
    `available_quantity` int          DEFAULT NULL,
    `total_quantity`     int          DEFAULT NULL,
    `book_isbn`          varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UKfm8td3jnn7jaqb3kxp1i5d4pl` (`book_isbn`),
    CONSTRAINT `FKlpk4g73musyyd99228nk9gft8` FOREIGN KEY (`book_isbn`) REFERENCES `book` (`isbn`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 11
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- Dumping data for table library.book_inventory: ~8 rows (approximately)
INSERT INTO `book_inventory` (`id`, `available_quantity`, `total_quantity`, `book_isbn`)
VALUES (1, 12, 12, '0195153448'),
       (2, 10, 12, '0887841740'),
       (3, 5, 5, '0345402871'),
       (4, 15, 15, '055321215X'),
       (5, 7, 7, '0440225701'),
       (6, 15, 15, '2070423204'),
       (7, 14, 14, '042511774X'),
       (8, 6, 6, '0441783589'),
       (9, 11, 11, '0743403843'),
       (10, 3, 3, '0312252617');

-- Dumping structure for table library.book_loan
CREATE TABLE IF NOT EXISTS `book_loan`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `borrowed_at` datetime(6)                          DEFAULT NULL,
    `due_date`    datetime(6)                          DEFAULT NULL,
    `returned_at` datetime(6)                          DEFAULT NULL,
    `status`      enum ('ACTIVE','OVERDUE','RETURNED') DEFAULT NULL,
    `book_isbn`   varchar(255)                         DEFAULT NULL,
    `user_id`     varchar(255)                         DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKhnysetm3oiweitenygtwnyxxp` (`book_isbn`),
    KEY `FKjni540ysvc2gl5qxmr32jchwa` (`user_id`),
    CONSTRAINT `FKhnysetm3oiweitenygtwnyxxp` FOREIGN KEY (`book_isbn`) REFERENCES `book` (`isbn`),
    CONSTRAINT `FKjni540ysvc2gl5qxmr32jchwa` FOREIGN KEY (`user_id`) REFERENCES `app_user` (`username`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 101
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- Dumping data for table library.book_loan: ~100 rows (approximately)
INSERT INTO `book_loan` (`id`, `borrowed_at`, `due_date`, `returned_at`, `status`, `book_isbn`, `user_id`)
VALUES (1, '2024-11-09 00:00:00.000000', '2024-11-22 00:00:00.000000', '2024-05-29 00:00:00.000000', 'OVERDUE',
        '0440225701', 'admin'),
       (2, '2024-10-20 00:00:00.000000', '2024-05-06 00:00:00.000000', '2024-11-26 00:00:00.000000', 'RETURNED',
        '2070423204', 'xaris'),
       (3, '2024-06-04 00:00:00.000000', '2024-05-27 00:00:00.000000', '2024-12-17 00:00:00.000000', 'OVERDUE',
        '0312252617', 'kri'),
       (4, '2024-06-01 00:00:00.000000', '2024-10-17 00:00:00.000000', '2024-09-25 00:00:00.000000', 'RETURNED',
        '0312252617', 'sakis'),
       (5, '2024-09-03 00:00:00.000000', '2024-05-03 00:00:00.000000', '2024-10-09 00:00:00.000000', 'ACTIVE',
        '0441783589', 'spyros'),
       (6, '2024-06-02 00:00:00.000000', '2024-06-08 00:00:00.000000', '2025-01-05 00:00:00.000000', 'OVERDUE',
        '0440225701', 'xaris'),
       (7, '2024-05-30 00:00:00.000000', '2024-12-02 00:00:00.000000', '2024-12-04 00:00:00.000000', 'RETURNED',
        '0743403843', 'sakis'),
       (8, '2024-05-08 00:00:00.000000', '2024-06-20 00:00:00.000000', '2024-05-02 00:00:00.000000', 'OVERDUE',
        '0743403843', 'admin'),
       (9, '2024-10-28 00:00:00.000000', '2024-07-27 00:00:00.000000', '2024-09-15 00:00:00.000000', 'OVERDUE',
        '0887841740', 'spyros'),
       (11, '2024-05-29 00:00:00.000000', '2024-09-18 00:00:00.000000', '2024-05-28 00:00:00.000000', 'ACTIVE',
        '0887841740', 'spyros'),
       (12, '2024-11-12 00:00:00.000000', '2024-09-23 00:00:00.000000', '2024-05-06 00:00:00.000000', 'ACTIVE',
        '0887841740', 'sakis'),
       (13, '2024-12-26 00:00:00.000000', '2024-06-23 00:00:00.000000', '2024-06-18 00:00:00.000000', 'OVERDUE',
        '055321215X', 'spyros'),
       (14, '2024-10-04 00:00:00.000000', '2025-01-06 00:00:00.000000', '2024-10-11 00:00:00.000000', 'RETURNED',
        '0440225701', 'spyros'),
       (15, '2024-10-18 00:00:00.000000', '2024-08-11 00:00:00.000000', '2024-10-18 00:00:00.000000', 'ACTIVE',
        '0312252617', 'spyros'),
       (16, '2024-12-04 00:00:00.000000', '2024-11-20 00:00:00.000000', '2024-08-03 00:00:00.000000', 'RETURNED',
        '0441783589', 'xaris'),
       (17, '2024-06-03 00:00:00.000000', '2024-10-30 00:00:00.000000', '2024-10-10 00:00:00.000000', 'ACTIVE',
        '0312252617', 'kri'),
       (18, '2024-08-30 00:00:00.000000', '2024-07-12 00:00:00.000000', '2024-09-07 00:00:00.000000', 'RETURNED',
        '055321215X', 'spyros'),
       (19, '2024-12-08 00:00:00.000000', '2024-08-19 00:00:00.000000', '2024-10-27 00:00:00.000000', 'ACTIVE',
        '0887841740', 'xaris'),
       (20, '2024-05-18 00:00:00.000000', '2024-11-15 00:00:00.000000', '2024-05-09 00:00:00.000000', 'OVERDUE',
        '042511774X', 'kri'),
       (21, '2024-10-24 00:00:00.000000', '2024-09-10 00:00:00.000000', '2024-12-25 00:00:00.000000', 'OVERDUE',
        '0887841740', 'kri'),
       (22, '2024-12-30 00:00:00.000000', '2024-06-21 00:00:00.000000', '2024-12-08 00:00:00.000000', 'OVERDUE',
        '0195153448', 'xaris'),
       (23, '2024-12-28 00:00:00.000000', '2024-06-14 00:00:00.000000', '2024-12-15 00:00:00.000000', 'RETURNED',
        '0312252617', 'kri'),
       (24, '2024-09-06 00:00:00.000000', '2024-07-07 00:00:00.000000', '2024-08-07 00:00:00.000000', 'RETURNED',
        '0441783589', 'spyros'),
       (25, '2024-09-27 00:00:00.000000', '2024-07-02 00:00:00.000000', '2024-08-10 00:00:00.000000', 'ACTIVE',
        '0312252617', 'xaris'),
       (26, '2024-12-05 00:00:00.000000', '2024-07-02 00:00:00.000000', '2024-08-20 00:00:00.000000', 'OVERDUE',
        '0887841740', 'kri'),
       (27, '2024-10-16 00:00:00.000000', '2024-12-21 00:00:00.000000', '2024-06-30 00:00:00.000000', 'RETURNED',
        '0887841740', 'kri'),
       (28, '2024-05-30 00:00:00.000000', '2024-05-15 00:00:00.000000', '2024-05-20 00:00:00.000000', 'RETURNED',
        '2070423204', 'sakis'),
       (29, '2024-12-20 00:00:00.000000', '2024-10-27 00:00:00.000000', '2024-09-04 00:00:00.000000', 'OVERDUE',
        '042511774X', 'spyros'),
       (30, '2024-06-04 00:00:00.000000', '2024-12-02 00:00:00.000000', '2024-06-17 00:00:00.000000', 'ACTIVE',
        '0312252617', 'xaris'),
       (31, '2024-12-25 00:00:00.000000', '2024-09-26 00:00:00.000000', '2024-09-05 00:00:00.000000', 'RETURNED',
        '042511774X', 'admin'),
       (32, '2024-07-08 00:00:00.000000', '2024-08-06 00:00:00.000000', '2024-05-16 00:00:00.000000', 'OVERDUE',
        '0312252617', 'sakis'),
       (33, '2024-06-23 00:00:00.000000', '2024-05-01 00:00:00.000000', '2024-11-30 00:00:00.000000', 'ACTIVE',
        '2070423204', 'xaris'),
       (34, '2024-07-15 00:00:00.000000', '2024-06-15 00:00:00.000000', '2024-08-13 00:00:00.000000', 'ACTIVE',
        '055321215X', 'spyros'),
       (35, '2024-08-13 00:00:00.000000', '2024-10-29 00:00:00.000000', '2024-11-02 00:00:00.000000', 'RETURNED',
        '0312252617', 'sakis'),
       (36, '2024-12-18 00:00:00.000000', '2024-10-29 00:00:00.000000', '2024-12-11 00:00:00.000000', 'ACTIVE',
        '0887841740', 'admin'),
       (37, '2024-07-12 00:00:00.000000', '2024-07-30 00:00:00.000000', '2024-11-01 00:00:00.000000', 'ACTIVE',
        '0345402871', 'admin'),
       (38, '2024-07-04 00:00:00.000000', '2024-05-18 00:00:00.000000', '2025-01-05 00:00:00.000000', 'OVERDUE',
        '0195153448', 'xaris'),
       (39, '2024-05-24 00:00:00.000000', '2024-07-30 00:00:00.000000', '2024-08-20 00:00:00.000000', 'ACTIVE',
        '2070423204', 'admin'),
       (40, '2024-06-16 00:00:00.000000', '2024-10-30 00:00:00.000000', '2024-07-08 00:00:00.000000', 'ACTIVE',
        '042511774X', 'kri'),
       (42, '2024-05-26 00:00:00.000000', '2024-08-18 00:00:00.000000', '2024-09-16 00:00:00.000000', 'RETURNED',
        '0345402871', 'kri'),
       (43, '2024-05-18 00:00:00.000000', '2024-11-07 00:00:00.000000', '2024-09-24 00:00:00.000000', 'ACTIVE',
        '0887841740', 'admin'),
       (44, '2024-09-04 00:00:00.000000', '2024-05-03 00:00:00.000000', '2024-05-09 00:00:00.000000', 'RETURNED',
        '0887841740', 'sakis'),
       (45, '2024-10-04 00:00:00.000000', '2025-01-01 00:00:00.000000', '2024-12-30 00:00:00.000000', 'ACTIVE',
        '0345402871', 'admin'),
       (46, '2024-06-27 00:00:00.000000', '2024-07-07 00:00:00.000000', '2024-10-09 00:00:00.000000', 'RETURNED',
        '0345402871', 'spyros'),
       (47, '2024-09-23 00:00:00.000000', '2024-06-11 00:00:00.000000', '2024-11-19 00:00:00.000000', 'RETURNED',
        '0887841740', 'xaris'),
       (48, '2024-06-29 00:00:00.000000', '2024-08-16 00:00:00.000000', '2024-10-29 00:00:00.000000', 'OVERDUE',
        '0743403843', 'xaris'),
       (49, '2024-07-17 00:00:00.000000', '2024-06-13 00:00:00.000000', '2024-06-21 00:00:00.000000', 'RETURNED',
        '2070423204', 'sakis'),
       (50, '2024-12-07 00:00:00.000000', '2024-05-09 00:00:00.000000', '2024-07-07 00:00:00.000000', 'OVERDUE',
        '0440225701', 'admin'),
       (51, '2024-06-16 00:00:00.000000', '2024-07-30 00:00:00.000000', '2024-05-19 00:00:00.000000', 'ACTIVE',
        '0743403843', 'admin'),
       (52, '2024-09-01 00:00:00.000000', '2024-05-07 00:00:00.000000', '2024-12-26 00:00:00.000000', 'RETURNED',
        '042511774X', 'sakis'),
       (53, '2024-09-27 00:00:00.000000', '2024-07-22 00:00:00.000000', '2024-06-02 00:00:00.000000', 'OVERDUE',
        '055321215X', 'kri'),
       (54, '2025-01-02 00:00:00.000000', '2024-12-17 00:00:00.000000', '2024-06-08 00:00:00.000000', 'OVERDUE',
        '0195153448', 'admin'),
       (55, '2025-01-02 00:00:00.000000', '2024-12-13 00:00:00.000000', '2024-05-20 00:00:00.000000', 'ACTIVE',
        '0743403843', 'sakis'),
       (56, '2024-08-14 00:00:00.000000', '2024-07-05 00:00:00.000000', '2024-06-17 00:00:00.000000', 'ACTIVE',
        '0441783589', 'admin'),
       (57, '2024-11-16 00:00:00.000000', '2024-05-18 00:00:00.000000', '2024-12-20 00:00:00.000000', 'OVERDUE',
        '0345402871', 'admin'),
       (58, '2024-06-05 00:00:00.000000', '2024-05-04 00:00:00.000000', '2024-05-04 00:00:00.000000', 'OVERDUE',
        '0441783589', 'spyros'),
       (59, '2024-05-22 00:00:00.000000', '2024-12-24 00:00:00.000000', '2024-12-15 00:00:00.000000', 'RETURNED',
        '0441783589', 'sakis'),
       (60, '2024-09-19 00:00:00.000000', '2024-05-24 00:00:00.000000', '2024-11-01 00:00:00.000000', 'OVERDUE',
        '0743403843', 'spyros'),
       (61, '2024-05-19 00:00:00.000000', '2024-06-01 00:00:00.000000', '2024-09-03 00:00:00.000000', 'RETURNED',
        '0887841740', 'xaris'),
       (62, '2024-10-16 00:00:00.000000', '2024-11-25 00:00:00.000000', '2024-12-27 00:00:00.000000', 'RETURNED',
        '0887841740', 'spyros'),
       (64, '2024-05-28 00:00:00.000000', '2024-09-09 00:00:00.000000', '2024-06-08 00:00:00.000000', 'ACTIVE',
        '0312252617', 'kri'),
       (65, '2024-05-31 00:00:00.000000', '2024-06-28 00:00:00.000000', '2024-07-06 00:00:00.000000', 'RETURNED',
        '0195153448', 'admin'),
       (66, '2024-12-19 00:00:00.000000', '2024-12-04 00:00:00.000000', '2024-10-22 00:00:00.000000', 'ACTIVE',
        '0312252617', 'admin'),
       (67, '2024-12-13 00:00:00.000000', '2024-09-11 00:00:00.000000', '2024-10-25 00:00:00.000000', 'RETURNED',
        '042511774X', 'spyros'),
       (68, '2024-12-06 00:00:00.000000', '2024-10-30 00:00:00.000000', '2024-10-26 00:00:00.000000', 'RETURNED',
        '0743403843', 'xaris'),
       (69, '2024-11-03 00:00:00.000000', '2024-08-19 00:00:00.000000', '2024-06-14 00:00:00.000000', 'ACTIVE',
        '0441783589', 'kri'),
       (70, '2024-11-15 00:00:00.000000', '2024-10-11 00:00:00.000000', '2024-06-14 00:00:00.000000', 'OVERDUE',
        '0441783589', 'sakis'),
       (71, '2024-11-13 00:00:00.000000', '2024-07-03 00:00:00.000000', '2024-12-21 00:00:00.000000', 'ACTIVE',
        '0312252617', 'kri'),
       (72, '2024-06-21 00:00:00.000000', '2024-11-11 00:00:00.000000', '2024-08-06 00:00:00.000000', 'RETURNED',
        '0312252617', 'spyros'),
       (73, '2024-12-01 00:00:00.000000', '2024-05-11 00:00:00.000000', '2024-11-14 00:00:00.000000', 'OVERDUE',
        '0312252617', 'sakis'),
       (74, '2024-09-26 00:00:00.000000', '2024-05-09 00:00:00.000000', '2024-08-02 00:00:00.000000', 'OVERDUE',
        '0195153448', 'xaris'),
       (75, '2024-11-06 00:00:00.000000', '2024-05-05 00:00:00.000000', '2024-07-04 00:00:00.000000', 'ACTIVE',
        '0441783589', 'xaris'),
       (76, '2024-05-03 00:00:00.000000', '2024-10-30 00:00:00.000000', '2024-05-18 00:00:00.000000', 'ACTIVE',
        '055321215X', 'sakis'),
       (77, '2024-11-12 00:00:00.000000', '2024-05-16 00:00:00.000000', '2024-12-16 00:00:00.000000', 'RETURNED',
        '0345402871', 'spyros'),
       (78, '2024-10-18 00:00:00.000000', '2024-07-03 00:00:00.000000', '2024-05-11 00:00:00.000000', 'ACTIVE',
        '2070423204', 'sakis'),
       (80, '2024-09-26 00:00:00.000000', '2024-12-24 00:00:00.000000', '2024-10-16 00:00:00.000000', 'OVERDUE',
        '042511774X', 'xaris'),
       (81, '2024-07-23 00:00:00.000000', '2024-07-05 00:00:00.000000', '2024-11-19 00:00:00.000000', 'OVERDUE',
        '0887841740', 'sakis'),
       (82, '2024-11-15 00:00:00.000000', '2024-10-21 00:00:00.000000', '2024-05-20 00:00:00.000000', 'ACTIVE',
        '0195153448', 'admin'),
       (83, '2024-11-06 00:00:00.000000', '2024-05-07 00:00:00.000000', '2024-05-01 00:00:00.000000', 'RETURNED',
        '0887841740', 'kri'),
       (84, '2024-09-21 00:00:00.000000', '2024-06-06 00:00:00.000000', '2024-10-22 00:00:00.000000', 'RETURNED',
        '0195153448', 'xaris'),
       (85, '2024-07-19 00:00:00.000000', '2024-07-05 00:00:00.000000', '2024-08-10 00:00:00.000000', 'RETURNED',
        '0441783589', 'sakis'),
       (87, '2024-11-12 00:00:00.000000', '2024-05-20 00:00:00.000000', '2024-11-15 00:00:00.000000', 'RETURNED',
        '0440225701', 'spyros'),
       (88, '2024-11-14 00:00:00.000000', '2024-08-18 00:00:00.000000', '2024-11-26 00:00:00.000000', 'OVERDUE',
        '0195153448', 'xaris'),
       (89, '2024-12-30 00:00:00.000000', '2024-10-09 00:00:00.000000', '2024-07-02 00:00:00.000000', 'OVERDUE',
        '0440225701', 'sakis'),
       (91, '2024-05-26 00:00:00.000000', '2024-12-20 00:00:00.000000', '2024-08-24 00:00:00.000000', 'RETURNED',
        '042511774X', 'xaris'),
       (92, '2025-01-01 00:00:00.000000', '2024-11-19 00:00:00.000000', '2024-08-31 00:00:00.000000', 'RETURNED',
        '0195153448', 'admin'),
       (93, '2024-08-06 00:00:00.000000', '2024-10-12 00:00:00.000000', '2024-07-04 00:00:00.000000', 'RETURNED',
        '055321215X', 'kri'),
       (94, '2024-12-23 00:00:00.000000', '2024-05-31 00:00:00.000000', '2024-09-08 00:00:00.000000', 'ACTIVE',
        '0312252617', 'sakis'),
       (95, '2024-07-07 00:00:00.000000', '2024-08-14 00:00:00.000000', '2024-07-28 00:00:00.000000', 'RETURNED',
        '0440225701', 'spyros'),
       (96, '2024-10-21 00:00:00.000000', '2024-11-15 00:00:00.000000', '2024-12-21 00:00:00.000000', 'RETURNED',
        '0195153448', 'kri'),
       (98, '2024-10-01 00:00:00.000000', '2024-09-05 00:00:00.000000', '2024-09-11 00:00:00.000000', 'RETURNED',
        '0345402871', 'kri'),
       (99, '2024-06-17 00:00:00.000000', '2024-05-15 00:00:00.000000', '2024-05-10 00:00:00.000000', 'RETURNED',
        '0441783589', 'xaris'),
       (100, '2024-06-16 00:00:00.000000', '2024-05-29 00:00:00.000000', '2024-10-21 00:00:00.000000', 'OVERDUE',
        '042511774X', 'sakis');

-- Dumping structure for table library.book_selection
CREATE TABLE IF NOT EXISTS `book_selection`
(
    `id`        bigint NOT NULL AUTO_INCREMENT,
    `book_isbn` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UKb97in3o052ylbabgnc5167tqw` (`book_isbn`),
    CONSTRAINT `FKd1j5m2dmd79g8alatyo00whc9` FOREIGN KEY (`book_isbn`) REFERENCES `book` (`isbn`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- Dumping data for table library.book_selection: ~5 rows (approximately)
INSERT INTO `book_selection` (`id`, `book_isbn`)
VALUES (1, '0195153448'),
       (2, '0312252617'),
       (3, '0345402871'),
       (4, '042511774X'),
       (5, '0440225701');

-- Dumping structure for table library.category
CREATE TABLE IF NOT EXISTS `category`
(
    `id`   bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- Dumping data for table library.category: ~8 rows (approximately)
INSERT INTO `category` (`id`, `name`)
VALUES (1, 'History'),
       (2, 'Mythology'),
       (3, 'Romance'),
       (4, 'Fiction'),
       (5, 'Non-fiction'),
       (6, 'Mistery'),
       (7, 'Language'),
       (8, 'Self-Help'),
       (9, 'Sci-Fi');

-- Dumping structure for table library.notification
CREATE TABLE IF NOT EXISTS `notification`
(
    `id`            bigint NOT NULL AUTO_INCREMENT,
    `created_at`    date         DEFAULT NULL,
    `is_read`       bit(1) NOT NULL,
    `message`       varchar(500) DEFAULT NULL,
    `user_username` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKbehuybxij203fy8cf70d54eak` (`user_username`),
    CONSTRAINT `FKbehuybxij203fy8cf70d54eak` FOREIGN KEY (`user_username`) REFERENCES `app_user` (`username`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- Dumping data for table library.notification: ~0 rows (approximately)

-- Dumping structure for table library.rating
CREATE TABLE IF NOT EXISTS `rating`
(
    `id`            bigint NOT NULL AUTO_INCREMENT,
    `score`         float  NOT NULL,
    `book_isbn`     varchar(255) DEFAULT NULL,
    `user_username` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKlr00tm4tfdblb17n55oibguoi` (`book_isbn`),
    KEY `FK55llhd6bs48mh9xt4q3eqvp8o` (`user_username`),
    CONSTRAINT `FK55llhd6bs48mh9xt4q3eqvp8o` FOREIGN KEY (`user_username`) REFERENCES `app_user` (`username`),
    CONSTRAINT `FKlr00tm4tfdblb17n55oibguoi` FOREIGN KEY (`book_isbn`) REFERENCES `book` (`isbn`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 101
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- Dumping data for table library.rating: ~100 rows (approximately)
INSERT INTO `rating` (`id`, `score`, `book_isbn`, `user_username`)
VALUES (1, 3.5, '0440225701', 'admin'),
       (2, 1.1, '055321215X', 'spyros'),
       (3, 2.3, '042511774X', 'spyros'),
       (4, 3.8, '0345402871', 'xaris'),
       (5, 1.2, '055321215X', 'spyros'),
       (6, 1.8, '2070423204', 'spyros'),
       (7, 2.1, '0441783589', 'sakis'),
       (8, 2.1, '2070423204', 'xaris'),
       (9, 1.6, '2070423204', 'spyros'),
       (10, 1.4, '042511774X', 'spyros'),
       (11, 1.1, '0195153448', 'spyros'),
       (12, 3.5, '0743403843', 'sakis'),
       (13, 2.5, '0195153448', 'sakis'),
       (14, 2.3, '0312252617', 'sakis'),
       (15, 3.3, '0345402871', 'kri'),
       (16, 4.3, '0440225701', 'spyros'),
       (17, 2.2, '0743403843', 'kri'),
       (18, 1.2, '0887841740', 'admin'),
       (19, 3.8, '0195153448', 'xaris'),
       (20, 2.3, '055321215X', 'sakis'),
       (21, 3.8, '0743403843', 'spyros'),
       (22, 2.5, '0195153448', 'sakis'),
       (23, 2.1, '042511774X', 'kri'),
       (24, 3.6, '042511774X', 'kri'),
       (25, 1.3, '0440225701', 'kri'),
       (26, 4.9, '0345402871', 'spyros'),
       (27, 4.7, '0440225701', 'xaris'),
       (28, 1.3, '0743403843', 'xaris'),
       (29, 4.5, '042511774X', 'sakis'),
       (30, 1.8, '2070423204', 'kri'),
       (31, 4.3, '0345402871', 'spyros'),
       (32, 1.5, '0743403843', 'kri'),
       (33, 1.9, '0743403843', 'spyros'),
       (34, 2.9, '0440225701', 'xaris'),
       (35, 1.3, '0440225701', 'spyros'),
       (36, 1.4, '055321215X', 'admin'),
       (37, 3.7, '0887841740', 'sakis'),
       (38, 1.8, '0743403843', 'admin'),
       (39, 2.9, '055321215X', 'kri'),
       (40, 2.2, '0887841740', 'xaris'),
       (41, 2, '0441783589', 'spyros'),
       (42, 1.7, '0441783589', 'xaris'),
       (43, 4.7, '0312252617', 'sakis'),
       (44, 3.7, '042511774X', 'admin'),
       (45, 4.5, '0441783589', 'admin'),
       (46, 3.1, '0743403843', 'kri'),
       (47, 5, '042511774X', 'sakis'),
       (48, 1.8, '0312252617', 'kri'),
       (49, 3.3, '2070423204', 'xaris'),
       (50, 4.6, '0743403843', 'admin'),
       (51, 2.3, '0312252617', 'spyros'),
       (52, 2.8, '042511774X', 'spyros'),
       (53, 1.7, '055321215X', 'sakis'),
       (54, 3, '0441783589', 'admin'),
       (55, 2.7, '0887841740', 'kri'),
       (56, 4.9, '0440225701', 'spyros'),
       (57, 1.1, '0195153448', 'kri'),
       (58, 2.2, '0887841740', 'kri'),
       (59, 2.5, '0441783589', 'admin'),
       (60, 5, '2070423204', 'admin'),
       (61, 1.9, '0743403843', 'xaris'),
       (62, 3.9, '0887841740', 'xaris'),
       (63, 4, '055321215X', 'spyros'),
       (64, 3.2, '0743403843', 'xaris'),
       (65, 1.5, '2070423204', 'spyros'),
       (66, 1, '0743403843', 'spyros'),
       (67, 1.9, '0743403843', 'kri'),
       (68, 1.5, '0887841740', 'kri'),
       (69, 4.1, '0441783589', 'admin'),
       (70, 2.5, '0743403843', 'sakis'),
       (71, 4.7, '0312252617', 'admin'),
       (72, 3.5, '0195153448', 'admin'),
       (73, 2, '042511774X', 'admin'),
       (74, 1, '055321215X', 'xaris'),
       (75, 1, '0887841740', 'spyros'),
       (76, 2.7, '042511774X', 'xaris'),
       (77, 2.9, '0345402871', 'sakis'),
       (78, 2.6, '2070423204', 'spyros'),
       (79, 4.9, '0440225701', 'sakis'),
       (80, 4.4, '0743403843', 'xaris'),
       (81, 2.9, '0743403843', 'kri'),
       (82, 3.1, '0743403843', 'admin'),
       (83, 4.2, '042511774X', 'kri'),
       (84, 3.7, '055321215X', 'spyros'),
       (85, 1.6, '0887841740', 'spyros'),
       (86, 1.7, '0441783589', 'kri'),
       (87, 3, '0195153448', 'kri'),
       (88, 1.2, '0195153448', 'spyros'),
       (89, 2.9, '042511774X', 'xaris'),
       (90, 2.2, '055321215X', 'sakis'),
       (91, 4.9, '0440225701', 'xaris'),
       (92, 2, '0440225701', 'spyros'),
       (93, 4.4, '0743403843', 'spyros'),
       (94, 2.2, '0441783589', 'admin'),
       (95, 4.9, '055321215X', 'admin'),
       (96, 4.4, '0887841740', 'sakis'),
       (97, 4.4, '0195153448', 'xaris'),
       (98, 4.7, '042511774X', 'kri'),
       (99, 3.2, '0887841740', 'sakis'),
       (100, 4.2, '2070423204', 'admin');

-- Dumping structure for table library.role
CREATE TABLE IF NOT EXISTS `role`
(
    `id`   bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- Dumping data for table library.role: ~2 rows (approximately)
INSERT INTO `role` (`id`, `name`)
VALUES (1, 'ADMIN'),
       (2, 'USER'),
       (3, 'LIBRARIAN');

-- Dumping structure for table library.user_role
CREATE TABLE IF NOT EXISTS `user_role`
(
    `username` varchar(255) NOT NULL,
    `role_id`  bigint       NOT NULL,
    PRIMARY KEY (`username`, `role_id`),
    KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
    CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
    CONSTRAINT `FKtcdhlmjy42y6a90l6qtavd20t` FOREIGN KEY (`username`) REFERENCES `app_user` (`username`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- Dumping data for table library.user_role: ~8 rows (approximately)
INSERT INTO `user_role` (`username`, `role_id`)
VALUES ('admin', 1),
       ('spyros', 1),
       ('admin', 2),
       ('kri', 2),
       ('sakis', 2),
       ('spyros', 2),
       ('xaris', 2),
       ('kri', 3);

/*!40103 SET TIME_ZONE = IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE = IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS = IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES = IFNULL(@OLD_SQL_NOTES, 1) */;
