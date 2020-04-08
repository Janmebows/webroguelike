<template>
  <div class="submitform">
  
      <h4>Login</h4>

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
      <button v-on:click="loginAccount" class="btn btn-primary">Login</button>
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
      authenticated: false,
      processing: false,
      message: "",
      error: "",
      warning: "",
    };
  },
  methods: {
    /* eslint-disable no-console */
    loginAccount() {
      this.error = "";
      this.warning = "";
      this.processing = true;
      var data = {
        username: this.account.username,
        password: this.account.password
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
            this.$router.push("/game");
          } else {
            this.processing = false;
            this.error =
              "Oops looks like the you entered invalid info, please try again.";
            this.authenticated = false;
          }
        })
        .catch(e => {
          this.processing = false;
          this.error = "Oops something went wrong! Please contact the admin.";
          console.log(e);
        });
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
/* Vue Fade animations */
.text-fade-enter-active,
.text-fade-leave-active {
    transition: opacity .2s ease
}

.text-fade-enter,
.text-fade-leave-to {
  opacity: 0
}
</style>
