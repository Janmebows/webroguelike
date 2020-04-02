<template>
    <div id="app" class="container-fluid">
        <div class="site-info">
            <h1>Office Heroes RPG</h1>
            <h3>Gamified remote office relationships</h3>
        </div>
        <nav>
            <router-link class="btn btn-primary" to="/home">Home</router-link>
            <router-link class="btn btn-primary" to="/Customers">Customers</router-link>
            <router-link class="btn btn-primary" to="/add">Add</router-link>
            <router-link class="btn btn-primary" to="/login">Login</router-link>
            <router-link class="btn btn-primary" to="/register">Register</router-link>

        </nav>
        <br/>
        <router-view/>
    </div>
</template>

<script>
export default {
  name: "app"
};
</script>

<style>
.site-info {
  color: blue;
  margin-bottom: 20px;
}

.btn-primary {
  margin-right: 5px;
}

.container-fluid {
  text-align: center;
}
</style>
