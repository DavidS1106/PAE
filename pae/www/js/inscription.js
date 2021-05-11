$(()=>{

	$("#inscription").click(function(){
        let nom=$("#nom").val();
        let prenom=$("#prenom").val();
        let pseudo=$("#pseudo").val();
        let email=$("#email_inscription").val();
        let mdp=$("#password").val();
        
        let inscrit = {nom:nom, prenom:prenom, pseudo:pseudo, mail:email, mdp: mdp};
        let json = JSON.stringify(inscrit);
        
        $('input').css("border-color", "");
        $("#error_inscription").css("display", "none");
        $("#empty_field").css("display", "none");
        $("#error_login").css("display", "none");
        $('#error_email_inscription').hide();
        
        $.ajax({
            url: '/',
            type: 'POST',
            data:{
                action: 'inscription',
                nom: nom,
                prenom: prenom,
                pseudo: pseudo,
                email: email,
                mdp: mdp,
                json: json
            },
            success: function(response){
                if(response === 'ok_inscription'){
                    loadPage('authentification');
                    $("#nom").val("");
                    $("#prenom").val("");
                    $("#pseudo").val("");
                    $("#email_inscription").val("");
                    $("#password").val("");
                    $('ok_inscription').css('background-color', 'green');
                    $('ok_inscription').show();
                }
            },
            error: function(error){
            	let msg = error.responseText;
            	if(msg === "error_email"){
            		$("#email_inscription").css("border-color", "red");
                	$('#error_email_inscription').css('display', 'block');
            	}
            	let bool = false;
            	if($("#nom").val()===""){
            		$("#nom").css("border-color", "red");
            		bool = true;
            	}
            	if($("#prenom").val()===""){
            		$("#prenom").css("border-color", "red");
            		bool = true;
            	}
            	if($("#pseudo").val()===""){
            		$("#pseudo").css("border-color", "red");
            		bool = true;
            	}
            	if($("#email_inscription").val()===""){
            		$("#email_inscription").css("border-color", "red");
            		bool = true;
            	}
            	
            	if($("#password").val()===""){
            		$("#password").css("border-color", "red");
            		bool = true;
                }
            	if(bool){
            		$("#empty_field").css("display", "block");
            	}
            	$("#error_inscription").css("display", "block");
            	
            	
            }
        });
    });
});