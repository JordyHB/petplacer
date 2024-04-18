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
 'lisa',
 'alderson',
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
 '$2a$10$9dtMcN92pXB41ZpPFRex8.fqOvOsxpYKdecDUaGCnn.XgszqLzFE6'),

--     password is banaan
('deletableuser',
 'test',
 'user',
 'test@email.com',
 '0612345678',
 true,
 now(),
 now(),
 '$2a$12$56kTXHcE78Hyt9PgnU/GdOtD7xo5YAcBWFGE9nGljfYPPxkX9RWZi');



INSERT INTO authorities (username, authority)
VALUES ('admin', 'ROLE_USER'),
       ('admin', 'ROLE_ADMIN'),
       ('msbear', 'ROLE_USER'),
       ('jord', 'ROLE_USER'),
       ('jord', 'ROLE_SHELTER_MANAGER');


INSERT INTO shelters (shelter_name, phone_number, email, address, city, postal_code, description, website, facilities,
                      opening_hours, date_of_registration, date_of_last_update)
values ('animal shelter the farm',
        '0612345678',
        'contactus@hetweitje.nl',
        'animal lane 1',
        'amsterdam',
        '1234ab',
        'animal shelter the farm is an animal shelter in amsterdam. we take in dogs and cats that dont have a home. we take care of them until they find a new home.',
        'www.hetweitje.nl',
        'dog kennels, cat kennels, playground, veterinarian',
        'monday to sunday: 09:00 - 17:00',
        now(),
        now()),

       ('second chance shelter',
        '0655667788',
        'adoptions@secondchance.nl',
        '1 oak road',
        'the hague',
        '2517kl',
        'second chance sanctuary is dedicated to finding loving homes for senior and neglected animals. we believe every animal deserves a happy ending.',
        'www.secondchance.nl',
        'comfortable senior pet suites, enrichment areas, palliative care facilities, extensive volunteer network',
        'wednesday to sunday: 12:00 - 16:00',
        now(),
        now()),

       ('cozy corner animal shelter',
        '0611223344',
        'info@cozycorner.org',
        '23 maple street',
        'haarlem',
        '5611xr',
        'cozy corner animal rescue provides a safe haven for rescued animals of all kinds. we offer foster care, adoption services, and community outreach programs.',
        'www.cozycorner.org',
        'small animal rooms, rabbit runs, outdoor enclosures, volunteer training center',
        'tuesday to saturday: 11:00 - 16:00',
        now(),
        now());


INSERT INTO shelter_managers (user_id, shelter_id)
VALUES ('jord', 1),
       ('admin', 1),
       ('msbear', 2),
       ('admin', 3);


INSERT INTO shelter_pets (name, species, breed, color, age, gender, size, description, spayed_neutered, good_with_kids,
                          good_with_dogs, good_with_cats, medical_history, special_needs, previous_situation, status,
                          date_of_registration, date_of_last_update, months_in_shelter, shelter_id, adoption_fee)
VALUES ('luna',
        'dog',
        'labrador retriever',
        'black',
        2,
        'MALE',
        'medium',
        'friendly and playful dog looking for a loving home.',
        true,
        true,
        true,
        false,
        'none',
        'none',
        'surrendered by previous owner',
        'AVAILABLE',
        now(),
        now(),
        5,
        1,
        300),

       ('charlie',
        'cat',
        'siamese',
        'white',
        1,
        'FEMALE',
        'small',
        'affectionate and playful cat',
        true,
        true,
        false,
        true,
        'none',
        'none',
        'abandoned',
        'AVAILABLE',
        now(),
        now(),
        2,
        1,
        150.3131313),

       ('max',
        'dog',
        'german shepherd',
        'black and tan',
        4,
        'MALE',
        'large',
        'loyal and protective dog',
        true,
        true,
        true,
        false,
        'minor ear infection',
        'none',
        'left at shelter by previous owner',
        'AVAILABLE',
        now(),
        now(),
        1,
        1,
        0);

INSERT INTO adoption_requests (adoption_applicant_username, requested_pet_id, status, submission_date, decision_date,
                               request_message)
VALUES ('msbear', 1, 'REJECTED', now(), now(), 'I think im a good fit for this pet'),
       ('msbear', 1, 'PENDING', now(), null, 'I would like to try again for this pet'),
       ('jord', 2, 'APPROVED', now(), now(), 'I would like to adopt this pet');

INSERT INTO donations (donation_amount, date_of_donation, date_of_last_update, donation_message, donator_username,
                       receiving_shelter_id)
VALUES (100, now(), now(), 'I would like to help the animals in need', 'msbear', 1),
       (50, now(), now(), 'I would like to donate to my own shelter', 'jord', 1),
       (200, now(), now(), 'I would also like to help the animals in need', 'admin', 1),
       (300, now(), now(), 'I would like to help', 'msbear', 1);

INSERT INTO user_owned_pets (age, good_with_cats, good_with_dogs, good_with_kids, is_adopted, spayed_neutered,
                             years_owned, date_of_last_update, date_of_registration, breed, color,
                             current_owner_username, description, gender, name, size, species)

VALUES (8, true, true, true, false, true,
        8, now(), now(), 'golden doodle', 'black',
        'jord', 'the happiest and kindest dog, she does love eating things she shouldnt.', 'FEMALE', 'kyara', 'large',
        'dog'),

       (5, false, false, true, false, false,
        1, now(), now(), 'budgerigar', 'green and yellow',
        'msbear', 'a cheerful and chatty bird.', 'MALE', 'kiwi', 'small', 'bird'),

       (1, true, false, false, false, true,
        0, now(), now(), 'german shepherd', 'black and tan',
        'admin', 'a loyal and energetic pup.', 'MALE', 'max', 'large', 'dog'),

       (8, false, true, true, true, false,
        5, now(), now(), 'siamese', 'cream and brown',
        'msbear', 'a shy but affectionate cat.', 'FEMALE', 'bella', 'small', 'cat');

