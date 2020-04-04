<template>
  <div class="submitform">
    <div v-if="!authenticated">
      <h4>Register</h4>

      <div class="form-group">
        <input
          type="text"
          placeholder="Username"
          class="form-control"
          id="username"
          required
          v-model="account.username"
          name="username"
        />
      </div>

      <div class="form-group">
        <input
          type="password"
          placeholder="Password"
          class="form-control"
          id="password"
          required
          v-model="account.password"
          name="password"
        />
      </div>

      <button @click="saveAccount" class="btn btn-success">Register</button>
      <br/>
      <p>{{error}}</p>
    </div>

    <div v-else>
      <h4>You registered in successfully!</h4>
      <router-link class="btn btn-success" to="/home">Continue</router-link>
      <!-- <button class="btn btn-success" to="/home">Continue</button> -->
    </div>
  </div>
</template>

<script>
import http from "../http-common";
//import {eventBus} from "../main";

export default {
  name: "add-account",
  data() {
    return {
      account: {
        id: 0,
        username: "",
        password: ""
      },
      authenticated: false,
      error: ""
    };
  },
  methods: {
    /* eslint-disable no-console */
    saveAccount() {
      var data = {
        username: this.account.username,
        password: this.account.password
      };
      http
        .post("/account", data)
        .then(response => {
          if (response.status === 200 && response.data != "") {
            this.account = response.data;
            console.log(response.data);
            this.$emit("auth");
            this.$emit("accountdata", response.data);
            this.authenticated = true;
 //           eventBus.$emit('responseData', response.data);
          } else {
            this.error = "Oops looks like the username is already taken or what you entered is invalid";
          }
        })
        .catch(e => {
          this.error = "Oops something went wrong! Please contact the admin.";
          console.log(e);
        });
    },
    newAccount() {
      this.authenticated = false;
      this.account = {};
    }
    /* eslint-enable no-console */
  }
};
</script>

<style>
.submitform {
  max-width: 300px;
  margin: auto;
}
.form-group {
  text-align: left;
}
</style>
