# Tablichka
Если вы видете это, вы (надеюсь...) разрабатываете табличку, разные маленькие изменения делайте в ветке dev/ рабочие релизы мёрджите в master/
спрашивайте Denery за Gradle'овские штуки.

Структура проекта:
-------
Подпроекты лежащие прямо в руте (по типу Entities, Discs, WayStones) являются самими плагинами со своими plugin.yml,
в свою очередь они могут зависеть от подпроектов в Libraries/ которые являются нашими локальными библиотеками
которые мы написали в процессе разработки основных плагинов. Наши библиотеки не являются отдельными плагинами, 
в процессе сборки они собираются вместе с теми плагинами, которые их используют.
Весь код из отдельных подпроектов по типу Entities при запуске команды jar в руте автоматически билдится в .server/plugins/.
.server/ является нашим локальным сервером на airplane для тестов. (Можете подрубить туда hotswap если знаете как)
Также в Libraries есть библиотека Misc,в которую можно пихать разные (извращённые) штуки которые используются по проекту повсеместно.

Разработка:
------
Можно тестить плагин запустив ```gradle test-plugin``` эта команда автоматом билдит все плагины со всеси библиотеками в .server/plugins/ и запускает airplane.

Как и какие можно добавлять зависимости на подпроекты.
------
1. В [билдскрипте](build.gradle) итерируя массив названий подроектов они инициализируются по примерно похожей схеме друг с другом.
В блоке dependencies {} вы увидете switch, если в нём есть case с вашим подроектом, то добавляете зависимость туда.
Если нет, то добавляете этот case с именем подроекта, и уже добавляете во внутрь зависимость, не заьывайте в конце case сделать пустой return.
2. Есть несколько видов зависимостей, я (Denery) настроил, что зависимость со словом ```implementation``` билдится во внутрь
подроекта, а зависимость со словом ```compileOnly``` видится проектом, но не билдится в него. (Это нужно для библиотек-плагинов по типк ProtocolLib)
3. Если нужно добавить зависимость на локальную библиотеку в Libraries/ то пишите ```implementation project(":Libraries:название_библиотеки")```.
4. Чтобы добавить зависимость на NMS пишите ```compileOnly files("$rootDir/.sources/patched_1.17.1.jar")```

Ради добавления новых подроектов/миграции с локальных репозиториев пишите Denery.
