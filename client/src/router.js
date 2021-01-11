import Vue from 'vue';
import Router from 'vue-router';
import Login from './views/Login.vue';
import Register from './views/Register.vue';

Vue.use(Router);

export const router = new Router({
    mode: 'history',
    routes: [
        {
            path: '/',
            name: 'home',
            component: () => import('./views/Targets.vue')
        },
        {
            path: '/home',
            component: () => import('./views/Targets.vue')
        },
        {
            path: '/login',
            component: Login
        },
        {
            path: '/register',
            component: Register
        },
        {
            path: '/profile',
            name: 'profile',
            // lazy-loaded
            component: () => import('./views/Profile.vue')
        },
        {
            path: '/target',
            name: 'target',
            component: () => import('./views/Target.vue')
        },
        {
            path: '/targets',
            name: 'targets',
            component: () => import('./views/Targets.vue')
        }
    ]
});

router.beforeEach((to, from, next) => {
    const publicPages = ['/login', '/register', '/home'];
    const authRequired = !publicPages.includes(to.path);
    const loggedIn = localStorage.getItem('user');

    // trying to access a restricted page + not logged in
    // redirect to login page
    console.log('trying to access to ' , to, from, loggedIn);
    if (authRequired && !loggedIn) {
        next('/login');
    } else {
        next();
    }
});