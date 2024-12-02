function downloadStringAsFile(giftcard) {
    const content = `GiftcardID: ${giftcard}`;
    // Create a Blob from the string content
    const blob = new Blob([content], { type: 'text/plain' });

    // Create a link element
    const link = document.createElement('a');

    // Set the download attribute with the filename
    link.download = "Gift Card.txt";

    // Create a URL for the Blob and set it as the href attribute
    link.href = window.URL.createObjectURL(blob);

    // Append the link to the body (required for Firefox)
    document.body.appendChild(link);

    // Programmatically click the link to trigger the download
    link.click();

    // Remove the link from the document
    document.body.removeChild(link);
}

document.addEventListener("DOMContentLoaded", () => {
    const cancelTicketForm = document.getElementById("cancel-ticket-form");

    cancelTicketForm.addEventListener("submit", (event) => {
        event.preventDefault();

        const ticketId = document.getElementById("ticket-id").value.trim();
        const reason = document.getElementById("reason").value.trim();

        if (!ticketId || !reason) {
            alert("Please fill in all required fields.");
            return;
        }

        const data = {
            ticketId,
            reason,
        };

        post_json('/api/cancel-ticket', data, (response) => {
            const result = JSON.parse(response);

            if (result.success) {
                // Ticket cancellation succeeded
                const refundMessage = result.message;
                const giftCardId = result.giftCard;
                downloadStringAsFile(giftCardId);
                alert(`${refundMessage}\nGift Card ID: ${giftCardId}`);
                window.location.href = "/"; // Redirect to home page
            } else {
                // Ticket cancellation failed
                alert(`Ticket cancellation failed: ${result.message}`);
            }
        });
    });
});
