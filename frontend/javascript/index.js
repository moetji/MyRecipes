
document.addEventListener("DOMContentLoaded", function () {
    const slides = document.querySelectorAll(".slide");
    const dots = document.querySelectorAll(".dot");
    const previousButton = document.querySelector(".previous");
    const nextButton = document.querySelector(".next");
    const title = document.getElementById("hero-title");
    const description = document.getElementById("hero-description");
    const hero = document.querySelector(".hero");

    const recipes = [
        {
            title: "Discover Your Next Favourite Recipe",
            description:
                "Find delicious meals, simple ingredients and recipes for every occasion."
        },
        {
            title: "Fresh Meals, Full of Flavour",
            description:
                "Bring colour to your table with fresh and tasty recipes."
        },
        {
            title: "Make Something Sweet",
            description:
                "Discover delicious desserts to share with family and friends."
        },
        {
        title: "Breakfast Made Delicious",
        description: "Start your day with simple and satisfying breakfast ideas."
     }, {
        title: "Quick Meals for Busy Days",
        description: "Prepare tasty meals without spending hours in the kitchen."
    },  {
        title: "Bake Something Wonderful",
        description: "Explore homemade cakes, breads and delicious pastries."
    }
    ];

    let currentIndex = 0;
    let slideshowTimer;

    function showSlide(index) {
        // Wrap around when reaching either end
        currentIndex = (index + slides.length) % slides.length;

        slides.forEach(function (slide, i) {
            slide.classList.toggle("active", i === currentIndex);
        });

        dots.forEach(function (dot, i) {
            dot.classList.toggle("active", i === currentIndex);
            dot.setAttribute(
                "aria-pressed",
                i === currentIndex ? "true" : "false"
            );
        });

        // Update the heading and description
        title.textContent = recipes[currentIndex].title;
        description.textContent = recipes[currentIndex].description;
    }

    function startSlideshow() {
        clearInterval(slideshowTimer);

        slideshowTimer = setInterval(function () {
            showSlide(currentIndex + 1);
        }, 4000);
    }

    function goToSlide(index) {
        showSlide(index);
        startSlideshow();
    }

    previousButton.addEventListener("click", function () {
        goToSlide(currentIndex - 1);
    });

    nextButton.addEventListener("click", function () {
        goToSlide(currentIndex + 1);
    });

    dots.forEach(function (dot, index) {
        dot.addEventListener("click", function () {
            goToSlide(index);
        });
    });

    // Pause automatic rotation while the visitor hovers
    hero.addEventListener("mouseenter", function () {
        clearInterval(slideshowTimer);
    });

    hero.addEventListener("mouseleave", startSlideshow);

    // Pause rotation when the visitor switches browser tabs
    document.addEventListener("visibilitychange", function () {
        if (document.hidden) {
            clearInterval(slideshowTimer);
        } else {
            startSlideshow();
        }
    });

    // Start the slideshow
    showSlide(0);
    startSlideshow();
    
    async function loadCategories() {
        const grid = document.getElementById("category-grid");

        if (!grid) return;

        const categoryImages = [
            "../images/header/main.png",
            "../images/header/healthy.jpg",
            "../images/header/dessert.jpg",
            "../images/header/breakfast.png",
            "../images/header/quick.png",
            "../images/header/baking.jpg"
        ];

        try {
            const response = await fetch(
                "http://localhost:8080/api/categories"
            );

            if (!response.ok) {
                throw new Error("HTTP error: " + response.status);
            }

            const categories = await response.json();

            grid.replaceChildren();

            if (categories.length === 0) {
                grid.textContent = "No categories have been added yet.";
                return;
            }

            categories.forEach(function (category, index) {
                const card = document.createElement("a");
                card.className = "category-card";
                card.href = "#recipes";

                const image = document.createElement("img");
                image.src = categoryImages[index % categoryImages.length];
                image.alt = category.name;

                const glass = document.createElement("div");
                glass.className = "category-glass";

                const heading = document.createElement("h3");
                heading.textContent = category.name;

                const description = document.createElement("p");
                description.textContent =
                    "Discover delicious " +
                    category.name.toLowerCase() + " recipes.";

                const link = document.createElement("span");
                link.textContent = "Explore recipes ↗";

                glass.append(heading, description, link);
                card.append(image, glass);
                grid.appendChild(card);
            });

        } catch (error) {
            console.error("Could not load categories:", error);

            grid.textContent =
                "Unable to load categories. Please try again later.";
        }
    }

    loadCategories();

    
async function loadRecipes() {
    const grid = document.getElementById("recipe-grid");

    if (!grid) return;

    try {
        const response = await fetch("http://localhost:8080/api/recipes");

        if (!response.ok) {
            throw new Error("HTTP error: " + response.status);
        }

        const recipes = await response.json();

        grid.replaceChildren();

        if (recipes.length === 0) {
            grid.textContent = "No recipes have been added yet.";
            return;
        }

        recipes.forEach(function (recipe) {
            const card = document.createElement("article");
            card.className = "recipe-card";

            const image = document.createElement("img");
            image.src = recipe.image
                ? "http://localhost:8080/" + recipe.image.replace(/^\/+/, "")
                : "../images/header/main.png";
            image.alt = recipe.name || "Recipe";
            image.onerror = function () {
                image.onerror = null;
                image.src = "../images/header/main.png";
            };

            const content = document.createElement("div");
            content.className = "recipe-card-content";

            const heading = document.createElement("h3");
            heading.textContent = recipe.name;

            const description = document.createElement("p");
            description.textContent =
                recipe.description || "Discover this delicious recipe.";

            const meta = document.createElement("div");
            meta.className = "recipe-meta";
            meta.textContent =
                "Prep: " + (recipe.prepTime ?? "N/A") + " min | Cook: " +
                (recipe.cookTime ?? "N/A") + " min";

            const link = document.createElement("a");
            link.className = "recipe-view-button";
            link.href = "recipe-details.html?id=" +
                encodeURIComponent(recipe.id);
            link.textContent = "View Recipe";

            content.append(heading, description, meta, link);
            card.append(image, content);
            grid.appendChild(card);
        });

    } catch (error) {
        console.error("Could not load recipes:", error);
        grid.textContent =
            "Unable to load recipes. Please try again later.";
    }
}

loadRecipes();

});
