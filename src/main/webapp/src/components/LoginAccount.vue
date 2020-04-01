<template>
  <div class="loginform">
    <h4>Login</h4>

    <div class="form-group">
      <input type="text" class="form-control" id="user" required v-model="account.username" name="username">
      <input type="password" class="form-control" id="pass" required v-model="account.password" name="password">
        
    </div>
    <div class="btn-group">
      <button v-on:click="loginUser" class="btn btn-success">Login</button>
    </div>

  <div v-if="this.account">
    It worked.
  </div>
    
  </div>
</template>

<script>
import http from "../http-common";

export default {
  name: "login-account",
  data() {
    return {
      account: Object
    };
  },
  methods: {
    /* eslint-disable no-console */
    loginUser() {
      http
        .post("/login")
        .then(response => {
          this.account = response.data; // JSON are parsed automatically.
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
    }
    /* eslint-enable no-console */
  }
};
</script>

<style>
.loginform {
  max-width: 300px;
  margin: auto;
}
</style>