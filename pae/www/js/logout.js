$(()=>{
	
	$('[name=disco-button-etudiant]').on('click', function(){
    	$.ajax({
            url: "/",
            type: 'POST',
            data:{
                action: 'logout'
            },

            success: function(response){
            	whoami = "";
                $('h3#bienvenue > span').append(whoami);
                loadPage('authentification');
                $('[name=etudiant-navbar]').hide();
                $("[name=prof-navbar]").hide();  
            },
            error: function(err){
                
            }
        });
    });
    
    $('[name=disco-button-prof]').on('click', function(){
    	$.ajax({
            url: "/",
            type: 'POST',
            data:{
                action: 'logout'
            },

            success: function(response){
                if(!msg_alerte(response)){
                    loadPage('authentification');
                    $('[name=etudiant-navbar]').hide();
                    $("[name=prof-navbar]").hide();  
                    whoami = "";  
                }
            },
            error: function(err){
                
            }
        });
    });
});