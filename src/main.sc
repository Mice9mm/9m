require: slotfilling/slotFilling.sc
  module = sys.zb-common
theme: /

    state: Start
        q!: $regex</start>
        script:
            $session.goal = "greeting";
            $session.facts = {};
            $session.history = [];
            $session.done = false;
            $session.retention_used = false;
        a: Здравствуйте! Я Алсу, HR-специалист ОЭЗ «Алабуга». Нашла ваше резюме и хотела бы обсудить интересную вакансию. Вы сейчас в поиске работы?

    state: Retention
        intent!: /нет
        script:
            if ($session.goal !== "greeting" || $session.retention_used) {
                $go.go("/DoneReject");
            }
            $session.retention_used = true;
            $session.goal = "retention";
        a: Понимаю, что сейчас не в активном поиске. Но ваш опыт действительно привлёк внимание — предложение может оказаться интересным. Давайте я коротко расскажу, а вы решите?

    state: AskRelocation
        intent!: /да
        script:
            if ($session.goal !== "greeting" && $session.goal !== "retention") {
                $go.go("/NoMatch");
            }
            $session.goal = "ask_relocation";
        a: ОЭЗ «Алабуга» — крупнейшая особая экономическая зона в Европе, Елабуга, Татарстан. ООО «Алабуга Девелопмент» — генподрядчик ОЭЗ. Работа предполагает нахождение на объекте в Елабуге. Вы рассматриваете переезд или ежедневные поездки в Елабугу?

    state: HandleRelocationObj
        intent!: /нет
        script:
            if ($session.goal !== "ask_relocation") {
                $go.go("/HandleNoIntent");
            }
            $session.goal = "handle_relocation_objection";
        a: У нас есть корпоративный транспорт — ежедневно из Набережных Челнов, Нижнекамска и Менделеевска. Корпоративное жильё ЖК «Южный Парк» от 4 000 руб/мес. Пропуск на авто через 3 мес. При таких условиях готовы рассмотреть?

    state: TellConditions
        intent!: /да
        script:
            if ($session.goal !== "ask_relocation" && $session.goal !== "handle_relocation_objection") {
                $go.go("/NoMatch");
            }
            $session.goal = "tell_conditions";
        a: Отлично! Корпоративный транспорт — из Елабуги, Набережных Челнов, Нижнекамска, Менделеевска. Жильё ЖК «Южный Парк» от 4 000 руб/мес, совместное проживание. Пропуск на авто через 3 мес. Трудоустройство по ТК РФ. Вас устраивают такие условия?

    state: TellPosition
        intent!: /да
        script:
            if ($session.goal !== "tell_conditions") {
                $go.go("/NoMatch");
            }
            $session.goal = "tell_position";
        a: Мы рассматриваем вас на должность Младшего Специалиста ПТО — заработная плата от 150 000 рублей до вычета НДФЛ. Такое предложение вас интересует?

    state: AskExperience
        intent!: /да
        script:
            if ($session.goal !== "tell_position") {
                $go.go("/NoMatch");
            }
            $session.goal = "ask_experience";
        a: Расскажите, пожалуйста, об опыте работы — где работали, какие задачи выполняли? Особенно интересует опыт с AutoCAD, Word и Excel.

    state: PresentVacancy
        event!: noMatch
        script:
            if ($session.goal !== "ask_experience") {
                $go.go("/NoMatch");
            }
            $session.facts.experience_told = true;
            $session.goal = "present_vacancy";
        a: Спасибо! Младший Специалист ПТО занимается подготовкой ТЗ для подрядчиков, ведением документооборота — акты, журналы, предписания, контролем расхода материалов и подготовкой исполнительной документации. Условия: от 150 000 руб, корпоративный транспорт, карьерный рост. Если нет вопросов — расскажу о корпоративной культуре компании.

    state: AskGames
        event!: noMatch
        script:
            if ($session.goal !== "present_vacancy") {
                $go.go("/NoMatch");
            }
            $session.goal = "ask_games";
        a: У нас 2 раза в год деловые игры-ассессменты — Business Cats, Civilization, Грузовики. Не компьютерные — оценка аналитики и лидерства. При трудоустройстве — Business Cats за 3 дня. Как вы относитесь к деловым играм-ассессментам?

    state: HandleGamesObj
        intent!: /нет
        script:
            if ($session.goal !== "ask_games") {
                $go.go("/HandleNoIntent");
            }
            $session.goal = "handle_games_objection";
        a: Это не только оценка — знакомство с руководством и коллегами, обмен опытом. При таком формате готовы рассмотреть?

    state: AskMotivation
        intent!: /да
        script:
            if ($session.goal !== "ask_games" && $session.goal !== "handle_games_objection") {
                $go.go("/NoMatch");
            }
            $session.goal = "ask_motivation";
        a: О системе мотивации: договор срочный 3 мес с продлением. Зарплата: 25% оклад + 50% за выполнение обязанностей + 25% KPI. Итого от 150 000 рублей. Вы рассматриваете такую систему мотивации?

    state: HandleMotivObj
        intent!: /нет
        script:
            if ($session.goal !== "ask_motivation") {
                $go.go("/HandleNoIntent");
            }
            $session.goal = "handle_motivation_objection";
        a: Система нацелена на результат — чем лучше выполняете план, тем выше доход. При полном выполнении итого от 150 000 руб. При таком подходе готовы рассмотреть?

    state: AskOvertime
        intent!: /да
        script:
            if ($session.goal !== "ask_motivation" && $session.goal !== "handle_motivation_objection") {
                $go.go("/NoMatch");
            }
            $session.goal = "ask_overtime";
        a: В строительстве бывают задержки — ЧП, срочная сдача. У нас это редкость, всегда компенсируется оплачиваемыми днями к отпуску. Как вы относитесь к ненормированному графику?

    state: HandleOvertimeObj
        intent!: /нет
        script:
            if ($session.goal !== "ask_overtime") {
                $go.go("/HandleNoIntent");
            }
            $session.goal = "handle_overtime_objection";
        a: Это редкость — только при ЧП или срочной сдаче. Всегда компенсируется оплачиваемыми днями к отпуску. Устраивает такой подход?

    state: AskCrime
        intent!: /да
        script:
            if ($session.goal !== "ask_overtime" && $session.goal !== "handle_overtime_objection") {
                $go.go("/NoMatch");
            }
            $session.goal = "ask_crime";
        a: Подскажите, пожалуйста, у вас имеется судимость или проблемы с законом?

    state: AskIP
        intent!: /нет
        script:
            if ($session.goal !== "ask_crime") {
                $go.go("/HandleNoIntent");
            }
            $session.facts.crime = false;
            $session.goal = "ask_ip";
        a: У вас открыто ИП, самозанятость или другая форма предпринимательства?

    state: HandleIPObj
        intent!: /да
        script:
            if ($session.goal !== "ask_ip") {
                $go.go("/NoMatch");
            }
            $session.facts.ip = true;
            $session.goal = "handle_ip_objection";
        a: Обязательное условие ОЭЗ «Алабуга» — отсутствие действующих ИП. Если готовы закрыть — будем рады принять. Готовы закрыть?

    state: AskSalary
        intent!: /нет
        script:
            if ($session.goal !== "ask_ip" && $session.goal !== "handle_ip_objection") {
                $go.go("/HandleNoIntent");
            }
            $session.goal = "ask_salary";
        a: Каковы ваши зарплатные ожидания? От какой суммы рассматриваете предложения?

    state: AskEmployment
        event!: noMatch
        script:
            if ($session.goal !== "ask_salary") {
                $go.go("/NoMatch");
            }
            $session.goal = "ask_employment";
        a: Сейчас работаете официально? Когда сможете написать заявление на увольнение? Потребуется ли отработка 2 недели?

    state: AskCallback
        event!: noMatch
        script:
            if ($session.goal !== "ask_employment") {
                $go.go("/NoMatch");
            }
            $session.facts.employment_told = true;
            $session.goal = "ask_callback";
        a: Спасибо! Передаю ваше резюме руководителю, при положительном решении он свяжется в ближайшие дни. Когда вам удобно принять звонок от руководителя?

    state: DoneOK
        event!: noMatch
        script:
            if ($session.goal !== "ask_callback") {
                $go.go("/NoMatch");
            }
            $session.done = true;
        a: Зафиксировала! Спасибо за уделённое время. Передаю резюме руководителю — ожидайте звонок. Хорошего дня!

    state: DoneReject
        intent!: /нет
        script:
            var rejectGoals = ["retention","tell_conditions","tell_position","handle_relocation_objection","handle_games_objection","handle_motivation_objection","handle_overtime_objection","handle_ip_objection","ask_callback"];
            if (rejectGoals.indexOf($session.goal) === -1) {
                $go.go("/HandleNoIntent");
            }
            $session.done = true;
        a: Понимаю, спасибо за уделённое время! Желаю удачи в поиске работы. До свидания!

    state: HandleNoIntent
        a: Понимаю. Вернёмся к нашему разговору — {{$session.goal == "ask_crime" ? "у вас есть судимость?" : "продолжим?"}}

    state: AlreadyDone
        event!: noMatch
        script:
            if (!$session.done) {
                $go.go("/NoMatch");
            }
        a: Наше собеседование уже завершено. Чтобы начать заново — нажмите «Начать».

    state: NoMatch
        event!: noMatch
        a: Не совсем поняла. Напишите, пожалуйста, ответ на мой вопрос.