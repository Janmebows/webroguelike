<template>
  <div id="app" class="container-fluid">
    <nav class="navbar bg-secondary text-uppercase fixed-top" role="navigation">
      <a class="navbar-item" href="/">
        <strong class="is-size-4">
          <img
            class="TechBay Logo"
            src="./assets/logo.png"
            alt="TechBay Logo"
            style="width:120px;height:75px;"
          />
          Office Heroes RPG
        </strong>
      </a>
      <div></div>
      <div>
        <router-link class="btn btn-secondary" to="/game">Game</router-link>
        <router-link class="btn btn-secondary" to="/chat">Chat</router-link>
        <router-link class="btn btn-primary" to="/login">Login</router-link>
        <router-link class="btn btn-primary" to="/register">Register</router-link>
      </div>
    </nav>

    <br />
    <router-view @auth="authUser" @accountdata="saveData($event)" />
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
      }
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
      this.$router.push("/");
    }
  }
};
</script>

<style>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s;
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
