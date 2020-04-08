import Vue from "vue";
import Router from "vue-router";
import Register from "./components/Register.vue";
import LoginAccount from "./components/LoginAccount.vue";
import Game from "./components/Game.vue";
// import EditAccount from "./components/EditAccount.vue";
import Chat from "./components/Chat.vue";
import Search from "./components/Search.vue";

Vue.use(Router);

// All restful controllers need components connected here
export default new Router({
  mode: "history",
  routes: [
    {
      path: '/',
      redirect: '/login',
    },

    {
      path: "/register",
      name: "register",
      component: Register
    },
    {
      path: "/login",
      name: "login",
      component: LoginAccount
    },
    // {
    //   path: "/editaccount",
    //   name: "editaccount",
    //   component: EditAccount
    // },
    {

      path: "/game",
      name: "map-details",
      component: Game
    },
    {
      path: "/map",
      name: "map",
      component: Map
    },
    {
      path: "/search",
      name: "search",
      component: Search
    },
    {
      path: "/chat",
      name: "chat",
      component: Chat
    }
  ]
});