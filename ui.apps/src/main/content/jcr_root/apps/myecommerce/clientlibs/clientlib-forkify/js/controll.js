document.addEventListener('DOMContentLoaded', function () {
  const recipeContainer = document.querySelector('.recipe');

  if (!recipeContainer) {
    console.error("Error: Recipe container not found.");
    return;
  }

  // Function to display the spinner
  function showSpinner(parentEl) {
    const markup = `
      <div class="spinner">
        <svg>
          <use href="img/icons.svg#icon-loader"></use>
        </svg>
      </div>
    `;
    parentEl.innerHTML = markup; // Clear the container and add the spinner
  }

  // Function to hide the spinner
  function hideSpinner(parentEl) {
    parentEl.innerHTML = ''; // Clear the spinner
  }

  // Function to fetch and display the recipe
  const getRecipe = async function () {
    try {
      const id = window.location.hash.replace('#', '');
      if (!id) return; // If no ID in hash, do nothing

      showSpinner(recipeContainer);

      // Fetch recipe data
      const res = await fetch(`https://forkify-api.herokuapp.com/api/v2/recipes/${id}`);
      const data = await res.json();

      if (!res.ok) throw new Error(`${data.message} (${res.status})`);

      // Extract recipe details
      const recipe = {
        id: data.data.recipe.id,
        title: data.data.recipe.title,
        publisher: data.data.recipe.publisher,
        sourceUrl: data.data.recipe.source_url,
        image: data.data.recipe.image_url,
        servings: data.data.recipe.servings,
        cookingTime: data.data.recipe.cooking_time,
        ingredients: data.data.recipe.ingredients,
      };

      // Generate dynamic ingredient list
      const ingredientsMarkup = recipe.ingredients
        .map(
          ing => `
          <li class="recipe__ingredient">
            <i class="fa-solid fa-check"></i>  
            <div class="recipe__quantity">${ing.quantity || ''}</div>
            <div class="recipe__description">
              <span class="recipe__unit">${ing.unit || ''}</span>
              ${ing.description}
            </div>
          </li>`
        )
        .join('');

      // Full recipe markup
      const markup = `
        <figure class="recipe__fig">
          <img src="${recipe.image}" alt="${recipe.title}" class="recipe__img" />
          <h1 class="recipe__title">
            <span>${recipe.title}</span>
          </h1>
        </figure>

        <div class="recipe__details">
          <div class="recipe__info">
            <i class="fa-solid fa-clock"></i>
            <span class="recipe__info-data">${recipe.cookingTime}</span>
            <span class="recipe__info-text">minutes</span>
          </div>
          <div class="recipe__info">
            <i class="fa-solid fa-users"></i>
            <span class="recipe__info-data">${recipe.servings}</span>
            <span class="recipe__info-text">servings</span>

            <div class="recipe__info-buttons">
              <button class="btn--tiny btn--increase-servings">
                <i class="fa-solid fa-minus-circle"></i>
              </button>
              <button class="btn--tiny btn--increase-servings">
                <i class="fa-solid fa-plus-circle"></i>
              </button>
            </div>
          </div>

          <div class="recipe__user-generated">
            <i class="fa-solid fa-user"></i>
          </div>
          <button class="btn--round">
            <i class="fa-solid fa-bookmark"></i>
          </button>
        </div>

        <div class="recipe__ingredients">
          <h2 class="heading--2">Recipe Ingredients</h2>
          <ul class="recipe__ingredient-list">
            ${ingredientsMarkup}
          </ul>
        </div>

        <div class="recipe__directions">
          <h2 class="heading--2">How to cook it</h2>
          <p class="recipe__directions-text">
            This recipe was created by <span class="recipe__publisher">${recipe.publisher}</span>.
            Check out the full instructions on their website.
          </p>
          <a class="btn--small recipe__btn" href="${recipe.sourceUrl}" target="_blank">
            <span>Directions</span>
            <i class="fa-solid fa-arrow-right"></i>
          </a>
        </div>
      `;

      // Hide the spinner and display the recipe
      recipeContainer.innerHTML = markup;
    } catch (error) {
      console.error("Error fetching recipe:", error);
      alert(error.message);
      hideSpinner(recipeContainer);
    }
  };

  // Listen for hash change and load recipe
  window.addEventListener('hashchange', getRecipe);
  window.addEventListener('load', getRecipe); // Load recipe on page refresh
});
