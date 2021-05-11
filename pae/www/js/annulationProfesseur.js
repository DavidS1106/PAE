$(()=>{
	
	$.ajax({
    	url:"/",
    	type:"POST",
    	data:{
    		action: 'getRaisons'
    	},
    	success:function(response){
    		var raisons = JSON.parse(response);
    		for (var i = 0; i < raisons.length; i++){
    			var option = $('<option>').val(raisons[i]).text(raisons[i]);
    			$('#raisons').append(option);
    		}
    	},
    	error: function(err){
    		
    	}
    });

    
    
	$('#tabAnnulationEtatDocuments').hide();
    $('#select_raisons').hide();
    $('#annulation_professeur').hide();
    $("#choisirEtudiantAnnulation").on("click", function(){
    	
        let etudiant = $('#etudiantsAnnulation').val();
        
        
        $.ajax({
            url: '/',
            type: 'POST',
            data:{
                action: 'etatDocuments',
                etudiant: etudiant
            },
            success: function(response){
            	$('#tabAnnulationEtatDocuments').show();
            	$('#select_raisons').show();
            	$('#annulation_professeur').show();
            	const documents = JSON.parse(response);
	
            	if(documents.contratBourse){
            		$('tabAnnulationEtatDocuments [name=contrat_de_bourse]').text('signé');
            	} else{
            		$('tabAnnulationEtatDocuments [name=contrat_de_bourse]').text('non-signé');
            	}
            	if(documents.conventionEtude){
            		$('tabAnnulationEtatDocuments [name=convention_d_etude]').text('signé');
            	} else{
            		$('tabAnnulationEtatDocuments [name=convention_d_etude]').text('non-signé');
            	}
            	if(documents.conventionStage){
            		$('tabAnnulationEtatDocuments [name=convention_stage]').text('signé');
            	} else{
            		$('tabAnnulationEtatDocuments [name=convention_stage]').text('non-signé');
            	}
            	if(documents.charteEtudiant){
            		$('tabAnnulationEtatDocuments [name=charte_etudiant]').text('signé');
            	} else{
            		$('tabAnnulationEtatDocuments [name=charte_etudiant]').text('non-signé');
            	}
            	if(documents.documentEngagement){
            		$('tabAnnulationEtatDocuments [name=document_engagement]').text('signé');
            	} else{
            		$('tabAnnulationEtatDocuments [name=document_engagement]').text('non-signé');
            	}
            	if(documents.premierPaiement){
            		$('tabAnnulationEtatDocuments [name=paiement_bourse]').text('signé');
            	} else{
            		$('tabAnnulationEtatDocuments [name=paiement_bourse]').text('non-signé');
            	}
            },
            error: function(e){
            	$('#tabAnnulationEtatDocuments').hide();
            }
        });
    });
    
    
    
    
});

$("#annulation_professeur").on("click", function(){
	
	if($("#raison").val() === ""){
        $("#alert").css("display", "block");
    }

    let id_raison = $("#raisons option:selected").val();
    let etudiant = $('#etudiantsAnnulation').val();

    $.ajax({
        url: '/',
        type: 'POST',
        data:{
            action: 'annulationMobilite',
            etudiant: etudiant,
            id_raison: id_raison
        },

        success: function(response){
            if(response === "ok"){
                $("#alert1").css("display", "none");
            }
        },
        error: function(e){
            console.log("probleme logout");
        }
    })
})