$(document).ready(function() {
   $(".file-upload").submit(function(){
      form = $(this);
      filename = form.find('input[type="file"]').val()
      filenameDiv = $('<input>').attr("type", "hidden")
                                .attr("name", "filename")
                                .val(filename);

      form.append(filenameDiv);
   });
});
