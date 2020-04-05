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
        <br/>
        <p>{{error}}</p>
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
      },
      authenticated: false,
      message:"",
      error: "",
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
        
          if (response.status === 200 && response.data != "") {
            this.account = response.data; // JSON automagically reads data
            console.log(response.data);
            this.$emit("auth");
            this.$emit("accountdata", response.data);
            this.authenticated = true;
            this.$router.push('/home');
          }
          else {
            this.error = "Oops looks like the you entered invalid info, please try again.";
            this.authenticated = false;
          }
        })
        .catch(e => {
          this.error = "Oops something went wrong! Please contact the admin.";
          console.log(e);
        });
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
