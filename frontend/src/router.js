import Vue from 'vue';
import Router from 'vue-router';
import Blog from './views/Blog.vue';
import PostView from "./views/blog/PostView";

Vue.use(Router);

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'blog',
      component: Blog
    },
    {
      path: '/posts/:title',
      name: 'postView',
      component: PostView
    },
    {
      path: '/admin',
      name: 'admin',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import(/* webpackChunkName: "about" */ './views/Admin.vue')
    },
  ],
});
