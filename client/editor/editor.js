/**
 * This section gets the currentUser object from local broswer storage
 */

if (!sessionStorage.getItem("currentUser")) {
    populateStorage();
}
let currentUser = JSON.parse(sessionStorage.getItem("currentUser"));
console.log(typeof (currentUser));
console.log(currentUser);

document.getElementById('main-header').innerText = `Welcome Back, ${currentUser.username}!`;

const logCurrentUser = () => {
    console.log(currentUser);
}


/*
 onload, we want to fetch ALL open requests associated with the user. 
*/

/**
 * Dictionary that stores all the current user's
 * request. The key is request id, the value is the request object 
 */
const requestMap = new Map();
const pitchMap = new Map();
const commentMap = new Map();
const personMap = new Map();
const decisionMap = new Map();
const draftMap = new Map(); 
let highPriorityPithches = 0; 

const handleCloseRequest = async (id) => {

    let response = await closeRequest(id);

    if (response.status === 200) {

        // Remove from outgoing requests if currentUser is sender, 
        // otherwise remove from incoming requests
        if (requestMap.get(id).senderId === currentUser.id) {
            document.getElementById('outgoing-requests-display-selection').
                removeChild(document.getElementById(`request-card-${id}`));
        } else {
            document.getElementById('incoming-requests-display-selection').
                removeChild(document.getElementById(`request-card-${id}`));
        }
        requestMap.delete(id);

    } else {
        alert('Technical difficulties: unable to close request');
    }
};


const updateRequest = async (id) => {

    let response = await putUpdatedRequest(requestMap.get(id));

    if (response.status === 200) {
        alert("Udpated Request recieved by system. We'll let you know if there are any updates!");
        document.getElementById('incoming-requests-display-selection').
            removeChild(document.getElementById(`request-card-${id}`));
        requestMap.delete(id);
    } else {
        alert("Could not update request");
    }
}

const saveComment = async (id, selectorString) => {

    const newCommentContent = document.getElementById(selectorString).value;
    if (newCommentContent.length === 0) {
        alert('Cannot save empty comment');
        return;
    }
    const newComment = {
        requestId: id,
        commentorId: currentUser.id,
        content: newCommentContent
    };

    let response = await postComment(newComment);

    if (response.status === 200) {
        const comment = JSON.parse(await response.json());
        document.getElementById(`request-card-${comment.requestId}-comment-section`)
            .innerHTML += createCommentCard(comment);

        /**
         * This block sometimes fails if a new request card is being added. However, it's
         * a benign failure, so we will wrap it in a try-catch block and print the error
         */
        try {
            document.getElementById(`request-card-${comment.requestId}-comment-section`).
                removeChild(document.getElementById(`draft-comment-response-section-${comment.requestId}`));
        } catch (e) {
            console.warn(e);
        }

    } else {
        alert("Internal System error: Unable to save comment");
        console.log(resposne);
    }

}


const handleRespond = (id) => {
    document.getElementById(`request-card-${id}-comment-section`).innerHTML +=
        `<div id="draft-comment-response-section-${id}">
        <textarea 
        class="form-control" 
        id="section-${id}-draft-comment"
        rows="4"
        ></textarea>
        <button 
        type="button" class="btn btn-primary"
        onclick='saveComment(${id}, \`section-${id}-draft-comment\`)'
        class 
        >
        Save
        </button>
        `;
}

const saveDecision = async (pitchId, type) => {

    alert(`making a decision of type ${type} for pitch ${pitchId}`);

    let pitchStage = pitchMap.get(pitchId).stage.name.toUpperCase();
    let decisionType = {};
    if (pitchStage === "FINAL REVIEW") {
        decisionType.name = `draft-${type}`;
        decisionType.id = type === "approval" ? 3 : 4;
    } else {
        decisionType.name = `pitch-${type}`
        decisionType.id = type === "approval" ? 1 : 2;
    }

    const decision = {
        editorId: currentUser.id,
        pitchId: pitchId,
        decisionType: decisionType,
        explanation: document.getElementById('explanation-draft-area').value
    };

    console.log(decision);

    let response = await postDecision(decision);

    if (response.status === 200) {
        let decision = JSON.parse(await response.json());
        decisionMap.set(decision.id, decision);
        console.log(decision);
    } else {
        console.log("Intenal system error: unable to post decision");
    }

}


/**
 * Callback method to populate modal with data. The id parameter specifies 
 * Is used to get get the corresponding pitch data from a hashmap. If id is default 
 * value of null, this indicates that a user wants to create a new pitch, and none
 * of the input values will be populated. 
 */
const populateModalWithData = async (id) => {

    if (id !== null) {
        document.getElementById("pitch-modal-body").innerHTML = await createPitchModalCard(pitchMap.get(id));
        document.getElementById("modal-btn-section").innerHTML = `
          <button type="button" class="btn btn-warning" onClick="updatePitch(${id})">Commit Pitch</button>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
          `;
    } else {
        throw Error("No pitchId specified")
    }

};


const postRequestWithInitialComment = async (targetId, targetType) => {

    let targetDraftId = targetPitchId = targetDecisionId = -1;

    let recieverId;
    if (targetType === "pitch") {
        recieverId = pitchMap.get(targetId).authorId;
        targetPitchId = targetId;
    } else if (targetType === "decision") {
        recieverId = decisionMap.get(targetId).editorId; 
        targetDecision = targetId; 
    }

    newRequest = {
        senderId: currentUser.id,
        recieverId: recieverId,
        targetDraftId: targetDraftId,
        targetPitchId: targetDraftId,
        targetDecisionId: targetDecisionId
    }

    console.log(newRequest);

    let response = await postRequest(newRequest);

    if (response.status === 200) {
        let request = JSON.parse(await response.json());
        console.log(request);
        requestMap.set(request.id, request);
        await createRequestCard(request);
        saveComment(request.id, 'request-draft-area')
    } else {
        console.log("Internal system error: unable to save request.")
    }

}



/**
 * 
 * @param {*} id The id of the current user
 */
const getRequests = async (id) => {

    let response = await fetchRequests(id);
    if (response.status === 200) {
        console.log(response);
        let requests = JSON.parse(await response.json());
        // This removes the spinners. Maybe we should check and remove in loadRequests? 
        document.getElementById("outgoing-requests-display-selection").innerHTML = "";
        document.getElementById("incoming-requests-display-selection").innerHTML = "";
        for (const request of requests) {
            requestMap.set(request.id, request);
            await createRequestCard(request);
            let response = await fetchCommentsByRequestId(request.id);
            if (response.status == 200) {
                let comments = JSON.parse(await response.json());
                for (let comment of comments) {
                    commentMap.set(comment.id, comment);
                    document.getElementById(`request-card-${comment.requestId}-comment-section`)
                        .innerHTML += createCommentCard(comment);
                }
            } else {
                alert(`unable to load comments for request ${request.id}`);
            }

        }

    } else {
        alert("could not fetch requests");
    }

}
getRequests(currentUser.id);

const getPitchesByGenreAndGeneralEditor = async () => {

    let response = await fetchPitchesByGeneralEditorId(currentUser.id);
    if (response.status === 200) {
        let pitches = JSON.parse(await response.json());
        for (const pitch of pitches) {
            pitchMap.set(pitch.id, pitch);

            if ( pitch.priorityLevel.name === "high") {
                document.getElementById("high-priority-pitch-data-display-row").innerHTML 
                += createPitchCard(pitch);
                highPriorityPithches++;
            } else {
                document.getElementById("general-pitch-data-display-row").innerHTML 
                += createPitchCard(pitch);
            }


            if (currentUser.role.name.toUpperCase().includes("EDITOR")) {
                let response = await getDecisionsByPitchIds(pitch.id);
                if (response.status === 200) {
                    let decisions = JSON.parse( await response.json());
                    console.log(decisions);
                    for (const decision of decisions) {
                        decisionMap.set(decision.id, decision);
                        document.getElementById(`pitch-card-${pitch.id}-decision-section`).innerHTML 
                        += createDecisionCard(decision);
                    }
                } else {
                    console.log("Internal system error: could not load decisions by pitch id");
                }
            }
        }
    } else {
        alert("Internal system error: could not load pitches by general id");
    }

    for (const genre of currentUser.genres) {
        let response = await fetchPitchesByGenre(genre.name);
        if (response.status === 200) {
            let pitches = JSON.parse(await response.json());
            for (const pitch of pitches) {
                pitchMap.set(pitch.id, pitch);

                if ( pitch.priorityLevel.name === "high") {
                    document.getElementById("high-priority-pitch-data-display-row").innerHTML 
                    += createPitchCard(pitch);
                    highPriorityPithches++
                } else {
                    document.getElementById("genre-pitch-data-display-row").innerHTML 
                    += createPitchCard(pitch);
                }

                if (currentUser.role.name.toUpperCase().includes("EDITOR")) {
                    let response = await getDecisionsByPitchIds(pitch.id);
                    if (response.status === 200) {
                        let decisions = JSON.parse( await response.json());
                        console.log(decisions);
                        for (const decision of decisions) {
                            decisionMap.set(decision.id, decision);
                            document.getElementById(`pitch-card-${pitch.id}-decision-section`).innerHTML 
                            += createDecisionCard(decision);
                        }
                    } else {
                        console.log("Internal system error: could not load decisions by pitch id");
                    }
                }

            }
        } else {
            alert("Internal system error: could not load pitches by genre");
        }
    }
}
getPitchesByGenreAndGeneralEditor();
