
$(function () {
    $('[data-toggle="tooltip"]').tooltip({ trigger: 'hover' })
})


const loadModalWithDecisionExplanationPrompt = (pitchId, type) => {

    const remainingChar = 2000;

    const updateRemainingChar = (event) => remainingChar - event.target.value.length;

    document.getElementById('pitch-modal-body').innerHTML =
        `<p> 
            You are making a decision of type ${type} for pitch ${pitchId}. 
            Please provide an explanation (${remainingChar} characters remaining).
         <p>
        <div>
        <div>
        <textarea id="explanation-draft-area" class="form-control" rows="8" />
        </textarea>
        </div>
        <div class="btn-group" role="group" aria-label="Basic mixed styles example">
            <button type="button" class="btn btn-primary" onClick="saveDecision(${pitchId}, \'${type}\')" 
            data-dismiss="modal" aria-label="Close" >
            Save
            </button>
            <button type="button" class="btn btn-danger" data-dismiss="modal" aria-label="Close" >
            Cancel
            </button>
        </div>
    `;
}

const loadModalWithRequestPrompt = (targetId, targetType) => {

    const remainingChar = 2000;

    const updateRemainingChar = (event) => remainingChar - event.target.value.length;

    document.getElementById('pitch-modal-body').innerHTML =
        `<p> 
      You are opening a request for a <strong>${targetType}</strong> with ID: <strong>${targetId}</strong>. 
      Requests can be opened to solicit additional information on a pitch, draft, or decision. Futhermore, 
      Requests can also be opened to communicate editorial suggestions to the author. 
      Please provide an initial comment for this request (${remainingChar} characters remaining).
   <p>
  <div>
  <div>
  <textarea id="request-draft-area" class="form-control" rows="8" />
  </textarea>
  </div>
  <div class="btn-group" role="group" aria-label="Basic mixed styles example">
      <button type="button" class="btn btn-primary" onClick="postRequestWithInitialComment(${targetId}, \'${targetType}\')" 
      data-dismiss="modal" aria-label="Close" >
      Save
      </button>
      <button type="button" class="btn btn-danger" data-dismiss="modal" aria-label="Close" >
      Cancel
      </button>
  </div>
`;
}

const createPitchCard = (pitch) => {


    let controlButtons, decisionHistory;
    if (currentUser.role.name.toUpperCase() === 'AUTHOR') {
        controlButtons =
            `<div class="btn-group" role="group" aria-label="Basic mixed styles example">
                <button type="button" class="btn btn-success" data-toggle="modal" 
                data-target="#exampleModal" onClick="populateModalWithData(${pitch.id})">
                    Open
                </button>
                <button type="button" class="btn btn-danger" onClick="deletePitch(${pitch.id})">Delete</button>
            </div>`;

        decisionHistory = '';

    } else {
        controlButtons =
            `<div class="btn-group" role="group" aria-label="Basic mixed styles example">
            <button type="button" class="btn btn-success" 
                onClick="loadModalWithDecisionExplanationPrompt(${pitch.id}, \'approval\')"
                data-toggle="modal"
                data-target="#exampleModal"
                >Approve
            </button>
            <button type="button" class="btn btn-info" data-toggle="modal" 
            data-target="#exampleModal" onClick="populateModalWithData(${pitch.id})">
                Open
            </button>
            <button type="button" class="btn btn-warning" 
                onClick="loadModalWithRequestPrompt(${pitch.id}, \'pitch\')"
                data-toggle="modal"
                data-target="#exampleModal"
                >Open Request
            </button>
            <button type="button" class="btn btn-danger" 
                onClick="loadModalWithDecisionExplanationPrompt(${pitch.id}, \'rejection\')"
                data-toggle="modal"
                data-target="#exampleModal"
                >Reject
            </button>
        </div>`;
        decisionHistory = `
            <strong>Decision History</strong>
                <div id="pitch-card-${pitch.id}-decision-section" class="card-decision-section">
            </div>`;

    }



    let generic_image_src = `https://thumbs.dreamstime.com/t/white-feather-quill-pen-glass-inkwell-old-glass-ink-pen-feather-well-quill-image-106467206.jpg`;


    let progressBarColorClass;
    switch (pitch.status.name.toUpperCase()) {
        case "APPROVED":
            progressBarColorClass = 'bg-success';
            break;
        case "REJECTED":
            progressBarColorClass = 'bg-danger';
            break;
        case "PENDING-EDITOR-REVIEW":
            progressBarColorClass = 'bg-warning';
            break;
        case "PENDING-AUTHOR-REVIEW":
            progressBarColorClass = 'bg-info';
            break;
    }

    let progressBarWidthValue;
    switch (pitch.stage.name.toUpperCase()) {
        case "GENRE REVIEW":
            progressBarWidthValue = 25;
            break;
        case "GENERAL REVIEW":
            progressBarWidthValue = 50;
            break;
        case "SENIOR REVIEW":
            progressBarWidthValue = 75;
            break;
        case "FINAL REVIEW":
            progressBarWidthValue = 100;
            break;
    }

    const pitchCard =
        `<div id="pitch-card-${pitch.id}" class="col-md-4">
  <div class="card">
    <img src="${generic_image_src} class="card-img-top" alt="..." />
    <div class="card-body">
      <h5 class="card-title"> ${pitch.title || 'no title found'} </h5>
      <p class="card-text">
        ${pitch.tagline.length > 100 ? `${pitch.tagline.slice(0, 100)}...` : pitch.tagline}
      </p>
    </div>
    <div class="progress">
      <div class="progress-bar ${progressBarColorClass}" role="progressbar" style="width: ${progressBarWidthValue}%" aria-valuenow="${progressBarWidthValue}" aria-valuemin="0" aria-valuemax="100"
      data-toggle="tooltip" data-placement="top" title="Status: ${pitch.status.name} | Stage: ${pitch.stage.name}"
      ></div>
      </div>
    <ul class="list-group list-group-flush">
      <li class="list-group-item">Genre: ${pitch.genre.name || 'genre unspecified'} </li>
      <li class="list-group-item">Form: ${pitch.form.name || 'form unspecified'} </li>
      <li class="list-group-item">Completion Deadline: ${pitch.completionDate || 'completion date unspecified'} </li>
    </ul>
    <div class="card-body">
        ${decisionHistory}
        ${controlButtons}
    </div>
  </div>
</div>`;

    $(function () {
        $('[data-toggle="tooltip"]').tooltip({ trigger: 'hover' })
        $('[data-toggle="tooltip"]').tooltip({ animation: 'true' })
    })


    return pitchCard;
}