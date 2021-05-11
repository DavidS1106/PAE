let whoami = "";
let whereami = null;
//let recupererRetenirwhoami = retenirwhoami;



$(()=>{
    whoami = $('[name=bienvenue] > span').text("");
	
	loadPage('authentification');

	$.ajax({
        url: '/',
        type: 'POST',
        data:{
            action: 'refresh'
        },
        success: function(response){
            //whereami = response;
            //loadPage(response); 
            if(response !== 'authentification'){
                recupFonction();
                recupEtudiantMobilite();
                chargerMobiliteExistante();
                afficherListePays();
                voirInscrits();
                afficherInfoEtudiant(); // rajouter
                selectInscrits(); // rajoute
            }
        },
        error: function(e){   
            loadPage(e.responseText);
        }
    });

    $('[name=choixMobiliteNav]').on('click', function(){
    	val = this.title;
    	loadPage(val);
    });
    
    $('[name=confirmerMobiliteNav]').on('click', function(){
    	var val = this.title;
    	loadPage(val);
    });
    
    $('[name=confirmerMobiliteProfNav]').on('click', function(){
    	var val = this.title;
        loadPage(val);
        //afficherInfoEtudiant(); // rajouter
    });
    
    $('[name=demandeMobiliteCsvNav]').on('click', function(){
    	var val = this.title;
    	loadPage(val);
    });
    
    $('[name=etatDocumentsProfsNav]').on('click', function(){
    	var val = this.title;
        loadPage(val);
        //afficherInfoEtudiant(); // rajouter 
    });
    
    $('[name=etatDocumentsEtudiantNav]').on('click', function(){
        var val = this.title;
    	loadPage(val);
    });
    
    $('[name=infosEtudiantsNav]').on('click', function(){
    	var val = this.title;
    	loadPage(val);
    	 afficherInfoEtudiant();
    });
    
    $('[name=infosEtudiantsProfsNav]').on('click', function(){
    	var val = this.title;
        loadPage(val);
        chargerDonnees();
        // afficherListePays();
        // afficherInfoEtudiant(); // rajouter
    });
    
    $('[name=infosPartenairesNav]').on('click', function(){
    	var val = this.title;
    	loadPage(val);
    });
    
    $('[name=retourMobiliteNav]').on('click', function(){
    	var val = this.title;
        loadPage(val);
        //afficherInfoEtudiant(); // rajouter
    });
    
    $('[name=annulationEtudiantNav]').on('click', function(){
    	var val = this.title;
    	loadPage(val);
    	
    });
    
    $('[name=annulationProfesseurNav]').on('click', function(){
    	var val = this.title;
        loadPage(val);
        //afficherInfoEtudiant(); // rajouter
    });

    $('[name=rechercheEtudiantNav]').on('click', function(){
    	var val = this.title;
    	loadPage(val);
    });
    
    $('[name=rechercheProfNav]').on('click', function(){
    	var val = this.title;
    	loadPage(val);
    });
    
    $('[name=voirInscritsNav]').on('click', function(){
    	var val = this.title;
    	loadPage(val);
    	
    });
    
     //export en csv
     $('#submit_csv').on('click',function(){
		exportTableToCSV("mobilites.csv");
	});
     
     $(document).on('click','input[name=confExistant]',function(){
         let idMobilite = $(this).parent().parent().attr("name");
         var email = $('#listeEtudiants').find(':selected').val();
         console.log("conf");
         $.ajax({
             url:'/',
                 type: 'POST',
                 data: {
                     action: 'confirmerMobiliteExistant',
                     idMobilite : idMobilite,
                     email:email
                 },      
                 success: function(response){
                 },
                 error: function(e){
                     console.log(e);
                 }
         });
         
     });
	
});

function msg_alerte(message){
	
	$('#no_result').hide();
	$('#empty_input').hide();
	$('#operation_succeeded').hide();
	$('#operation_failed').hide();
	
	if(message === 'no_result'){
		$('#no_result').show();
	}	
	else if(message === 'empty_input'){
		$('#empty_input').show();
	}
	else if(message === 'ok'){
		$('#operation_succeeded').show();
		return false;
	}
	else if(message === 'ko'){
		$('#operation_failed').show();
	}
	else{
		return false;
	}
	
	return true;
}

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
        if($(p).css('display') == 'block'){
            whereami = $(p);
        }
		$(p).hide();
		if(p.id===idPage){
			$(p).show();
		}
    });
}
////NUM VERSION
/*function recupNumVersion(leChoix,mail){
	
	requetePrec= $.ajax({
        url:"/",
        type:"POST",
        data:{
            action:"getNumVersion",choix:leChoix,mail:mail
        },
        beforeSend:function(){
        	if(requetePrec!==null){
        		requetePrec.abort();
        		console.log("requete termine");
        	}
        },
        success : function(version,jqXHR){
        	console.log("retour du getNumVersion"+version);
            let json=JSON.parse(version);*/
           /* if(jqXHR.aborted){
            	console.log("returned");
            	return;
            }
    
            if(json[1]==="modifierMob"){
            	//numVersionMob=json[0];
            	console.log("numVersion mobilite "+numVersionMob);
            	 
            }*/
            /*if(json[1]==="etatDocuments"){
            	 numVersionDocument=json[0];
            	console.log("numVersion etat document "+numVersionDocument);
            	
            	$('#modifierEtatDocuments').on('click', clickEtatDoc);
            		
            }*/
           /* if(json[1]==="annulationMobilite"){
            	numVersionMobilite=json[0];
            	console.log("numVersion annulation "+numVersionMobilite);
            	//prof
            	$("#annulation_professeur").on("click", clickAnnulationProf);
                   
            	//eleve
            	$("#annulation_etudiant").on('click', clickAnnulationEtud);
 	*/
            /*}*/
            /*if(json[1]==="partenaire"){
            	numVersionPartenaire=json[0];
            	console.log("numVersionPart "+numVersionPartenaire);
            	
            	 $('#sauvegarder_part').on("click",clickPartenaire);
            	
            }*/
            /*if(json[1]==="etudiant_prof"){
            	
                 numVersionEtChoisis=json[0];
                 console.log("numVersionProf "+numVersionEtChoisis);

                 $("#sauvegarder_infos").on("click",clickEtudProf);
                    
            }
            if(json[1]==="etudiant"){
            	console.log("numVersionEt "+numVersionEt);
                numVersionEt=json[0];
               
                $("#sauvegarder").click(clickInfoEtud);
            }
            
        },
        error: function(e) {
            console.log(e.message);
        }
       
    });
}*/
///FIN NUM VERSION

function recupFonction(){
	$.ajax({
        url:"/",
        type:"POST",
        data:{
            action:"recupFonction"
        },
        success : function(response2){
            if(response2 == "choixMobilite") {
                loadPage(response2);
                $('[name=etudiant-navbar]').show();
                chargerMobNonExistant("e");
            }
            else {
                loadPage(response2);
                $("[name=prof-navbar]").show(); 
            }
            // if(response2 === "E"){
            //     loadPage('choixMobilite');
            //     $('[name=etudiant-navbar]').show();
            //     $("#partenariatExistant").hide();
            // }
            // else{
            //     loadPage('confirmerMobiliteProf');
            //     $("[name=prof-navbar]").show();  
            // }
        },
        error: function(e) {
            console.log(e.message);
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
        url:'/',
        type: 'POST',
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
                var input = $('<input>');
                td = $('<td><input name="numOrdre" type="number" value="'+mobilite[i].numOrdre+'"></td>');
                tr.append(td);
                td = $('<td><input name="typeDeMobilite" id="programme_mod'+[i]+'"></td>');
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
                console.log("programme"+mobilite[i].typeDeMobilite);
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

function chargerMobNonExistant(email){
	$('#partenariatNonExistant table tbody').empty();
    $.ajax({
        url:'/',
            type: 'POST',
            data: {
                action: 'chargerMobiliteNonExistante',
                email: email
            },
            success: function(response){
                const tbody = $('#partenariatNonExistant table tbody');
                partenaire={};
                var partenaire = JSON.parse(response);
                console.log(partenaire);
                for(let i=0 ; i<partenaire.length ; i++){
                    var tr = $('<tr name="'+partenaire[i].id_mobilite+'">');
                    var td = $('<td>');
                    td = $('<td name="2nom'+[i]+'"><p></p></td>');
                    tr.append(td);
                    td = $('<td name="2partenaire'+[i]+'"><p></p></td>');
                    tr.append(td);
                    td = $('<td><input type="button" name="confExistant" value="Confirmer"></td>');
                    tr.append(td);
                    tbody.append(tr);
                    $('input[name=2nom'+[i]+']').val(partenaire[i].mail);
                    $('input[name=2partenaire'+[i]+']').text(partenaire[i].nomPartenaire);
                    console.log(partenaire[i].nomPartenaire);
                }
            },
            error: function(e){
                console.log(e);
            }
    });
}

function chargerMobiliteExistante(){
    $.ajax({
        url:'/',
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
                    $('#partenaire'+[i]+'').text(partenaire[i].nomPartenaire);
                }
            },
            error: function(e){
                console.log(e);
            }
    });
}

function selectInscrits(){
    $.ajax({
    	url:"/",
    	type:"POST",
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
}



function afficherListePays(){
    let tabPays={};
 $.ajax({
     url: '/',
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
                 $('#select_pays_part').append('<option value="'+tabPays[i]+'">'+tabPays[i]+'</option>');
                 
             }
     },
     error: function(e) {
         console.log(e.message);
     }
 });
 return;
};

function voirInscrits(){
	$.ajax({
	    url:'/',
	        type: 'POST',
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
}

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