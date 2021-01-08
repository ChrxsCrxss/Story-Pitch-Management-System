const postDraft = async (draft) => {
    let response = await fetch('http://localhost:4000/api/drafts', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(draft)
    });
    return response;
}

const putDraft = async (draft) => {
    let response = await fetch(`http://localhost:4000/api/drafts`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(draft)
    });
    return response;
}

const fetchDraftByPitchId = async (pitchId) => {
    let response = await fetch(`http://localhost:4000/api/drafts/pitchid/${pitchId}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    });
    return response; 
}