function login() {
    var user_name = $("#userName").val();
    var password = $("#userPassword").val();
    var masked = mask(user_name, password);
    $.ajax({
        type: "POST",
        url: "/amtica/session/login",
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        headers: {
                'Authorization': masked,
                'X-Forwarded-For': '10.1.2.3',
                'User-Agent': 'Firefox'
            },
        success: function (data) {

           alert(data);

        },
        error: function (e) {

            console.log(e);
        }
    });
}

function mask(userName, password) {
    var unmasked = userName + ":" + password;
    var masked = "";
    for(var i = 0 ; i < unmasked.length ; i++) {
    	var ascii = unmasked.charCodeAt(i);
    	ascii = ascii + masked.length;
    	masked = masked + String.fromCharCode(ascii);
    }
    return masked;
}

function redirectToRegistration() {
    window.location.replace("templates/registration.html");
}
