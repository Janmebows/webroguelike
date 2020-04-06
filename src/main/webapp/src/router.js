import Vue from "vue";
import Router from "vue-router";
import CustomersList from "./components/CustomersList.vue";
import AddCustomer from "./components/AddCustomer.vue";
import Register from "./components/Register.vue";
import LoginAccount from "./components/LoginAccount.vue";
import Customer from "./components/Customer.vue";
import Game from "./components/Game.vue";
import EditAccount from "./components/EditAccount.vue";
import Chat from "./components/Chat.vue";

Vue.use(Router);

// All restful controllers need components connected here
export default new Router({
  mode: "history",
  routes: [
    {
      path: "/customers",
      name: "customers",
      alias: "/customer",
      component: CustomersList,
      children: [
        {
          path: "/customer/:id",
          name: "customer-details",
          component: Customer,
          props: true
        }
      ]
    },
    {
      path: "/add",
      name: "add",
      component: AddCustomer
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
    {
      path: "/editaccount",
      name: "editaccount",
      component: EditAccount
    },
    {

      path: "/game",
      name: "map-details",
      component: Game
    },
    {
      path: "/chat",
      name: "chat",
      component: Chat
    }
  ]
});