document.addEventListener('DOMContentLoaded', function () {
  const recipeContainer = document.querySelector('.recipe');

  if (!recipeContainer) {
    console.error("Error: Recipe container not found.");
    return;
  }

  const getRecipe = async function () {
    try {
      const res = await fetch('https://forkify-api.herokuapp.com/api/v2/recipes/5ed6604591c37cdc054bc886');
      const data = await res.json();

      if (!res.ok) throw new Error(`${data.message} (${res.status})`);

      let { recipe } = data.data;
      recipe = {
        id: recipe.id,
        title: recipe.title,
        publisher: recipe.publisher,
        sourceUrl: recipe.source_url,
        image: recipe.image_url,
        servings: recipe.servings,
        cookingTime: recipe.cooking_time,
        ingredients: recipe.ingredients
      };

      // Generate dynamic ingredient list
      const ingredientsMarkup = recipe.ingredients
        .map(ing => `
          <li class="recipe__ingredient">
            <i class="fa-solid fa-check"></i>  <!-- FontAwesome Check Icon -->
            <div class="recipe__quantity">${ing.quantity || ''}</div>
            <div class="recipe__description">
              <span class="recipe__unit">${ing.unit || ''}</span>
              ${ing.description}
            </div>
          </li>
        `)
        .join('');

      // Full markup including ingredients
      const markup = `
        <figure class="recipe__fig">
          <img src="${recipe.image}" alt="${recipe.title}" class="recipe__img" />
          <h1 class="recipe__title">
            <span>${recipe.title}</span>
          </h1>
        </figure>

        <div class="recipe__details">
          <div class="recipe__info">
            <i class="fa-solid fa-clock"></i>  <!-- Clock Icon -->
            <span class="recipe__info-data">${recipe.cookingTime}</span>
            <span class="recipe__info-text">minutes</span>
          </div>
          <div class="recipe__info">
            <i class="fa-solid fa-users"></i>  <!-- Users Icon -->
            <span class="recipe__info-data">${recipe.servings}</span>
            <span class="recipe__info-text">servings</span>

            <div class="recipe__info-buttons">
              <button class="btn--tiny btn--increase-servings">
                <i class="fa-solid fa-minus-circle"></i>  <!-- Minus Icon -->
              </button>
              <button class="btn--tiny btn--increase-servings">
                <i class="fa-solid fa-plus-circle"></i>  <!-- Plus Icon -->
              </button>
            </div>
          </div>

          <div class="recipe__user-generated">
            <i class="fa-solid fa-user"></i>  <!-- User Icon -->
          </div>
          <button class="btn--round">
            <i class="fa-solid fa-bookmark"></i>  <!-- Bookmark Icon -->
          </button>
        </div>

        <div class="recipe__ingredients">
          <h2 class="heading--2">Recipe Ingredients</h2>
          <ul class="recipe__ingredient-list">
            ${ingredientsMarkup}  <!-- Injects dynamic ingredients -->
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
            <i class="fa-solid fa-arrow-right"></i>  <!-- Arrow Icon -->
          </a>
        </div>
      `;

      recipeContainer.innerHTML = markup;
    } catch (error) {
      console.error("Error fetching recipe:", error);
      alert(error.message);
    }
  };

  getRecipe();
});
