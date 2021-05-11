$(()=>{
	
    afficherListePartenaires();
    afficherListePays();
   // afficherInfoEtudiant(); 


    $("#ajouterMobilité").click(function(){
    	
    	init_alert();
    	
        let preference = $("#preference").val();
        let partenaire = $('#select_partenaires').find(':selected').text();
    	let pays = $('#select_pays_creer_mobi').find(':selected').text();
        let quadrimestre = $("#quadrimestre").find(':selected').text();
        
        if(alert_num_ordre(preference)){
        	$("#preference").css("border-color", "red");
        	return;
        }
        
        let mobilite = {numOrdre: preference, nomPartenaire:partenaire, pays:pays, quadri: quadrimestre};
        let json = JSON.stringify(mobilite);
        
    	$.ajax({
    		url: '/',
            type: 'POST',
            data: {
                action: 'ajouterMobilite',
                json: json
            },
            success: function(response) {
            	$('#success_mob').append('ajoutée');
            	$('#success_mob').css('display', 'block');
                $("#preference").val("0");
            },
            error: function(e) {
            	$('#failed_mob').append('l\'ajout de la mobilité');
        		$('#failed_mob').css('display', 'block');
            }
    	});
    });
    
    $("#ajouterMobilitéComplet").click(function(){
    	
    	init_alert();
    	
        let preference = $("#preferenceComplet").val();
        let partenaire = $('#partenaireComplet').val();
    	let programme = $('#select_programmeComplet').find(':selected').text();
    	let code = $('#select_codeComplet').find(':selected').text();
    	let pays = $('#select_pays_creer_mobiComplet').find(':selected').text();
        let quadrimestre = $("#quadrimestreComplet").find(':selected').text();
        
        if(alert_num_ordre(preference)){
        	$("#preferenceComplet").css("border-color", "red");
        	return;
        }
        
        let mobilite = {numOrdre: preference, nomPartenaire:partenaire, typeDeMobilite:programme, code:code, pays:pays, quadri: quadrimestre};
        let json = JSON.stringify(mobilite);
        
        $.ajax({
    		url: '/',
            type: 'POST',
            data: {
                action: 'ajouterMobiliteComplet',
                programme: programme,
                json: json
            },
            success: function(response) {
            	if(response === 'ok'){
            		$('#success_mob').append('ajoutée');
            		$('#success_mob').css('display', 'block');
            	}
                $("#preferenceComplet").val("");
                $('#partenaireComplet').val("");
            },
            error: function(e){
            	$('#failed_mob').append('l\'ajout de la mobilité');
        		$('#failed_mob').css('display', 'block');
            }
        });
    });
    $(document).on('click','input[name=mod_mob]',function(){
    	
    	init_alert();
    	
    	let tr = $(this).closest('tr');
        
    	let idMobilite = tr.attr('name');
        let numOrdre = tr.find("td:eq(0) input").val();
        let typeDeMobilite = tr.find("td:eq(1) input").val();
        let code = tr.find("td [name=code]").val();
        let nomPartenaire = tr.find("td:eq(3) input").val(); 
        let pays = tr.find("td:eq(4) input").val();
        let quadri = tr.find("td [name=quadri]").val();

        if(alert_num_ordre(numOrdre)){
        	tr.children('td [name=numOrdre]').css("border-color", "red");
        	return;
        }
        let mobilite = {nomPartenaire:nomPartenaire, id_mobilite:idMobilite, numOrdre:numOrdre, pays:pays, typeDeMobilite:typeDeMobilite, 
        		code:code, quadri:quadri};
        let json = JSON.stringify(mobilite);
     
        $.ajax({
            url:'/',
                type: 'POST',
                data: {
                    action: 'modifierMob',
                    id: idMobilite,
                    json: json
                },      
                success: function(response){
                	if(response === 'ok'){
                		$('#success_mob').append('modifiée');
                		$('#success_mob').css('display', 'block');
                	}
                },
                error: function(e){
                	$('#failed_mob').append('la modification de la mobilité');
            		$('#failed_mob').css('display', 'block');
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

    $("#listeEtudiants").change(function(){
        var email = $('#listeEtudiants').find(':selected').val();
        $('#partenariatExistant table tbody').empty();
    	$('#partenariatNonExistant table tbody').empty();
        $.ajax({
            url:'/',
                type: 'POST',
                data: {
                    action: 'chargerMobiliteExistante',
                    email: email
                },
                success: function(response){
                	console.log(response);
                    const tbody = $('#partenariatExistant table tbody');
                    partenaire={};
                    var partenaire = JSON.parse(response);
                    console.log(partenaire);
                    for(let i=0 ; i<partenaire.length ; i++){
                        var tr = $('<tr name="'+partenaire[i].id_mobilite+'">');
                        var td = $('<td>');
                        td = $('<td name="nom'+[i]+'"><p></p></td>');
                        tr.append(td);
                        td = $('<td name="partenaire'+[i]+'"><p></p></td>');
                        tr.append(td);
                        td = $('<td><input type="button" name="conf" value="Confirmer"></td>');
                        tr.append(td);
                        tbody.append(tr);
                        $('input[name=nom'+[i]+']').text(partenaire[i].mail);
                        $('input[name=partenaire'+[i]+']').text(partenaire[i].nomPartenaire);
                    }
                    chargerMobNonExistant(email);
                },
                error: function(e){
                    console.log(e);
                }
        });
        
    });
    
    $(document).on('click','input[name=conf]',function(){
    	
    	init_alert();
    	
        let idMobilite = $(this).parent().parent().attr("name");
        $.ajax({
            url:'/',
                type: 'POST',
                data: {
                    action: 'confirmerMobilite',
                    idMobilite : idMobilite
                },      
                success: function(response){
                	if(response === 'ok'){
                		$('#success_mob').append('confirmée');
                		$('#success_mob').css('display', 'block');
                	}
                },
                error: function(e){
                	$('#failed_mob').append('la confirmation de la mobilité');
            		$('#failed_mob').css('display', 'block');
                }
        });
        
    });
    
    let tabMobilites={};
    
    $.ajax({
        url:'/',
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
    
});

function afficherListePartenaires(){
    let tabPays={};
 $.ajax({
     url: '/',
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

function init_alert(){
	$('#success_mob').css('display', 'none');
	$('#failed_mob').css('display', 'none');
	$('#success_mob').text('Mobilité ');
	$('#success_mob').css('background-color', 'green');
	$('#failed_mob').text('Echec de ');
	$('#error_ordre_num').css('display', 'none');
	$('#error_ordre_neg').css('display', 'none');
	$("#preference").css('border-color', '');
	$("#preferenceComplet").css('border-color', '');
}

function alert_num_ordre(preference){
	try{
    	if(preference === ''){
    		throw 'error_ordre_num';
    	}
    	else if(preference < 0){
    		throw 'error_ordre_neg';
    	}
    }
    catch(error){
    	if(error === "error_ordre_num"){
        	$('#error_ordre_num').css('display', 'block');
        }
    	else if(error === "error_ordre_neg"){
        	$('#error_ordre_neg').css('display', 'block');
        }
    	return true;
    }
}