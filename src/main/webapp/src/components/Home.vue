<template>
  <div class="list row">
    <div class="col-md-6">
      <h4>Game Map</h4>
      <ul>
        <li v-for="(map, index) in maps" :key="index">
          <router-link
            :to="{
                            name: 'map-details',
                            params: { map: map, id: map.id }
                        }"
          >{{map.mapName}}</router-link>
        </li>
      </ul>
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
    /* eslint-disable no-console */
    retrieveMaps() {
      http
        .post("/home")
        .then(response => {
          this.maps = response.data; // JSON are parsed automatically.
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
    },
    refreshList() {
      this.retrieveMaps();
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
