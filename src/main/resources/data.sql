INSERT INTO kind_of_shift (code, description, is_working)
VALUES ('REGULAR_SHIFT', 'Turno Normal', true)
ON CONFLICT (code, description,) DO NOTHING;

INSERT INTO kind_of_shift (code, description, is_working)
VALUES ('OVERTIME', 'Turno Extra', true)
ON CONFLICT (code, description,) DO NOTHING;

INSERT INTO kind_of_shift (code, description, is_working)
VALUES ('DAY_OFF', 'Dia Libre', false)
ON CONFLICT (code, description,) DO NOTHING;

INSERT INTO kind_of_shift (code, description, is_working)
VALUES ('VACATION', 'Vacaciones', false)
ON CONFLICT (code, description,) DO NOTHING;