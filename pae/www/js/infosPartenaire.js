$(()=>{
	   
    
   // Infos Partenaires 
   $.ajax({
        url:"/",
        type:'POST',
        data:{
            action: 'get_partenaires_list'
        },
        success:function(response){
            var partenaires = JSON.parse(response);
            for (var i = 0; i < partenaires.length; i++){
                var option = $('<option>').val(partenaires[i].nomLegal).text(partenaires[i].nomLegal);
                $('#liste_partenaires').append(option);
            }
        },
        error: function(e){
            console.log(e);
        }
    });

    // Infos Partenaires 
    $('#liste_partenaires').on('click', function(){
    	
        var nom_legal = $('#liste_partenaires').find(':selected').val();
        
        
        $.ajax({
            url: '/',
            type: 'POST',
            data: {
                action: 'recupererDonneesPartenaireSelectionne',
                email: nom_legal
            },
            success: function(response) {
            	console.log("partenaire:"+response);
                jsonToForm($('#form_partenaire'), response);
                $('#select_pays_partenaire option[value="'+$('#pays_part').val()+'"]').attr('selected','selected');
            },
            error: function(e) {
                console.log(e.message);
            }
        });
    });
    
    
});

$('#sauvegarder_part').on("click",function(){
	
	$('#operation_succeeded').hide();
	$('input').css("border-color", "");
	$('#error_email').hide();
	$('#error_nb').hide();
	
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
    let mail = $('#form_partenaire input[name=email]').val();
      
    let partenaire = {nomLegal:nom_legal, nomAffaire:nom_affaire, nomComplet:nom_complet, typeOrganisation:type_org, adresse:adresse, pays:pays,
      codePostal:code_postal, ville:ville, siteWeb:site_web, tel:tel, code:code, departement:departement, email:mail};
    let json = JSON.stringify(partenaire);

    console.log("json partenaires : " + json);
    $.ajax({
        url: '/',
        type: 'POST',
        data: {
            action: 'save_info_part',
            json: json,
            nbEmploye : nbr_employe
        },
        success: function(response) {
        	$('#operation_succeeded').css('background-color', 'green');
    		$('#operation_succeeded').show();
        },
        error: function(error) {
        	let msg = error.responseText;
            if(msg === "error_email"){
            	$("#form_partenaire input[name=email]").css("border-color", "red");
            	$('#error_email').show();
            }
            if(msg === "error_nb"){
            	$("#form_partenaire input[name=nbEmploye]").css("border-color", "red");
            	$('#error_nb').show();
            }
        }
    });
});