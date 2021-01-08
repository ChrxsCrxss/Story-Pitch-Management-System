

const fetchRequests = async (id) => {
    let resposne = await fetch(`http://localhost:4000/api/requests/personid/${id}`, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    });
    return resposne;
}

const closeRequest = async (id) => {

    let response = await fetch(`http://localhost:4000/api/requests/close/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    }); 
    return response; 
}

const putUpdatedRequest = async (request) => {

    let response = await fetch(`http://localhost:4000/api/requests`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(request)
    }); 
    return response; 
}


const postRequest = async (request) => {
    let response = await fetch(`http://localhost:4000/api/requests`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(request)
    }); 
    return response; 
}