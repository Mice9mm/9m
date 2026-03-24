// ─── YANDEX GPT API ──────────────────────────────────────────────────────────
function callYandexAPI(systemText, history, userText, temperature) {
    var msgs = [{ role: "system", text: systemText }];
    if (history && history.length > 0) {
        for (var i = 0; i < history.length; i++) {
            msgs.push({ role: history[i].role === "assistant" ? "assistant" : "user", text: history[i].text });
        }
    }
    if (userText) msgs.push({ role: "user", text: userText });

    var body = {
        modelUri: "gpt://" + CONFIG.FOLDER_ID + "/" + CONFIG.MODEL,
        completionOptions: { stream: false, temperature: temperature, maxTokens: "400" },
        messages: msgs
    };

    var response = $http.query({
        url: CONFIG.YANDEX_API_URL,
        method: "POST",
        dataType: "JSON",
        body: JSON.stringify(body),
        headers: {
            "Authorization": "Bearer " + CONFIG.IAM_TOKEN,
            "x-folder-id": CONFIG.FOLDER_ID,
            "Content-Type": "application/json"
        }
    });

    if (response && response.data && response.data.result) {
        return response.data.result.alternatives[0].message.text;
    }
    return "Извините, произошла техническая ошибка. Повторите попытку.";
}

// ─── BUILD PROMPT ─────────────────────────────────────────────────────────────
function buildPrompt(facts, goal) {
    var goalScript = GOAL_SCRIPTS[goal] || "";
    var factsText = "";
    if (facts && Object.keys(facts).length > 0) {
        var lines = [];
        for (var k in facts) { if (facts[k]) lines.push(k + ": " + facts[k]); }
        if (lines.length > 0) factsText = "\n\nФАКТЫ О КАНДИДАТЕ:\n" + lines.join("\n");
    }
    return SYSTEM_BASE + factsText + "\n\nТЕКУЩАЯ ЗАДАЧА:\n" + goalScript + "\n\nБАЗА ЗНАНИЙ:\n" + KNOWLEDGE_BASE;
}

// ─── INIT SESSION ─────────────────────────────────────────────────────────────
function initSession() {
    if (!$session.facts)   $session.facts   = {};
    if (!$session.history) $session.history = [];
}

// ─── SPEAK FIRST (приветствие — без user message) ─────────────────────────────
function speakFirst(goal) {
    $session.facts   = {};
    $session.history = [];
    var resp = callYandexAPI(buildPrompt($session.facts, goal), [], "", CONFIG.TEMP_RESPONSE);
    $session.history.push({ role: "assistant", text: resp });
    $temp.gptResponse = resp;
    return resp;
}

// ─── SPEAK (вызов GPT + сохранение в историю) ────────────────────────────────
function speak(goal) {
    var userMsg = $request.query || "";
    if (userMsg) $session.history.push({ role: "user", text: userMsg });
    var resp = callYandexAPI(buildPrompt($session.facts, goal), $session.history, "", CONFIG.TEMP_RESPONSE);
    $session.history.push({ role: "assistant", text: resp });
    if ($session.history.length > 20) $session.history = $session.history.slice(-20);
    $temp.gptResponse = resp;
    return resp;
}
