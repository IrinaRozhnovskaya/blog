<template>
  <div class="home">
    <!--<img alt="Vue logo" src="../assets/logo.png">-->
    <h1>Welcome to my {{ appName }}!</h1>
    <br/>
    <ul v-for="post in posts">
      <p class="right"><router-link :to="{ name: 'postView', params: { title: post.title }}">open this posts</router-link></p>
      <Post :post="post"/>
    </ul>
  </div>
</template>

<script>
  import Post from './blog/Post';

  export default {
    name: 'Blog',
    data() {
      return {
        blogServiceBaseUrl: process.env.VUE_APP_BLOG_SERVICE_BASE_URL,
        appName: 'Blog',
        posts: [
          {
            title: 'There are no posts available at the moment',
            body: 'So I have got nothing to tell you, my friend...',
          },
        ],
      };
    },
    components: {
      Post,
    },
    created() {
      this.fetchData();
    },
    methods: {
      fetchData() {
        fetch(`${this.blogServiceBaseUrl}/api/v1/posts/find-all`)
          .then(data => data.json())
          .then(posts => {
            if (posts.length)
              this.posts = posts;
          })
      },
    },
  };
</script>

<style>
  ul {
    list-style: none;
  }
</style>
