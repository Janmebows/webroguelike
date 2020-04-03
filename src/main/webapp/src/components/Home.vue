<template>
  <div class="list row">
    <div class="col-md-6">
      <h4>Game Map</h4>
      <div id="map"></div>
    </div>
    <div class="col-md-6">
      <router-view @refreshData="refreshList"></router-view>
    </div>
  </div>
</template>

<script>
import http from "../http-common";

export default {
  name: "maps-list",
  data() {
    return {
      maps: []
    };
  },
  methods: {
    retrieveMaps() {
      http
        .post("/home")
        .then(response => {
          this.maps = response.data; // JSON are parsed automatically.
          console.log(this.maps);

          const ROWS = 20;
          const COLS = 20;
          var map = document.getElementById("map");
          var table = document.createElement(
            "table"
          ); /*Create `table` element*/
          for (var i = 0; i < ROWS; i++) {
            var tr = document.createElement("tr"); /*Create `tr` element*/
            for (var j = 0; j < COLS; j++) {
              var td = document.createElement("td"); /*Create `td` element*/
              var cellText = document.createTextNode(
                this.maps[i][j]
              ); /*Create text for `td` element*/
              td.appendChild(cellText); /*Append text to `td` element*/
              tr.appendChild(td); /*Append `td` to `tr` element*/
            }
            table.appendChild(tr); /*Append `tr` to `table` element*/
          }
          map.appendChild(table);
        })
        .catch(e => {
          console.log("Post /home Error" + e);
        });
    }
    /* eslint-enable no-console */
  },
  mounted() {
    this.retrieveMaps();
  }
};
</script>

<style>
.list {
  text-align: left;
  max-width: 450px;
  margin: auto;
}
</style>
