document.addEventListener("DOMContentLoaded", () => {
    const moviesTableBody = document.getElementById("movies-body");

    // Fetch movies from API and render the table
    function fetchMovies() {
        get('/api/movies', (httpRequest) => {
            const movies = JSON.parse(httpRequest.responseText);
            renderMovies(movies);
        });
    }

    // Function to render the movie table
    function renderMovies(movies) {
        moviesTableBody.innerHTML = ""; // Clear table content

        movies.forEach((movie) => {
            const row = document.createElement("tr");

            // Movie Title
            const titleCell = document.createElement("td");
            titleCell.textContent = movie.title;

            // Add Date
            const dateCell = document.createElement("td");
            dateCell.textContent = movie.addDate;

            // Action Buttons
            const actionCell = document.createElement("td");
            const addShowtimeButton = document.createElement("button");
            addShowtimeButton.textContent = "Add Showtime";
            addShowtimeButton.classList.add("add-showtime");
            addShowtimeButton.addEventListener("click", () => {
                window.location.href = `/add-showtime?movieTitle=${encodeURIComponent(movie.title)}`;
            });

            const removeMovieButton = document.createElement("button");
            removeMovieButton.textContent = "Remove Movie";
            removeMovieButton.classList.add("remove-movie");
            removeMovieButton.addEventListener("click", () => {
                if (confirm(`Are you sure you want to remove "${movie.title}"?`)) {
                    removeMovie(movie.movieId);
                }
            });

            actionCell.appendChild(addShowtimeButton);
            actionCell.appendChild(removeMovieButton);

            // Append cells to the row
            row.appendChild(titleCell);
            row.appendChild(dateCell);
            row.appendChild(actionCell);

            // Append row to the table body
            moviesTableBody.appendChild(row);
        });
    }

    // Function to remove a movie
    function removeMovie(movieId) {
        request_delete(
            `/api/admin/remove-movie`,
            () => {
                window.location.href = '/admin';
            },
            { movieId: movieId });
    }

    // Fetch and render movies initially
    fetchMovies();
});
