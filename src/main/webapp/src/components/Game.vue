<template>
<div class="container">
  <div>
    <h2>Game</h2>
    </div>
  <div class="row">
    <div class="col-md-5" style="float:left">
      <div id="map" align="center"></div>
      <div class="" align="center">
      <button type="button" class="btn btn-outline-info">Up</button>
      <button type="button" class="btn btn-outline-info">Down</button>
      <button type="button" class="btn btn-outline-info">Left</button>
      <button type="button" class="btn btn-outline-info">Right</button>
      </div>
    </div>
     <div class="col-md-7" style="float:right">
      <Chat />
    </div>
  </div>
</div>

<!-- <body bgcolor="#ff0000">
  <div>
   
  </div>
</body> -->
</template>

<script>
import http from "../http-common";
import Chat from "./Chat.vue";

export default {
  name: "home",
  components: {
    Chat
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
    }
  },
  mounted() {
    this.retrieveMaps();
  }
};
</script>
