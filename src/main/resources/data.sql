INSERT IGNORE INTO kind_of_shift (code, description_en, description_es, is_working)
VALUES ('RS', 'REGULAR_SHIFT', 'Turno Normal', true);

INSERT IGNORE INTO kind_of_shift (code, description_en, description_es, is_working)
VALUES ('O', 'OVERTIME', 'Turno Extra', true);

INSERT IGNORE INTO kind_of_shift (code, description_en, description_es, is_working)
VALUES ('DO', 'DAY_OFF', 'Dia Libre', false);

INSERT IGNORE INTO kind_of_shift (code, description_en, description_es, is_working)
VALUES ('V', 'VACATION', 'Vacaciones', false);