# Главный Репозиторий Foton
Если вы видете это, вы (надеюсь...) разрабатываете Foton, разные тестируемые / сырые изменения делайте в ветке dev/ рабочие релизы идущие на релизный сервер мёрджите в master/
спрашивайте Denery за Gradle и Scala штуки.

Люди имеющие доступ к репозиторию:
------
- FO (Foton Owner) - Руководитель всех комманд Foton.
- Danik Vitek - Тим-Лид, всегда имеет ТЗ и распределяет работу по команде. Разрабатывает подпроекты Discs, DiscRegistry, Waystones. Языки: Java, Scala.
- Denery (A.K.A ExtraExtremeERA) - Архитектор, Performance Engineer, в основном делает основные библиотеки так, чтобы в будущем другие программисты не страдали от их использования / Производительности. Может принимать тех. Решения в проекте. (Какую, где и как использовать различные технологии в проектах). Разрабатывает подпроекты: Entities, EntityRegistry. Языки: Java, Scala, Groovy (Gradle).
- NitkaNikita21 - Глава разработки хаба, плагины хаба имеют отдельный репозиторий. Языки: Java, JavaScript.
- SapeNeCo - Разработчик Discord ботов коммуницирующих с сервером в своих репозиториях, в этом репозитории на данный момент ничего не разрабатывает. Языки: Java.

Структура проекта:
-------
Мы используем многопроектный билд сделанный на Gradle Kotlin DSL, подпроекты с "pl-" в начале означают то что это плагин, 
подпроекты лежащие в "libraries" - библиотеки разработанные во время разработки основного функционала.
Всё это компилируется в отдельные джарники, имеющие plugin.yml. Все проекты имеют подключенный NMS на Mojang Mapping'ах,
это сделанно с целью лёгкого порта в моды при необходимости, и для лучшей читаемости NMS.
plugin.yml настраивается в build.gradle.kts, и проекты не должны в resources/ содержать его. Это сделанно засчёт
Gradle плагина "plugin-yml" от Minecrell, генерирующие его в момент билда. Библиотека "misc" содержит в себе
все компоненты, слишком маленькие чтобы иметь личную библеотеку, но использующиеся во многих других подпроектах.

Разработка:
------
Можно тестить плагин запустив ```runServer``` в правой менюшке в разделе ```TabLight > Tasks > run paper```, эта команда автоматом билдит все плагины и библеотеки и запускает новейший билд paper'а, сама конфигурация сервера находится в папке ```run/```

Про Scala:
-------
Scala - JVM-based язык компилируемый в Java байткод и полностью поддерживаемый джавой, основная парадигма написания программ на Scala это
функциональное программирование, которое при грамотном использовании будет намного удобнее и быстрее в написании чем ООП.

Мы не принуждаем к изучению Scala, но желательно чтобы вы хотя бы понимали базовые вещи чтобы коммуницировать со Scala на проекте

Инициатива добавить scala как второй язык на проект была введена с целью снижения времени на разработку библиотек и их сложных компонентов,
код на scala написан в Функциональном стиле Tagless Final, есть одна замечательная [конференция](https://www.youtube.com/watch?v=sWEtnq0ReZA&t=2024s) и [видео туториал](https://www.youtube.com/watch?v=ZNK57IXgr3M) посвящённые кодингу на этом стиле,
с которых мы и берём примеры. Помимо стандартной библиотеки Scala на проекте используется библиотека Tofu включающая в себя Cats, Cats Effect и Cats Tagless и другие компоненты. Ради поддержки скалы на сервере была введена библиотека "scala-support" в которой при билде будет забилжена внутрь стандартная библиотека Scala с Tofu и его зависимостями. (Есть другой способ запуска сервера с поддержкой Scala запуская его в скрипте с коммандой ```scala``` вместо ```java```) Также ещё так сложилось, что на момент принятия Scala на проект вышел Gradle 7.3 поддерживает новейшие версии scala 3+, так что для нас всё сложилось очень удобно.

P.S.
-------
По вопросам связанных с Gradle (добавление новых зависимостей/подпроектов/миграции с локальных репозиториев), а также по разным тех. решениям пишите Denery.
