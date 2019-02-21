import 'material-design-icons/iconfont/material-icons.css';
import 'materialize-css/dist/css/materialize.css';

import Vue from 'vue';
import App from './App.vue';
import router from './router';

Vue.config.productionTip = false;

new Vue({
  router,
  render: h => h(App)
}).$mount('#app');
