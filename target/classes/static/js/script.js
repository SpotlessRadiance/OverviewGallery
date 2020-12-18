$(document).ready(function(){
	var dropZone = $('#upload-container');


	dropZone.on('drag dragstart dragend dragover dragenter dragleave drop', function(){
		return false;
	});

	dropZone.on('dragover dragenter', function() {
		dropZone.addClass('dragover');
	});


	dropZone.on('drop', function(e) {
		dropZone.removeClass('dragover');
		let files = e.originalEvent.dataTransfer.files;
		sendFiles(files);
	});

/*.submit(function(){
         var ddd= $("#tF").val();
             if(ddd==""){
                alert("Вы не выбрали файл");
                 return false;
                }
            else{
                return true;
                }

    });*/

	$('#file-input').change(function() {
		var ext=$(this).val().split('.').pop();
		var allow=new Array('png','jpeg','jpg');
        if($.inArray(ext,allow)===-1){
        		$(this).val('');
        		alert('Недопустимый тип файла');
        	}
	});

   $('#cancel-button').on('click', function(){
   $('#file-input').val('');
   $('#title-input').val('');
   $('#tags-input').val('');
   $('#uploaded-img').src="./img/black.jpg";
   });



})
