## Текстовый квест

### Use Cases (Сценарии использования)

***Осмотреться***

1. Вывести на экран описание текущей локации: список предметов и направлений.


***Инвентарь***

1. Вывести на экран описание предметов в инвентаре.


***Идти "направление"***

1. Проверить, можно ли пойти в указанном направлении.
2. Если можно, сменить текущую локацию.
3. Осмотреться.


***Взять "предмет"***

1. Проверить, есть ли предмет на локации.
2. Проверить, можно ли взять предмет.
3. Если есть и можно взять, удалить предмет из локации и добавить в инвентарь игрока.   


***Использовать "объект" "субъект"***

*Допущения:*
- объект должен находиться в инвентаре игрока;

1. Проверить, находится ли объект в инвентаре игрока.
2. Проверить, существует ли субъект.
3. Если существуют и субъект и объект, то проверить возможность их взаимодействия.
4. Поместить результирующий объект в инвентарь игрока.
5. Удалить объект из инвентаря игрока.
6. Удалить субъект, если он перемещаемый.
7. Вывести сообщение на экран.


***Выйти***

1. Завершить работу программы.

### Примерная Диаграмма классов будет представлена в отдельном файле
Чтобы его как-то изменить - загрузите adventure.xml в сервис draw.io.


***Реализация***

1.Генератор рандомной карты с заданием размера.

2.Консольное меню.

3.Сохранение/Загрузка.

4.Тесты.

5.Сюжет - бродишь, лутаешь, выживаешь.

6.Stream API где наиболее удобно.