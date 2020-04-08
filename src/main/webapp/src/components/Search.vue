<template>
  <div class="container">
    <div class="jumbotron">
      <h4>Search</h4>
      <form>
        <label for="searchInput">Search for player name:</label>
        <input
          type="text"
          placeholder="Find by Name"
          class="form-control"
          id="name"
          v-model="searchFilters.name"
          name="name"
        />

        <label for="levelDirection">Level must be:</label>
        <div class="row">
          <div class="col-md-3">
            <select class="form-control" id="levelDirection">
              <option>greaterThan</option>
              <option>lessThan</option>
            </select>
          </div>
          <div class="form-group col">
            <input
              type="number"
              placeholder="Find by Level"
              class="form-control"
              id="level"
              v-model="searchFilters.level"
              name="level"
            />
          </div>
        </div>

        <label for="killDirection">Kills must be:</label>
        <div class="row">
          <div class="col-md-3">
            <select class="form-control" id="killDirection">
              <option>greaterThan</option>
              <option>lessThan</option>
            </select>
          </div>
          <div class="form-group col">
            <input
              type="number"
              placeholder="Find by Kills"
              class="form-control"
              id="killCount"
              v-model="searchFilters.killCount"
              name="killCount"
            />
          </div>
        </div>
        <button @click.prevent="retrievePlayers" class="btn btn-secondary">Search</button>
      </form>
    </div>
    <div class="col-md-6">
      <h4>Players List</h4>
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
    <div class="col-md-6">
      <router-view @refreshData="refreshList"></router-view>
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
