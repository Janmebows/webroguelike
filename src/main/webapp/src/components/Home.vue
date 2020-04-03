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

export default {
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
