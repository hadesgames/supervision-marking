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


$(document).ready(function() {
    $('.getPage input[type="submit"]').click(function() {
       val = $('.getPage input[type="text"]').val();

       window.location.href="/getPage/" + val;
    })
})
