
/**
 * For more on client-side browser handling: 
 * https://developer.mozilla.org/en-US/docs/Web/API/File/Using_files_from_web_applications
 */


const onPitchModalLoad = () => {
    const fileSelect = document.getElementById("fileSelect"),
        fileElem = document.getElementById("fileElem"),
        fileList = document.getElementById("fileList");

    fileSelect.addEventListener("click", function (e) {
        if (fileElem) {
            fileElem.click();
        }
        e.preventDefault(); // prevent navigation to "#"
    }, false);

    fileElem.addEventListener("change", handleFiles, false);
}



const upload = async (file) => {
    let response = await fetch(`http://localhost:4000/upload/:${file.name}`, {
        method: 'POST',
        body: file
    })

    if (response.status === 200) {
        alert("successfully uploaded files");
    } else {
        alert("Internal system error: could not post files");
    }

};

function handleFiles() {
    if (!this.files.length) {
        fileList.innerHTML = "<p>No files selected!</p>";
    } else {
        fileList.innerHTML = "";
        const list = document.createElement("ul");
        fileList.appendChild(list);


        for (let file of this.files) {
            const li = document.createElement("li");
            list.appendChild(li);

            const img = document.createElement("img");
            img.src = URL.createObjectURL(file);
            img.height = 60;
            img.onload = function () {
                URL.revokeObjectURL(this.src);
            }
            // li.appendChild(img);
            const info = document.createElement("span");
            info.innerHTML = file.name;
            li.appendChild(info);

            // Attempt to upload file
            upload(file);
        }
    }
}



