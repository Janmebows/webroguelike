<template>
  <div class="col-md-6">
    <form class="form-inline my-2 my-lg-0">
      <input
        class="form-control mr-sm-2"
        type="text"
        placeholder="Find Players"
        id="searchInput"
        name="searchInput"
        v-model="PlayerCharacter.charName"
      />
      <button v-on:click="retrievePlayers" class="btn btn-secondary">Search</button>
    </form>
    <div class="col-md-6">
      <h4>Players List</h4>
      <ul>
        <li v-for="(player, index) in players" :key="index">
          <router-link
            :to="{
                            name: 'players',
                            params: { player: player, id: player.id }
                        }"
          >{{player.name}}</router-link>
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
      PlayerCharacter: {
        characterName: "",
        characterSymbol: "",
        level: 0
      },
      players: []
    };
  },
  methods: {
    /* eslint-disable no-console */
    retrievePlayers() {
      var charName = this.PlayerCharacter.characterName;
      var charSymbol = this.PlayerCharacter.characterSymbol;
      var charLevel = this.PlayerCharacter.level;

      http
        .post("/search", charName, charSymbol, charLevel)
        .then(response => {
          console.log(this.data);
          this.players = response.data; // JSON are parsed automatically.
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
    },
    refreshList() {
      this.retrievePlayers();
    }
    /* eslint-enable no-console */
  },
  mounted() {
    this.retrievePlayers();
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

