INSERT INTO users (username, password, fingerprint)
VALUES
    ('john_doe', 'hashed_password_example', 'ab3f4c8c2d321f8f730928b3e5a07d1d5b7a1d7c9f0baee928f123a4bf4b5678'),
    ('alice_smith', 'another_hashed_password', 'd7f7e4c0b3492eaf27a12c44d459ac68a47d6f8b998f16e3e64c7f67f9b8f493'),
    ('bob_jones', 'password123_hash', '1c9b4a8b839c9b4cde4e77356cfd9f84b399cc4b97c0c22c6c6f245bb4d4cc4e'),
    ('charlie_brown', 'securehashedpassword456', '25b8fbd56b0c1eac560183c5e10b7d7670eae6e457dbfcd070b48c03286511d0'),
    ('emma_wilson', 'topsecretpassword', 'f3b8314d31f60b457c28cf24bcead5aeb78b173b29eafaf290d184db720fa2a4'),
    ('david_clark', '12345securehash', 'd2f953ce7e85d8252f2684690c60b201ff68db1a0b072cc0bc3d1c16db8c4c9f'),
    ('lucy_king', 'securePass789!', 'cf3451bc9a12b3427b76b4e7c73c4c5e4101d441e468736f6b207affed254b9d'),
    ('michael_jackson', 'verystrongpassword987', '8bb0454d178ec8037c0f7f840380c313b1b01415d8cd67f4b938bff6d442cc5e'),
    ('susan_adams', 'myhashedpassword123', '7123e95cb57b408fc0e37a396230abff2b9a09701f8b4043ea914dbb6b265c9c'),
    ('james_taylor', 'supersecret123hash', 'ca8c7a621dd8b12bc772e43705a6857d6e6591ea2ab2ad5d161ba4b3a84c6a02');

INSERT INTO logging (username, category, intruder, time)
VALUES
    ('john_doe', 1, 'charlie_brown', '2025-01-03 14:30:21'),
    ('john_doe', 1, 'emma_wilson', '2024-12-15 12:24:52'),
    ('michael_jackson', 2, NULL, '2024-12-16 09:11:22'),
    ('alice_smith', 1, 'bob_jones', '2025-01-02 10:20:15'),
    ('alice_smith', 2, NULL, '2025-01-01 08:45:30'),
    ('bob_jones', 1, 'lucy_king', '2024-12-20 14:12:09'),
    ('bob_jones', 2, NULL, '2024-12-18 16:33:45'),
    ('charlie_brown', 2, NULL, '2024-12-22 11:11:11'),
    ('emma_wilson', 1, 'david_clark', '2024-12-17 13:00:00'),
    ('emma_wilson', 2, NULL, '2024-12-23 15:59:00'),
    ('david_clark', 1, 'susan_adams', '2024-12-19 17:25:45'),
    ('david_clark', 2, NULL, '2024-12-24 14:50:35'),
    ('lucy_king', 1, 'james_taylor', '2025-01-02 09:10:28'),
    ('lucy_king', 2, NULL, '2024-12-25 13:20:00');