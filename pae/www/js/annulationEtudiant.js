

$("#annulation_etudiant").on('click', function(){
	

	  if($("#raison").val() === ""){
        $("#alert").css("display", "block");
	  }
    
    let raison = $("#raison").val();

    $.ajax({
        url: '/',
        type: 'POST',
        data:{
            action: 'annulationMobilite',
            raison: raison
        },

        success: function(response){
            if(response === "ok"){
                $("#alert1").css("display", "block");
            }
            else if(response === "ko"){
            	$("#alert2").css("display", "block");
            }
        },
        error: function(err){
            
        }
    });
});