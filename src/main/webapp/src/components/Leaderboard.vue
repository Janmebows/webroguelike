<template>
  <div class="container">
    <div class="jumbotron">
      <h4>Top Players:</h4>
      <ul>
        <li v-for="(player, index) in players" :key="index">
          {{
          "Name: " + player.characterName
          }}
          <br />
          {{
          "Level: " + player.level
          }}
          <br />
          {{
          "Kills: " + player.killCount
          }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import http from "../http-common";

export default {
  name: "search",
  data() {
    return {
      searchFilters: {
        name: "",
        level: 0,
        killCount: 0,
        levelDirection: "greaterThan",
        killDirection: "greaterThan"
      },
      players: []
    };
  },
  methods: {
    retrievePlayers() {
      var data = {
        name: this.searchFilters.name,
        level: this.searchFilters.level,
        killCount: this.searchFilters.killCount,
        levelDirection: this.searchFilters.levelDirection,
        killDirection: this.searchFilters.killDirection
      };
      http
        .post("/search", data)
        .then(response => {
          this.players = response.data;
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
    },
    refreshList() {
      this.retrievePlayers();
    }
  },
  mounted() {
    this.retrievePlayers();
    if (!this.$parent.authenticated) {
      this.$router.push("/register");
    }
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
