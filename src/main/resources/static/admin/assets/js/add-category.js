let categoryCount = 0;

function addNewCategory() {
    const container = document.getElementById('newCategoriesContainer');
    const categoryDiv = document.createElement('div');
    categoryDiv.className = 'row mt-2';
    categoryDiv.innerHTML = `
    <div class="col-md-10">
        <div class="form-group">
            <input type="text" class="form-control" name="newCategories[${categoryCount}].name" placeholder="Category Name" required>
        </div>
    </div>
    <div class="col-md-2">
        <button type="button" class="btn btn-danger" onclick="this.closest('.row').remove()">Remove</button>
    </div>
  `;
    container.appendChild(categoryDiv);
    categoryCount++;
}
