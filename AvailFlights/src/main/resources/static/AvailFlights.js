function isChecked(clickedCheckboxId){
    var oneway = document.getElementById("business");
    var roundtrip = document.getElementById("economy");
    if(clickedCheckboxId === "business"){
        roundtrip.checked = false;
    }
    else if(clickedCheckboxId === "business2"){
        document.getElementById("economy2").checked = false;
    }
    else if(clickedCheckboxId === "economy2"){
        document.getElementById("business2").checked = false;
    }
    else{
        oneway.checked = false;
    }
}
function StartAnimation(){
    var object = document.getElementById("planeicon");
    object.classList.add("animate");
}