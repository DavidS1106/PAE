$(()=>{
	
	// afficherListePays();
    // afficherInfoEtudiant();    
    
});

function afficherInfoEtudiant(){    
    $('#natio_etudiant').css('display','none');
	    $.ajax({
	        url: '/',
	        type: 'POST',
	        data: {
	            action: 'get_etudiant'
	        },
	        success: function(response) {
	            
	            $('#etudiant_date').attr("name","dateNaissanceaRenvoyer");
	        
	            jsonToForm($('#formulaire_etudiant'), response);
	            $('#select_pays_etudiant option[value="'+$('#natio_etudiant').val()+'"]').attr('selected','selected');
	                
	        },
	        error: function(e) {
	            console.log(e.message);
	        }
	    });
	    $('#etudiant_date').attr("name","dateNaissance");
};
  
    
$("#sauvegarder").on('click',function(){
    	
    
	
	$('input').css('border-color', '');
	$('#error_nb_etud').css('display', 'none');
	$('#error_tel').css('display', 'none');
	$('#error_compte').css('display', 'none');
	$('#success_save_etud').css('display', 'none');
	$('#error_save_etud').css('display', 'none');
	$('#error_code').css('display', 'none');
	$('#error_nb_etud').text('Erreur format numérique : ');
	
	$('#etudiant_date').attr("name","dateNaissance");
    let nationalite = $('#select_pays_etudiant option:selected').val();
    $('#natio_etudiant').val(""+nationalite+"");
    let date_naissance = $('#formulaire_etudiant input[name=dateNaissance]').val();
    let adresse = $('#formulaire_etudiant input[name=adresse]').val();
    let tel = $('#formulaire_etudiant input[name=tel]').val();
    let sexe = $('#formulaire_etudiant input[name=sexe_etudiant]:checked').val();
    let statut = $('#formulaire_etudiant input[name=statut_etudiant]:checked').val();
    let nbr_annee_etudes = $('#formulaire_etudiant input[name=nbrAnneesEtudes]').val();
    let num_compte_bancaire = $('#formulaire_etudiant input[name=numCompteBancaire]').val();
    let titulaire_compte = $('#formulaire_etudiant input[name=titulaireCompte]').val();
    let banque = $('#formulaire_etudiant input[name=nomBanque]').val();
    let code_bic = $('#formulaire_etudiant input[name=codeBic]').val();
    let departement = $('#formulaire_etudiant input[name=departement]').val();
    let numero = $('#formulaire_etudiant input[name=numero]').val();
    let code_postal = $('#formulaire_etudiant input[name=codePostal]').val();
    
    let stop = false;
    let etud={statut:statut,adresse:adresse,sexe:sexe,
        numCompteBancaire:num_compte_bancaire,titulaireCompte:titulaire_compte,nomBanque:banque,codeBic:code_bic,departement:departement,codePostal:code_postal,nationalite:nationalite};
    
    try{
    	if(nbr_annee_etudes === '' || nbr_annee_etudes < 0){
    		throw 'error_nb';
    	}
    	etud.nbrAnneesEtudes = nbr_annee_etudes;
    }
    catch(error){
    	if(error === "error_nb"){
        	$("#formulaire_etudiant input[name=nbrAnneesEtudes]").css("border-color", "red");
        	$('#error_nb_etud').css('display', 'block');
        	$('#error_nb_etud').append('Nombre années d\'études | ');
        }
    	stop = true;
    }
    try{
    	if(numero === '' || numero < 0){    
    		throw 'error_num';
    	}
    	etud.numero = numero;
    }
    catch(error){
    	if(error === "error_num"){
        	$("#formulaire_etudiant input[name=numero]").css("border-color", "red");
        	$('#error_nb_etud').css('display', 'block');
        	$('#error_nb_etud').append('Numéro');
        }
    	stop = true;
    }
    try{
    	console.log('tel'+tel);
    	if(!tel.match(eval('/^[+]?\\d{9,10}$/')) && tel !== ''){
    		throw 'error_tel';
    	}
    	etud.tel = tel;
    }
    catch(error){
    	if(error === "error_tel"){
        	$("#formulaire_etudiant input[name=tel]").css("border-color", "red");
        	$('#error_tel').css('display', 'block');
        }
    	stop = true;
    }
    try{
    	console.log(num_compte_bancaire);
    	if(!num_compte_bancaire.match(eval('/^[A-Z]{2}[0-9]{2}\\s?[0-9]{4}\\s?[0-9]{4}\\s?[0-9]{4}$/')) && num_compte_bancaire != ''){
    		throw 'error_compte';
    	}
    }
    catch(error){
    	if(error === "error_tel"){
        	$("#formulaire_etudiant input[name=tel]").css("border-color", "red");
        	$('#error_tel').css('display', 'block');
        }
    	stop = true;
    }
    try{
    	if(!code_bic.match(eval('/^[A-Z]{8}$/')) && code_bic !== ''){
    		throw 'error_code';
    	}
    }
    catch(error){
    	if(error === 'error_code'){
    		$("#formulaire_etudiant input[name=codeBic]").css("border-color", "red");
        	$('#error_code').css('display', 'block');
    	}
    	stop = true;
    }
    if(stop){
    	return;
    }
    let json=JSON.stringify(etud);
    
    $.ajax({
        url: '/',
        type: 'POST',
        data: {
            action: 'save_info',
            json:json,
            date_naissance: date_naissance
        },
        success: function(response) {
       		afficherInfoEtudiant();
       		$('#success_save_etud').css('background-color', 'green');
       		$('#success_save_etud').css('display', 'block');
        },
        error: function(error) {
        	if(error.responseText === 'ko_save_etud'){
        		$('#error_save_etud').css('display', 'block');
        	}
        }
    });
});
