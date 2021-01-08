const createDecisionCard = (decision) =>
    `<div class="decision-card" class="card-body"
    onClick="loadModalWithRequestPrompt(${decision.id}, \'decision\')"
    data-toggle="modal"
    data-target="#exampleModal"
    >
        <strong>${decision.id} @${decision.editorId}</strong>
        <p> ${decision.decisionType.name} </p>
        <p>${decision.explanation}</p>
    </div> 
    <hr>   
    `; 