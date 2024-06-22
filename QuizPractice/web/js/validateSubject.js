function validateSubjectName() {
    const nameInput = document.getElementById('name');
    const errorMessage = document.getElementById('error-message');
    const subjectName = nameInput.value;

    // Regular expression to allow only letters and spaces (minimum 3 characters, maximum 50 characters)
    const regex = /^[A-Za-z0-9\s]{3,50}$/;

    if (regex.test(subjectName.trim())) {
        errorMessage.style.display = 'none';
        // Here you can proceed with form submission or further actions
    } else {
        errorMessage.style.display = 'block';
    }
}

function validateDescription() {
    const descriptionTextarea = document.getElementById('description');
    const errorMessage = document.getElementById('error-message-description');
    const description = descriptionTextarea.value.trim(); // Trim whitespace

    if (description === "") {
        errorMessage.style.display = 'block';
    } else {
        errorMessage.style.display = 'none';
    }
}


