<template>
  <div class="container">
    <div class="row">
      <div class="col-md-5" style="float:left">
        <Map />
      </div>
      <div class="col-md-7" style="float:right">
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
    return {};
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
</style>