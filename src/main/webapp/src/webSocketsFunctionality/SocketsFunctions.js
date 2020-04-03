import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import * as $ from 'jquery';

var stompClient = null;

function setConnected(connected) {
    // Shows conversation if connected and hides if disconnected.
    // Also disables connect if connected and disconnect if disconnected.
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
}

function connect() {
    // connects to the socket and subscribes to a stream/channel (?)
    // Also determines what to do with data it finds in this channel.
    // Currently set up to receive things from '/topic/chat' (i.e it will take output from 
    // any controllers annotated with @SendTo('/topic/chat')).
    // This should be a messageOutput which it then calls showMessageOutput on.
    // We can subscribe to multiple streams in the one connection.
    // Will likely have a game stream and a chat stream.

    //input to SockJS is endpoint from WebSocketConfig
    var socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/chat', function (messageOutput) {
            showMessageOutput(JSON.parse(messageOutput.body));
        });
    });
}

function disconnect() {
    // disconnects from current stomp connection
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

$(function () {
    // This connects on the button connect. Its not great but the alternative 
    // (from Matt's) isn't working for some reason
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () { connect(); });
    $("#disconnect").click(function () { disconnect(); });
});

export function sendMessage() {

    var from = document.getElementById('from').value;
    var text = document.getElementById('text').value;
    stompClient.send("/app/chat", {}, JSON.stringify({ 'from': from, 'text': text }));
}

function showMessageOutput(messageOutput) {

    var response = document.getElementById('response');
    var p = document.createElement('p');
    p.style.wordWrap = 'break-word';
    p.appendChild(document.createTextNode(messageOutput.sender + ": " + messageOutput.text + " (" + messageOutput.time + ")"));
    response.appendChild(p);
}