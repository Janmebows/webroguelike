<template>
  <div id="app" class="">
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary" role="navigation">
      <div class="container">
      <!-- Hidden on mobile -->

      <a class="navbar-item" href="/">
        <strong class="is-size-4">
          <img
            class="TechBay Logo"
            src="./assets/logo.png"
            alt="TechBay Logo"
            style="width:120px;height:75px;"
          />
        </strong>
      </a>
    <a class="navbar-brand" href="/">Office Heroes RPG</a>

    
    <div v-show="navbarShow" class="navbar-collapse" id="navbarColor01" style="">
        <ul class="navbar-nav mr-auto flex-right" v-if="authenticated">
          <li class="nav-item">
            <!--Use this for showing the current <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a> -->
          <router-link class="nav-link" to="/game">Game</router-link>
          </li>
          <li class="nav-item">
          <router-link class="nav-link" to="/chat">Chat</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/editaccount">Edit Account</router-link>
          </li>
          <li class="nav-item">
            <router-link @click.native="logout" class="nav-link" to="/">Logout</router-link>
          </li>
        </ul>
        <ul class="navbar-nav mr-auto flex-right" v-else>
          <li class="nav-item">
            <router-link class="nav-link" to="/login">Login</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/register">Register</router-link>
          </li>
        </ul>
      </div>
      <button @click="toggleNav" class="navbar-toggler collapsed" type="button" data-toggle="collapse" data-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      </div>
    </nav>
    <br />
    <transition name="fade" mode="out-in">
      <router-view @auth="authUser" @accountdata="saveData($event)" />
    </transition>
  </div>
</template>
<script>
export default {
  name: "app",
  components: {},
  data() {
    return {
      authenticated: false,
      account: {
        id: 0,
        username: "",
        password: ""
      },
      navbarShow: false,
    };
  },
  methods: {
    toggleNav() {
      this.navbarShow = !this.navbarShow;
    },
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
      this.$router.push("/");
    }
  }
};
</script>

<style>
.flex-right {
    flex: auto;
    justify-content: flex-end;
}
/* Vue Fade animations */
.fade-enter-active,
.fade-leave-active {
    transition: opacity .3s ease
}
.fade-enter,
.fade-leave-to {
  opacity: 0
}
</style>
