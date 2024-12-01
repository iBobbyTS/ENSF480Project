document.addEventListener("DOMContentLoaded", () => {
    const timeContainer = document.getElementById("time-container");
    const addMoreButton = document.getElementById("add-more-button");
    // Calculate the date for today + 7 days
    const date = new Date();
    date.setDate(date.getDate() + 7);

    const year = date.getFullYear();
    const month = String(date.getMonth() + 1);
    const day = String(date.getDate());

    // Set the default values for the input fields
    document.querySelector(".year").value = year;
    document.querySelector(".month").value = month;
    document.querySelector(".day").value = day;

    // Function to add a new time line
    window.addMoreLines = function () {
        // Calculate the date for today + 7 days
        const date = new Date();
        date.setDate(date.getDate() + 7);

        const year = date.getFullYear();
        const month = String(date.getMonth() + 1);
        const day = String(date.getDate());
        const hour = '19';
        const minute = '00';

        // Create a new line with pre-input values
        const newTimeLine = document.createElement("div");
        newTimeLine.classList.add("time-line");
        newTimeLine.innerHTML = `
            <input type="number" class="year" placeholder="YYYY" value="${year}">
            <input type="number" class="month" placeholder="MM" value="${month}">
            <input type="number" class="day" placeholder="DD" value="${day}">
            <input type="number" class="hour" placeholder="HH" value="${hour}">
            <input type="number" class="minute" placeholder="MM" value="${minute}">
            <button class="remove-line" onclick="removeLine(this)">Remove</button>
        `;

        // Insert the new line before the "Add More" button
        timeContainer.insertBefore(newTimeLine, addMoreButton);
    };
    window.removeLine = function (button) {
        const line = button.parentElement;
        timeContainer.removeChild(line);
    };
    // Function to submit showtimes

});

function submitShowtimes() {
    const timeLines = document.querySelectorAll(".time-line");
    const showtimes = [];

    // Collect showtime data
    timeLines.forEach((line) => {
        const year = line.querySelector(".year").value;
        const month = line.querySelector(".month").value.padStart(2, '0');
        const day = line.querySelector(".day").value.padStart(2, '0');
        const hour = line.querySelector(".hour").value.padStart(2, '0');
        const minute = line.querySelector(".minute").value.padStart(2, '0');

        if (year && month && day && hour && minute) {
            showtimes.push(`${year}-${month}-${day} ${hour}:${minute}`);
        }
    });
    // Get the current URL
    const urlParams = new URLSearchParams(window.location.search);
    // Get a specific parameter value
    const movieId = urlParams.get('id');
    // Example submission logic
    post_json(
        "/api/admin/add-showtime",
        { showtimes },
        (response) => {
            const result = JSON.parse(response);
            if (result.success === 5) {
                alert("Showtimes added successfully!");
                window.location.href = "/admin"; // Redirect to admin page
            } else {
                alert("Failed to add showtimes.");
            }
        },
        {movieId: movieId}
    );
};