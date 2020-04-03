<template>
    <div id="app" class="container-fluid">
      <Nav/>
        <!-- <div class="site-info">
            <h1>Office Heroes RPG</h1>
            <h3>Gamified remote office relationships</h3>
        </div> -->
        <br/>
        <router-view/>
        <h3>Session controls</h3>
        <div>
          <button v-on:click="createSession" class="btn btn-success">create Session</button>
          <button v-on:click="checkSession" class="btn btn-success">check Session</button>
          <button v-on:click="destroySession" class="btn btn-success">destroy Session</button>
         </div>
         <p>{{message}}</p>
    </div>
</template>
<script>
// navigitagion from https://auth0.com/blog/beginner-vuejs-tutorial-with-user-login/
import Nav from './components/navigation/Nav.vue';
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
