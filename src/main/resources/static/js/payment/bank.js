document.addEventListener("DOMContentLoaded", () => {
    const output = document.getElementById("output");

    // Button event listeners
    document.querySelector(".success").addEventListener("click", () => {
        output.textContent = "Payment successful!";
        output.style.color = "green";
    });

    document.querySelector(".reject").addEventListener("click", () => {
        output.textContent = "Payment rejected.";
        output.style.color = "red";
    });

    document.querySelector(".error").addEventListener("click", () => {
        output.textContent = "An error occurred during payment.";
        output.style.color = "orange";
    });
});

function redirectTo(success) {
    // Get the current URL
    const urlParams = new URLSearchParams(window.location.search);
    // Get a specific parameter value
     let redirect = urlParams.get('redirect');
    redirect = decodeURIComponent(redirect);
    // if redirect includes ? then add & else add ?
    if (redirect.includes('?')) {
        redirect += "&success=" + success;
    } else {
        redirect += "?success=" + success;
    }
    // redirect
    window.location.href = redirect;
}