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
      <div v-if="!processing">
      <button @click="saveAccount" class="btn btn-primary">Register</button>
      <hr style="opacity: 0" />
      </div>
      <div class="progress" v-else >
          <div class="progress-bar progress-bar-striped progress-bar-animated" 
          role="progressbar" aria-valuenow="100" 
          aria-valuemin="0" aria-valuemax="100" style="width: 100%"></div>
      </div>
      <p class="text-warning" >{{warning}}</p>
       <p class="text-danger" >{{error}}</p>
    </div>

    <div v-else>
      <h4>You registered in successfully!</h4>
      <router-link class="btn btn-primary" to="/game">Continue</router-link>
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
      processing: false,
      authenticated: false,
      warning: "",
      error: "",
    };
  },
  methods: {
    /* eslint-disable no-console */
    saveAccount() {
      this.error = "";
      this.warning = "";
      this.processing = true;
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
            this.processing = false;
            this.warning =
              "Oops looks like the username is already taken or what you entered is invalid";
          }
        })
        .catch(e => {
        this.processing = false;
          this.error = "Oops something went wrong! Please contact the admin.";
          console.log(e);
        });
    },
    newAccount() {
      this.processing = false;
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
