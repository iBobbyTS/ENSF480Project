function signin() {

    const signinForm = document.getElementById('signin-form');
    const emailInput = document.getElementById('email');
    const passwordInput = document.getElementById('password');
    const emailError = document.getElementById('email-error');
    const passwordError = document.getElementById('password-error');
    const serverError = document.getElementById('server-error');

    // Clear error messages
    emailError.textContent = '';
    passwordError.textContent = '';
    serverError.textContent = '';

    const email = emailInput.value.trim();
    const password = passwordInput.value.trim();

    // Client-side validation
    if (!email) {
        emailError.textContent = 'Email is required.';
        return;
    }
    if (!password) {
        passwordError.textContent = 'Password is required.';
        return;
    }

    // Send POST request to backend using post_json
    post_json('/api/sign-in', {'email': email, 'password': sha1(password)}, (responseText) => {
        const data = JSON.parse(responseText);

        // Handle the response
        if (data.success === 0) {
            // Redirect to home page on success
            window.location.href = '/';
        } else {
            // Display server-side error message
            serverError.textContent = data.message || 'Invalid login credentials.';
        }
    });
}
