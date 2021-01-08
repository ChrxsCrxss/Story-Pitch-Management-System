const authorRedirectURL = `/Users/majestikmind/Revature_Training/2011-nov9-usf/Project1/client/author/authorDashBoard.html`;
const editorRedirectURL = `/Users/majestikmind/Revature_Training/2011-nov9-usf/Project1/client/editor/editorDashBoard.html`;

const auth = async (event) => {

    event.preventDefault();

    let inputUsername = document.getElementById('inputUsername').value;
    let inputPassword = document.getElementById('inputPassword').value;
    const user = {
        id: 0,
        username: inputUsername,
        password: inputPassword
    };

    let response = await checkUser(user);

    if (response.status == 200) {
        alert('Authentication succeeded');
        let currentUser = await response.json();
        sessionStorage.setItem("currentUser", currentUser);
        currentUser = JSON.parse(currentUser);
        console.log(currentUser);
        switch (currentUser.role.name.toUpperCase()) {
            case "AUTHOR":
                window.location.href = authorRedirectURL;
                break;
            case "ASSISTANT EDITOR":
            case "SENIOR EDITOR":
                window.location.href = editorRedirectURL;
                break;
            default:
                alert('something went wrong :(');
                break;
        }
    } else {
        alert('Authentication failed');
    }
};