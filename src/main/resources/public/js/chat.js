
window.onload = function() {
    document.querySelector('#chat-input').addEventListener('keypress', function (e) {
        var key = e.which || e.keyCode;
        if (key === 13) {
            let chatinput = document.getElementById('chat-input').value ;
            let username = document.getElementById('username').value ;
            if (chatinput.trim() !== ""){
                if (username.trim() === ""){
                    username = "defaultUser";
                }
                webSocket.send("{\"username\": \""+username+"\", \"message\": \""+chatinput+"\"}")
            }
            document.getElementById('chat-input').value = "";
        }
    });
};

var webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/chat");

webSocket.onmessage = function (msg) {
    let data = JSON.parse(msg.data);
    let message = "<p><u>"+data.username+"</u> : "+data.message+"</p>";
    document.getElementById('chat').insertAdjacentHTML("beforeend", message);
    document.getElementById("chat").scrollTop = document.getElementById("chat").scrollHeight;

};
