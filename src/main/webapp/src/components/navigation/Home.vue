<template>
  <div class="list row">
    <div>
      <h4>Game Map</h4>
      <div id="map"></div>
    </div>
  </div>
</template>

<script>
import http from "../http-common";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

export default {
  name: "home",
  components: {
    WebsocketGreetings
  },
  methods: {
    retrieveMaps() {
      http
        .post("/home")
        .then(response => {
          this.maps = response.data;

          const ROWS = 20;
          const COLS = 20;
          var map = document.getElementById("map");
          var table = document.createElement("table");

          for (var i = 0; i < ROWS; i++) {
            var tr = document.createElement("tr");
            for (var j = 0; j < COLS; j++) {
              var td = document.createElement("td");
              var cellText = document.createTextNode(this.maps[i][j]);
              td.appendChild(cellText);
              tr.appendChild(td);
            }
            table.appendChild(tr);
          }
          map.appendChild(table);
        })
        .catch(e => {
          console.log("Post /home Error, " + e);
        });
    },
    setConnected(connected) {
      // Shows conversation if connected and hides if disconnected.
      // Also disables connect if connected and disconnect if disconnected.
      $("#connect").prop("disabled", connected);
      $("#disconnect").prop("disabled", !connected);
      if (connected) {
        $("#conversation").show();
      } else {
        $("#conversation").hide();
      }
    },

    connect() {
      // connects to the socket and subscribes to a stream/channel (?)
      // Also determines what to do with data it finds in this channel.
      // Currently set up to receive things from '/topic/chat' (i.e it will take output from
      // any controllers annotated with @SendTo('/topic/chat')).
      // This should be a messageOutput which it then calls showMessageOutput on.
      // We can subscribe to multiple streams in the one connection.
      // Will likely have a game stream and a chat stream.

      //input to SockJS is endpoint from WebSocketConfig
      var stompClient = null;
      var socket = new SockJS("/chat");
      stompClient = Stomp.over(socket);
      stompClient.connect({}, function(frame) {
        setConnected(true);
        console.log("Connected: " + frame);
        stompClient.subscribe("/topic/chat", function(messageOutput) {
          showMessageOutput(JSON.parse(messageOutput.body));
        });
      });
    },

    disconnect() {
      // disconnects from current stomp connection
      var stompClient = null;
      if (stompClient !== null) {
        stompClient.disconnect();
      }
      setConnected(false);
      console.log("Disconnected");
    },

    sendMessage() {
      var stompClient = null;
      var from = document.getElementById("from").value;
      var text = document.getElementById("text").value;
      stompClient.send(
        "/app/chat",
        {},
        JSON.stringify({ from: from, text: text })
      );
    },

    showMessageOutput(messageOutput) {
      var response = document.getElementById("response");
      var p = document.createElement("p");
      p.style.wordWrap = "break-word";
      p.appendChild(
        document.createTextNode(
          messageOutput.sender +
            ": " +
            messageOutput.text +
            " (" +
            messageOutput.time +
            ")"
        )
      );
      response.appendChild(p);
    }
  },
  mounted() {
    this.retrieveMaps();
  }
};
</script>
