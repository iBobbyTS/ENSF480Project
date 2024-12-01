function submit() {
    // Collect form data
    const formData = {
        title: document.getElementById("movie-name").value.trim(),
        duration: parseInt(document.getElementById("movie-duration").value.trim(), 10),
        description: document.getElementById("movie-description").value.trim(),
        trailerUrl: document.getElementById("trailer-link").value.trim(),
        coverUrl: document.getElementById("cover-url").value.trim(),
    };

    // Validate input (basic example)
    if (!formData.title || !formData.duration || !formData.description || !formData.trailerUrl || !formData.coverUrl) {
        alert("Please fill out all fields correctly.");
        return;
    }

    // Send data to backend (example implementation, replace with actual API)
    post_json("/api/admin/add-movie", formData, (httpRequest) => {
        const response = JSON.parse(httpRequest.responseText);
        if (response.success === 0) {
            alert("Movie added successfully!");
            window.location.href = "/admin-dashboard"; // Redirect to admin dashboard
        } else {
            alert("Failed to add movie: " + response.message);
        }
    });
}