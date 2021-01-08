const postComment = async (comment) => {
    let response = await fetch('http://localhost:4000/api/comments', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(comment)
    });
    return response;
}

const fetchCommentsByRequestId = async (requestId) => {
    let response = await fetch(`http://localhost:4000/api/comments/requestid/${requestId}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    });
    return response; 
}