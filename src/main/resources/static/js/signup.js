function signup() {
    const signupForm = document.getElementById('signup-form');
    const passwordInput = document.getElementById('password');
    const repeatPasswordInput = document.getElementById('repeat-password');
    const emailInput = document.getElementById('email');

    const passwordError = document.getElementById('password-error');
    const repeatPasswordError = document.getElementById('repeat-password-error');
    const emailError = document.getElementById('email-error');

    let valid= true;

    // Clear previous error messages
    passwordError.textContent = '';
    repeatPasswordError.textContent = '';
    emailError.textContent = '';

    // Email format check
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(emailInput.value)) {
        emailError.textContent = 'Invalid email format.';
        valid = false;
    }

    // Password length check
    if (passwordInput.value.length < 8) {
        passwordError.textContent = 'Password must be at least 8 characters long.';
        valid = false;
    }

    // Password match check
    if (passwordInput.value !== repeatPasswordInput.value) {
        repeatPasswordError.textContent = 'Passwords do not match.';
        valid = false;
    }

    if (!valid) {
        return; // Stop if validation fails
    }

    // Proceed with form submission using post_json
    const userData = {
        email: emailInput.value,
        password: sha1(passwordInput.value)
    };

    post_json('/api/sign-up', userData, (responseText) => {
        const response = JSON.parse(responseText);
        if (response.status === 0) {
            window.location.href = '/';
        } else {
            // Failure: show notification
            emailError.textContent = 'Email already exists.';
        }
    });
}