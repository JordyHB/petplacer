INSERT INTO users (username, first_name, last_name, email, phone_number, enabled, created_at, updated_at, password)
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
 '$2a$12$kzGTdysMkZKC9Y7iVqJlCeEtcf8MkdQajp82M.OLpGCSxsSRCXG6e'),
--    password is '5678"
('msbear',
 'Lisa',
 'Alderson',
 'lina@testuser.com',
 '0642345678',
 true,
 now(),
 now(),
 '$2a$12$W2PbO.NvsW7x8HBnxFLhw.vJOvHrCnWt/alVvxEIqwdZzznDXcuOW'),

('jord',
 'jordy',
 'doe',
 'jordy.doe@example.com',
 '0612345678',
 true,
 now(),
 now(),
 '$2a$10$9dtMcN92pXB41ZpPFRex8.fqOvOsxpYKdecDUaGCnn.XgszqLzFE6');


INSERT INTO authorities (username, authority)
VALUES ('admin', 'ROLE_USER'),
       ('admin', 'ROLE_ADMIN'),
       ('msbear', 'ROLE_USER'),
       ('jord', 'ROLE_USER'),
       ('jord', 'ROLE_SHELTER_MANAGER');


INSERT INTO shelters (shelter_name, phone_number, email, address, city, postal_code, description, website, facilities,
                      opening_hours, date_of_registration, date_of_last_update)
VALUES ('dierenopvangcentrum het weitje',
        '0612345678',
        'contactus@hetweitje.nl',
        'dierenlaan 1',
        'amsterdam',
        '1234ab',
        'dierenopvangcentrum het weitje is een dierenopvangcentrum in amsterdam. wij vangen honden en katten op die geen huis hebben. wij zorgen voor ze totdat ze een nieuw huisje hebben gevonden.',
        'www.hetweitje.nl',
        'hondenkennels, kattenkennels, speelveld, dierenarts',
        'maandag t/m zondag: 09:00 - 17:00',
        now(),
        now());


INSERT INTO shelter_managers (user_id, shelter_id)
VALUES ('jord', 1);


INSERT INTO shelter_pets (name, species, breed, color, age, gender, size, description, spayed_neutered, good_with_kids,
                          good_with_dogs, good_with_cats, medical_history, special_needs, previous_situation, status,
                          date_of_registration, date_of_last_update, months_in_shelter, shelter_id, adoption_fee)
VALUES ('Luna',
        'Dog',
        'Labrador Retriever',
        'Black',
        2,
        'MALE',
        'Medium',
        'Friendly and playful dog looking for a loving home.',
        true,
        true,
        true,
        false,
        'None',
        'None',
        'Surrendered by previous owner',
        'AVAILABLE',
        now(),
        now(),
        5,
        1,
        300),

       ('Charlie',
        'Cat',
        'Siamese',
        'White',
        1,
        'FEMALE',
        'Small',
        'Affectionate and playful cat',
        true,
        true,
        false,
        true,
        'None',
        'None',
        'Abandoned',
        'AVAILABLE',
        now(),
        now(),
        2,
        1,
        150.3131313),

       ('Max',
        'Dog',
        'German Shepherd',
        'Black and Tan',
        4,
        'MALE',
        'Large',
        'Loyal and protective dog',
        true,
        true,
        true,
        false,
        'Minor ear infection',
        'None',
        'Left at shelter by previous owner',
        'AVAILABLE',
        now(),
        now(),
        1,
        1,
        0);

INSERT INTO adoption_requests (adoption_applicant_username, requested_pet_id, status, submission_date, decision_date, request_message)
VALUES ('msbear', 1, 'REJECTED', now(), now(), 'I think im a good fit for this pet'),
       ('msbear', 1, 'PENDING', now(), null, 'I would like to try again for this pet'),
       ('jord', 2, 'APPROVED', now(), now(), 'I would like to adopt this pet');