<template>
    <div id="app" class="container-fluid">
      <Nav/>
        <!-- <div class="site-info">
            <h1>Office Heroes RPG</h1>
            <h3>Gamified remote office relationships</h3>
        </div> -->
        <br/>
        <router-view/>
         <button v-on:click="checkSession" class="btn btn-success">checkSession</button>
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
    checkSession() {
      console.log(this.$session.exists());
      this.message = this.$session.getAll();
    }
  },
};
</script>

<style>
/* .site-info {
  color: blue;
  margin-bottom: 20px;
} */
.container-fluid {
  text-align: center;
}
</style>
