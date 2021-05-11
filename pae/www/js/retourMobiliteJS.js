$(function(){
    let etudiants={};
    $.ajax({
        url:"/",
        type:'POST',
        data:{action:'getEtudiantsSignature'},
        success: function(json){
            console.log(json);
           etudiants=JSON.parse(json);
        },
        error: function(e){
            console.log(e.message);
        }
    })
})

function remplirSelect(tableau){
    let taille=tableau.length;
    for(let i=0;i<taille;i++){
        $('#select_etudiants').append('<option>'+''+'<option>');
    }
}