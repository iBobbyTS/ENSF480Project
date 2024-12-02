document.addEventListener("DOMContentLoaded", () => {
    const seatMapContainer = document.getElementById("seatmap");
    const orderSummary = document.getElementById("order-summary");
    const summaryMovie = document.getElementById("summary-movie");
    const summarySeats = document.getElementById("summary-seats");
    const total = document.getElementById("total");
    const loadSeatsButton = document.getElementById("load-seats");
    const dateTimeDropdown = document.getElementById("date-time");

    let selectedSeats = [];
    const ticketPrice = 25;

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

    // Function to render seat map
    // Function to render seat map
    function renderSeatMap(seats) {
        // Clear the existing seat map
        seatMapContainer.innerHTML = "";

        // Total rows and columns
        const totalRows = 10;
        const totalColumns = 10;

        // Create a 2D grid with all seats
        const seatGrid = Array.from({ length: totalRows }, (_, rowIndex) => {
            return Array.from({ length: totalColumns }, (_, columnIndex) => ({
                seatRow: rowIndex,
                seatColumn: columnIndex,
                isReserved: false,
            }));
        });

        // Mark reserved seats based on backend data
        seats.forEach((seat) => {
            const row = seat.seatRow;
            const column = seat.seatColumn;
            seatGrid[row][column].isReserved = true;
        });

        // Render the grid
        seatGrid.forEach((rowSeats, rowIndex) => {
            const rowDiv = document.createElement("div");
            rowDiv.classList.add("seat-row");

            rowSeats.forEach((seat) => {
                const seatElement = document.createElement("div");
                seatElement.classList.add("seat");
                seatElement.classList.add(seat.isReserved ? "reserved" : "empty");
                seatElement.dataset.seatNumber = `${String.fromCharCode(65 + rowIndex)}${seat.seatColumn + 1}`;
                seatElement.dataset.seatId = `${rowIndex}-${seat.seatColumn}`; // Unique ID for the seat

                // Hover behavior
                seatElement.addEventListener("mouseenter", () => {
                    seatElement.title = seat.isReserved
                        ? "Already Reserved"
                        : seatElement.dataset.seatNumber;
                });

                // Click behavior for empty seats
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

        // Update movie and time
        summaryMovie.textContent = `${movieName} at ${selectedDateTime}`;

        // Update seat list
        summarySeats.innerHTML = "";
        selectedSeats.forEach((seat) => {
            const seatItem = document.createElement("li");
            seatItem.innerHTML = `Seat ${seat} <span>$${ticketPrice}</span>`;
            summarySeats.appendChild(seatItem);
        });

        // Update total
        total.textContent = `Total: $${selectedSeats.length * ticketPrice}`;
    }

    // Fetch seats from the backend and render seat map
    loadSeatsButton.addEventListener("click", () => {
        const selectedShowtimeId = dateTimeDropdown.value;

        if (!selectedShowtimeId) {
            alert("Please select a showtime before loading seats.");
            return;
        }

        // Call backend API to get seat data
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
