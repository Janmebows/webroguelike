<template>
  <div id="app" class="container-fluid">
    <nav class="navbar container" role="navigation">
      <a class="navbar-item" href="/">
        <strong class="is-size-4">Office Heroes RPG</strong>
      </a>
       <transition name="fade">
      <div v-if="authenticated">
        <router-link class="btn btn-secondary" to="/home">Home</router-link>
        <!-- <router-link class="btn btn-secondary" to="/Customers">Customers</router-link>
        <router-link class="btn btn-secondary" to="/add">Add</router-link> -->
        <router-link class="btn btn-secondary" to="/edit-account">Edit Character</router-link>
        <button v-on:click="logout" class="btn btn-secondary">Logout</button>
      </div>
      <div v-else>
        <router-link class="btn btn-primary" to="/login">Login</router-link>
        <router-link class="btn btn-primary" to="/Register">Register</router-link>
      </div>
      </transition>
    </nav>

    <!-- <div class="site-info">
            <h1>Office Heroes RPG</h1>
            <h3>Gamified remote office relationships</h3>
    </div>-->
    <br />





<!-- <<<<<<< HEAD -->
    <router-view @auth="authUser" @accountdata="saveData($event)"/>

    <!-- <h3 class="">Session controls</h3>
    <div class="">
      <button v-on:click="authUser" class="btn btn-success">create Session</button>
      <button v-on:click="checkSession" class="btn btn-success">check Session</button>
      <button v-on:click="logout" class="btn btn-success">destroy Session</button>
    </div>
    <p>{{message}}</p> -->
  </div>
</template>
<script>
// navigitagion from https://auth0.com/blog/beginner-vuejs-tutorial-with-user-login/
//import Register from "./components/Register.vue";
export default {
  name: "app",
  components: {
   // Register

// =======
   
//     <router-view />
//   </div>

// </template>
// <script>
// // navigation from https://auth0.com/blog/beginner-vuejs-tutorial-with-user-login/
// import Nav from "./components/navigation/Nav.vue";
// export default {
//   name: "app",
//   components: {
//     Nav
// >>>>>>> VueJSVer2






  },
  data() {
    return {
      authenticated: false, 
      account: {
        id: 0,
        username: "",
        password: ""
      },
    };
  },
  methods: {
    authUser() {
      this.authenticated = true;
      },
    saveData(accountData) {
      console.log(accountData);
      this.account = accountData;
      },
    logout() {
      this.accountData = {};
      this.account = {};
      this.authenticated = false;
      this.$router.push('/');
    }
  }
};
</script>

<style>
/* .site-info {
  color: blue;
  margin-bottom: 20px;
} */
.fade-enter-active, .fade-leave-active {
  transition: opacity .5s;
}
nav {
  margin-bottom: 2em;
  background: lightgray;
}

nav > a {
  font-weight: bold;
  color: #2c3e50;
}

a.router-link-exact-active {
  color: #d88d00;
}

.btn-primary {
  margin-right: 15px;
}

.btn-secondary {
  margin-right: 15px;
}
.btn-success {
  margin-right: 1em;
}
.container-fluid {
  text-align: center;
}
.hidden {
  display: none;
}
</style>
