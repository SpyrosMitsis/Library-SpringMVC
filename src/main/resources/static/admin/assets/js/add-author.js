    let authorCount = 0;

    function addNewAuthor() {
    const container = document.getElementById('newAuthorsContainer');
    const authorDiv = document.createElement('div');
    authorDiv.className = 'row mt-2';
    authorDiv.innerHTML = `
    <div class="col-md-5">
        <div class="form-group">
            <input type="text" class="form-control" name="newAuthors[${authorCount}].firstName" placeholder="First Name" required>
        </div>
    </div>
    <div class="col-md-5">
        <div class="form-group">
            <input type="text" class="form-control" name="newAuthors[${authorCount}].lastName" placeholder="Last Name" required>
        </div>
    </div>
    <div class="col-md-2">
        <button type="button" class="btn btn-danger" onclick="this.closest('.row').remove()">Remove</button>
    </div>
  `;
    container.appendChild(authorDiv);
    authorCount++;
}