INSERT INTO department (id, name)
VALUES
    ('01927c77-c915-7b2a-92c8-2a597d47e29a', 'department 1'),
    ('01927c79-65ce-7688-aa9f-a406404918e8', 'department 2');

INSERT INTO employee (id, name, reference, department_id)
VALUES
    ('01927c78-99c2-7b28-8d54-9ad48dab0344', 'employee 1', 'empl1', '01927c77-c915-7b2a-92c8-2a597d47e29a'),
    ('01927c79-65ce-7688-aa9f-a407da7e17e0', 'employee 2', 'empl2', '01927c77-c915-7b2a-92c8-2a597d47e29a'),
    ('01927c79-65ce-7688-aa9f-a4089c019f74', 'employee 3', 'empl3', '01927c79-65ce-7688-aa9f-a406404918e8');
