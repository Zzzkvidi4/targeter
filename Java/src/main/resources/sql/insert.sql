INSERT INTO public.target_category(id, user_id, name)
VALUES (1, null, 'Общие');

INSERT INTO public.motivation(
            id, text, target_category_id)
VALUES (1, 'Пора сделать новый шаг к своей цели!', 1);
INSERT INTO public.motivation(
    id, text, target_category_id)
VALUES (2, 'Как говорит мой знакомый, "Хватит ныть! Действуй"', 1);
INSERT INTO public.motivation(
    id, text, target_category_id)
VALUES (3, 'Цель сама себя не выполнит. Давай, дружище, у тебя все получится!', 1);

INSERT INTO public.target_category(id, user_id, name)
VALUES (2, null, 'Спорт');

INSERT INTO public.target_category(id, user_id, name)
VALUES (3, null, 'Работа');

INSERT INTO public.target_category(id, user_id, name)
VALUES (4, null, 'Хобби');

INSERT INTO public.target_category(id, user_id, name)
VALUES (5, null, 'Окружение');

INSERT INTO public.target_category(id, user_id, name)
VALUES (6, null, 'Красота и здоровье');
