const postPitch = async (pitch) => {
    let response = await fetch('http://localhost:4000/api/pitches', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(pitch)
    });
    return response;
}

const putPitch = async (pitch) => {
    let response = await fetch(`http://localhost:4000/api/pitches/${pitch.id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(pitch)
    });
    return response;
}

const fetchPitchesByAuthorId = async (authorId) => {
    let response = await fetch(`http://localhost:4000/api/pitches/authorid/${authorId}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    });
    return response; 
}

const fetchPitchesByGeneralEditorId = async (generalEditorId) => {
    let response = await fetch(`http://localhost:4000/api/pitches/generaleditor/${generalEditorId}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    });
    return response; 
}

const fetchPitchesByGenre = async (genre) => {
    let response = await fetch(`http://localhost:4000/api/pitches/genre/${genre}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    });
    return response; 
}