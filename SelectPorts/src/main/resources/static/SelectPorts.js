function isChecked(clickedCheckboxId){
    var oneway = document.getElementById("oneway");
    var roundtrip = document.getElementById("roundtrip");
    var label = document.getElementById("returnlabel");
    var dropdown = document.getElementById("retdate");
    if(clickedCheckboxId === "oneway"){
        roundtrip.checked = false;
        label.style.visibility = "hidden";
        dropdown.style.visibility = "hidden";
    }
    else{
        oneway.checked = false;
        label.style.visibility = "visible";
        dropdown.style.visibility = "visible";
    }
}
function isPortSame(){
    var departure = document.getElementById("Departure");
    var arrival = document.getElementById("Arrival");
    if(departure.value === arrival.value){
        alert("Departure Port and Arrival Port can not be the same");
    }
}
function StartAnimation(){
    var object = document.getElementById("planeicon");
    object.classList.add("animate");
}

const depDateInput = document.getElementById("depdate");
const retDateInput = document.getElementById("retdate");

retDateInput.addEventListener("change", function() {
    const depDate = new Date(depDateInput.value);
    const retDate = new Date(retDateInput.value);

    if (retDate < depDate) {
        alert("Return date cannot be earlier than departure date.");
        retDateInput.value = depDateInput.value; // Reset return date to departure date
    }
});
