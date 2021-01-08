

const isSenderAndEditor = (request) => {
    return request.senderId === currentUser.id &&
        currentUser.role.name.toUpperCase().includes('EDITOR');
}

const createRequestCard = (request) => {

    // Only senders should be able to close requets. So we expose the close function
    // on if the current user is the sender. Dynamic rendering is possible using 
    // template literals and the ternary statement
    let controlButtons;
    if (isSenderAndEditor(request)) {
        controlButtons =
            `<div class="btn-group" role="group" aria-label="Basic mixed styles example">
            <button type="button" class="btn btn-primary" onClick="handleRespond(${request.id})">Add Comment</button>
            <button type="button" class="btn btn-danger" onClick="handleCloseRequest(${request.id})">Close</button>
        </div>`
    } else {
        controlButtons =
            `<div class="btn-group" role="group" aria-label="Basic mixed styles example">
            <button type="button" class="btn btn-primary" onClick="handleRespond(${request.id})">Add Comment</button>
        </div>`
    }

    const requestCardTemplate =
        `<div id="request-card-${request.id}" class="col-md-4">
            <div class="card">
              <div class="card-body">
                <h5 class="card-title">${request.id}</h5>
                <p>From: ${request.senderId === currentUser.id ? 'Me' : request.senderId}</p>
                <p> To: ${request.recieverId === currentUser.id ? 'Me' : request.recieverId} </p>
                <p>Status: ${request.status.name}</p>
                <hr />
                <div>
                  <h6>Comments</h6>
                </div>
                ... ... ... 
                <div id="request-card-${request.id}-comment-section" class="card-comment-section">
                <div id="request-card-${request.id}-new-comment-section" class="card-comment-section"></div>
                </div>
                    ${controlButtons}
                </div>
        </div>`;


    if (request.senderId === currentUser.id) {
        document.getElementById("outgoing-requests-display-selection").innerHTML += requestCardTemplate;
    } else if (request.recieverId === currentUser.id) {
        document.getElementById("incoming-requests-display-selection").innerHTML += requestCardTemplate;
    } else {
        alert('currentUser is neither sender or reciever of request');
    }

    return Promise.resolve;
}