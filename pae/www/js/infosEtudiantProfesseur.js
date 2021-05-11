$(()=>{


	//prof
	// Infos Etudiants Profs
    $('#liste_etudiants').on('click', function(){
        chargerDonnees();
        
   });

    // Infos Etudiants Profs
    $.ajax({
    	url:"/",
    	type:"POST",
    	data:{
    		action: 'getStudentsProf'
    	},
    	success:function(response){
    		
    		
    		var etudiants = JSON.parse(response);
    		for (var i = 0; i < etudiants.length; i++){
    			var option = $('<option>').val(etudiants[i].email).text(etudiants[i].nom + " " + etudiants[i].prenom);
    			$('#listeEtudiants').append($('<option>').val(etudiants[i].email).text(etudiants[i].nom + " " + etudiants[i].prenom));
    			$('#liste_etudiants').append(option);
    			
    		}
            $('#listeEtudiants').val($("#listeEtudiants option:first").val());
    	},
    	error: function(err){
    		
    	}
    });
	
    
});

function chargerDonnees() {
    var email = $('#liste_etudiants').find(':selected').val();
    
   
    $.ajax({
        url: '/',
        type: 'POST',
        data: {
            action: 'recupererEtudiantSelectionne',
            email: email
        },
        success: function(response) {
            $('#etudiant_date_prof').attr("name","dateNaissanceaRenvoyer");
            jsonToForm($('#formulaire_infos'), response);
            $('#select_pays_etudiant_prof option[value="'+$('#natio_etudiant_prof').val()+'"]').attr('selected','selected');
            console.log("pays:"+$('#natio_etudiant').val());
        },
        error: function(e) {
            console.log(e.message);
        }
    });
    $('#etudiant_date_prof').attr("name","dateNaissance");
 }

$("#sauvegarder_infos").on("click",function(){
	
	console.log("sauvegarde en cours");
	$('#etudiant_date_prof').attr("name","dateNaissance");
    let email = $('#liste_etudiants').find(':selected').val();
    let nationalite = $('#select_pays_etudiant_prof option:selected').val();
    $('#natio_etudiant').val(""+nationalite+"");
    let date_naissance = $('#formulaire_infos input[name=dateNaissance]').val();
    let adresse = $('#formulaire_infos input[name=adresse]').val();
    let tel = $('#formulaire_infos input[name=tel]').val();
    let sexe = $('#formulaire_infos input[name=sexe_etudiant_prof]:checked').val();
    let statut = $('#formulaire_infos input[name=statut_etudiant_prof]:checked').val();
    let nbr_annee_etudes = $('#formulaire_infos input[name=nbrAnneesEtudes]').val();
    let num_compte_bancaire = $('#formulaire_infos input[name=numCompteBancaire]').val();
    let titulaire_compte = $('#formulaire_infos input[name=titulaireCompte]').val();
    let banque = $('#formulaire_infos input[name=nomBanque]').val();
    let code_bic = $('#formulaire_infos input[name=codeBic]').val();
    let departement = $('#formulaire_infos input[name=departement]').val();
    let numero = $('#formulaire_infos input[name=numero]').val();
    let code_postal = $('#formulaire_infos input[name=codePostal]').val();

    let etud={statut:statut,adresse:adresse,tel:tel,sexe:sexe,nbrAnneesEtudes:nbr_annee_etudes,
        numCompteBancaire:num_compte_bancaire,titulaireCompte:titulaire_compte,nomBanque:banque,codeBic:code_bic,departement:departement,
        numero:numero,codePostal:code_postal,nationalite:nationalite,email:email};

    let json=JSON.stringify(etud);

     $.ajax({
         url: '/',
         type: 'POST',
         data: {
             action: 'save_infos_prof',
             date_naissance: date_naissance,json:json
             
         },
         success: function(response) {
             chargerDonnees();
         },
         error: function(e) {
            $("#alert5").css("display", "block");
         }
     });
});