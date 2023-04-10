let stompClient = null;
let selectedUser;
let url = 'http://localhost:8800';

// Receiving messages from the private chatroom
function connectToPrivateChat(userName) {
    const socket = new SockJS(url + '/websocket/');
    stompClient = Stomp.over(socket); // Using stomp over socket
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/user/' + userName, function (message) { // Subscribe to the private chatroom with unique user
            let messages = JSON.parse(message.body);
            console.log(messages);
            render(messages.message, messages.receiver);
        });
    });
}

// Sending messages to the private chatroom
function sendPrivateMessage(sender, message) {
    stompClient.send("/toWhom/websocket/" + selectedUser + {} + JSON.stringify({
        sender: sender,
        message: message
    }));
}

function registration() {
    let userName = document.getElementById("userName").value;
    $.get(url + "/registration/" + userName, function (message) {
        connectToPrivateChat(userName); // On Successful Registration connect to the private chatroom
    }).fail(function (error) {
        if (error.status == 400) {
            alert("Some Thing Wrong with The server. Will be fixed Soon! or Probably You are already registered")
        }
    })
}
function selectUser(userName) {
    console.log(userName);
    selectedUser = userName;
    $('#selectedUserId').html(''); // Clear the previous selected user name from the div with id selectedUserId
    $('#selectedUserId').append("Chatting with " + userName);
}

function fetchAll() {
    $.get(url + "/fetchAll", function (response) {
        console.log(response);
        let users = response;
        let userTemplateHtml = '';
        for (let i = 0; i < users.length; i++) {
            userTemplateHtml += '<a href="#" onclick="selectUser(\''+ users[i]+ '\')"><li class="clearfix">\n' +
                '                <div class="about">\n' +
                '                    <div id="userNameAppender_' + users[i] + '" class="name">' + users[i] + '</div>\n' +
                '                    <div class="status">\n' +
                '                        <i class="fa fa-circle offline"></i>\n' +
                '                    </div>\n' +
                '                </div>\n' +
                '            </li></a>';
        }
        $('#userList').html(userTemplateHtml);
    });
}

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#send").click(function () {
        sendName();
    });
});