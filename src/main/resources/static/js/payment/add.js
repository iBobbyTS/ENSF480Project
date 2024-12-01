function submitPaymentMethod() {
    const cardNumber = document.getElementById("cardNumber").value.trim();
    const expiryDate = document.getElementById("expiryDate").value.trim();
    const cvc = document.getElementById("cvc").value.trim();
    const name = document.getElementById("name").value.trim();

    if (!cardNumber || !expiryDate || !cvc || !name) {
        alert("Please fill in all fields.");
        return;
    }

    const paymentInfo = {
        cardNumber: cardNumber,
        expiryDate: expiryDate,
        cvc: cvc,
        name: name,
    };

    post_json("/api/add-payment-method", paymentInfo, (responseText) => {
        const response = JSON.parse(responseText);
        if (response.success) {
            // Get the current URL
            const urlParams = new URLSearchParams(window.location.search);
            // Get a specific parameter value
            const redirect = urlParams.get('redirect');
            if (redirect) {
                window.location.href = redirect;
            } else {
                window.location.href = "/";
            }
        } else {
            alert("Error: " + response.message);
        }
    });
}
