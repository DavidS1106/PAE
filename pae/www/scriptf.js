$(()=>{
    let whoami = "";

	loadPage('authentification');
	$.ajax({
        url: '/Authentification',
        type: 'POST',
        data:{
            action: 'refresh'
        },
        success: function(response){
            loadPage(response);
            afficherInfoEtudiant(); 
            if(response !== 'authentification'){
                recupFonction();
                recupEtudiantMobilite();
                chargerMobiliteExistante();
            }
        },
        error: function(e){   
            loadPage(e.responseText);
        }
    });

	afficherListePartenaires();
    $("#login").click(function(){
    	 
    	// if($("#email").val()===""){
        //  	$("#alert3").css("display", "block");
        //  }
    	
    	// if($("#mdp").val()===""){
        //  	$("#alert2").css("display", "block");
        //  }
    	
        let email=$("#email").val();
        let mdp=$("#mdp").val();

        $.ajax({
            url: '/Authentification',
            type: 'POST',
            data:{
                action: 'login',
                email: email,
                mdp: mdp
            },
           
            success: function(response){
            	//if(response ==="ok"){
                    if(response == "not ok") {
                        if(response == UNEXISTING_LOGIN) {
                            showErrorNotif($("#dataNotif"), "Cet utilisateur n'existe pas !");
                        }
                        else if(response == ERROR_LOGIN) {
                            showErrorNotif($("#emailNotif"), "Ce mail n'existe pas !");
                        }
                        else if(response == ERROR_EMAIL) {
                            showErrorNotif($("#emailNotif"), "Mail incorrect !");
                        }
                        else if(response == ERROR_MDP) {
                            showErrorNotif($("#mdpNotif"), "Password incorrect !");
                        }
                        else if(response == ERROR_INVALID_DATA) {
                            showErrorNotif($("#dataNotif"), "Données incorrect !");
                        }
                    }
                    else {
                        whoami = response;
                        $('h3#bienvenue > span').text("");
                        $('h3#bienvenue > span').append(whoami);
                        recupFonction();
                        loadPage('creerMobilite');
                        recupEtudiantMobilite();
                        chargerMobiliteExistante();
                    }    

                    
                    /// NE SUPRIMMEZ PAS CET APPEL DE METHODE \\\
                    afficherInfoEtudiant();
                }
            ,
            error: function(e){     
             	// if(($("#email").val()!=="" && $("#mdp").val()!=="")){
             	// 	$("#alert3").css("display", "none");
             	// 	$("#alert2").css("display", "none");
             	// 	$("#alert1").css("display", "block");
                //  }
                console.log(e.message);
            },
        });
    });
    
    $("#inscription").click(function(){
        let nom=$("#nom").val();
        let prenom=$("#prenom").val();
        let pseudo=$("#pseudo").val();
        let email=$("#email_inscription").val();
        let mdp=$("#password").val();
        $.ajax({
            url: '/Authentification',
            type: 'POST',
            data:{
                action: 'inscription',
                nom: nom,
                prenom: prenom,
                pseudo: pseudo,
                email: email,
                mdp: mdp
            },
            success: function(response){
                if(response==="ok"){
                    loadPage('authentification');
                    $("#nom").val("");
                    $("#prenom").val("");
                    $("#pseudo").val("");
                    $("#email_inscription").val("");
                    $("#password").val("");
                }
            },
            error: function(e){
            	  $("#alert4").css("display", "block");
                  console.log(e.message);
            }
        });
    });
    
    $.ajax({
        url:'/Authentification',
            type: 'GET',
            data: {
                action: 'getInscrits'
            },
            success: function(response){
                const tbody = $('#tabInscritsContenu');
                inscrits={};
                var inscrits = JSON.parse(response);
                for(let i=0 ; i<inscrits.length ; i++){
                	var tr = $('<tr name="'+inscrits[i].nom+'">');
                	var td = $('<td>');
                	td = $('<td id="nom'+[i]+'"><p></p></td>');
                    tr.append(td);
                    td = $('<td id="prenom'+[i]+'"><p></p></td>');
                    tr.append(td);
                    td = $('<td id="mail'+[i]+'"><p></p></td>');
                    tr.append(td);
                    tbody.append(tr);
                    $('#nom'+[i]+'').text(inscrits[i].nom);
                    $('#prenom'+[i]+'').text(inscrits[i].prenom);
                    $('#mail'+[i]+'').text(inscrits[i].mail);

                }
            },
            error: function(e){
                console.log(e);
            }
    });
    
    $("#ajouterMobilité").click(function(){
        let preference = $("#preference").val();
        let partenaire = $('#select_partenaires').find(':selected').text();
    	let pays = $('#select_pays_creer_mobi').find(':selected').text();
        let quadrimestre = $("#quadrimestre").find(':selected').text();
    	$.ajax({
    		url: '/Authentification',
            type: 'POST',
            data: {
                action: 'ajouterMobilite',
                preference: preference,
                programme : programme,
                type: type,
                partenaire: partenaire,
                pays: pays,
                quadrimestre :quadrimestre
            },
            success: function(response) {
                $("#preference").val("");
            },
            error: function(e) {
                console.log(e.message);
            }
    	});
    });
    
    $("#ajouterMobilitéComplet").click(function(){
        let preference = $("#preferenceComplet").val();
        let partenaire = $('#partenaireComplet').val();
    	let programme = $('#select_programmeComplet').find(':selected').text();
    	let code = $('#select_codeComplet').find(':selected').text();
    	let pays = $('#select_pays_creer_mobiComplet').find(':selected').text();
        let quadrimestre = $("#quadrimestreComplet").find(':selected').text();
        $.ajax({
    		url: '/Authentification',
            type: 'POST',
            data: {
                action: 'ajouterMobiliteComplet',
                preference: preference,
                partenaire: partenaire,
                pays: pays,
                quadrimestre :quadrimestre,
                code : code,
                programme, programme,
            },
            success: function(response) {
                $("#preferenceComplet").val("");
                $('#partenaireComplet').val("");
            },
            error: function(e){
                console.log(e);
            }
        });
    });
        $(document).on('click','input[name=mod_mob]',function(){
            let idMobilite = $(this).parent().parent().attr("name");
            let json = formToJSON($(this).parent().parent());
            console.log(json.nomPartenaire);
            console.log(json.typeDeMobilite);
            $.ajax({
                url:'/Authentification',
                    type: 'POST',
                    data: {
                        action: 'modifierMob',
                        idMobilite : idMobilite,
                        numOrdre : json.numOrdre,
                        nomPartenaire : json.nomPartenaire,
                        pays : json.pays,
                        typeDeMobilite : json.typeDeMobilite,
                        code : json.code,
                        quadri : json.quadri
                    },      
                    success: function(response){
                        console.log(response);
                    },
                    error: function(e){
                        $("#alertModifError").css("display", "block");
                    }
            });
            
        });

    /*$.ajax({
        url:'/Authentification',
            type: 'POST',
            data: {
                action: 'chargerMobiliteExistante'
            },
            success: function(response){
                const tbody = $('#partenariatNonExistant table tbody');
                partenaire={};
                var partenaire = JSON.parse(response);
                for(let i=0 ; i<partenaire.length ; i++){
                	var tr = $('<tr name="'+partenaire[i].nomPartenaire+'">');
                	var td = $('<td>');
                	td = $('<td id="nom'+[i]+'"><p></p></td>');
                    tr.append(td);
                    td = $('<td id="partenaire'+[i]+'"><p></p></td>');
                    tr.append(td);
                    td = $('<td><input type="button" name="conf" value="Confirmer"></td>');
                    tr.append(td);
                    tbody.append(tr);
                    $('#nom'+[i]+'').text(partenaire[i].nom);
                    $('#partenaire'+[i]+'').text(partenaire[i].nomPartenaire);
                }
            },
            error: function(e){
                console.log(e);
            }
    });*/
    
    $(document).on('click','input[name=conf]',function(){
            let idMobilite = $(this).parent().parent().attr("name");
            $.ajax({
                url:'/Authentification',
                    type: 'POST',
                    data: {
                        action: 'confirmerMobilite',
                        idMobilite : idMobilite
                    },      
                    success: function(response){
                    },
                    error: function(e){
                        console.log(e);
                    }
            });
            
    });

    
    // Infos Etudiants
    // nécéssaire pour la page Infos Etudiants
    afficherListePays();
    let numVersionEt=0;
    let numVersionEtChoisis=0;
    //changer whoami pour savoir si prof ou eleve
    recupNumVersion("etudiant","");
    function afficherInfoEtudiant(){
        
        $('#natio_etudiant').css('display','none');
        
        
        $.ajax({
            url: '/Authentification',
            type: 'POST',
            data: {
                action: 'get_etudiant'
            },
            success: function(response) {
                
                $('#etudiant_date').attr("name","dateNaissanceaRenvoyer");
            
                jsonToForm($('#formulaire_etudiant'), response);
                $('#select_pays_etudiant option[value="'+$('#natio_etudiant').val()+'"]').attr('selected','selected');
                recupNumVersion("etudiant","");
               
                
                            
            },
            error: function(e) {
                console.log(e.message);
            }
        });
        $('#etudiant_date').attr("name","dateNaissance");

        
    };
    
   function afficherListePays(){
       let tabPays={};
    $.ajax({
        url: '/Authentification',
        type: 'POST',
        data: {
            action: 'recupererListePays'
        },
        success: function(response) {
               
                tabPays=JSON.parse(response);
                
                for(let i=0;i<tabPays.length;i++){

                    $('#select_pays_creer_mobi').append('<option value="'+tabPays[i]+'">'+tabPays[i]+'</option>');
                    $('#select_pays_etudiant').append('<option value="'+tabPays[i]+'">'+tabPays[i]+'</option>');
                    $('#select_pays_etudiant_prof').append('<option value="'+tabPays[i]+'">'+tabPays[i]+'</option>');
                    $('#select_pays_creer_mobiComplet').append('<option value="'+tabPays[i]+'">'+tabPays[i]+'</option>');
                    
                }
        },
        error: function(e) {
            console.log(e.message);
        }
    });
    return;
   };
   
   function afficherListePartenaires(){
       let tabPays={};
    $.ajax({
        url: '/Authentification',
        type: 'POST',
        data: {
            action: 'recupererListePartenaires'
        },
        success: function(response) {
               
                tabPartenaires=JSON.parse(response);
                
                for(let i=0;i<tabPartenaires.length;i++){
                	
                    $('#select_partenaires').append('<option value="'+tabPartenaires[i]+'">'+tabPartenaires[i]+'</option>');
                    
                }
        },
        error: function(e) {
            console.log(e.message);
        }
    });
    return;
   };
 
   

     function chargerDonnees() {
        var email = $('#liste_etudiants').find(':selected').val();
        
        recupNumVersion("etudiant_prof",email);
        $.ajax({
            url: '/Authentification',
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

    // Infos Etudiants Profs
    $('#liste_etudiants').on('click', function(){
        chargerDonnees();
   });

    // Infos Etudiants Profs
    $.ajax({
    	url:"/Authentification",
    	type:"GET",
    	data:{
    		action: 'getStudentsProf'
    	},
    	success:function(response){
    		var etudiants = JSON.parse(response);
    		for (var i = 0; i < etudiants.length; i++){
    			var option = $('<option>').val(etudiants[i].email).text(etudiants[i].nom + " " + etudiants[i].prenom);
    			$('#liste_etudiants').append(option);
    		}
    	},
    	error: function(err){
    		
    	}
    });
    // fini info etudiants profs
    
    // Infos Partenaires 
    $('#sauvegarder_part').click(function() {
        let nom_legal = $('#form_partenaire input[name=nomLegal]').val();
        let nom_affaire = $('#form_partenaire input[name=nomAffaire]').val();
        let nom_complet = $('#form_partenaire input[name=nomComplet]').val();
        let type_org = $('#form_partenaire input[name=type_organisation]:checked').val();
        let nbr_employe = $('#form_partenaire input[name=nbEmploye]').val();
        let adresse = $('#form_partenaire input[name=adresse]').val();
        let pays = $('#form_partenaire input[name=pays]').val();
        let code_postal = $('#form_partenaire input[name=codePostal]').val();
        let ville = $('#form_partenaire input[name=ville]').val();
        let site_web = $('#form_partenaire input[name=siteWeb]').val();
        let tel = $('#form_partenaire input[name=tel]').val();
        let code = $('#form_partenaire input[name=code]').val();
        let departement = $('#form_partenaire input[name=departement]').val();
        let email = $('#liste_partenaires').find(':selected').val(); 
        $.ajax({
            url: '/Authentification',
            type: 'POST',
            data: {
                action: 'save_info_part',
                nom_legal: nom_legal,
                nom_affaire: nom_affaire,
                nom_complet: nom_complet,
                type_organisation: type_org,
                nbr_employe: nbr_employe,
                adresse: adresse,
                pays: pays,
                code_postal: code_postal,
                ville: ville,
                site_web: site_web,
                tel : tel,
                code: code,
                departement: departement,
                email: email
            },
            success: function(response) {
            },
            error: function(e) {
                console.log(e.message);
            }
        });
    });
    
    // Infos Partenaires 
    $.ajax({
    	url:"/Authentification",
    	type:'POST',
    	data:{
    		action: 'get_partenaires_list'
    	},
    	success:function(response){
            var partenaires = JSON.parse(response);
    		for (var i = 0; i < partenaires.length; i++){
    			var option = $('<option>').val(partenaires[i].email).text(partenaires[i].nomLegal);
                $('#liste_partenaires').append(option);
    		}
    	},
    	error: function(e){
    		console.log(e);
    	}
    });

    // Infos Partenaires 
    $('#liste_partenaires').on('click', function(){
        var email = $('#liste_partenaires').find(':selected').val();
        $.ajax({
            url: '/Authentification',
            type: 'POST',
            data: {
                action: 'recupererDonneesPartenaireSelectionne',
                email: email
            },
            success: function(response) {
                jsonToForm($('#form_partenaire'), response);

            },
            error: function(e) {
                console.log(e.message);
            }
        });
    });

    $('#creerMobiliteNav').on('click', function(){
    	val = this.title;
    	loadPage(val);
    });
    
    $('#confirmerMobiliteNav').on('click', function(){
    	var val = this.title;
    	loadPage(val);
    });
    
    $('#confirmerMobiliteNavProf').on('click', function(){
    	var val = this.title;
    	loadPage(val);
    });
    
    $('#demandeMobiliteCsvNav').on('click', function(){
    	var val = this.title;
    	loadPage(val);
    });
    
    $('#etatDocumentsNav').on('click', function(){
    	var val = this.title;
    	loadPage(val);
    });
    
    $('#etatDocumentsEtudiantNav').on('click', function(){
    	var val = this.title;
    	loadPage(val);
    });
    
    $('#infosEtudiantsNav').on('click', function(){
    	var val = this.title;
    	loadPage(val);
    });
    
    $('#infosEtudiantsProfsNav').on('click', function(){
    	var val = this.title;
    	loadPage(val);
    });
    
    $('#infosPartenairesNav').on('click', function(){
    	var val = this.title;
    	loadPage(val);
    });
    
    $('#retourMobiliteNav').on('click', function(){
    	var val = this.title;
    	loadPage(val);
    });
    
    $('#annulationEtudiantNav').on('click', function(){
    	var val = this.title;
    	loadPage(val);
    });
    
    $('#annulationProfesseurNav').on('click', function(){
    	var val = this.title;
    	loadPage(val);
    });
    
    $("#annulation_etudiant").on('click', function(){

        if($("#raison").val() === ""){
            $("#alert").css("display", "block");
        }
        
        let raison = $("#raison").val();

        $.ajax({
            url: '/Authentification',
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
    
    $.ajax({
    	url:"/Authentification",
    	type:"GET",
    	data:{
    		action: 'getInscrits'
    	},
    	success:function(response){
    		var etudiants = JSON.parse(response);
    		for (var i = 0; i < etudiants.length; i++){
    			var option1 = $('<option>').val(etudiants[i].mail).text(etudiants[i].prenom + " " + etudiants[i].nom);
    			var option2 = $('<option>').val(etudiants[i].mail).text(etudiants[i].prenom + " " + etudiants[i].nom);
    			$('.etudiantsEtatDocuments').append(option1);
    			$('.etudiantsAnnulation').append(option2);
    		}
    	},
    	error: function(err){
    		
    	}
    });
    
    $('#tabEtatDocuments').hide();
    $('#demande_premier_paiement').hide();
    $('#demande_second_paiement').hide();
    
    $("#choisirEtudiantEtatDocument").on("click", function(){

        let etudiant = $('#etudiantsEtatDocuments').val();

        $.ajax({
            url: '/Authentification',
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
    
    $.ajax({
        url: '/Authentification',
        type: 'GET',
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
    
    $('#modifierEtatDocuments').on('click', function(){
    	let etudiant = $('#etudiantsEtatDocuments').val();

    	$.ajax({
        	url:"/Authentification",
        	type:"POST",
        	data:{
        		action: 'updateEtatDocuments',
        		etudiant: etudiant,
        		contratBourse: $('[name=contrat_bourse]').prop('checked'),
    			conventionEtude: $('[name=convention_etude]').prop('checked'),
    			conventionStage: $('[name=convention_stage]').prop('checked'),
    			charteEtudiant: $('[name=charte]').prop('checked'),
    			documentEngagement: $('[name=doc_engagement]').prop('checked'),
    			demandePremierPaiement: $('[name=demande_1_paiement]').prop('checked'),
    			premierPaiement: $('[name=paiement_1]').prop('checked'),
    			infoEtudiant: $('[name=infos_etudiant]').prop('checked'),
    			infoPartenaire: $('[name=infos_partenaire]').prop('checked'),
    			attestationSejour: $('[name=attestation]').prop('checked'),
    			releveNotes: $('[name=releve_notes]').prop('checked'),
    			certificatStage: $('[name=certificat_stage]').prop('checked'),
    			rapportFinal: $('[name=rapport]').prop('checked'),
    			demandeSecondPaiement: $('[name=demande_2_paiement]').prop('checked'),
    			secondPaiement: $('[name=paiement_2]').prop('checked')
        	},
        	success:function(response){
        		
        	},
        	error: function(err){
        		
        	}
        });
    });
    
    $.ajax({
    	url:"/Authentification",
    	type:"GET",
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

    $("#annulation_professeur").on("click", function(){
        if($("#raison").val() === ""){
            $("#alert").css("display", "block");
        }

        let id_raison = $("#raisons option:selected").val();
        let etudiant = $('#etudiantsAnnulation').val();

        $.ajax({
            url: '/Authentification',
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
    });
    
    $('#tabAnnulationEtatDocuments').hide();
    $('#select_raisons').hide();
    $('#annulation_professeur').hide();
    $("#choisirEtudiantAnnulation").on("click", function(){

        let etudiant = $('#etudiantsAnnulation').val();

        $.ajax({
            url: '/Authentification',
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
    
    $('#rechercheNavEtudiant').on('click', function(){
    	var val = this.title;
    	loadPage(val);
    });
    
    $('#rechercheNavProf').on('click', function(){
    	var val = this.title;
    	loadPage(val);
    });
    
    $('#voirInscritsNav').on('click', function(){
    	var val = this.title;
    	loadPage(val);
    });
    
    $('#tab_etudiant').hide();
    $('#tab_partenaire').hide();
    $('#tab_mobilite').hide();
    
    $('#recherche_etudiant').on('click', function(){
    	
    	$('#tab_etudiant').hide();
        $('#tab_partenaire').hide();
        $('#tab_mobilite').hide();
    	
    	let nom = $('#nom_etudiant').val();
    	let prenom = $('#prenom_etudiant').val();
    	
    	$('#nom_etudiant').val("");
    	$('#prenom_etudiant').val("");
    	
    	$.ajax({
    		url: '/Authentification',
    		type: 'POST',
    		data:{
    			action: 'rechercheEtudiant',
    			nom: nom,
    			prenom: prenom
    		},
    		success: function(response){
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
    		},
    		error: function(error){
    			
    		}
    	});
    });
    
    $('#recherche_partenaire').on('click', function(){
    	
    	$('#tab_etudiant').hide();
        $('#tab_partenaire').hide();
        $('#tab_mobilite').hide();
    	
    	let nom = $('#nom_partenaire').val();
        let pays = $('#pays_partenaire').val();
        let ville = $('#ville_partenaire').val();
        
        $('#nom_partenaire').val("");
        $('#pays_partenaire').val("");
        $('#ville_partenaire').val("");
    	
    	$.ajax({
    		url: '/Authentification',
    		type: 'POST',
    		data:{
    			action: 'recherchePartenaire',
    			nom: nom,
                pays: pays,
                ville: ville
    		},
    		success: function(response){
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
    		},
    		error: function(error){
    			
    		}
    	});
    });

	$('#recherche_mobilite').on('click', function(){
		
		$('#tab_etudiant').hide();
	    $('#tab_partenaire').hide();
	    $('#tab_mobilite').hide();
		
		let annee = $('#annee_mobilite').val();
		let etat = $('#etat_mobilite').val();
		
		$('#annee_mobilite').val("");
		$('#etat_mobilite').val("");
		
		$.ajax({
			url: '/Authentification',
			type: 'POST',
			data:{
				action: 'rechercheMobilite',
				annee: annee,
				etat: etat
			},
			success: function(response){
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
			},
			error: function(error){
				
			}
		});
	});
   
    $('#disco-button-etudiant').on('click', function(){
    	$.ajax({
            url: '/Authentification',
            type: 'POST',
            data:{
                action: 'logout'
            },

            success: function(response){
                if(response === "ok"){
                    whoami = "";
                    $('h3#bienvenue > span').append(whoami);
                    loadPage('authentification');
                	$("#etudiant-navbar").hide();
                    $("#prof-navbar").hide();  
                  }
                else if(response === "ko"){
                	$("#alert2").css("display", "block");
                }
            },
            error: function(err){
                
            }
        });
    });
    
    $('#disco-button-prof').on('click', function(){
    	$.ajax({
            url: '/Authentification',
            type: 'POST',
            data:{
                action: 'logout'
            },

            success: function(response){
                if(response === "ok"){
                    loadPage('authentification');
                	$("#etudiant-navbar").hide();
                    $("#prof-navbar").hide();  
                    whoami = "";  
                }
                else if(response === "ko"){
                	$("#alert2").css("display", "block");
                }
            },
            error: function(err){
                
            }
        });
    });
    
    let tabMobilites={};
    
    $.ajax({
        url:'/Authentification',
        type:'POST',
        data:{action:'avoirMobilites'},
        success: function(json){
        	 
            
			tabMobilites=JSON.parse(json);
			afficherDemandesMobilites(tabMobilites);
			
        },
        error: function(e){
            console.log(e.message);
        }
    });
    
    function afficherDemandesMobilites(tableau){
    	let n=0;
    	for(let i=0;i<tableau.length;i++){
    		$('#contenu_tab').append('<tr></tr>');
    		
    	}
    	//$('tbody tr').each(function(){
		$('#contenu_tab > tr').each(function(){
    		
    		for(let k=0;k<9;k++){
    			choisirBonChamp($(this),k,tableau,n);
			}
    		
    		n++;	
    	});
    }
    
    
    function choisirBonChamp(tr,k,tableau,n){
    	
		
    	if(k===0){
    		$(tr).append('<td>'+tableau[n].nom+' '+tableau[n].prenom+'</td>');
    	}
    	if(k===1){
    		$(tr).append('<td>'+tableau[n].dateIntro.dayOfMonth+'/'+tableau[n].dateIntro.monthValue+'/'+tableau[n].dateIntro.year+'</td>');
    	}
    	if(k===2){
    		$(tr).append('<td>'+tableau[n].numOrdre+'</td>');
    	}
    	if(k===3){
    		if(tableau[n].typeDeMobilite==null){
    			$(tr).append('<td></td>');
    		}
    		else{
    			$(tr).append('<td>'+tableau[n].typeDeMobilite+'</td>');
    		}
    		
    	}
    	if(k===4){
    		if(tableau[n].code==null){
    			$(tr).append('<td></td>');
    		}
    		else{
    			$(tr).append('<td>'+tableau[n].code+'</td>');
    		}
    		
    	}
    	if(k===5){
    		if(tableau[n].nomPartenaire==null){
    			$(tr).append('<td></td>');
    		}
    		else{
    			$(tr).append('<td>'+tableau[n].nomPartenaire+'</td>');
    		}
    		
    	}
    	if(k===6){
    		if(tableau[n].quadri==0){
    			$(tr).append('<td></td>');
    		}
    		else{
    			$(tr).append('<td>'+tableau[n].quadri+'</td>');
    		}
    		
    	}
    	if(k===7){
    		$(tr).append('<td>Informatique de gestion</td>');
    	}
    	if(k===8){
    		if(tableau[n].pays==null){
    			$(tr).append('<td></td>');
    		}
    		else{
    			$(tr).append('<td>'+tableau[n].pays+'</td>');
    		}
    		
    	}
    }
    
    //export en csv
    $('#submit_csv').on('click',function(){
		exportTableToCSV("mobilites.csv");
	});
	function downloadCSV(csv, filename) {
		var csvFile;
		var downloadLink;
	
		// CSV file
		csvFile = new Blob([csv], {type: "text/csv"});
	
		// Download link
		downloadLink = document.createElement("a");
	
		// File name
		downloadLink.download = filename;
	
		// Create a link to the file
		downloadLink.href = window.URL.createObjectURL(csvFile);
	
		// Hide download link
		downloadLink.style.display = "none";
	
		// Add the link to DOM
		document.body.appendChild(downloadLink);
	
		// Click download link
		downloadLink.click();
	}
	function exportTableToCSV(filename) {
		var csv = [];
		var rows = document.querySelectorAll("#tableBalises tr");
		
		for (var i = 0; i < rows.length; i++) {
			var row = [], cols = rows[i].querySelectorAll("td, th");
			
			for (var j = 0; j < cols.length; j++) 
				row.push(cols[j].innerText);
			
			csv.push(row.join(","));        
		}
	
		// Download CSV file
		downloadCSV(csv.join("\n"), filename);
	}
});

function jsonToForm(formulaire, json) {
    
    var dict = JSON.parse(json);
    
    for(var key in dict) {
        formulaire.find("[name=" + key + "]").val(dict[key]);
    }
    if(dict.sexe=="M"){
        $('#sex_m').prop("checked",true);
    }
    if(dict.sexe=="F"){
        $('#sex_f').prop("checked",true);
        
    }
    if(dict.statut=="M"){
        $('#statut_m').prop("checked",true);
        
    }
    if(dict.statut=="Mme"){
        $('#statut_mme').prop("checked",true);
        
    }
    if(dict.statut=="Mlle"){
        $('#statut_mlle').prop("checked",true);
    }

    if(dict.sexe=="M"){
        $('#sex_m_prof').prop("checked",true);
    }
    if(dict.sexe=="F"){
        $('#sex_f_prof').prop("checked",true);
        
    }
    if(dict.statut=="M"){
        $('#statut_m_prof').prop("checked",true);
        
    }
    if(dict.statut=="Mme"){
        $('#statut_mme_prof').prop("checked",true);
        
    }
    if(dict.statut=="Mlle"){
        $('#statut_mlle_prof').prop("checked",true);
    }
    if(dict.typeOrganisation == "TPE") {
        $('#type_tpe').prop("checked",true);
    }
    if(dict.typeOrganisation == "PME") {
        $('#type_pme').prop("checked",true);
    }
    if(dict.typeOrganisation == "ETI") {
        $('#type_eti').prop("checked",true);
    }
    if(dict.typeOrganisation == "TGE") {
        $('#type_tge').prop("checked",true);
    }
};

function JSONToTab(tab,json){
    var dict = JSON.parse(json);
    for(var key in dict) {
        tab.find("input[name=" + key + "]").val(dict[key]);
    }
};

function loadPage(idPage){
	const pages = document.querySelectorAll(".page");

	pages.forEach(p => {
		$(p).hide();
		if(p.id===idPage){
			$(p).show();
		}
    });
}
function recupNumVersion(leChoix,mail){
    $.ajax({
        url:"/Authentification",
        type:"POST",
        data:{
            action:"getNumVersion",choix:leChoix,mail:mail,
        },
        success : function(version){
            let json=JSON.parse(version);
            if(json[1]="etudiant_prof"){
                 numVersionEtChoisis=json[0];
                 

                 $("#sauvegarder_infos").click(function() {
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
                        numero:numero,codePostal:code_postal,nationalite:nationalite,numVersion:numVersionEtChoisis,email:email};

                    let json=JSON.stringify(etud);
                
                     $.ajax({
                         url: '/Authentification',
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
            }
            if(json[1]==="etudiant"){
                
                numVersionEt=json[0];
               
                $("#sauvegarder").click(function() {
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
                    
    
                    let etud={statut:statut,adresse:adresse,tel:tel,sexe:sexe,nbrAnneesEtudes:nbr_annee_etudes,
                        numCompteBancaire:num_compte_bancaire,titulaireCompte:titulaire_compte,nomBanque:banque,codeBic:code_bic,departement:departement,
                    numero:numero,codePostal:code_postal,nationalite:nationalite,numVersion:numVersionEt};
                    let json=JSON.stringify(etud);
                    $.ajax({
                        url: '/Authentification',
                        type: 'POST',
                        data: {
                            action: 'save_info',json:json,
                            date_naissance: date_naissance
                        },
                        success: function(response) {
                            afficherInfoEtudiant();
                            
                        },
                        error: function(e) {
                            $("#alert5").css("display", "block");
                        }
                    });
                });
            }
            
            
        },
        error: function(e) {
            console.log(e.message);
            
        }
    });

   

}
function recupFonction(){
	$.ajax({
	url:"/Authentification",
    type:"POST",
	data:{
		action:"recupFonction"
	},
	success : function(response2){
		
		if(response2 === "E"){
			$("#etudiant-navbar").show();
            $("#partenariatExistant").hide();
		}
		else{
            $("#prof-navbar").show();
            
		}
	}
	}); 
}

function showErrorNotif(notif, messageError){
    notif.text(messageError);
    notif.show();
}

function formToJSON(racine){
    var dico={};

    $(racine).find('input[type=number], [type=text], [type=password], textarea').each(function(){
		if($(this).val() !== "")
			dico[$(this).attr('name')] = $(this).val();
    });
    
    $(racine).find('input[type=radio]').each(function(){
        dico[$(this).attr('name')]=$(this).is(':checked');
    });

    $(racine).find('input[type=checkbox]').each(function(){
        if($(this).is(':checked')){
            dico[$(this).attr('name')]=$(this).val();
        }else{
            dico[$(this).attr('name')]=null;
        }
    });

    /*$(racine).find('select:not([multiple]) option:selected').each(function(){
        dico[$(this).attr('name')]=$(this).val();
    });*/
    $(racine).find('select').each(function(i, el) {
		var selected = $(el).find('option:selected');
		dico[$(el).attr('name')] = $(el).val();
	});

    return dico;
}

function recupEtudiantMobilite(){
    $.ajax({
        url:'/Authentification',
        type: 'GET',
        data: {
            action: 'recupEtudiantMobilite'
        },
        success: function(response){
            const tbody = $('#ancien table tbody');
            mobilite={};
            var mobilite = JSON.parse(response);
            console.log(mobilite);
            for(let i=0 ; i<mobilite.length ; i++){

                var tr = $('<tr name="'+mobilite[i].id_mobilite+'">');
                var td = $('<td>');
                td = $('<td><input name="numOrdre" type="number" id="preference_mod'+[i]+'"></td>');
                tr.append(td);
                td = $('<td><select name="typeDeMobilite" id="programme_mod'+[i]+'"><option value="Erasmus+">Erasmus+</option><option value="Erabel">Erabel</option><option value="Fame">Fame</option></select></td>');
                tr.append(td);
                td = $ ('<td><select name="code" id="type_mod'+[i]+'"><option value="SMS">SMS</option><option value="SMP">SMP</option</select></td>');
                tr.append(td);
                td = $('<td><input name="nomPartenaire" type="text" id="partenaire_mod'+[i]+'"></td>');
                tr.append(td);
                td = $('<td><input name="pays" type="text" id="pays_mod'+[i]+'"></td>');
                tr.append(td);
                td = $('<td><select name="quadri" id="quadrimestre_mod'+[i]+'"><option value="1">1</option><option value="2">2</option></select></td>');
                tr.append(td);
                td = $('<td><input type="button" name="mod_mob" value="Modifier"></td>');
                tr.append(td);
                tbody.append(tr);
                
                $('#preference_mod'+[i]+'').val(mobilite[i].numOrdre);
                $('#programme_mod'+[i]+'').val(mobilite[i].typeDeMobilite);
                console.log(mobilite[i].code);

                $('#type_mod'+[i]+'').val(mobilite[i].code);
                $('#partenaire_mod'+[i]+'').val(mobilite[i].nomPartenaire);
                $('#pays_mod'+[i]+'').val(mobilite[i].pays);
                $('#quadrimestre_mod'+[i]+'').val(mobilite[i].quadri);
            }
        },
        error: function(e){
            console.log(e);
        }
    });
}

function chargerMobiliteExistante(){
    $.ajax({
        url:'/Authentification',
            type: 'POST',
            data: {
                action: 'chargerMobiliteExistante'
            },
            success: function(response){
                const tbody = $('#partenariatNonExistant table tbody');
                partenaire={};
                var partenaire = JSON.parse(response);
                console.log(partenaire);
                for(let i=0 ; i<partenaire.length ; i++){
                	var tr = $('<tr name="'+partenaire[i].id_mobilite+'">');
                	var td = $('<td>');
                	td = $('<td id="nom'+[i]+'"><p></p></td>');
                    tr.append(td);
                    td = $('<td id="partenaire'+[i]+'"><p></p></td>');
                    tr.append(td);
                    td = $('<td><input type="button" name="conf" value="Confirmer"></td>');
                    tr.append(td);
                    tbody.append(tr);
                    $('#nom'+[i]+'').text(partenaire[i].mail);
                    if(partenaire[i].nomPartenaire==null){
                        $('#partenaire'+[i]+'').text("Non-défini");
                    }else{
                        $('#partenaire'+[i]+'').text(partenaire[i].nomPartenaire);
                    }
                }
            },
            error: function(e){
                console.log(e);
            }
    });
}

function msg_alerte(message){
	
}

