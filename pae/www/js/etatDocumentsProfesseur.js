$(()=>{
	
	selectInscrits();
    
	$('#tabEtatDocuments').hide();
    $('#demande_premier_paiement').hide();
    $('#demande_second_paiement').hide();
    
    $("#choisirEtudiantEtatDocument").on("click", function(){
    	
        let etudiant = $('#etudiantsEtatDocuments').val();
        
        $.ajax({
            url: '/',
            type: 'POST',
            data:{
                action: 'etatDocuments',
                etudiant: etudiant
            },
            success: function(response){

            	$('#tabEtatDocuments').show();
            	const documents = JSON.parse(response);
            
            	$('#tabEtatDocuments [name=contrat_bourse]').prop('checked', documents.contratBourse);
            	$('#tabEtatDocuments [name=convention_etude]').prop('checked', documents.conventionEtude);
            	$('#tabEtatDocuments [name=convention_stage]').prop('checked', documents.conventionStage);
            	$('#tabEtatDocuments [name=charte]').prop('checked', documents.charteEtudiant);
            	$('#tabEtatDocuments [name=doc_engagement]').prop('checked', documents.documentEngagement);
            	$('#tabEtatDocuments [name=demande_1_paiement]').prop('checked', documents.demandePremierPaiement);
            	$('#tabEtatDocuments [name=paiement_1]').prop('checked', documents.premierPaiement);
            	$('#tabEtatDocuments [name=infos_etudiant]').prop('checked', documents.infoEtudiant);
            	$('#tabEtatDocuments [name=infos_partenaire]').prop('checked', documents.infoPartenaire);
            	$('#tabEtatDocuments [name=attestation]').prop('checked', documents.attestationSejour);
            	$('#tabEtatDocuments [name=releve_notes]').prop('checked', documents.releveNotes);
            	$('#tabEtatDocuments [name=certificat_stage]').prop('checked', documents.certificatStage);
            	$('#tabEtatDocuments [name=rapport]').prop('checked', documents.rapportFinal);
            	$('#tabEtatDocuments [name=demande_2_paiement]').prop('checked', documents.demandeSecondPaiement);
            	$('#tabEtatDocuments [name=paiement_2]').prop('checked', documents.secondPaiement);
            },
            error: function(e){
            	$('#tabEtatDocuments').hide();
            }
        });
    });
    
   $('#modifierEtatDocuments').on('click', function(){
    	let etudiant = $('#etudiantsEtatDocuments').val();
    	
    	let contratBourse = $('[name=contrat_bourse]').prop('checked');
		let conventionEtude = $('[name=convention_etude]').prop('checked');
		let conventionStage = $('[name=convention_stage]').prop('checked');
		let charteEtudiant = $('[name=charte]').prop('checked');
		let documentEngagement = $('[name=doc_engagement]').prop('checked');
		let demandePremierPaiement = $('[name=demande_1_paiement]').prop('checked');
		let premierPaiement = $('[name=paiement_1]').prop('checked');
		let infoEtudiant = $('[name=infos_etudiant]').prop('checked');
		let infoPartenaire = $('[name=infos_partenaire]').prop('checked');
		let attestationSejour = $('[name=attestation]').prop('checked');
		let releveNotes = $('[name=releve_notes]').prop('checked');
		let certificatStage = $('[name=certificat_stage]').prop('checked');
		let rapportFinal = $('[name=rapport]').prop('checked');
		let demandeSecondPaiement = $('[name=demande_2_paiement]').prop('checked');
		let secondPaiement = $('[name=paiement_2]').prop('checked');

    	let statut_mobilite = {contratBourse:contratBourse, conventionEtude:conventionEtude, conventionStage:conventionStage, charteEtudiant:charteEtudiant,
    			documentEngagement:documentEngagement, demandePremierPaiement:demandePremierPaiement, premierPaiement:premierPaiement, infoEtudiant:infoEtudiant, 
    			infoPartenaire:infoPartenaire, attestationSejour:attestationSejour, releveNotes:releveNotes, certificatStage:certificatStage, rapportFinal:rapportFinal,
    			demandeSecondPaiement:demandeSecondPaiement, secondPaiement:secondPaiement};
    	
    	let json = JSON.stringify(statut_mobilite);
    	
    	$.ajax({
        	url:"/Authentification",
        	type:"POST",
        	data:{
        		action: 'updateEtatDocuments',
        		etudiant: etudiant,
        		json: json
        	},
        	success:function(response){
        	},
        	error: function(err){
        		
        	}
        });
    });
});

