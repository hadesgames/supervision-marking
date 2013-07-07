$(document).ready(function() {
   $(".file-upload").submit(function(){
      var form = $(this);
      var filename = form.find('input[type="file"]').val()
      var filenameDiv = $('<input>').attr("type", "hidden")
                                .attr("name", "filename")
                                .val(filename);

      form.append(filenameDiv);
   });
});


$(document).ready(function() {
   $('a.delete').click(function(evt) {
     evt.preventDefault();
     evt.stopPropagation();
     var link = $(this);
     var accept = confirm("Are you sure you want to delete this?")
     if (accept) {
        $.ajax({
            url: link.attr("href"),
            type: 'DELETE',
            success: function(result) {
                link.parents("tr").fadeOut();
            },
            error: function(err) {
                console.log(err);
                alert("There was an error deleting the item");
            }
        });
      }
      return false;
   });
})
