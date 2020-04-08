<template>
  <div class="container">
    <div class="row">
      <div class="col-md-12 col-lg-6">
        <span v-if="!saved">
          <!-- // EDIT ACCOUNT -->
          <h1>Edit a Account</h1>
          <div>
            <label>Account id:</label>
            {{this.$parent.account.id}}
          </div>
          <div>
            <label>Username:</label>
            {{this.$parent.account.username}}
          </div>
          <div class="row">
            <div class="form-group col-md-12 col-lg-6">
              <label
                class=""
                for="password"
              >Edit Password:</label>
              <input
                type="password"
                placeholder="New Password"
                class="form-control"
                id="password"
                v-model="account.password"
                name="password"
              />
              <button
                @click="saveData"
                class="btn btn-success"
              >Save</button>
            </div>
          </div>
          <hr />
          <!-- // EDIT Character -->
          <div>
            <div class="form-group">
              <label>Alive:</label>
              {{this.$parent.account.playerCharacter.alive}}
            </div>
            <div class="form-group">
              <label>Attack:</label>
              {{this.$parent.account.playerCharacter.attack}}
            </div>
            <div class="form-group">
              <label>Character Name:</label>
              {{this.$parent.account.playerCharacter.characterName}}
            </div>
            <div class="form-group">
              <label>Blue:</label>
              {{this.$parent.account.playerCharacter.colorblue}}
            </div>
            <div class="form-group">
              <label>Green:</label>
              {{this.$parent.account.playerCharacter.colorgreen}}
            </div>
            <div class="form-group">
              <label>Blue:</label>
              {{this.$parent.account.playerCharacter.colorred}}
            </div>
            <div class="form-group">
              <label>Current HP:</label>
              {{this.$parent.account.playerCharacter.currentHP}}
            </div>
            <div class="form-group">
              <label>exp:</label>
              {{this.$parent.account.playerCharacter.exp}}
            </div>
            <div class="form-group">
              <label>Character id:</label>
              {{this.$parent.account.playerCharacter.id}}
            </div>
            <div class="form-group">
              <label>Level:</label>
              {{this.$parent.account.playerCharacter.level}}
            </div>
            <div class="form-group">
              <label>Map:</label>
              {{this.$parent.account.playerCharacter.map}}
            </div>
            <div class="form-group">
              <label>Map:</label>
              {{this.$parent.account.playerCharacter.map}}
            </div>
          </div>
        </span>
        <span v-else>
          <h4>You saved in successfully!</h4>
          <router-link
            class="btn btn-success"
            to="/home"
          >Play game</router-link>
          <button
            class="btn btn-success"
            to="/edit-account"
          >Edit more</button>
        </span>

      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "app",
  components: {
    // Register
  },
  data() {
    return {
      saved: false,
      account: {
        id: this.$parent.account.id,
        username: this.$parent.account.username,
        password: ""
      }
    };
  },
  methods: {
    authUser() {
      this.authenticated = true;
    },
    saveData(accountData) {
      console.log(accountData);
      this.account = accountData;
    },
    logout() {
      this.$session.destroy();
      this.accountData = {};
      this.authenticated = false;
      this.$router.push("/");
    }
  },
  mounted() {
    
  }
};
</script>

<style>
#id {
  width: 50% !important;
}
</style>