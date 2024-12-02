document.addEventListener("DOMContentLoaded", () => {
    const seatMapContainer = document.getElementById("seatmap");
    const orderSummary = document.getElementById("order-summary");
    const summaryMovie = document.getElementById("summary-movie");
    const summarySeats = document.getElementById("summary-seats");
    const total = document.getElementById("total");
    const loadSeatsButton = document.getElementById("load-seats");
    const payButton = document.getElementById("pay-button");
    const dateTimeDropdown = document.getElementById("date-time");
    const giftCardInput = document.getElementById("gift-card-input");
    const applyGiftCardButton = document.getElementById("apply-gift-card");
    const remainingAmountText = document.getElementById("remaining-amount");

    let selectedSeats = [];
    const ticketPrice = 25; // Price per ticket
    let totalPrice = 0; // Total price of selected seats

    // A mapping of showtime strings to their corresponding IDs
    let showtimeMap = {};

    // Populate the dropdown with showtimes and their IDs
    function populateShowtimes(showtimes) {
        dateTimeDropdown.innerHTML = ""; // Clear existing options
        showtimes.forEach((showtime) => {
            const option = document.createElement("option");
            option.value = showtime.showTimeId; // Use showTimeId for value
            option.textContent = new Date(showtime.showTime).toLocaleString(); // Format date
            showtimeMap[showtime.showTimeId] = new Date(showtime.showTime).toLocaleString(); // Map for summary
            dateTimeDropdown.appendChild(option);
        });
    }

    // Function to render the seat map
    function renderSeatMap(seats) {
        seatMapContainer.innerHTML = "";
        const totalRows = 10;
        const totalColumns = 10;

        // Create a grid and mark reserved seats
        const seatGrid = Array.from({ length: totalRows }, (_, rowIndex) =>
            Array.from({ length: totalColumns }, (_, columnIndex) => ({
                seatRow: rowIndex,
                seatColumn: columnIndex,
                isReserved: false,
            }))
        );

        seats.forEach((seat) => {
            seatGrid[seat.seatRow][seat.seatColumn].isReserved = true;
        });

        // Render seat grid
        seatGrid.forEach((rowSeats, rowIndex) => {
            const rowDiv = document.createElement("div");
            rowDiv.classList.add("seat-row");

            rowSeats.forEach((seat) => {
                const seatElement = document.createElement("div");
                seatElement.classList.add("seat", seat.isReserved ? "reserved" : "empty");
                seatElement.dataset.seatNumber = `${String.fromCharCode(65 + rowIndex)}${seat.seatColumn + 1}`;
                seatElement.dataset.seatId = `${rowIndex}-${seat.seatColumn}`;

                seatElement.addEventListener("mouseenter", () => {
                    seatElement.title = seat.isReserved
                        ? "Already Reserved"
                        : seatElement.dataset.seatNumber;
                });

                if (!seat.isReserved) {
                    seatElement.addEventListener("click", () => {
                        if (seatElement.classList.contains("selected")) {
                            seatElement.classList.remove("selected");
                            selectedSeats = selectedSeats.filter(
                                (s) => s !== seatElement.dataset.seatNumber
                            );
                        } else {
                            seatElement.classList.add("selected");
                            selectedSeats.push(seatElement.dataset.seatNumber);
                        }
                        updateOrderSummary();
                    });
                }

                rowDiv.appendChild(seatElement);
            });

            seatMapContainer.appendChild(rowDiv);
        });
    }

    // Function to update the order summary
    function updateOrderSummary() {
        const movieName = document.querySelector("h1").textContent;
        const selectedShowtimeId = dateTimeDropdown.value;
        const selectedDateTime = showtimeMap[selectedShowtimeId];

        summaryMovie.textContent = `${movieName} at ${selectedDateTime}`;

        summarySeats.innerHTML = "";
        selectedSeats.forEach((seat) => {
            const seatItem = document.createElement("li");
            seatItem.innerHTML = `Seat ${seat} <span>$${ticketPrice}</span>`;
            summarySeats.appendChild(seatItem);
        });

        totalPrice = selectedSeats.length * ticketPrice;
        total.textContent = `Total: $${totalPrice}`;
        remainingAmountText.textContent = ""; // Clear remaining amount
    }

    // Handle applying a gift card
    applyGiftCardButton.onclick = () => {
        applyGiftCardButton.disabled = true;
        const giftCardId = giftCardInput.value.trim();
        if (!giftCardId) {
            alert("Please enter a valid Gift Card ID.");
            return;
        }

        get(`/api/giftcard`, (httpRequest) => {
            const response = JSON.parse(httpRequest.responseText);
            if (response.success) {
                const balanceApplied = response.balanceApplied;
                totalPrice -= balanceApplied;
                if (totalPrice <= 0) {
                    totalPrice = 0; // Prevent negative price
                }
                total.textContent = `Total: $${totalPrice}`;
                remainingAmountText.textContent = `Remaining amount to pay: $${response.remainingAmount}`;
            } else {
                alert("Gift Card application failed: " + response.message);
            }
        }, { giftCardId, amount: totalPrice });
    };

    // Handle Pay button click
    payButton.onclick = () => {
        const selectedShowtimeId = dateTimeDropdown.value;

        if (!selectedShowtimeId || selectedSeats.length === 0) {
            alert("Please select a showtime and at least one seat.");
            return;
        }

        let seat = "";
        for (let i = 0; i < selectedSeats.length; i++) {
            let s = selectedSeats[i];
            const [row, column] = s.match(/\d+|[A-Z]+/g);
            seat += row.charCodeAt(0) - 65 + "-" + (parseInt(column, 10) - 1) + ",";
        }
        seat = seat.slice(0, -1);
        seat = encodeURIComponent(seat);
        window.location.href = `/payment/bank?redirect=/payment/done/ticket&showtimeId=${selectedShowtimeId}&seats=${seat}`;
    };

    // Fetch seats from the backend and render seat map
    loadSeatsButton.addEventListener("click", () => {
        const selectedShowtimeId = dateTimeDropdown.value;

        if (!selectedShowtimeId) {
            alert("Please select a showtime before loading seats.");
            return;
        }

        get(`/api/seats`, (httpRequest) => {
            const seats = JSON.parse(httpRequest.responseText);
            renderSeatMap(seats);
            selectedSeats = [];
            updateOrderSummary();
        }, { showtimeId: selectedShowtimeId });
    });

    // Load initial showtimes from backend
    const urlParams = new URLSearchParams(window.location.search);
    const movieId = urlParams.get("movieId");
    if (movieId) {
        get(`/api/showtimes`, (httpRequest) => {
            const showtimes = JSON.parse(httpRequest.responseText);
            populateShowtimes(showtimes);
        }, { movieId });
    }
});
