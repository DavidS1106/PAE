$(()=>{
    
	$('#tab_etudiant').hide();
    $('#tab_partenaire').hide();
    $('#tab_mobilite').hide();
    
    $('#recherche_etudiant').on('click', function(){
    	
    	$('#tab_etudiant').hide();
        $('#tab_partenaire').hide();
        $('#tab_mobilite').hide();
        $('#no_result').hide();
    	$('#empty_input').hide();
    	
    	let nom = $('#nom_etudiant').val();
    	let prenom = $('#prenom_etudiant').val();
    	
    	let etudiant = {nom:nom, prenom:prenom};
    	let json = JSON.stringify(etudiant);
    	
    	$.ajax({
    		url: '/',
    		type: 'POST',
    		data:{
    			action: 'rechercheEtudiant',
    			json: json
    		},
    		success: function(response){
    			$('#nom_etudiant').val("");
    	    	$('#prenom_etudiant').val("");
   				rechercheEtudiant(response);
    		},
    		error: function(error){
    			if(nom === "" && prenom === ""){
    				$('#empty_input').show();
                }
    			else{
    				$('#no_result').show();
    			}
    		}
    	});
    });
    
    $('#recherche_partenaire').on('click', function(){
    	
    	$('#tab_etudiant').hide();
        $('#tab_partenaire').hide();
        $('#tab_mobilite').hide();
        $('#no_result').hide();
    	$('#empty_input').hide();
    	
    	let nom = $('#nom_partenaire').val();
        let pays = $('#pays_partenaire').val();
        let ville = $('#ville_partenaire').val();
        
        let partenaire = {nomLegal:nom, nomAffaire:nom, nomComplet:nom, pays:pays, ville:ville};
        let json = JSON.stringify(partenaire);
    	
    	$.ajax({
    		url: '/',
    		type: 'POST',
    		data:{
    			action: 'recherchePartenaire',
                json: json
    		},
    		success: function(response){
    			recherchePartenaire(response);
    			$('#nom_partenaire').val("");
    	        $('#pays_partenaire').val("");
    	        $('#ville_partenaire').val("");
    		},
    		error: function(error){
    			if(nom === "" && pays === "" && ville === ""){
    				$('#empty_input').show();
                }
    			else{
    				$('#no_result').show();
    			}
    		}
    	});
    });

	$('#recherche_mobilite').on('click', function(){
		
		$('#tab_etudiant').hide();
	    $('#tab_partenaire').hide();
	    $('#tab_mobilite').hide();
	    $('#no_result').hide();
		$('#empty_input').hide();
		
		let annee = $('#annee_mobilite').val();
		let etat = $('#etat_mobilite').val();	
		
		$.ajax({
			url: '/',
			type: 'POST',
			data:{
				action: 'rechercheMobilite',
				annee: annee,
				etat: etat
			},
			success: function(response){
    			rechercheMobilite(response);
    			$('#annee_mobilite').val("");
    			$('#etat_mobilite').val("");
			},
			error: function(error){
				if(annee === ""){
    				$('#empty_input').show();
                }
    			else{
    				$('#no_result').show();
    			}
			}
		});
	});
});

function rechercheEtudiant(response){
	$('#tab_etudiant').show();
	$('#resultat_etudiant > tr').remove();
	const tbody = $('#resultat_etudiant');
    var etudiants = JSON.parse(response);
    
    for(let i = 0 ; i < etudiants.length ; i++){
    	var tr = $('<tr>');
    	var td = $('<td>');
    	td = $('<td>'+etudiants[i].nom+'</td>');
        tr.append(td);
        td = $('<td>'+etudiants[i].prenom+'</td>');
        tr.append(td);
        td = $('<td>'+etudiants[i].email+'</td>');
        tr.append(td);
        td = $('<td>'+etudiants[i].dateNaissance+'</td>');
        tr.append(td);
        td = $('<td>'+etudiants[i].nationalite+'</td>');
        tr.append(td);
        td = $('<td>'+etudiants[i].adresse+'</td>');
        tr.append(td);
        td = $('<td>'+etudiants[i].codePostal+'</td>');
        tr.append(td);
        td = $('<td>'+etudiants[i].sexe+'</td>');
        tr.append(td);
        td = $('<td>'+etudiants[i].statut+'</td>');
        tr.append(td);
        td = $('<td>'+etudiants[i].tel+'</td>');
        tr.append(td);
        td = $('<td>'+etudiants[i].departement+'</td>');
        tr.append(td);
        td = $('<td>'+etudiants[i].nbrAnneesEtudes+'</td>');
        tr.append(td);
        td = $('<td>'+etudiants[i].numCompteBancaire+'</td>');
        tr.append(td);
        td = $('<td>'+etudiants[i].titulaireCompte+'</td>');
        tr.append(td);
        td = $('<td>'+etudiants[i].nomBanque+'</td>');
        tr.append(td);
        td = $('<td>'+etudiants[i].codeBic+'</td>');
        tr.append(td);
        tbody.append(tr);
    }
}

function recherchePartenaire(response){
	$('#tab_partenaire').show();
	$('#resultat_partenaire > tr').remove();
	const tbody = $('#resultat_partenaire');
    var partenaires = JSON.parse(response);
    
    for(let i = 0 ; i < partenaires.length ; i++){
    	var tr = $('<tr>');
    	var td = $('<td>');
    	td = $('<td>'+partenaires[i].nomLegal+'</td>');
        tr.append(td);
        td = $('<td>'+partenaires[i].nomAffaire+'</td>');
        tr.append(td);
        td = $('<td>'+partenaires[i].nomComplet+'</td>');
        tr.append(td);
        td = $('<td>'+partenaires[i].pays+'</td>');
        tr.append(td);
        td = $('<td>'+partenaires[i].region+'</td>');
        tr.append(td);
        td = $('<td>'+partenaires[i].ville+'</td>');
        tr.append(td);
        td = $('<td>'+partenaires[i].adresse+'</td>');
        tr.append(td);
        td = $('<td>'+partenaires[i].codePostal+'</td>');
        tr.append(td);
        td = $('<td>'+partenaires[i].code+'</td>');
        tr.append(td);
        td = $('<td>'+partenaires[i].typeOrganisation+'</td>');
        tr.append(td);
        td = $('<td>'+partenaires[i].departement+'</td>');
        tr.append(td);
        td = $('<td>'+partenaires[i].nbEmploye+'</td>');
        tr.append(td);
        td = $('<td>'+partenaires[i].email+'</td>');
        tr.append(td);
        td = $('<td>'+partenaires[i].siteWeb+'</td>');
        tr.append(td);
        td = $('<td>'+partenaires[i].tel+'</td>');
        tr.append(td);
        tbody.append(tr);
    }
}

function rechercheMobilite(response){
	$('#tab_mobilite').show();
	$('#resultat_mobilite > tr').remove();
	const tbody = $('#resultat_mobilite');
    var mobilites = JSON.parse(response);
    
    for(let i = 0 ; i < mobilites.length ; i++){
    	var tr = $('<tr>');
    	var td = $('<td>');
    	td = $('<td>'+mobilites[i].mail+'</td>');
        tr.append(td);
        td = $('<td>'+mobilites[i].nom+'</td>');
        tr.append(td);
        td = $('<td>'+mobilites[i].prenom+'</td>');
        tr.append(td);
        td = $('<td>'+mobilites[i].nomPartenaire+'</td>');
        tr.append(td);
        td = $('<td>'+mobilites[i].pays+'</td>');
        tr.append(td);
        td = $('<td>'+mobilites[i].typeDeMobilite+'</td>');
        tr.append(td);
        td = $('<td>'+mobilites[i].code+'</td>');
        tr.append(td);
        td = $('<td>'+mobilites[i].numOrdre+'</td>');
        tr.append(td);
        td = $('<td>'+mobilites[i].anneeAcademique+'</td>');
        tr.append(td);
        td = $('<td>'+mobilites[i].quadri+'</td>');
        tr.append(td);
        td = $('<td>'+mobilites[i].dateIntro+'</td>');
        tr.append(td);
        tbody.append(tr);
    }
}