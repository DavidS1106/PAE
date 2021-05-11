$(()=>{
	
	$.ajax({
        url: "/",
        type: 'POST',
        data:{
            action: 'etatDocuments',
        },
        success: function(response){
        	$('#tabEtatDocumentsEtudiant').show();
        	const documents = JSON.parse(response);

        	if(documents.contratBourse){
        		$('#tabEtatDocumentsEtudiant [name=contrat_bourse]').text('signé');
        	} else{
        		$('#tabEtatDocumentsEtudiant [name=contrat_bourse]').text('non-signé');
        	}
        	if(documents.contratBourse){
        		$('#tabEtatDocumentsEtudiant [name=convention_etude]').text('signé');
        	} else{
        		$('#tabEtatDocumentsEtudiant [name=convention_etude]').text('non-signé');
        	}
        	if(documents.contratBourse){
        		$('#tabEtatDocumentsEtudiant [name=convention_stage]').text('signé');
        	} else{
        		$('#tabEtatDocumentsEtudiant [name=convention_stage]').text('non-signé');
        	}
        	if(documents.contratBourse){
        		$('#tabEtatDocumentsEtudiant [name=charte]').text('signé');
        	} else{
        		$('#tabEtatDocumentsEtudiant [name=charte]').text('non-signé');
        	}
        	if(documents.contratBourse){
        		$('#tabEtatDocumentsEtudiant [name=doc_engagement]').text('signé');
        	} else{
        		$('#tabEtatDocumentsEtudiant [name=doc_engagement]').text('non-signé');
        	}
        	if(documents.contratBourse){
        		$('#tabEtatDocumentsEtudiant [name=demande_1_paiement]').text('signé');
        	} else{
        		$('#tabEtatDocumentsEtudiant [name=demande_1_paiement]').text('non-signé');
        	}
        	if(documents.contratBourse){
        		$('#tabEtatDocumentsEtudiant [name=paiement_1]').text('signé');
        	} else{
        		$('#tabEtatDocumentsEtudiant [name=paiement_1]').text('non-signé');
        	}
        	if(documents.contratBourse){
        		$('#tabEtatDocumentsEtudiant [name=infos_etudiant]').text('signé');
        	} else{
        		$('#tabEtatDocumentsEtudiant [name=infos_etudiant]').text('non-signé');
        	}
        	if(documents.contratBourse){
        		$('#tabEtatDocumentsEtudiant [name=infos_partenaire]').text('signé');
        	} else{
        		$('#tabEtatDocumentsEtudiant [name=infos_partenaire]').text('non-signé');
        	}
        	if(documents.contratBourse){
        		$('#tabEtatDocumentsEtudiant [name=attestation]').text('signé');
        	} else{
        		$('#tabEtatDocumentsEtudiant [name=attestation]').text('non-signé');
        	}
        	if(documents.contratBourse){
        		$('#tabEtatDocumentsEtudiant [name=releve_notes]').text('signé');
        	} else{
        		$('#tabEtatDocumentsEtudiant [name=releve_notes]').text('non-signé');
        	}
        	if(documents.contratBourse){
        		$('#tabEtatDocumentsEtudiant [name=certificat_stage]').text('signé');
        	} else{
        		$('#tabEtatDocumentsEtudiant [name=certificat_stage]').text('non-signé');
        	}
        	if(documents.contratBourse){
        		$('#tabEtatDocumentsEtudiant [name=rapport_final]').text('signé');
        	} else{
        		$('#tabEtatDocumentsEtudiant [name=rapport_final]').text('non-signé');
        	}
        	if(documents.contratBourse){
        		$('#tabEtatDocumentsEtudiant [name=certificat_stage]').text('signé');
        	} else{
        		$('#tabEtatDocumentsEtudiant [name=certificat_stage]').text('non-signé');
        	}
        	if(documents.contratBourse){
        		$('#tabEtatDocumentsEtudiant [name=demande_2_paiement]').text('signé');
        	} else{
        		$('#tabEtatDocumentsEtudiant [name=demande_2_paiement]').text('non-signé');
        	}
        	if(documents.contratBourse){
        		$('#tabEtatDocumentsEtudiant [name=paiement_2]').text('signé');
        	} else{
        		$('#tabEtatDocumentsEtudiant [name=paiement_2]').text('non-signé');
        	}
        },
        error: function(e){
        	$('#tabEtatDocumentsEtudiant').hide();
        }
    });
});