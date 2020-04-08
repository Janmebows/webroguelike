<template>
  <div class="container">
    <div class="row">
      <div class="col-sm">
        <h2>Game</h2>
      </div>
    </div>
    <div class="row jumbotron padding-3">
      <div class="col-sm">
        <h4>Objectives</h4>
        <p>Squash the bugs</p>
      </div>
      <div class="col-sm">
        <h4>The Bugs</h4>
        <p>
          (
          <span class="enemy">#</span> ) Hash 100xp
        </p>
        <p>
          (
          <span class="enemyLow">¢</span> ) 5 cents 10xp
        </p>
        <p>
          (
          <span class="enemyStrong">+</span> ) Tough 100xp
        </p>
        <p>
          (
          <span class="enemyHigh">$</span> ) Gold 1000xp
        </p>
      </div>
    </div>
    <hr />
    <div class="row">
      <div class="col-md-12" style>
        <Map />
      </div>
    </div>
    <div class="row">
      <div class="col-md-12" style>
        <Chat />
      </div>
    </div>
  </div>
</template>

<script>
import Chat from "./Chat.vue";
import Map from "./Map.vue";

export default {
  name: "Game",
  data() {
    return {
      account: this.$parent.account,
      authenticated: this.$parent.authenticated
    };
  },
  components: {
    Chat,
    Map
  },
  methods: {},
  mounted() {
    if (!this.authenticated) {
      this.$router.push("/login");
    }
  }
};
</script>
<style scoped="">
.row {
  padding-bottom: 25px;
}
.padding-3 {
  padding: 3em;
}
.enemy {
  color: rgb(255, 255, 255);
}
.enemyLow {
  color: rgb(255, 102, 40);
}
.enemyStrong {
  color: rgb(255, 0, 0);
}
.enemyHigh {
  color: rgb(255, 255, 0);
}
</style>