INSERT INTO users (username, first_name, last_name, email,phone_number, enabled, created_at, updated_at, password)
VALUES

--     password is '12345'
    ('admin',
     'bennie',
     'mccool',
     'mrcool@admin.nl',
     '0612345678',
        true,
        now(),
        now(),
     '$2a$12$kzGTdysMkZKC9Y7iVqJlCeEtcf8MkdQajp82M.OLpGCSxsSRCXG6e');

('msbear',
    'Lisa',
    'Alderson',
    'lina@testuser.com',
    '0642345678',
    true,
    now(),
    now(),
    '$2a$12$kzGTdysMkZKC9Y7iVqJlCeEtcf8MkdQajp82M.OLpGCSxsSRCXG6e');

INSERT INTO authorities (username, authority)
VALUES
    ('admin', 'ROLE_USER'),
    ('admin', 'ROLE_ADMIN');
    ('msbear', 'ROLE_USER')
