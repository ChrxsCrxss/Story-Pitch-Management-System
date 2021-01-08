


const getDraftForPitch = async (pitch) => {
    let response = await fetchDraftByPitchId(pitch.id);
    if (response.status === 200) {
        let draft = JSON.parse(await response.json());
        return draft;
    } else {
        alert("Internal system error: could not get draft by id.");
    }
}


const createNewDraft = (pitch) => {
    return {
        authorId: currentUser.id,
        pitchId: pitch.id,
        status: {
            id: 3,
            name: 'pending-editor-review'
        },
        content: ''
    };
}

const createDraftSpace = (draft) =>
    `
<h4> Draft </h4>
<hr>
<textarea
class="form-control" 
id="draft-input-form"
name="draft" 
onInput="handleDraftContentChange(${draft.pitchId})" 
rows="16">
${draft.content}
</textarea>
<button type="button" class="btn btn-success" data-dismiss="modal" onClick="commitDraft(${draft.pitchId})">Commit Draft</button>
`;


const createPitchModalCard = async (pitch) => {


    let draftSpace = ``;
    if (pitch.stage.name.toUpperCase() === "FINAL REVIEW") {
        // First, check the store
        let draftFromCache = null;
        draftFromCache = draftMap.get(pitch.id);

        // If the draft isn't saved locally, reach out to server
        if (!draftFromCache) {
            let draftFromServer = await getDraftForPitch(pitch);
            // If the server returns a draft
            if (draftFromServer) {
                draftMap.set(draftFromServer.pitchId, draftFromServer);
                console.log(draftFromServer);
                draftSpace = createDraftSpace(draftFromServer);
            } else /* Server did not return draft */ {
                let newDraft = createNewDraft(pitch);
                draftMap.set(newDraft.pitchId, newDraft);
                draftSpace = createDraftSpace(newDraft);
            }
        } else /* Draft saved locally */ {
            draftSpace = createDraftSpace(draftFromCache);
        }
    }

    let attachmentInput = '';
    if (currentUser.role.name.toUpperCase() === "AUTHOR") {
        attachmentInput =
        `
        <input type="file" id="fileElem" multiple accept="*" style="display:none">
        <a href="#" id="fileSelect">Select some files</a>
        <div id="fileList">
        <p>No files selected!</p>
        </div>
        <div id='preview'>
        `
    }

    let pitchModalCard =
        `
        <h4> Pitch Info </h4>
        <hr>
    <p>
        Status: ${pitch.status.name} |
        Stage: ${pitch.stage.name} |
        Priority: ${pitch.priorityLevel.name}
    <p>
    <p>
        Created: ${pitch.createdTime} |
        LastModified: ${pitch.lastModifiedTime} |
        Due: ${pitch.deadline}
    <p>
    <input 
    type="text" 
    class="form-control" 
    name="title" 
    value="${pitch.title}"
    onInput="handlePitchContentChange(${pitch.id})"
    >
    <input 
    type="text" 
    class="form-control" 
    name="tagline" 
    value="${pitch.tagline}"
    onInput="handlePitchContentChange(${pitch.id})"
    >
    <h5>Genre: ${pitch.genre.name} </h5>
    <h5>Form: ${pitch.form.name} </h5>
    ${draftSpace}

    <h4>Attachments</h4>
    <hr>
    ${attachmentInput}
    `;

    return pitchModalCard;




}

