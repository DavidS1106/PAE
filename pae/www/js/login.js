$(()=>{
   
    $("#login").on('click', function(){

        let email=$("#email").val();
        let mdp=$("#mdp").val();
        
        let inscrit = {mail:email, mdp: mdp};
        let json = JSON.stringify(inscrit);
        
        $('input').css("border-color", "");
        $("#error_login").css("display", "none");
        $("#error_inscription").css("display", "none");
        $("#empty_field").css("display", "none");

        $.ajax({
            url: '/',
            type: 'POST',
            data:{
                action: 'login',
                json: json
            },
            success: function(response){
                whoami = response;
	            $('[name=bienvenue] > span').text("");
	            $('[name=bienvenue] > span').append(whoami);
	            recupFonction();
                afficherListePays();
                voirInscrits();// rajouter
                //afficherInfoEtudiant(); // rajouter

            	// recupEtudiantMobilite();
                // chargerMobiliteExistante();  
                // afficherListePartenaires(); // rajouter
                // afficherListePays(); // rajouter
                // afficherInfoEtudiant(); // rajouter
                
                $("#email").val("");
                $("#mdp").val("");
            },
            error: function(error){
            	let bool = false;
            	if($("#email").val()===""){
                    $("#email").css("border-color", "red");
                    bool = true;
                }
            	if($("#mdp").val()===""){
                    $("#mdp").css("border-color", "red");
                    bool = true;
                }
            	if(bool){
            		$("#empty_field").css("display", "block");
            	}
            	$("#error_login").css("display", "block");
            },
        });
    });
});