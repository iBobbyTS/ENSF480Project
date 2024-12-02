// Declare `newDetailsBlock` as a global variable
let newDetailsBlock = null;
document.addEventListener("DOMContentLoaded", () => {
    const movieGrid = document.getElementById("movie-grid");
    const detailsBlockTemplate = document.getElementById("details-block"); // Correctly define the variable here
    const searchInput = document.querySelector(".search-bar input");
    let movies = []; // Store movies fetched from the API

    // Function to render movies
    function renderMovies(filteredMovies) {
        movieGrid.innerHTML = ""; // Clear the movie grid

        filteredMovies.forEach((movie, index) => {
            // Create movie element
            const movieElement = document.createElement("div");
            movieElement.classList.add("movie");
            movieElement.dataset.index = index;

            // Create cover container
            const coverContainer = document.createElement("div");
            coverContainer.classList.add("cover-container");

            // Add cover image
            const cover = document.createElement("img");
            cover.src = movie.coverUrl;
            cover.alt = movie.title;
            cover.classList.add("movie-cover");

            // Append cover image to the container
            coverContainer.appendChild(cover);

            // Add title
            const title = document.createElement("div");
            title.classList.add("movie-title");
            title.textContent = movie.title;

            // Append cover container and title to movie element
            movieElement.appendChild(coverContainer);
            movieElement.appendChild(title);

            // Append movie to grid
            movieGrid.appendChild(movieElement);

            // Add click event for details
            movieElement.addEventListener("click", () => {
                // Remove existing details block if any
                if (newDetailsBlock) {
                    newDetailsBlock.remove();
                    newDetailsBlock = null;
                }

                // Clone the details block template
                newDetailsBlock = detailsBlockTemplate.cloneNode(true);
                newDetailsBlock.classList.add("inserted");
                newDetailsBlock.style.display = "block";

                // Update details block content using class selectors
                newDetailsBlock.querySelector(".details-title").textContent = movie.title;
                newDetailsBlock.querySelector(".details-actors").textContent = movie.description;
                newDetailsBlock.querySelector(".details-duration").textContent = `Duration: ${movie.duration} minutes`;
                newDetailsBlock.querySelector("#btn-watch-trailer").href = movie.trailerUrl;
                newDetailsBlock.querySelector("#btn-buy-ticket").href = `/movie/buy-ticket?movieId=${movie.movieId}`;

                // Add a close button to the details block
                const closeButton = document.createElement("button");
                closeButton.classList.add("btn-close");
                closeButton.textContent = "Close";
                closeButton.addEventListener("click", () => {
                    newDetailsBlock.remove();
                    newDetailsBlock = null;
                });
                newDetailsBlock.appendChild(closeButton);

                // Calculate the position to insert the details block
                const rowSize = 5;
                const rowIndex = Math.floor(index / rowSize);
                const insertAfterIndex = (rowIndex + 1) * rowSize - 1;
                const adjustedIndex = Math.min(insertAfterIndex, filteredMovies.length - 1);

                const insertAfterElement = movieGrid.children[adjustedIndex];

                // Insert the details block after the insertAfterElement
                insertAfterElement.after(newDetailsBlock);
            });
        });
    }

    // Fetch movies from API
    get('/api/movies', (httpRequest) => {
        movies = JSON.parse(httpRequest.responseText); // Store the fetched movies
        renderMovies(movies); // Initially render all movies
    });

    // Filter movies on search
    searchInput.addEventListener("input", () => {
        const searchQuery = searchInput.value.trim().toLowerCase();
        if (searchQuery === "") {
            renderMovies(movies); // Show all movies when search box is empty
        } else {
            const filteredMovies = movies.filter(movie =>
                movie.title.toLowerCase().includes(searchQuery)
            );
            renderMovies(filteredMovies);
        }
    });

    const userEmailButton = document.getElementById("user-email-button");
    const userDropdown = document.getElementById("user-dropdown");

    // Toggle dropdown menu visibility on button click
    if (userEmailButton && userDropdown) {
        userEmailButton.addEventListener("click", (event) => {
            event.stopPropagation(); // Prevent the click from propagating to the document
            userDropdown.classList.toggle("show");
        });
        // Close dropdown menu when clicking outside
        document.addEventListener("click", () => {
            userDropdown.classList.remove("show");
        });
    }
});

function logout() {
    request_delete('/api/sign-out', () => {
        window.location.href = '/';
    });
}
