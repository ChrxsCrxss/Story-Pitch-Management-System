
const checkUser = async (user) => {
    let response = await fetch('http://localhost:4000/api/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(user)
    });
    return response;
}