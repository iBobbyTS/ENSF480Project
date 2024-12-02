document.addEventListener("DOMContentLoaded", () => {
    const ticketListContainer = document.getElementById("ticket-list");

    // Function to convert row number to a letter
    function rowToLetter(rowNumber) {
        return String.fromCharCode(65 + rowNumber); // ASCII conversion: A=65
    }

    // Function to render tickets
    function renderTickets(tickets) {
        if (tickets.length === 0) {
            ticketListContainer.innerHTML = "<p>You have no tickets.</p>";
            return;
        }

        ticketListContainer.innerHTML = ""; // Clear the loading message

        tickets.forEach((ticket) => {
            const ticketDiv = document.createElement("div");
            ticketDiv.classList.add("ticket");

            const movieTitle = document.createElement("h3");
            movieTitle.textContent = `Movie: ${ticket.movieTitle}`;
            ticketDiv.appendChild(movieTitle);

            const showTime = document.createElement("p");
            showTime.textContent = `Showtime: ${new Date(ticket.showTime).toLocaleString()}`;
            ticketDiv.appendChild(showTime);

            const seatInfo = ticket.seat;
            const seat = `${rowToLetter(seatInfo.seatRow)}${seatInfo.seatColumn + 1}`;

            const seatElement = document.createElement("p");
            seatElement.textContent = `Seat: ${seat}`;
            ticketDiv.appendChild(seatElement);

            ticketListContainer.appendChild(ticketDiv);
        });
    }

    // Function to fetch tickets from the backend
    function fetchTickets() {
        get('/api/my-tickets', (httpRequest) => {
            const tickets = JSON.parse(httpRequest.responseText);
            renderTickets(tickets);
        });
    }

    fetchTickets();
});
