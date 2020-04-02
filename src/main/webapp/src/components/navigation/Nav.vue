<template>
  <nav class="navbar container" role="navigation">
    <a class="navbar-item" href="/">
      <strong class="is-size-4">Office Heroes RPG</strong>
    </a>
    <div v-if="this.$session.exists()">
      <router-link class="btn btn-secondary" to="/home">Home</router-link>
      <router-link class="btn btn-secondary" to="/Customers">Customers</router-link>
      <router-link class="btn btn-secondary" to="/add">Add</router-link>
      <router-link class="btn btn-secondary" to="/edit-account">Edit Account</router-link>
    </div>
    <div v-else>
      <router-link class="btn btn-primary" to="/login">Login</router-link>
      <router-link class="btn btn-primary" to="/register">Register</router-link>
    </div>
  </nav>
</template>
<script>
export default {
  name: "Nav"
};
</script>
<style>
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
</style>