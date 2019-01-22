<template>
  <div class="home">
    <!--<img alt="Vue logo" src="../assets/logo.png">-->
    <h1>Welcome to my {{ appName }}!</h1>
    <ul v-for="post in posts">
      <Post :post="post" />
    </ul>
  </div>
</template>

<script>
  import Post from './blog/Post';
  export default {
    name: 'Blog',
    data() {
      return {
        appName: 'Blog',
        posts: [],
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
/*
        fetch('http://127.0.0.1:8080/blog/api/posts/all')
          .then(domain => domain.json())
          .then(json => JSON.parse(json))
          .then(posts => this.posts = posts)
*/
        this.posts = [
          {
            title: 'ololo',
            body: 'trololo',
          },
          {
            title: 'prived',
            body: 'medved',
          },
        ];
      }
    }
  };
</script>

<style>
  ul {
    list-style: none;
  }
</style>
