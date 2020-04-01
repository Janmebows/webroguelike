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
    
  </div>
</template>

<script>
import http from "../http-common";

export default {
  name: "login-account",
  data() {
    return {
      account: {
        id: 0,
        username: "",
        password: ""
      },
    };
  },
  methods: {
    /* eslint-disable no-console */
    loginUser() {
      var data = {
        username: this.account.username,
        password: this.account.password
      }
      http
        .post("/login")
        .then(response => {
          if (response.data == null) {
            console.log("its null!");
            console.log(response.data);
          } else {
             console.log("its an account!");
             this.account = response.data; // JSON are parsed automatically.

          }
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