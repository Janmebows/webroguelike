<template>
  <div class="container">
    <div class="row">
      <div class="form-group" align>
        <label for="connect">Start Game:</label>
        <button
          id="mapConnect"
          class="btn btn-outline-info"
          type="submit"
          :disabled="mapConnected == 1"
          @click.prevent="connect"
        >Play</button>
        <button
          id="disconnect"
          class="btn btn-default"
          type="submit"
          :disabled="mapConnected == 0"
          @click.prevent="disconnect"
        >Close Connection</button>
      </div>
      <div class="col-md-5" style="float:left">
        <div id="map" align="center"></div>
        <div class align="center">
          <button type="button" @click="go('w')" class="btn btn-outline-info">Up</button>
          <button type="button" @click="go('s')" class="btn btn-outline-info">Down</button>
          <button type="button" @click="go('a')" class="btn btn-outline-info">Left</button>
          <button type="button" @click="go('d')" class="btn btn-outline-info">Right</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import http from "../http-common";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

export default {
  name: "game",
  data() {
    return {
      received_map: 0,
      mapConnected: 0,
      playerCharacter: this.$parent.account.playerCharacter,
    };
  },
  components: {},
  methods: {
    go : function (input) {
      console.log(input);
      // DO THE THING HERE
    },
    retrieveMaps() {
      http
        .post("/game")
        .then(response => {
          this.maps = response.data;
          console.log("retrieve maps " + this.maps);

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
          this.recieved_map = map;
        })
        .catch(e => {
          console.log("Post /game Error, " + e);
        });
    },

    input() {
      if (this.stompClient && this.stompClient.connected) {
        var from = document.getElementById("from").value;
        var text = document.getElementById("text").value;
        this.stompClient.send(
          "/app/game",
          JSON.stringify({ from: from, text: text }),
          {}
        );
      }
    },
    connect() {
      this.socket = new SockJS("http://localhost:4201/game");
      this.stompClient = Stomp.over(this.socket);

      this.stompClient.connect(
        {},
        frame => {
          this.mapConnected = 1;
          console.log("Connect frame " + frame);

          this.stompClient.subscribe("/topic/game", tick => {
            const ROWS = 20;
            const COLS = 20;
            this.map = JSON.parse(tick.body);
            var map = document.getElementById("map");
            var table = document.createElement("table");

            for (var i = 0; i < ROWS; i++) {
              var tr = document.createElement("tr");
              for (var j = 0; j < COLS; j++) {
                var td = document.createElement("td");
                var cellText = document.createTextNode(this.map[i][j]);
                td.appendChild(cellText);
                tr.appendChild(td);
              }
              table.appendChild(tr);
            }
            if (map.firstChild != null) {
              map.removeChild(map.childNodes[0]);
            }
            map.appendChild(table);
          });
        },
        error => {
          console.log(error);
          this.mapConnected = 0;
        }
      );
    },
    disconnect() {
      if (this.stompClient) {
        this.stompClient.disconnect();
      }
      this.mapConnected = 0;
    },

    tickleConnection() {
      this.mapConnected ? this.disconnect() : this.mapConnect();
    }
  },

  mounted() {
    this.retrieveMaps();
  }
};
</script>
<style scoped="">
</style>
