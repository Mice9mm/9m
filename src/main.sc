require: slotfilling/slotFilling.sc
  module = sys.zb-common
theme: /

    state: Start
        q!: $regex</start>
        a: Здравствуйте! Я Алсу, HR-специалист ОЭЗ «Алабуга». Нашла ваше резюме и хотела бы обсудить интересную вакансию. Вы сейчас в поиске работы?
        state: Yes
            intent!: /да
            go!: /AskRelocation
        state: No
            intent!: /нет
            go!: /Retention

    state: Retention
        a: Понимаю, что сейчас не в активном поиске. Но ваш опыт действительно привлёк внимание — предложение может оказаться интересным. Давайте я коротко расскажу?
        state: Yes
            intent!: /да
            go!: /AskRelocation
        state: No
            intent!: /нет
            go!: /DoneReject

    state: AskRelocation
        a: ОЭЗ «Алабуга» — крупнейшая особая экономическая зона в Европе, Елабуга, Татарстан. ООО «Алабуга Девелопмент» — генподрядчик ОЭЗ. Работа на объекте в Елабуге. Вы рассматриваете переезд или ежедневные поездки?
        state: Yes
            intent!: /да
            go!: /TellConditions
        state: No
            intent!: /нет
            go!: /HandleRelocationObj

    state: HandleRelocationObj
        a: У нас корпоративный транспорт — из Набережных Челнов, Нижнекамска, Менделеевска. Жильё ЖК «Южный Парк» от 4 000 руб/мес. Пропуск на авто через 3 мес. При таких условиях готовы рассмотреть?
        state: Yes
            intent!: /да
            go!: /TellConditions
        state: No
            intent!: /нет
            go!: /DoneReject

    state: TellConditions
        a: Корпоративный транспорт ежедневно из Елабуги, Челнов, Нижнекамска, Менделеевска. Жильё ЖК «Южный Парк» от 4 000 руб/мес, совместное проживание. Пропуск на авто через 3 мес. Трудоустройство по ТК РФ. Вас устраивают такие условия?
        state: Yes
            intent!: /да
            go!: /TellPosition
        state: No
            intent!: /нет
            go!: /DoneReject

    state: TellPosition
        a: Мы рассматриваем вас на должность Младшего Специалиста ПТО — заработная плата от 150 000 рублей до вычета НДФЛ. Такое предложение вас интересует?
        state: Yes
            intent!: /да
            go!: /AskExperience
        state: No
            intent!: /нет
            go!: /DoneReject

    state: AskExperience
        a: Расскажите об опыте работы — где работали, какие задачи выполняли? Особенно интересует опыт с AutoCAD, Word и Excel.
        state: Told
            event!: noMatch
            go!: /PresentVacancy

    state: PresentVacancy
        a: Спасибо! Младший Специалист ПТО занимается подготовкой ТЗ для подрядчиков, ведением документооборота, контролем расхода материалов и исполнительной документацией. Условия: от 150 000 руб, корпоративный транспорт, карьерный рост. Если нет вопросов — расскажу о корпоративной культуре.
        state: Next
            event!: noMatch
            go!: /AskGames

    state: AskGames
        a: У нас 2 раза в год деловые игры-ассессменты — Business Cats, Civilization, Грузовики. Не компьютерные — оценка аналитики и лидерства. При трудоустройстве — Business Cats за 3 дня. Как вы к ним относитесь?
        state: Yes
            intent!: /да
            go!: /AskMotivation
        state: No
            intent!: /нет
            go!: /HandleGamesObj

    state: HandleGamesObj
        a: Это не только оценка — знакомство с руководством и коллегами, обмен опытом. При таком формате готовы рассмотреть?
        state: Yes
            intent!: /да
            go!: /AskMotivation
        state: No
            intent!: /нет
            go!: /DoneReject

    state: AskMotivation
        a: О системе мотивации: договор срочный 3 мес с продлением. Зарплата: 25% оклад + 50% за выполнение обязанностей + 25% KPI. Итого от 150 000 рублей. Вы рассматриваете такую систему?
        state: Yes
            intent!: /да
            go!: /AskOvertime
        state: No
            intent!: /нет
            go!: /HandleMotivObj

    state: HandleMotivObj
        a: Система нацелена на результат — чем лучше выполняете план, тем выше доход. При полном выполнении итого от 150 000 руб. При таком подходе готовы?
        state: Yes
            intent!: /да
            go!: /AskOvertime
        state: No
            intent!: /нет
            go!: /DoneReject

    state: AskOvertime
        a: В строительстве бывают задержки — ЧП, срочная сдача. У нас это редкость, всегда компенсируется оплачиваемыми днями к отпуску. Как относитесь к ненормированному графику?
        state: Yes
            intent!: /да
            go!: /AskCrime
        state: No
            intent!: /нет
            go!: /HandleOvertimeObj

    state: HandleOvertimeObj
        a: Это редкость — только при ЧП или срочной сдаче. Всегда компенсируется днями к отпуску. Устраивает такой подход?
        state: Yes
            intent!: /да
            go!: /AskCrime
        state: No
            intent!: /нет
            go!: /DoneReject

    state: AskCrime
        a: Подскажите, пожалуйста, у вас имеется судимость или проблемы с законом?
        state: No
            intent!: /нет
            go!: /AskIP
        state: Yes
            intent!: /да
            go!: /DoneReject

    state: AskIP
        a: У вас открыто ИП, самозанятость или другая форма предпринимательства?
        state: No
            intent!: /нет
            go!: /AskSalary
        state: Yes
            intent!: /да
            go!: /HandleIPObj

    state: HandleIPObj
        a: Обязательное условие ОЭЗ «Алабуга» — отсутствие действующих ИП. Если готовы закрыть — будем рады принять. Готовы закрыть?
        state: Yes
            intent!: /да
            go!: /AskSalary
        state: No
            intent!: /нет
            go!: /DoneReject

    state: AskSalary
        a: Каковы ваши зарплатные ожидания? От какой суммы рассматриваете предложения?
        state: Next
            event!: noMatch
            go!: /AskEmployment

    state: AskEmployment
        a: Сейчас работаете официально? Когда сможете написать заявление на увольнение? Потребуется ли отработка 2 недели?
        state: Next
            event!: noMatch
            go!: /AskCallback

    state: AskCallback
        a: Спасибо! Передаю ваше резюме руководителю, при положительном решении он свяжется в ближайшие дни. Когда вам удобно принять звонок?
        state: Yes
            intent!: /да
            go!: /DoneOK
        state: Next
            event!: noMatch
            go!: /DoneOK
        state: No
            intent!: /нет
            go!: /DoneReject

    state: DoneOK
        a: Зафиксировала! Спасибо за уделённое время. Передаю резюме руководителю — ожидайте звонок. Хорошего дня!

    state: DoneReject
        a: Понимаю, спасибо за уделённое время! Желаю удачи в поиске работы. До свидания!