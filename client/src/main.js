import Vue from 'vue';
import App from './App.vue';
import { router } from './router';
import store from './store';
import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import VeeValidate from 'vee-validate';
import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import {
    faHome,
    faUser,
    faUserPlus,
    faSignInAlt,
    faSignOutAlt
} from '@fortawesome/free-solid-svg-icons';
import VKAuth from '@dyadikov/vue-vk-oauth2';

library.add(faHome, faUser, faUserPlus, faSignInAlt, faSignOutAlt);

Vue.config.productionTip = false;

Vue.use(VeeValidate);
Vue.use(VKAuth, {apiId: 7662595,
    widgets: [{
        widget: 'ContactUs',
        selector: 'vk_contact_us',
        props: {
            text: 'Задайте свой вопрос'
        }
    },
        {
            widget: 'Auth',
            selector: 'vk_auth',
            props: {
                text: 'Войти через VK',
                display : 'popup',
                response_type : 'code'
            }
        }]
});
Vue.component('font-awesome-icon', FontAwesomeIcon);

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app');
