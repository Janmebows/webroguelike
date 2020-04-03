<template>
  <div class="submitform">
    <div v-if="!authenticated">
          <h4>Login</h4>

        <div class="form-group">
          <input type="text" placeholder="Username" class="form-control" id="username" required v-model="account.username" name="username">
        </div>
    
        <div class="form-group">
          <input type="password" placeholder="Password" class="form-control" id="password" required v-model="account.password" name="password">
        </div>
    
        <button v-on:click="loginAccount" class="btn btn-success">Login</button>

    </div>
    <div v-else>
      <h4>You Logged in successfully!</h4>
      <p>{{account.username}}</p>
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
        password: "",
        active: false,
        playerCharacter: 0,
      },
      authenticated: false,
      message:"",
    };
  },
  methods: {
    /* eslint-disable no-console */
    loginAccount() {
      var data = {
        username: this.account.username,
        password: this.account.password,
        
      };
      http
        .post("/login", data)
        .then(response => {
          // https://www.npmjs.com/package/vue-session
          console.log(response);
          if (response.status === 200 && 'token' in response.body) {
            this.authenticated = true;
            this.account = response.data; // JSON automagically reads data
            console.log(response.data);
          }
          else {

            this.authenticated = false;
          }
        })
        .catch(e => {
          console.log(e);
        });

      
    },
    logoutAccount() {
      this.authenticated = false;
      this.account = {};
    },
    /* eslint-enable no-console */
  }
};
</script>

<style>
.submitform {
  max-width: 300px;
  margin: auto;
}
</style>
