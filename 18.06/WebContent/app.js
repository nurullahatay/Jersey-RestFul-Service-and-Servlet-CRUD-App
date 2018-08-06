var $newTask = $("#new-task");
var $toDoList = $("#incomplete-tasks");
var $completedList = $("#completed-tasks");
var $addButton = $("#add-button");
var $deleteButton = $(".delete-button");
var $editButton = $(".edit-button");
var $checkbox = $(".checkbox");
var $label = $("label");
var $editInput = $("input[type=text]");

//generate new list item
var makeNewListItem = function(taskToAdd) {

  var $newListItem = $("<li></li>");
  var $newCheckbox = $("<input type='checkbox' class='checkbox'>");
  var $newLabel = $("<label></label>");
  var $newEditInput = $("<input type='text' class='edit-text'>");
  var $newEditButton = $("<button class='edit-button'>Edit</button>");
  var $newDeleteButton = $("<button class='delete-button'>Delete</button>");

  $newListItem.append($newCheckbox)
  .append($newLabel.html(taskToAdd))
  .append($newEditInput)
  .append($newEditButton)
  .append($newDeleteButton);

  return $newListItem;
}

//add new list item to To-Do list when add-button is clicked
$addButton.on( "click", function(){
  var listItem = makeNewListItem($newTask.val());
  $toDoList.append(listItem);
  $newTask.val("");
})

//delete list item from list when delete-button is clicked
$deleteButton.on( "click", function(){
  $(this).parent().remove();  
});