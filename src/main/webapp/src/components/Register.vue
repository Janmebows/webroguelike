<template>
  <div class="submitform">
    <div v-if="!submitted">
          <h4>Register</h4>

        <div class="form-group">
          <input type="text" placeholder="Username" class="form-control" id="username" required v-model="account.username" name="username">
        </div>
    
        <div class="form-group">
          <input type="password" placeholder="Password" class="form-control" id="password" required v-model="account.password" name="password">
        </div>
    
        <button v-on:click="saveAccount" class="btn btn-success">Register</button>
    </div>
    
    <div v-else>
      <h4>You submitted successfully!</h4>
      <button class="btn btn-success" v-on:click="newAccount">Continue</button>
    </div>
  </div>
</template>

<script>
import http from "../http-common";

export default {
  name: "add-account",
  data() {
    return {
      account: {
        id: 0,
        username: "",
        password: "",
        active: false
      },
      submitted: false
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
          this.account.id = response.data.id;
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });

      this.submitted = true;
    },
    newAccount() {
      this.submitted = false;
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
</style>
