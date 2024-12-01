document.addEventListener("DOMContentLoaded", () => {
    const moviesTableBody = document.getElementById("movies-body");
    const batchRemoveButton = document.getElementById("batch-remove-movie");

    // Simulated movie data
    const movies = [
        { id: 1, name: "Movie 1", addDate: "2024-11-25" },
        { id: 2, name: "Movie 2", addDate: "2024-11-26" },
        { id: 3, name: "Movie 3", addDate: "2024-11-27" },
    ];

    // Function to render the movie table
    function renderMovies() {
        moviesTableBody.innerHTML = ""; // Clear table content

        movies.forEach((movie) => {
            const row = document.createElement("tr");

            // Movie Name
            const nameCell = document.createElement("td");
            nameCell.textContent = movie.name;

            // Add Date
            const dateCell = document.createElement("td");
            dateCell.textContent = movie.addDate;

            // Action Buttons
            const actionCell = document.createElement("td");
            const addShowtimeButton = document.createElement("button");
            addShowtimeButton.textContent = "Add Showtime";
            addShowtimeButton.classList.add("add-showtime");
            addShowtimeButton.addEventListener("click", () => {
                window.location.href = `/add-showtime?movieId=${movie.id}`;
            });

            const removeMovieButton = document.createElement("button");
            removeMovieButton.textContent = "Remove Movie";
            removeMovieButton.classList.add("remove-movie");
            removeMovieButton.addEventListener("click", () => {
                if (confirm(`Are you sure you want to remove "${movie.name}"?`)) {
                    removeMovie(movie.id);
                }
            });

            actionCell.appendChild(addShowtimeButton);
            actionCell.appendChild(removeMovieButton);

            // Append cells to the row
            row.appendChild(nameCell);
            row.appendChild(dateCell);
            row.appendChild(actionCell);

            // Append row to the table body
            moviesTableBody.appendChild(row);
        });
    }

    // Function to remove a movie
    function removeMovie(movieId) {
        const movieIndex = movies.findIndex((movie) => movie.id === movieId);
        if (movieIndex > -1) {
            movies.splice(movieIndex, 1);
            renderMovies(); // Re-render the table
        }
    }

    // Batch remove button handler (for demo purposes)
    batchRemoveButton.addEventListener("click", () => {
        if (confirm("Are you sure you want to remove all movies?")) {
            movies.length = 0; // Clear all movies
            renderMovies(); // Re-render the table
        }
    });

    // Initial render
    renderMovies();
});
