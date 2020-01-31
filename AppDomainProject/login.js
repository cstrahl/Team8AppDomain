

function validate(){
    var username = document.getElementById("username").value;
    var user = "evanyott";
    var admin = "admin";
    var password = document.getElementById("password").value;
    var userPassword = "user123";
    var adminPassword = "admin123";
    var attempts = 3;

    if (username === user && password === userPassword) {
        window.location.href = 'Dashboard.html';
    }
    else if (username === admin && password === adminPassword) {
        window.location.href = 'Dashboard-Admin.html';
    }
    else if (username === ""){
        alert("Please enter a valid username.")
    }
    else if (username !== "" && password === ""){
        alert("Please enter your password.")
    }
    else if (username === admin && password !== adminPassword){
        attempts --;
        alert("Incorrect password. You have " + attempts + " attempts remaining.")
        return false;
    }
    else if (username === user && password !== userPassword){
        attempts --;
        alert("Incorrect password. You have " + attempts + " attempts remaining.")
        return false;
    }
    else {

    }
    return false;
}


