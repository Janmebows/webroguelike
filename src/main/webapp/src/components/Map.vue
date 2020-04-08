<template>
  <div class="container">
    <div class="row">
      <div class="form-group" >
        <h4>Start Game:</h4>
        <label for="connect"> </label>
        <button
          id="mapConnect"
          class="btn btn-primary"
          type="submit"
          :disabled="mapConnected == 1"
          @click.prevent="connect"
        >Play</button>
        <button
          id="disconnect"
          class="btn btn-primary"
          type="submit"
          :disabled="mapConnected == 0"
          @click.prevent="disconnect"
        >Quit Game</button>
      </div>
      <div class="col-md-12" style="">
        <div id="map" align="center"></div>
        <div class align="center">
          <div class="col-md-6">
          <button type="button" @click="go('w')" class="btn btn-outline-info">Up</button>
          </div>
          <button type="button" @click="go('a')" class="btn btn-outline-info">Left</button>
          <button type="button" @click="go('s')" class="btn btn-outline-info">Down</button>
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
      username: this.$parent.account.username
    };
  },
  components: {},
  methods: {
    go : function (input) {
      //move in direction
            var data = {
              // playerCharacter: this.playerCharacter,
              id: this.playerCharacter.id,
              nextInput: input
      };
      http
        .post("/input",data)
        .catch(e => {
          console.log("Post /game Error, " + e);
        });
    },
    retrieveMaps() {
      http
        .post("/game")
        .then(response => {
          this.maps = response.data;


          const ROWS = 20;
          const COLS = 40;
          var map = document.getElementById("map");
          var table = document.createElement("table");

          for (var i = 0; i < ROWS; i++) {
            var tr = document.createElement("tr");
            for (var j = 0; j < COLS; j++) {
              var td = document.createElement("td");
              // var cellText = document.createTextNode(this.maps[i][j]);
              // td.appendChild(cellText);
              td.innerHTML = this.map[j][i];
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
          const COLS = 40;
            this.map = JSON.parse(tick.body);
            var map = document.getElementById("map");
            var table = document.createElement("table");

          for (var i = 0; i < ROWS; i++) {
            var tr = document.createElement("tr");
            for (var j = 0; j < COLS; j++) {
                var td = document.createElement("td");
                // var cellText = document.createTextNode(this.map[i][j]);
                // td.appendChild(cellText);
              td.innerHTML = this.map[j][i];
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
       var data = {
              // playerCharacter: this.playerCharacter,
             // id: this.playerCharacter == null ? 0 : this.playerCharacter.id,
             //  characterName: this.username
            id: this.$parent.account.id
              
            };
          console.log(data);
      http
      .post("/joinGame",data)
        .then(response => {
          console.log(response.data);
           this.$parent.account = response.data;
           this.playerCharacter = response.data.playerCharacter;
        })

        .catch(e => {
          console.log("Post /joinGame Error, " + e);
        });
    },
    disconnect() {
      //       var data = {
      //   playerCharacter: this.$parent.account.playerCharacter
      // };
          // console.log(data);

      http
      .post("/leaveGame",this.$parent.account.playerCharacter)
      .catch(e => {
          console.log("Post /disconnect Error, " + e);
        });
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
    // Key event listeners
    // var gameApp = this;
    // window.addEventListener('keyup', function(e) {
    //    switch (e.keyCode) {
    //        case 37:
    //           // alert('Left key pressed');
    //           gameApp.go('a');
    //           break;
    //        case 38:
    //           // alert('Up key pressed');
    //           gameApp.go('w');
    //           break;
    //        case 39:
    //           // alert('Right key pressed');
    //           gameApp.go('d');
    //           break;
    //        case 40:
    //           // alert('Down key pressed');
    //           gameApp.go('s');
    //           break;
    //     }
    // });
  },
};
</script>
<style scoped="">
</style>
