<template>
  <div id="app">
    <div id="nav">
      <router-link to="/">Blog</router-link> |
      <a :href="blogServiceBaseUrl">Blog REST API</a><!-- |
      <router-link to="/admin">Admin</router-link>-->
    </div>
    <router-view/>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        blogServiceBaseUrl: process.env.VUE_APP_BLOG_SERVICE_BASE_URL,
      }
    },
  }
</script>
