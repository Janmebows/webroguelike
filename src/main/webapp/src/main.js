import Vue from "vue";
import App from "./App.vue";
import router from './router';
import VueSession from 'vue-session';

Vue.use(VueSession, { persist: true });
Vue.config.productionTip = false;

// Home page, 
new Vue({
  router,
  render: h => h(App)
}).$mount("#app");

// new vue objects go here.