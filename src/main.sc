require: slotfilling/slotFilling.sc
  module = sys.zb-common

require: scripts/functions.js
require: scripts/config.js

theme: /

    # ─── СТАРТ ───────────────────────────────────────────────────────────────
    state: Start
        q!: $regex</start>
        script:
            speakFirst("greeting")
        a: {{$temp.gptResponse}}
        state: Yes
            intent!: /да
            go!: /AskRelocation
        state: No
            intent!: /нет
            go!: /Retention

    # ─── УДЕРЖАНИЕ ───────────────────────────────────────────────────────────
    state: Retention
        script:
            speak("retention")
        a: {{$temp.gptResponse}}
        state: Yes
            intent!: /да
            go!: /AskRelocation
        state: No
            intent!: /нет
            go!: /DoneReject

    # ─── ПЕРЕЕЗД ─────────────────────────────────────────────────────────────
    state: AskRelocation
        script:
            speak("ask_relocation")
        a: {{$temp.gptResponse}}
        state: Yes
            intent!: /да
            go!: /TellConditions
        state: No
            intent!: /нет
            go!: /HandleRelocationObj

    # ─── ВОЗРАЖЕНИЕ: ПЕРЕЕЗД ─────────────────────────────────────────────────
    state: HandleRelocationObj
        script:
            speak("handle_relocation_objection")
        a: {{$temp.gptResponse}}
        state: Yes
            intent!: /да
            go!: /TellConditions
        state: No
            intent!: /нет
            go!: /DoneReject

    # ─── УСЛОВИЯ ─────────────────────────────────────────────────────────────
    state: TellConditions
        script:
            speak("tell_conditions")
        a: {{$temp.gptResponse}}
        state: Yes
            intent!: /да
            go!: /TellPosition
        state: No
            intent!: /нет
            go!: /DoneReject

    # ─── ДОЛЖНОСТЬ И ЗАРПЛАТА ────────────────────────────────────────────────
    state: TellPosition
        script:
            speak("tell_position")
        a: {{$temp.gptResponse}}
        state: Yes
            intent!: /да
            go!: /AskExperience
        state: No
            intent!: /нет
            go!: /DoneReject

    # ─── ОПЫТ ────────────────────────────────────────────────────────────────
    state: AskExperience
        script:
            speak("ask_experience")
        a: {{$temp.gptResponse}}
        state: Next
            event!: noMatch
            go!: /PresentVacancy

    # ─── ПРЕЗЕНТАЦИЯ ВАКАНСИИ ────────────────────────────────────────────────
    state: PresentVacancy
        script:
            speak("present_vacancy")
        a: {{$temp.gptResponse}}
        state: Next
            event!: noMatch
            go!: /AskGames

    # ─── АССЕССМЕНТЫ ─────────────────────────────────────────────────────────
    state: AskGames
        script:
            speak("ask_games")
        a: {{$temp.gptResponse}}
        state: Yes
            intent!: /да
            go!: /AskMotivation
        state: No
            intent!: /нет
            go!: /HandleGamesObj

    # ─── ВОЗРАЖЕНИЕ: АССЕССМЕНТЫ ─────────────────────────────────────────────
    state: HandleGamesObj
        script:
            speak("handle_games_objection")
        a: {{$temp.gptResponse}}
        state: Yes
            intent!: /да
            go!: /AskMotivation
        state: No
            intent!: /нет
            go!: /DoneReject

    # ─── МОТИВАЦИЯ ───────────────────────────────────────────────────────────
    state: AskMotivation
        script:
            speak("ask_motivation")
        a: {{$temp.gptResponse}}
        state: Yes
            intent!: /да
            go!: /AskOvertime
        state: No
            intent!: /нет
            go!: /HandleMotivObj

    # ─── ВОЗРАЖЕНИЕ: МОТИВАЦИЯ ───────────────────────────────────────────────
    state: HandleMotivObj
        script:
            speak("handle_motivation_objection")
        a: {{$temp.gptResponse}}
        state: Yes
            intent!: /да
            go!: /AskOvertime
        state: No
            intent!: /нет
            go!: /DoneReject

    # ─── НЕНОРМИРОВАННЫЙ ГРАФИК ──────────────────────────────────────────────
    state: AskOvertime
        script:
            speak("ask_overtime")
        a: {{$temp.gptResponse}}
        state: Yes
            intent!: /да
            go!: /AskCrime
        state: No
            intent!: /нет
            go!: /HandleOvertimeObj

    # ─── ВОЗРАЖЕНИЕ: ГРАФИК ──────────────────────────────────────────────────
    state: HandleOvertimeObj
        script:
            speak("handle_overtime_objection")
        a: {{$temp.gptResponse}}
        state: Yes
            intent!: /да
            go!: /AskCrime
        state: No
            intent!: /нет
            go!: /DoneReject

    # ─── СУДИМОСТЬ ───────────────────────────────────────────────────────────
    state: AskCrime
        script:
            speak("ask_crime")
        a: {{$temp.gptResponse}}
        state: No
            intent!: /нет
            go!: /AskIP
        state: Yes
            intent!: /да
            go!: /DoneReject

    # ─── ИП / САМОЗАНЯТОСТЬ ──────────────────────────────────────────────────
    state: AskIP
        script:
            speak("ask_ip")
        a: {{$temp.gptResponse}}
        state: No
            intent!: /нет
            go!: /AskSalary
        state: Yes
            intent!: /да
            go!: /HandleIPObj

    # ─── ВОЗРАЖЕНИЕ: ИП ──────────────────────────────────────────────────────
    state: HandleIPObj
        script:
            speak("handle_ip_objection")
        a: {{$temp.gptResponse}}
        state: Yes
            intent!: /да
            go!: /AskSalary
        state: No
            intent!: /нет
            go!: /DoneReject

    # ─── ЗАРПЛАТНЫЕ ОЖИДАНИЯ ─────────────────────────────────────────────────
    state: AskSalary
        script:
            speak("ask_salary")
        a: {{$temp.gptResponse}}
        state: Next
            event!: noMatch
            go!: /AskEmployment

    # ─── ТЕКУЩАЯ ЗАНЯТОСТЬ ───────────────────────────────────────────────────
    state: AskEmployment
        script:
            speak("ask_employment")
        a: {{$temp.gptResponse}}
        state: Next
            event!: noMatch
            go!: /AskCallback

    # ─── ВРЕМЯ ЗВОНКА ────────────────────────────────────────────────────────
    state: AskCallback
        script:
            speak("ask_callback")
        a: {{$temp.gptResponse}}
        state: Next
            event!: noMatch
            go!: /DoneOK

    # ─── УСПЕШНОЕ ЗАВЕРШЕНИЕ ─────────────────────────────────────────────────
    state: DoneOK
        script:
            speak("done_ok")
        a: {{$temp.gptResponse}}

    # ─── ОТКАЗ ───────────────────────────────────────────────────────────────
    state: DoneReject
        script:
            speak("done_reject")
        a: {{$temp.gptResponse}}
