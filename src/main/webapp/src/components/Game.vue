<template>
  <div class="container">
    <div class="row">
      <div class="col-sm">
        <h2>Game</h2>
      </div>
    </div>
    <div class="row jumbotron padding-3">
      <div class="col-sm">
          <h4>Goal</h4>
          <p>Squash the bugs!</p>
      </div>
      <div class="col-sm">
          <h4>Objectives</h4>
          <p>Squash the bugs</p>
      </div>

      </div>
      <hr/>
    <div class="row">
      <div class="col-md-12" style="float:left">
        <Map />
      </div>
      </div>
      <div class="row ">
      <div class="col-md-12" style="float:right">
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
    };
  },
  components: {
    Chat,
    Map
  },
  methods: {},
  mounted() {}
};
</script>
<style scoped="">
.row {
  padding-bottom: 25px;
}
.padding-3 {
  padding: 3em;
}
</style>