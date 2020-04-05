<template>
  <div id="app" class="container">
    <Nav />
    <!-- <div class="site-info">
            <h1>Office Heroes RPG</h1>
            <h3>Gamified remote office relationships</h3>
    </div>-->
    <br />
    <router-view />
  </div>

</template>
<script>
// navigation from https://auth0.com/blog/beginner-vuejs-tutorial-with-user-login/
import Nav from "./components/navigation/Nav.vue";
export default {
  name: "app",
  components: {
    Nav
  },
  data() {
    return {
      message:"",
    };
  },
  methods: {
    createSession() {
      console.log(this.$session.exists());
      this.$session.start();
      this.message = this.$session.getAll();
    },
    checkSession() {
      console.log(this.$session.exists());
      this.message = this.$session.getAll();
    },
    destroySession() {
      this.$session.destroy();
      console.log(this.$session.exists());
      this.message = this.$session.getAll();
    },
  },
};
</script>

<style>
/* .site-info {
  color: blue;
  margin-bottom: 20px;
} */
.btn-success{
  margin-right: 1em;
}
.container-fluid {
  text-align: center;
}
</style>
