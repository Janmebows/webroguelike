<template>
  <div v-if="this.account">
    <h4>Account</h4>
    <div>
      <label>Username: </label> {{this.account.username}}
    </div>
    <div>
      <label>Password: </label> {{this.account.password}}
    </div>
  
    <span class="button is-small btn-danger" v-on:click="deleteAccount()">Delete</span>
  </div>
  <div v-else>
    <br/>
    <p>Please click on a Account...</p>
  </div>
</template>

<script>
import http from "../http-common";

export default {
  name: "account",
  props: ["account"],
  methods: {
    /* eslint-disable no-console */
    updateActive(status) {
      var data = {
        id: this.account.id,
        name: this.account.username,
        age: this.account.password,
        active: status
      };

      http
        .put("/account/" + this.account.id, data)
        .then(response => {
          this.account.active = response.data.active;
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
    },
    deleteAccount() {
      http
        .delete("/account/" + this.account.id)
        .then(response => {
          console.log(response.data);
          this.$emit("refreshData");
          this.$router.push('/');
        })
        .catch(e => {
          console.log(e);
        });
    }
    /* eslint-enable no-console */
  }
};
</script>